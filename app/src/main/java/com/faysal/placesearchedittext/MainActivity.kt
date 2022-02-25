package com.faysal.placesearchedittext


import com.faysal.library.PlaceSearch
import com.faysal.library.adapters.PlacesAutoCompleteAdapter
import com.faysal.library.models.OnPlaceDetailsListener
import com.faysal.library.models.details.PlaceDetails
import com.faysal.library.models.places.Places
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.Toast
import com.faysal.placesearchedittext.databinding.ActivityMainBinding
import java.lang.StringBuilder

const val GOOGLE_MAP_API_KEY : String = "ENTER GOOGLE MAP API KEY HERE"
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val placesApi = PlaceSearch.Builder()
        .apiKey(GOOGLE_MAP_API_KEY)
        .build(this@MainActivity)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.autoCompleteEditText.setAdapter(PlacesAutoCompleteAdapter(this, placesApi))
        binding.autoCompleteEditText.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                val place = parent.getItemAtPosition(position) as Places
                setupUI(place)
               /* val place = parent.getItemAtPosition(position) as Place
                binding.autoCompleteEditText.setText(place.description)
                getPlaceDetails(place.id)*/
            }
    }

    private fun getPlaceDetails(placeId: String) {
        placesApi.getPlaceDetails(placeId, object :
            OnPlaceDetailsListener {
            override fun onError(errorMessage: String) {
                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }

            override fun onSuccess(placeDetails: PlaceDetails) {
              //
            }
        })
    }

    private fun setupUI(placeDetails: Places) {
        val builder = StringBuilder()
        builder.append("Place Id : ${placeDetails.place_id} \n")
        builder.append("Name : ${placeDetails.name} \n")
        builder.append("Address : ${placeDetails.formatted_address} \n")
        builder.append("latitude : ${placeDetails.geometry.location.lat} \n")
        builder.append("Longitude : ${placeDetails.geometry.location.lng} \n")

        binding.autoCompleteEditText.setText(placeDetails.formatted_address)
        binding.placeResponse.text = builder.toString()
    }
}