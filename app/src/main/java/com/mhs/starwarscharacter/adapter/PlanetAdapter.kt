package com.mhs.starwarscharacter.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mhs.starwarscharacter.databinding.PlanetItemBinding
import com.mhs.starwarscharacter.response.planet.PlanetList
import com.mhs.starwarscharacter.ui.activity.PlanetDetailsActivity
import com.mhs.starwarscharacter.ui.activity.StarShipDetailsActivity
import javax.inject.Inject

class PlanetAdapter @Inject constructor(private val context: Context) : RecyclerView.Adapter<PlanetAdapter.MyViewHolder>() {

    // The data source for the adapter, initially an empty mutable list of PlanetList.Result
    private val data: MutableList<PlanetList.Result> = mutableListOf()

    // ViewHolder class for holding and binding the views of each item in the RecyclerView
    inner class MyViewHolder(private val itemBinding: PlanetItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        // Bind data to the views
        fun bind(item: PlanetList.Result) {
            itemBinding.apply {
                // Set text for TextViews with planet details
                tvName.text = "Name: ".plus(item.name)
                tvDiameter.text = "Diameter: ".plus(item.diameter)
                tvPopulation.text = "Population: ".plus(item.population)

                // Set onClickListener for the entire item
                root.setOnClickListener {
                    // Create an Intent to open the PlanetDetailsActivity
                    val intent = Intent(context, PlanetDetailsActivity::class.java)
                    // Add the planet's URL as an extra to the Intent
                    intent.putExtra("itemURL", item.url)
                    // Start the DetailsActivity
                    context.startActivity(intent)
                }
            }
        }
    }

    // onCreateViewHolder is called when the RecyclerView needs a new ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Inflate the layout for the item view
        val inflater = LayoutInflater.from(parent.context)
        val binding = PlanetItemBinding.inflate(inflater, parent, false)
        // Return a new instance of MyViewHolder with the inflated binding
        return MyViewHolder(binding)
    }

    // onBindViewHolder is called to bind data to a ViewHolder at a given position
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // Call the bind method of the ViewHolder to bind data to views
        holder.bind(data[position])
        // Disable recycling of ViewHolder to keep its state
        holder.setIsRecyclable(false)
    }

    // getItemCount returns the number of items in the data list
    override fun getItemCount(): Int = data.size

    // submitData is a custom method to update the data in the adapter
    fun submitData(newData: List<PlanetList.Result>) {
        // Clear existing data and add the new data
        data.clear()
        data.addAll(newData)
        // Notify the adapter that the data set has changed
        notifyDataSetChanged()
    }
}