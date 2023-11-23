package com.mhs.starwarscharacter.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mhs.starwarscharacter.databinding.CharacterItemBinding
import com.mhs.starwarscharacter.response.character.CharacterList
import com.mhs.starwarscharacter.ui.activity.CharacterDetailsActivity
import javax.inject.Inject

class CharacterAdapter @Inject constructor(private val context: Context) : RecyclerView.Adapter<CharacterAdapter.MyViewHolder>() {

    // The data source for the adapter, initially an empty mutable list of CharacterList.Result
    private val data: MutableList<CharacterList.Result> = mutableListOf()

    // ViewHolder class for holding and binding the views of each item in the RecyclerView
    inner class MyViewHolder(private val itemBinding: CharacterItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        // Bind data to the views
        fun bind(item: CharacterList.Result) {
            itemBinding.apply {
                // Set text for TextViews with character details
                tvName.text = "Name: ".plus(item.name)
                tvHeight.text = "Height: ".plus(item.height)
                tvGender.text = "Gender: ".plus(item.gender)

                // Set onClickListener for the entire item
                root.setOnClickListener {
                    // Create an Intent to open the CharacterDetailsActivity
                    val intent = Intent(context, CharacterDetailsActivity::class.java)
                    // Add the character's URL as an extra to the Intent
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
        val binding = CharacterItemBinding.inflate(inflater, parent, false)
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
    fun submitData(newData: List<CharacterList.Result>) {
        // Clear existing data and add the new data
        data.clear()
        data.addAll(newData)
        // Notify the adapter that the data set has changed
        notifyDataSetChanged()
    }
}