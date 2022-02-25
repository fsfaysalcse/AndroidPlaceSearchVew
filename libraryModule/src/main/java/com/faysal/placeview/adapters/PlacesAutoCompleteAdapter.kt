package com.faysal.placeview.adapters


import android.content.Context
import android.library.R
import android.library.databinding.AutocompleteListItemBinding
import android.library.databinding.AutocompleteListItemBinding.inflate
import com.faysal.placeview.PlaceSearch
import com.faysal.placeview.models.places.Places
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable

/**
 * Created by Faysal Hossain
 */

private const val TAG = "PlacesAutoCompleteAdapt"

class PlacesAutoCompleteAdapter(mContext: Context, val placesApi: PlaceSearch) :
    ArrayAdapter<Places>(mContext, R.layout.autocomplete_list_item), Filterable {

    var resultList: ArrayList<Places> = ArrayList()

    override fun getCount(): Int {
        return when {
            resultList.isNullOrEmpty() -> 0
            else -> resultList.size
        }
    }

    override fun getItem(position: Int): Places {
        return when {
            resultList.isNullOrEmpty() -> Places()
            else -> resultList[position]
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val inflater = LayoutInflater.from(context)
        val binding = inflate(inflater, parent, false)
        view = binding.root
        view.tag = binding.root.tag
        val place = resultList[position]
        bindView(binding, place, position)
        return view
    }

    private fun bindView(binding: AutocompleteListItemBinding, place: Places, position: Int) {
        if (!resultList.isNullOrEmpty()) {

            if (place.isError) {
                binding.bottomImage.visibility = View.GONE
                binding.title.visibility = View.GONE
                binding.placeIcon.visibility = View.GONE
                binding.errorMessage.visibility = View.VISIBLE
                binding.errorMessage.text = place.errorMessage
                return
            }

            binding.title.text = place.formatted_address
            if (position == resultList.size - 1) {
                binding.bottomImage.visibility = View.VISIBLE
            } else {
                binding.bottomImage.visibility = View.GONE
            }

        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged()
                } else {
                    notifyDataSetInvalidated()
                }
            }

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint != null) {
                    val parResponse = placesApi.getPlaces(constraint.toString())
                    return if (parResponse.first.isNotEmpty() && parResponse.second.isEmpty()) {
                        Log.d(TAG, "performFiltering: ${parResponse.first.size}")
                        resultList.clear()
                        resultList.addAll(parResponse.first.filter { it.formatted_address.isNotEmpty() })
                        filterResults.values = resultList
                        filterResults.count = resultList.size
                        filterResults
                    } else {
                        Log.d(TAG, "performFiltering: ${parResponse.second}")
                        resultList.clear()
                        resultList.add(
                            Places(
                                errorMessage = parResponse.second,
                                isError = true
                            )
                        )
                        filterResults.values = resultList
                        filterResults.count = resultList.size
                        filterResults
                    }
                }
                return filterResults
            }
        }
    }


}
