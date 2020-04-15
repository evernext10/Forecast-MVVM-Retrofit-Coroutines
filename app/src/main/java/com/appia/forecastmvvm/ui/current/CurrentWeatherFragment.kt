package com.appia.forecastmvvm.ui.current

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.appia.forecastmvvm.R
import com.appia.forecastmvvm.data.ApixuWeatherApiService
import kotlinx.android.synthetic.main.fragment_current.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CurrentWeatherFragment : Fragment() {

    private lateinit var dashboardViewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_current, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dashboardViewModel = ViewModelProviders.of(this).get(CurrentWeatherViewModel::class.java)
        val apiService = ApixuWeatherApiService()

        GlobalScope.launch(Dispatchers.Main) {
            val currentWeatherResponse = apiService.getTCurrentWeather("Bogota").await()
            textView.text = currentWeatherResponse.currentWeatherEntry.toString()
        }
    }
}
