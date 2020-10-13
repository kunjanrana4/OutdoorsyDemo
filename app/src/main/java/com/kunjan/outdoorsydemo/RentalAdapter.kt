package com.kunjan.outdoorsydemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kunjan.outdoorsydemo.pojo.Data
import java.util.*
import kotlin.collections.ArrayList

class RentalAdapter(private var rental_list: MutableList<Data>) :
    RecyclerView.Adapter<RentalAdapter.ViewHolder>(), Filterable{
    var displayList: MutableList<Data> = rental_list.toMutableList()
    var fullList: List<Data>
    init {
        fullList = ArrayList(displayList)
    }
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val img:ImageView = itemView.findViewById(R.id.img)
        val name = itemView.findViewById<TextView>(R.id.name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return rental_list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rental = rental_list[position]
        holder.name.text = rental.attributes.name

        Glide.with(holder.itemView.context)
            .load(rental.attributes.primary_img_url)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.img)
    }

    override fun getFilter(): Filter {
        return resultFilter
    }

    private val resultFilter : Filter = object :Filter(){
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList: MutableList<Data> = ArrayList()
            if(constraint == null || constraint.isEmpty())
                filteredList.addAll(fullList)
            else{
                val pattern = constraint.toString().toLowerCase(Locale.ROOT).trim()
                for(item in fullList){
                    if(item.attributes.name.toLowerCase(Locale.ROOT).contains(pattern)){
                        filteredList.add(item)
                    }
                }
            }
            val result = FilterResults()
            result.values = filteredList
            return result
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            rental_list.clear()
            rental_list = results!!.values as ArrayList<Data>
            notifyDataSetChanged()
        }
    }
}