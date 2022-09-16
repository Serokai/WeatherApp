
package fr.aymber.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var editCity: EditText
    lateinit var btnSearch: Button
    lateinit var imageWeather: ImageView
    lateinit var tvTemperature: TextView
    lateinit var tvCity: TextView
    lateinit var layoutWeather: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editCity = findViewById(R.id.editCity)
        btnSearch = findViewById(R.id.btnSearch)
        imageWeather = findViewById(R.id.imageWeather)
        tvCity = findViewById(R.id.tvCity)
        tvTemperature = findViewById(R.id.tvTemperature)
        layoutWeather = findViewById(R.id.layoutWeather)

        btnSearch.setOnClickListener{
            val city = editCity.text.toString()
            if(city.isEmpty()) {
                Toast.makeText(this, "Please provide a valid city.", Toast.LENGTH_SHORT).show()
            } else {
                getWeatherByCity(city)
            }
        }
    }

    fun getWeatherByCity(city: String) {
        // Create RetroFit Instance
        val retrofit = Retrofit.Builder().
        baseUrl("https://api.openweathermap.org/data/2.5/weather/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Call WeatherAPI
        val weatherService = retrofit.create(WeatherService::class.java)
        val result = weatherService.getWeatherByCity(city)

        result.enqueue(object : Callback<WeatherResult> {
            override fun onResponse(call: Call<WeatherResult>, response: Response<WeatherResult>) {
                if(response.isSuccessful) {
                    val result = response.body()

                    tvTemperature.text = "${result?.main?.temp}°C"
                    tvCity.text = result?.name
                    Picasso.get().load("https://openweathermap.org/img/w/${result?.weather?.get(0)?.icon}.png").into(imageWeather)

                    layoutWeather.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<WeatherResult>, t: Throwable) {
                Toast.makeText(applicationContext, "Server Error", Toast.LENGTH_SHORT).show()
            }

        })
    }
}