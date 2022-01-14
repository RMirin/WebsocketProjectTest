package com.websocket.project.ui.candle

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.tradingview.lightweightcharts.api.interfaces.ChartApi
import com.tradingview.lightweightcharts.api.interfaces.SeriesApi
import com.tradingview.lightweightcharts.api.options.models.*
import com.tradingview.lightweightcharts.api.series.common.SeriesData
import com.tradingview.lightweightcharts.api.series.enums.CrosshairMode
import com.tradingview.lightweightcharts.api.series.models.PriceScaleId
import com.tradingview.lightweightcharts.view.ChartsView
import com.websocket.project.R
import com.websocket.project.databinding.FragmentCandleBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job

@AndroidEntryPoint
class CandleFragment : Fragment() {

    private val viewModel: CandleViewModel by viewModels()
    lateinit var binding: FragmentCandleBinding
    private val args: CandleFragmentArgs by navArgs()

    private var series: MutableList<SeriesApi> = mutableListOf()
    private var realtimeDataJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_candle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCandleBinding.bind(view)

        with(binding) {
            candleCurrencyTextView.text = args.pairName
        }
        viewModel.getCandle(args.pairName)
        observeViewModelData()
        subscribeOnChartReady(binding.candleChartsView)
        applyChartOptions()
    }

    private fun subscribeOnChartReady(view: ChartsView) {
        view.subscribeOnChartStateChange { state ->
            when (state) {
                is ChartsView.State.Preparing -> Unit
                is ChartsView.State.Ready -> {
                    Toast.makeText(context, "Chart is ready", Toast.LENGTH_SHORT).show()
                }
                is ChartsView.State.Error -> {
                    Toast.makeText(context, state.exception.localizedMessage, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun observeViewModelData() {
        viewModel.candleSnapshot.observe(viewLifecycleOwner, { data ->
            createSeriesWithData(data, PriceScaleId.RIGHT, binding.candleChartsView.api) { series ->
                this.series.clear()
                this.series.add(series)

                viewModel.candleUpdate.observe(viewLifecycleOwner, { data ->
                    realtimeDataJob = lifecycleScope.launchWhenResumed {
                        Log.d("TAG", "mapCandleToBarDataFragment: ${data.time.date}")
                        series.update(data)
                    }
                })
            }
        })
    }

    private fun applyChartOptions() {
        binding.candleChartsView.api.applyOptions {
            crosshair = crosshairOptions {
                mode = CrosshairMode.NORMAL
            }
            timeScale = timeScaleOptions {
                timeVisible = true
            }
        }
    }

    private fun createSeriesWithData(
        data: List<SeriesData>,
        priceScale: PriceScaleId,
        chartApi: ChartApi,
        onSeriesCreated: (SeriesApi) -> Unit
    ) {
        chartApi.addCandlestickSeries(
            options = CandlestickSeriesOptions(),
            onSeriesCreated = { api ->
                api.setData(data)
                onSeriesCreated(api)
            }
        )
    }
}