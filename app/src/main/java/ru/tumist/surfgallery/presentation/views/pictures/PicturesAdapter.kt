package ru.tumist.surfgallery.presentation.views.pictures

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.tumist.surfgallery.databinding.ItemPictureMainBinding
import ru.tumist.surfgallery.domain.model.PictureModel

class PicturesAdapter(
    val onFavoriteClick: (model: PictureModel, isChecked: Boolean) -> Unit,
    val onItemClick: (model: PictureModel) -> Unit
) : ListAdapter<PictureModel, PicturesAdapter.PicturesViewHolder>(
    DiffUtilCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicturesViewHolder {
        return PicturesViewHolder(
            ItemPictureMainBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PicturesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PicturesViewHolder(
        private val binding: ItemPictureMainBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PictureModel) {
            binding.favoriteBtn.setOnCheckedChangeListener(null);
            binding.pictureIv.load(item.photoUrl)
            binding.titleTv.text = item.title
            binding.favoriteBtn.isChecked = item.isFavorite
            binding.favoriteBtn.setOnCheckedChangeListener { _, isChecked ->
                onFavoriteClick(item, isChecked)
            }
            binding.pictureItem.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    private object DiffUtilCallback : DiffUtil.ItemCallback<PictureModel>() {
        override fun areItemsTheSame(oldItem: PictureModel, newItem: PictureModel): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: PictureModel, newItem: PictureModel): Boolean =
            oldItem.id == newItem.id
                    && oldItem.content == newItem.content
                    && oldItem.title == newItem.title
                    && oldItem.photoUrl == newItem.photoUrl
    }
}