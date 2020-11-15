package com.batzalcancia.tracks.presentation.features.tracks

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.batzalcancia.core.enums.UiState
import com.batzalcancia.core.helpers.prepareReturnTransition
import com.batzalcancia.core.helpers.px
import com.batzalcancia.tracks.R
import com.batzalcancia.tracks.databinding.FragmentTracksBinding
import com.batzalcancia.tracks.databinding.ItemTrackBinding
import com.batzalcancia.tracks.presentation.features.tracks.adapters.TracksAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class TracksFragment : Fragment(R.layout.fragment_tracks) {

    private lateinit var viewBinding: FragmentTracksBinding
    private val viewModel: TracksViewModel by viewModels()

    private val tracksAdapter by lazy {
        TracksAdapter(ItemTrackBinding::bind)
    }

    private var isSavedTrackHandled = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentTracksBinding.bind(view)

        viewBinding.rcvTracks.adapter = tracksAdapter

        viewBinding.rcvTracks.prepareReturnTransition(this)

        viewBinding.btnRetry.setOnClickListener {
            viewModel.executeGetAllTracks()
        }

        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.apbMain) { v, insets ->
            v.updatePadding(top = insets.systemWindowInsetTop)
            insets
        }

        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.root) { v, insets ->
            v.updatePadding(bottom = 0 + viewBinding.root.paddingBottom)
            insets
        }

        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.rcvTracks) { v, insets ->
            v.updatePadding(bottom = insets.systemWindowInsetBottom + 16.px(requireContext()))
            insets
        }

        setupOutputs()

    }

    private fun setupOutputs() {
        viewModel.savedTrack.onEach {
            if (it != null) {
                if (!isSavedTrackHandled) {
                    isSavedTrackHandled = true
                    findNavController().navigate(
                        TracksFragmentDirections.actionTracksToTrackDetails(
                            Json.encodeToString(it)
                        )
                    )
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.tracks.onEach {
            tracksAdapter.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.tracksState.onEach {
            viewBinding.prgTracks.isVisible = it == UiState.Loading
            viewBinding.rcvTracks.isInvisible = it == UiState.Loading
            viewBinding.btnRetry.isVisible = it is UiState.Error
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

}