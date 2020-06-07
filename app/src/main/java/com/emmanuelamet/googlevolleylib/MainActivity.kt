package com.emmanuelamet.googlevolleylib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    var volleyRequest: RequestQueue? = null
    val stringUrl = "https://www.dictionary.com/browse/awe"
    var stringLink = "https://corona.lmao.ninja/v2/countries"
    var linkJsonObject = "https://corona.lmao.ninja/v2/countries"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        volleyRequest = Volley.newRequestQueue(this)

        //getString(stringUrl)
        //getJsonArray(stringLink)
        getJsonObject(linkJsonObject)
    }


    fun getJsonObject(Url: String){
        val jsonObjectReq = JsonObjectRequest(Request.Method.GET, Url,
        Response.Listener {
            response: JSONObject -> try {
            val metaDataObject = response.getJSONObject("metadata")
            var title = metaDataObject.getString("title")
            Log.i("Title: ", title)

            val featureArray = response.getJSONArray("name")
            for(i in 0..featureArray.length() -1){
                //Get the porperrty obj
                val propertyObject = featureArray.getJSONObject(i).getJSONObject("properties")
                var place = propertyObject.getString("place")
                Log.i("Feature Array Object: ", place)

            }
            var feature = response.getString("type")
            Log.i("Type: ", feature)
            }  catch (e: JSONException){
            e.printStackTrace()
        }
        },
            Response.ErrorListener {
                    error: VolleyError? ->
                try {
                    Log.d("Error: ", error.toString())
                }catch (e: JSONException){
                    e.printStackTrace()
                }
            })
        volleyRequest!!.add(jsonObjectReq)
    }


    fun getJsonArray(Url: String){
        val jsonRequest = JsonArrayRequest(Request.Method.GET, Url,
        Response.Listener {
            response: JSONArray ->  try {
                Log.d("Response: ", response.toString())
            for(i in 0..response.length() - 1){
                val movieObj = response.getJSONObject(i)
                var showCountry = movieObj.getString("country")
                Log.i("Resonse Country: ", showCountry)
            }
            }catch (e: JSONException){
            e.printStackTrace()
        }
        },
            Response.ErrorListener {
                    error: VolleyError? ->
                try {
                    Log.d("Error: ", error.toString())
                }catch (e: JSONException){
                    e.printStackTrace()
                }
            })

        volleyRequest!!.add(jsonRequest)
    }

    fun getString(Url: String){
        val stringReq = StringRequest(Request.Method.GET, Url,
        Response.Listener { response: String? ->
            try {
                Log.d("Respond: ", response)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        },
            Response.ErrorListener {
                error: VolleyError? ->
                try {
                    Log.d("Error: ", error.toString())
                }catch (e: JSONException){
                    e.printStackTrace()
                }
            })
            volleyRequest!!.add(stringReq)

    }
}
