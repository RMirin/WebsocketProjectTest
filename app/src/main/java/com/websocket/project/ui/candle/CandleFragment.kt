package com.websocket.project.ui.candle

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.tradingview.lightweightcharts.api.chart.models.color.toIntColor
import com.tradingview.lightweightcharts.api.interfaces.ChartApi
import com.tradingview.lightweightcharts.api.interfaces.SeriesApi
import com.tradingview.lightweightcharts.api.options.models.*
import com.tradingview.lightweightcharts.api.series.common.SeriesData
import com.tradingview.lightweightcharts.api.series.enums.CrosshairMode
import com.tradingview.lightweightcharts.api.series.models.BarData
import com.tradingview.lightweightcharts.api.series.models.PriceScaleId
import com.tradingview.lightweightcharts.view.ChartsView
import com.websocket.project.R
import com.websocket.project.databinding.FragmentCandleBinding
import com.websocket.project.ui.main.CryptoPairFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CandleFragment : Fragment() {

    private val viewModel: CandleViewModel by viewModels()
    lateinit var binding: FragmentCandleBinding
    private val args: CandleFragmentArgs by navArgs()

    private var series: MutableList<SeriesApi> = mutableListOf()

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
        subscribeOnChartReady(binding.chartsView)
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
        viewModel.candle.observe(viewLifecycleOwner, { data ->
            Log.d("TAG", "observeViewModelData: $data")
            createSeriesWithData(data, PriceScaleId.RIGHT, binding.chartsView.api) { series ->
                this.series.clear()
                this.series.add(series)
            }
        })
    }

    private fun applyChartOptions() {
        binding.chartsView.api.applyOptions {
            handleScale = handleScaleOptions {
                kineticScroll = kineticScrollOptions {
                    touch = false
                    mouse = false
                }
            }
            layout = layoutOptions {
                backgroundColor = Color.WHITE.toIntColor()
                textColor = Color.argb(255, 33, 56, 77).toIntColor()
            }
            crosshair = crosshairOptions {
                mode = CrosshairMode.NORMAL
            }
            rightPriceScale = priceScaleOptions {
                borderColor = Color.argb(255, 197, 203, 206).toIntColor()
            }
            timeScale = timeScaleOptions {
                borderColor = Color.argb(255, 197, 203, 206).toIntColor()
                fixRightEdge = true
                minBarSpacing = 0.7f
            }
        }
    }

    private fun createSeriesWithData(
        data: List<BarData>,
        priceScale: PriceScaleId,
        chartApi: ChartApi,
        onSeriesCreated: (SeriesApi) -> Unit
    ) {
        chartApi.addBarSeries(
            options = BarSeriesOptions(
                priceScaleId = priceScale,
                thinBars = true,
                downColor = Color.BLACK.toIntColor(),
                upColor = Color.BLACK.toIntColor(),
            ),
            onSeriesCreated = { api ->
                api.setData(data)
                onSeriesCreated(api)
            }
        )
    }
}