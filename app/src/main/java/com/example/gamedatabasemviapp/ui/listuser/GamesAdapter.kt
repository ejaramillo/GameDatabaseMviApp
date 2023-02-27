package com.example.gamedatabasemviapp.ui.listuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gamedatabasemviapp.R
import com.example.gamedatabasemviapp.domain.GameInfoModel
import com.example.gamedatabasemviapp.databinding.ItemGameBinding
import com.example.gamedatabasemviapp.ui.listuser.GameViewHolder.Companion.DIFF_UTIL

class GamesAdapter : ListAdapter<GameInfoModel, GameViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = ItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

}

class GameViewHolder(private val binding: ItemGameBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bindData(gameInfoModel: GameInfoModel) {
        binding.run {
            tvGameName.text = gameInfoModel.name
            tvGameRelease.text = gameInfoModel.released
            rbGameRate.rating = gameInfoModel.rating?.toFloat() ?: 0F
            tvGameRate.text = StringBuilder("${(gameInfoModel.rating?.div(2)).toString()}/5")
            Glide.with(binding.root)
                .load(gameInfoModel.backgroundImage)
                .placeholder(R.color.white)
                .into(ivGamePoster)
        }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<GameInfoModel>() {
            override fun areItemsTheSame(oldItem: GameInfoModel, newItem: GameInfoModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: GameInfoModel,
                newItem: GameInfoModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}

