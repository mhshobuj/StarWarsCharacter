package com.mhs.starwarscharacter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mhs.starwarscharacter.databinding.CharacterItemBinding
import com.mhs.starwarscharacter.response.character.CharacterList
import javax.inject.Inject

class CharacterAdapter @Inject constructor() : RecyclerView.Adapter<CharacterAdapter.MyViewHolder>() {

    private lateinit var binding: CharacterItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterAdapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = CharacterItemBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterAdapter.MyViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class MyViewHolder(private val itemBinding: CharacterItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: CharacterList.Result){
            itemBinding.apply {
                tvName.text = item.name
                tvHeight.text = item.height
                tvName.text = item.gender
            }
        }
    }

    private val differCallBack = object : DiffUtil.ItemCallback<CharacterList.Result>() {
        override fun areItemsTheSame(
            oldItem: CharacterList.Result,
            newItem: CharacterList.Result
        ): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(
            oldItem: CharacterList.Result,
            newItem: CharacterList.Result
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)
}