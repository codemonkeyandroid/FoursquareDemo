package com.codemonkey.android.foursquaredemo.ui.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.codemonkey.android.foursquaredemo.databinding.VenueOverviewFragmentBinding
import com.codemonkey.android.foursquaredemo.utils.Resource
import com.codemonkey.android.foursquaredemo.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VenueOverviewFragment : Fragment(), VenuesAdapter.VenueAdapterListener {

    private val viewModel: VenueOverviewViewModel by viewModels()
    private var binding: VenueOverviewFragmentBinding by autoCleared()
    private lateinit var adapter: VenuesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = VenueOverviewFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        binding.button.setOnClickListener { viewModel.load(binding.locationInput.text.toString()) }
    }

    private fun setupRecyclerView() {
        adapter = VenuesAdapter(this)
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.venues.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) {
                        adapter.setItems(ArrayList(it.data))
                    } else {
                        adapter.clearItems()
                    }
                }
                Resource.Status.ERROR ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->
                    binding.progressBar.visibility = View.VISIBLE
            }
        })
    }

    override fun onVenueClicked(venueId: String) {
        findNavController().navigate(VenueOverviewFragmentDirections.actionVenueOverviewFragmentToVenueDetailFragment(venueId))
    }
}