package com.mhs.starwarscharacter.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mhs.starwarscharacter.R
import com.mhs.starwarscharacter.databinding.CharacterItemBinding
import com.mhs.starwarscharacter.response.character.CharacterList
import com.mhs.starwarscharacter.ui.activity.CharacterDetailsActivity
import javax.inject.Inject

class CharacterAdapter @Inject constructor(private val context: Context) : RecyclerView.Adapter<CharacterAdapter.MyViewHolder>() {

    private val data: MutableList<CharacterList.Result> = mutableListOf()

    inner class MyViewHolder(private val itemBinding: CharacterItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: CharacterList.Result) {
            itemBinding.apply {
                tvName.text = "Name: ".plus(item.name)
                tvHeight.text = "Height: ".plus(item.height).plus(" inch")
                tvGender.text = "Gender: ".plus(item.gender)

                // Set onClickListener for the entire item
                root.setOnClickListener {
                    // Get the name of the clicked item
                    val intent = Intent(context, CharacterDetailsActivity::class.java)
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
        val binding = CharacterItemBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int = data.size

    fun submitData(newData: List<CharacterList.Result>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
}