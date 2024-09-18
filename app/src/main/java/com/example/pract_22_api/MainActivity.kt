package com.example.pract_22_api

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var city1 : EditText
    lateinit var textView1: TextView
    lateinit var textView2: TextView
    lateinit var textView3: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }



    fun getResult(view: View){
        city1=findViewById(R.id.sityy)
        if (city1.text.toString().isNotEmpty()&&city1.text.toString()!=null) {
            var key = "86f4460e2dad7e27402ccfa1c2db026a"
            var url="https://api.openweathermap.org/data/2.5/weather?q=${city1.text.toString()}&appid=${key.toString()}=metric&lang=ru"
            val queue = Volley.newRequestQueue(this)
            val stringRequest = StringRequest(
                Request.Method.GET,
                url,
                {
                        response ->
                    try {
                        val obj = JSONObject(response)
                        val temp = obj.getJSONObject("main")
                        Log.d("MyLog", "Response^ ${temp.getString("temp")}")
                        textView1 = findViewById(R.id.textVie1)
                        textView1.setText("Температура = ${temp.getString("temp")}")


                        val pressure = obj.getJSONObject("main")
                        textView2 = findViewById(R.id.textVie2)
                        textView2.setText("Давление = ${pressure.getString("pressure")}")

                        val speed = obj.getJSONObject("wind")
                        textView3 = findViewById(R.id.textVie3)
                        textView3.setText("Скорость ветра = ${speed.getString("speed")}")

                    }catch (e: Exception){
                        var sn= Snackbar.make(findViewById(android.R.id.content),"Ошибка получения данных", Snackbar.LENGTH_LONG)
                        sn.setActionTextColor(Color.RED)
                        sn.show()
                    }
                },
                {
                    Log.d("MyLog","Volley error: $it")
                }
            )
            queue.add(stringRequest)
        }
        else{
            var sn= Snackbar.make(findViewById(android.R.id.content),"Такого города нет", Snackbar.LENGTH_LONG)
            sn.setActionTextColor(Color.RED)
            sn.show()
        }
    }
}