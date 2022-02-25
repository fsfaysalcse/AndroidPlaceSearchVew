package com.faysal.placesearchedittext

import android.library.PlaceSearch
import android.library.adapters.PlacesAutoCompleteAdapter
import android.library.models.details.PlaceDetailsDTO
import android.library.models.places.Result
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.Toast
import com.faysal.placesearchedittext.databinding.ActivityMainBinding
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    val placesApi = PlaceSearch.Builder()
        .apiKey("AIzaSyBvOFVDazvd9et5EEafsE2jHdFJpF604-Y")
        .build(this@MainActivity)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.autoCompleteEditText.setAdapter(PlacesAutoCompleteAdapter(this, placesApi))
        binding.autoCompleteEditText.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                val place = parent.getItemAtPosition(position) as Result
                setupUI(place)
               /* val place = parent.getItemAtPosition(position) as Place
                binding.autoCompleteEditText.setText(place.description)
                getPlaceDetails(place.id)*/
            }
    }

    private fun getPlaceDetails(placeId: String) {
       /* placesApi.fetchPlaceDetails(placeId, object :
            OnPlaceDetailsListener {
            override fun onError(errorMessage: String) {
                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }

            override fun onPlaceDetailsFetched(placeDetails: PlaceDetails) {
                setupUI(placeDetails)
            }
        })*/
    }

    private fun setupUI(placeDetails: Result) {
        val builder = StringBuilder()
        builder.append("Name : ${placeDetails.name} \n")
        builder.append("Address : ${placeDetails.formatted_address} \n")
        builder.append("latitude : ${placeDetails.geometry.location.lat} \n")
        builder.append("Longitude : ${placeDetails.geometry.location.lng} \n")

        binding.autoCompleteEditText.setText(placeDetails.formatted_address)
        binding.placeResponse.text = builder.toString()
    }
}