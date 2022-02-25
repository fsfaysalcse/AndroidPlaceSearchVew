package com.faysal.placesearchedittext

import android.library.PlaceSearch
import android.library.adapters.PlacesAutoCompleteAdapter
import android.library.models.details.PlaceDetailsDTO
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.Toast
import com.faysal.placesearchedittext.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    val placesApi = PlaceSearch.Builder()
        .apiKey("AIzaSyBvOFVDazvd9et5EEafsE2jHdFJpF604-Y")
        .build(this@MainActivity)

    var street = ""
    var city = ""
    var state = ""
    var country = ""
    var zipCode = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.autoCompleteEditText.setAdapter(PlacesAutoCompleteAdapter(this, placesApi))
        binding.autoCompleteEditText.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
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

    private fun setupUI(placeDetails: PlaceDetailsDTO) {
       // val address = placeDetails.address
       /* parseAddress(address)
        runOnUiThread {
            binding.apply {
                streetTextView.text = street
                cityTextView.text = city
                stateTextView.text = state
                countryTextView.text = country
                zipCodeTextView.text = zipCode
                latitudeTextView.text = placeDetails.lat.toString()
                longitudeTextView.text = placeDetails.lng.toString()
                placeIdTextView.text = placeDetails.placeId
                urlTextView.text = placeDetails.url
                utcOffsetTextView.text = placeDetails.utcOffset.toString()
                vicinityTextView.text = placeDetails.vicinity
                compoundCodeTextView.text = placeDetails.compoundPlusCode
                globalCodeTextView.text = placeDetails.globalPlusCode
            }
        }*/
    }

  /*  private fun parseAddress(address: ArrayList<Address>) {
        (0 until address.size).forEach { i ->
            when {
                address[i].type.contains("street_number") -> street += address[i].shortName + " "
                address[i].type.contains("route") -> street += address[i].shortName
                address[i].type.contains("locality") -> city += address[i].shortName
                address[i].type.contains("administrative_area_level_1") -> state += address[i].shortName
                address[i].type.contains("country") -> country += address[i].shortName
                address[i].type.contains("postal_code") -> zipCode += address[i].shortName
            }
        }
    }*/
}