package com.example.kitten
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.kitten.ApiImageResponse.ApiImagenResponse
import com.example.kitten.ApiResponse.ApiResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_home.*
import com.squareup.picasso.Picasso


class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        buttonSearch.setOnClickListener{
            verifyAndConnect(editTextBreed.text.toString())
        }

    }

    private fun verifyAndConnect(id: String = "3542519"){
        if(Network.verifyConnection(this)){
            httpVolley(getUrlApi(id))
        }else{
            Toast.makeText(this,"No tienes conexion a internet",Toast.LENGTH_SHORT).show()
        }
    }

    private fun httpVolley(url: String){
        val queue = Volley.newRequestQueue(this)

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                Log.d("HTTPVolley",  response)
                Toast.makeText(this, "Conexión establecida", Toast.LENGTH_LONG).show()
                jsonToObject(response)
            },
            Response.ErrorListener {
                Log.d("HTTPVolley", "Error en la URL $url")
                Toast.makeText(this, "¡Ha ocurrido un error en la conexión!", Toast.LENGTH_SHORT).show()
            })
        queue.add(stringRequest)
    }

    private fun getUrlApi (name: String): String{
        return "https://api.thecatapi.com/v1/images/search?breed_id=$name"
    }

    private fun jsonToObject(response: String){
        val gson = Gson()
        val apiResponse = gson.fromJson(response,ApiResponse::class.java)

        textViewNameDisplay.text = apiResponse.name.capitalize()
        textViewDescriptionDisplay.text=apiResponse.description.toString()
        textViewTemperamentDisplay.text=apiResponse.temperament.toString()
        textViewLifeSpanDisplay.text=apiResponse.lifeSpan.toString()
        textViewOriginDisplay.text=apiResponse.origin.toString()

        Toast.makeText(this, "Datos obtenidos correctamente", Toast.LENGTH_SHORT).show()
    }
}


