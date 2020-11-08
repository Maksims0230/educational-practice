package com.example.weatherpractice

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WeatherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WeatherFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var api: API = API()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        var str: String = ""
        var City = "Moscow"
        api.URL = City
        val request = Request.Builder().url(api.URL).build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) { Log.e("Error", e.toString()) }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                api.str = response!!.body()!!.string()
            }
        })
        var z = api

        while (str == "")
            str = z.str

        api.Weather.description = ((JSONObject(str).getJSONArray("weather").getJSONObject(0)).get("description")).toString()
        api.Weather.visibility = (JSONObject(str).get("visibility")).toString() + " м"
        api.Weather.main.temp =  (JSONObject(str).getJSONObject("main").get("temp")).toString() + " °c"
        api.Weather.main.feels_like =  (JSONObject(str).getJSONObject("main").get("feels_like")).toString() + " °c"
        api.Weather.main.temp_max =  (JSONObject(str).getJSONObject("main").get("temp_max")).toString() + " °c"
        api.Weather.main.temp_min =  (JSONObject(str).getJSONObject("main").get("temp_min")).toString() + " °c"
        api.Weather.main.pressure =  (JSONObject(str).getJSONObject("main").get("pressure")).toString()
        api.Weather.main.humidity =  (JSONObject(str).getJSONObject("main").get("humidity")).toString()
        api.Weather.wind.speed = (JSONObject(str).getJSONObject("wind").get("speed")).toString() + " м/сек"
        api.Weather.wind.deg = (JSONObject(str).getJSONObject("wind").get("deg")).toString() + " °F"

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v: View = inflater.inflate(R.layout.fragment_weather, container, false)
        var description = v?.findViewById<TextView>(R.id.description)
        var visibility = v?.findViewById<TextView>(R.id.visibility)
        var temp = v?.findViewById<TextView>(R.id.temp)
        var feels_like = v?.findViewById<TextView>(R.id.feels_like)
        var temp_max = v?.findViewById<TextView>(R.id.temp_max)
        var temp_min = v?.findViewById<TextView>(R.id.temp_min)
        var humidity = v?.findViewById<TextView>(R.id.humidity)
        var pressure = v?.findViewById<TextView>(R.id.pressure)
        var speed = v?.findViewById<TextView>(R.id.speed)
        var deg = v?.findViewById<TextView>(R.id.deg)

        description?.text =  api.Weather.description
        visibility?.text = api.Weather.visibility
        temp?.text = api.Weather.main.temp
        feels_like?.text = api.Weather.main.feels_like
        temp_max?.text = api.Weather.main.temp_max
        temp_min?.text = api.Weather.main.temp_min
        humidity?.text = api.Weather.main.humidity
        pressure?.text = api.Weather.main.pressure
        speed?.text = api.Weather.wind.speed
        deg?.text = api.Weather.wind.deg

        // Inflate the layout for this fragment
        return v
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WeatherFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WeatherFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
