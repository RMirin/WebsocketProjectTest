package com.websocket.project.ui.candle

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.tradingview.lightweightcharts.api.chart.models.color.IntColor
import com.tradingview.lightweightcharts.api.chart.models.color.toIntColor
import com.tradingview.lightweightcharts.api.interfaces.ChartApi
import com.tradingview.lightweightcharts.api.interfaces.SeriesApi
import com.tradingview.lightweightcharts.api.options.models.*
import com.tradingview.lightweightcharts.api.series.common.SeriesData
import com.tradingview.lightweightcharts.api.series.enums.LineStyle
import com.tradingview.lightweightcharts.api.series.enums.PriceLineSource
import com.tradingview.lightweightcharts.api.series.models.PriceScaleId
import com.tradingview.lightweightcharts.view.ChartsView
import com.websocket.project.databinding.FragmentCandleBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import com.websocket.project.R


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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCandleBinding.bind(view)

        with(binding) {
            candleCurrencyTextView.text = args.pairName
        }
        observeViewModelData()
        subscribeOnChartReady(binding.candleChartsView)
        applyChartOptions()
    }

    override fun onResume() {
        super.onResume()
        viewModel.subscribeCandle(args.pairName)
    }

    override fun onPause() {
        super.onPause()
        viewModel.unsubscribeCandle(args.pairName)
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

    @RequiresApi(Build.VERSION_CODES.M)
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

    @RequiresApi(Build.VERSION_CODES.M)
    private fun applyChartOptions() {
        binding.candleChartsView.api.applyOptions {
            grid = gridOptions {
                horzLines = gridLineOptions {
                    color = getColor(R.color.candle_border_color)
                }
                vertLines = gridLineOptions {
                    color = getColor(R.color.candle_border_color)
                }
            }
            layout = layoutOptions {
                textColor = getColor(R.color.candle_font_color)
            }
            timeScale = timeScaleOptions {
                timeVisible = true
                secondsVisible = true
                borderColor = getColor(R.color.candle_border_color)
            }
            rightPriceScale = priceScaleOptions {
                borderColor = getColor(R.color.candle_border_color)
                scaleMargins = priceScaleMargins {
                    top = 0.95f
                    bottom = 0.95f
                }
                invertScale = true
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun createSeriesWithData(
        data: List<SeriesData>,
        priceScale: PriceScaleId,
        chartApi: ChartApi,
        onSeriesCreated: (SeriesApi) -> Unit
    ) {
        chartApi.addCandlestickSeries(

            options = candlestickSeriesOptions {
                priceLineSource = PriceLineSource.LAST_VISIBLE
                priceLineStyle = LineStyle.SOLID
                priceLineVisible = true
                baseLineColor = getColor(R.color.candle_border_color)
                borderUpColor = getColor(R.color.candle_up_color)
                borderDownColor = getColor(R.color.candle_down_color)
                upColor = getColor(R.color.candle_up_color)
                downColor = getColor(R.color.candle_down_color)
                wickUpColor = getColor(R.color.candle_up_color)
                wickDownColor = getColor(R.color.candle_down_color)
            },
            onSeriesCreated = { api ->
                api.setData(data)
                onSeriesCreated(api)
            }
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getColor(colorId: Int): IntColor {
        return requireActivity().getColor(colorId).toIntColor()
    }
}