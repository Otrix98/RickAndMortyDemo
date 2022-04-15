package com.example.rickandmortydemo.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.rickandmortydemo.R
import com.example.rickandmortydemo.data.models.CharacterDetail
import com.example.rickandmortydemo.databinding.DetailFragmentBinding
import com.example.rickandmortydemo.ui.viemodels.DetailViewModel
import com.example.rickandmortydemo.utils.ViewBindingFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : ViewBindingFragment<DetailFragmentBinding>(DetailFragmentBinding::inflate) {

    private val viewModel by viewModels<DetailViewModel>()
    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDetails(args.id)
        bindViewModel()
    }

    private fun bindViewModel() {
        viewModel.itemsList.observe(viewLifecycleOwner) { setInfo(it) }
    }

    private fun setInfo(details: CharacterDetail) {
        with(binding) {
            barTextView.text = details.name
            speciesTextView.text = resources.getString(R.string.spices) + details.species
            genderTextView.text = resources.getString(R.string.gender) + details.gender
            statusTextView.text = resources.getString(R.string.status) + details.status
            locationTextView.text = resources.getString(R.string.location) + details.location.name
            episodesTextView.text =
                resources.getString(R.string.episode) + details.episode?.size.toString()

        }

        Glide.with(requireContext())
            .load(details.image)
            .error(R.drawable.no_image)
            .circleCrop()
            .into(binding.imageView)
    }
}