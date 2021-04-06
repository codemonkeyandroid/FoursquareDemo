package com.codemonkey.android.foursquaredemo.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.codemonkey.android.foursquaredemo.databinding.VenueDetailFragmentBinding
import com.codemonkey.android.foursquaredemo.utils.Resource
import com.codemonkey.android.foursquaredemo.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VenueDetailFragment : Fragment() {

    private val viewModel: VenueDetailViewModel by viewModels()
    private var binding: VenueDetailFragmentBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = VenueDetailFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val venueId = VenueDetailFragmentArgs.fromBundle(it).venueDetailFragmentArgs
            viewModel.load(venueId)
        }

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.venueDetail.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                }

                Resource.Status.ERROR ->
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })

        viewModel.photos.observe(viewLifecycleOwner, { bindPhotos(it) })
    }

    private fun bindPhotos(fromJSON: List<String>) {
        fromJSON.forEach {
            val viewId = View.generateViewId()
            val image = ImageView(requireContext()).apply {
                id = viewId
            }
            Glide.with(binding.root)
                .load(it)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(image)
            binding.photoLayout.addView(image)
        }
    }
}