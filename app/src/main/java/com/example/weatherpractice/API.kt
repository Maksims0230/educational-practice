package com.example.weatherpractice
import android.os.Parcelable
import android.util.Log
import com.squareup.picasso.BuildConfig.*
import com.squareup.picasso.Picasso
import kotlinx.android.parcel.Parcelize
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.lang.reflect.Field
import retrofit2.Call
import retrofit2.http.GET



class API {
    private val API_KEY = BuildConfig.API_KEY

    var URL: String = ""
        get() = field
        set(value) {
            field = ("https://api.openweathermap.org/data/2.5/weather?q=$value&appid=$API_KEY&lang=ru&units=metric")
        }

    var Weather: weather = weather()

    var str: String = ""

}

class weather {
    var main: main = main()
    var description: String = ""
    var visibility: String = ""
    var wind: wind = wind()
}

class main {
    var temp: String = ""
    var feels_like: String = ""
    var temp_min: String = ""
    var temp_max: String = ""
    var pressure: String = ""
    var humidity: String = ""
}

class wind {
    var speed: String = ""
    var deg: String = ""
}