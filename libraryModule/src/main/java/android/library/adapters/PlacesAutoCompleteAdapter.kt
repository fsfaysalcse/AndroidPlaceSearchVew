package android.library.adapters


import android.content.Context
import android.library.PlaceSearch
import android.library.R
import android.library.models.OnPlacesListListener
import android.library.models.places.Result
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView

/**
 * Created by mukeshsolanki on 28/02/19.
 */

private const val TAG = "PlacesAutoCompleteAdapt"

class PlacesAutoCompleteAdapter(mContext: Context, val placesApi: PlaceSearch) :
    ArrayAdapter<Result>(mContext, R.layout.autocomplete_list_item), Filterable {

    var resultList: ArrayList<Result> = ArrayList()

    override fun getCount(): Int {
        return resultList.size
    }

    override fun getItem(position: Int): Result {
        return resultList[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val viewHolder: ViewHolder
        if (view == null) {
            viewHolder = ViewHolder()
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.autocomplete_list_item, parent, false)
            viewHolder.description = view.findViewById(R.id.autocompleteText) as TextView
            viewHolder.footerImageView = view.findViewById(R.id.footerImageView) as ImageView
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }
        val place = resultList[position]
        bindView(viewHolder, place, position)
        return view!!
    }

    private fun bindView(viewHolder: ViewHolder, place: Result, position: Int) {
        if (!resultList.isNullOrEmpty()) {
            if (position != resultList.size - 1) {
                viewHolder.description?.text = place.formatted_address
                viewHolder.footerImageView?.visibility = View.GONE
                viewHolder.description?.visibility = View.VISIBLE
            } else {
                viewHolder.footerImageView?.visibility = View.VISIBLE
                viewHolder.description?.visibility = View.GONE
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
                    return if (parResponse.first.isNotEmpty() && parResponse.second.isEmpty()){
                        Log.d(TAG, "performFiltering: ${parResponse.first.size}")
                        resultList.clear()
                        resultList.addAll(parResponse.first.filter { it.formatted_address.isNotEmpty() })
                        filterResults.values = resultList
                        filterResults.count = resultList.size
                        filterResults
                    }else{
                        Log.d(TAG, "performFiltering: ${parResponse.second}")
                        resultList.clear()
                        resultList.add(Result(formatted_address = "${parResponse.second}", isEmpty = true))
                        filterResults.values = resultList
                        filterResults.count = resultList.size
                        filterResults
                    }
                }
                return filterResults
            }
        }
    }



    internal class ViewHolder {
        var description: TextView? = null
        var footerImageView: ImageView? = null
    }
}
