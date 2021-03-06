package com.faysal.placesearchedittext


import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.faysal.placesearchedittext.databinding.ActivityMainBinding
import com.faysal.placeview.PlacesView
import com.faysal.placeview.adapters.PlacesAutoCompleteAdapter
import com.faysal.placeview.models.OnPlaceDetailsListener
import com.faysal.placeview.models.details.PlaceDetails
import com.faysal.placeview.models.places.Places


private const val TAG = "MainActivity"
const val GOOGLE_MAP_API_KEY : String = ""
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val placesApi = PlacesView.Builder()
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
                getPlaceDetails(place.place_id)
            }
    }
    private fun getPlaceDetails(placeId: String) {
        placesApi.getPlaceDetails(placeId, object :
            OnPlaceDetailsListener {
            override fun onError(errorMessage: String) {
                Log.d(TAG, "onError: $errorMessage")
                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }

            override fun onSuccess(placeDetails: PlaceDetails) {
                Log.d(TAG, "onSuccess: $placeDetails")
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