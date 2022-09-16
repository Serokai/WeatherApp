package fr.aymber.weatherapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    companion object{
        const val API_KEY = "2b199543bcb0db0c3cfc1ea5883cd734"
    }

    @GET("?units=metric&appid=$API_KEY")
    fun getWeatherByCity(@Query("q") city: String): Call<WeatherResult>
}