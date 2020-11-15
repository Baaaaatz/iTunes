package com.batzalcancia.tracks.presentation.features.tracks.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.batzalcancia.core.helpers.loadImageFromUrl
import com.batzalcancia.core.helpers.setContainerTransition
import com.batzalcancia.core.utils.ViewBindingViewHolder
import com.batzalcancia.tracks.R
import com.batzalcancia.tracks.data.local.entities.ItunesTrackLocal
import com.batzalcancia.tracks.databinding.ItemTrackBinding
import com.batzalcancia.tracks.presentation.features.tracks.TracksFragmentDirections
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * @param setupTracksBinding setup viewbinding for each item
 */
class TracksAdapter(
    private val setupTracksBinding: (View) -> ItemTrackBinding
) : ListAdapter<ItunesTrackLocal, ViewBindingViewHolder<ItemTrackBinding>>(
    object : DiffUtil.ItemCallback<ItunesTrackLocal>() {
        override fun areItemsTheSame(oldItem: ItunesTrackLocal, newItem: ItunesTrackLocal) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ItunesTrackLocal, newItem: ItunesTrackLocal) = oldItem == newItem

    }
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewBindingViewHolder<ItemTrackBinding> {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_track, parent, false)
        return ViewBindingViewHolder(setupTracksBinding(view))
    }

    override fun onBindViewHolder(holder: ViewBindingViewHolder<ItemTrackBinding>, position: Int) {
        val user = getItem(position)
        holder.viewBinding.run {
            user?.let {
                txtTrackName.text = it.trackName
                txtGenre.text = it.genre
                txtPrice.text = "$ ${it.trackPrice}"
                imgTrack.loadImageFromUrl(it.artworkUrl100)

                root.setContainerTransition(
                    root.context.getString(
                        R.string.container_transition_name,
                        it.id.toString()
                    )
                )

                root.setOnClickListener { view ->
                    view.findNavController().navigate(
                        TracksFragmentDirections.actionTracksToTrackDetails(
                            Json.encodeToString(it)
                        ), FragmentNavigatorExtras(
                            root to root.transitionName,
                        )
                    )
                }
            }
        }
    }

}