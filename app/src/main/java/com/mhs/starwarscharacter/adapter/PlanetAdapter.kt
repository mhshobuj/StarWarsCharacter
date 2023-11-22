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

    private val data: MutableList<PlanetList.Result> = mutableListOf()

    inner class MyViewHolder(private val itemBinding: PlanetItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: PlanetList.Result) {
            itemBinding.apply {
                tvName.text = "Name: ".plus(item.name)
                tvDiameter.text = "Diameter: ".plus(item.diameter)
                tvPopulation.text = "Population: ".plus(item.population)

                // Set onClickListener for the entire item
                root.setOnClickListener {
                    // Get the name of the clicked item
                    val intent = Intent(context, PlanetDetailsActivity::class.java)
                    // Add the string value as an extra to the Intent
                    intent.putExtra("itemURL", item.url)
                    // Start the DetailsActivity
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PlanetItemBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int = data.size

    fun submitData(newData: List<PlanetList.Result>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
}