package com.mhs.starwarscharacter.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mhs.starwarscharacter.databinding.StarShipItemBinding
import com.mhs.starwarscharacter.response.starShip.StarShipList
import com.mhs.starwarscharacter.ui.activity.StarShipDetailsActivity
import javax.inject.Inject

class StarShipAdapter @Inject constructor(private val context: Context) : RecyclerView.Adapter<StarShipAdapter.MyViewHolder>() {

    private val data: MutableList<StarShipList.Result> = mutableListOf()

    inner class MyViewHolder(private val itemBinding: StarShipItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: StarShipList.Result) {
            itemBinding.apply {
                tvName.text = "Name: ".plus(item.name)
                tvModel.text = "Model: ".plus(item.model)
                tvCost.text = "Cost: ".plus(item.costInCredits)

                // Set onClickListener for the entire item
                root.setOnClickListener {
                    // Get the name of the clicked item
                    val intent = Intent(context, StarShipDetailsActivity::class.java)
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
        val binding = StarShipItemBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int = data.size

    fun submitData(newData: List<StarShipList.Result>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
}