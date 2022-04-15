package com.example.rickandmortydemo.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmortydemo.R
import com.example.rickandmortydemo.data.models.Character
import com.example.rickandmortydemo.databinding.ListItemBinding



class ListAdapter() : PagingDataAdapter<Character, ListAdapter.MenuHolder>(ItemComparator) {
    private var listener: AdaptersListener? = null
    fun setOnClickListener(onClickListener: AdaptersListener) {
        this.listener = onClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MenuHolder {
        val itemBinding =
            ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuHolder(
            itemBinding
        )
    }

    override fun onBindViewHolder(holder: MenuHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }


    private object ItemComparator : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }


    inner class MenuHolder(
        private val binding: ListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(value: Character) {
            val characterImageView = itemView.findViewById<ImageView>(R.id.itemImage)

            binding.nameTextView.text = value.name
            binding.speciesTextView.text = "раса: " + value.species
            binding.genderTextView.text = "пол: " + value.gender

            Glide.with(characterImageView)
                .load(value.image)
                .circleCrop()
                .error(R.drawable.no_image)
                .into(characterImageView)

            initListener(value)
        }

        private fun initListener(item: Character) {
            binding.root.setOnClickListener {
                listener?.onClickItem(item)
            }
        }
    }
}