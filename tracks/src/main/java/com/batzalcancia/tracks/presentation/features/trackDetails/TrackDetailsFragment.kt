package com.batzalcancia.tracks.presentation.features.trackDetails

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.batzalcancia.core.helpers.containerTransition
import com.batzalcancia.core.helpers.loadImageFromUrl
import com.batzalcancia.core.helpers.setContainerTransition
import com.batzalcancia.tracks.R
import com.batzalcancia.tracks.data.local.entities.ItunesTrackLocal
import com.batzalcancia.tracks.databinding.FragmentTrackDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.json.Json
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class TrackDetailsFragment : Fragment(R.layout.fragment_track_details) {

    private lateinit var viewBinding: FragmentTrackDetailsBinding

    private val viewModel: TrackDetailsViewModel by viewModels()

    private val trackDetailsNavArgs by navArgs<TrackDetailsFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = containerTransition()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentTrackDetailsBinding.bind(view)

        val trackDetails =
            Json.decodeFromString(ItunesTrackLocal.serializer(), trackDetailsNavArgs.trackDetails)

        viewBinding.root.setContainerTransition(
            getString(
                R.string.container_transition_name,
                trackDetails.id.toString()
            )
        )

        trackDetails.run {
            viewModel.executeSaveTrack(this)
            viewBinding.imgTrack.loadImageFromUrl(artworkUrl100)
            viewBinding.txtArtist.text = artistName
            viewBinding.txtGenre.text = genre
            viewBinding.txtLongDesc.text = longDescription
            viewBinding.txtTrack.text = trackName
        }

        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.apbMain) { v, insets ->
            v.updatePadding(top = insets.systemWindowInsetTop)
            insets
        }

        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.clpMain) { v, insets ->
            v.updatePadding(top = 0)
            insets
        }

        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.cstItunesTracks) { v, insets ->
            v.updatePadding(bottom = insets.systemWindowInsetBottom + viewBinding.cstItunesTracks.paddingBottom)
            insets
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.executeClearTrack()
    }

}