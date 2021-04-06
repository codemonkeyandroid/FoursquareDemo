package com.codemonkey.android.foursquaredemo.ui.overview


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.codemonkey.android.foursquaredemo.databinding.ItemViewBinding
import com.codemonkey.android.foursquaredemo.data.entities.Venue
import com.codemonkey.android.foursquaredemo.utils.fromJSON
import com.codemonkey.android.foursquaredemo.utils.parseFormatAddress


class VenuesAdapter(
    private val adapterListener: VenueAdapterListener
) : RecyclerView.Adapter<VenuesViewHolder>() {

    interface VenueAdapterListener {
        fun onVenueClicked(venueId: String)
    }

    private val items = ArrayList<Venue>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): VenuesViewHolder {
        val binding: ItemViewBinding = ItemViewBinding.inflate(LayoutInflater.from(parent.context))
        return VenuesViewHolder(binding, adapterListener)
    }

    override fun onBindViewHolder(holder: VenuesViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    fun setItems(items: ArrayList<Venue>) {
        this.items.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    fun clearItems() {
        this.items.clear()
        notifyDataSetChanged()
    }

}

class VenuesViewHolder(
    private val binding: ItemViewBinding,
    private val listener: VenuesAdapter.VenueAdapterListener
) : RecyclerView.ViewHolder(binding.root),
    View.OnClickListener {

    private lateinit var selectedVenue: Venue

    init {
        binding.root.setOnClickListener(this)
    }

    fun bind(venue: Venue) {

        selectedVenue = venue
        binding.apply {
            title.text = venue.title
            location.text = venue.formattedAddress?.fromJSON()?.parseFormatAddress()
            title.tag = venue.id

            venue.categoryIcon?.let { iconPath ->
                Glide.with(binding.root)
                    .load(iconPath)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(categoryIcon)
            }
        }

    }

    override fun onClick(v: View?) {
        listener.onVenueClicked(selectedVenue.id)
    }
}

