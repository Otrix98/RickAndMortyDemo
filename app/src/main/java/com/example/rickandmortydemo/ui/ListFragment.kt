package com.example.rickandmortydemo.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortydemo.R
import com.example.rickandmortydemo.utils.ViewBindingFragment
import com.example.rickandmortydemo.data.models.Character
import com.example.rickandmortydemo.databinding.FragmentListBinding
import com.example.rickandmortydemo.ui.adapters.AdaptersListener
import com.example.rickandmortydemo.ui.adapters.ListAdapter
import com.example.rickandmortydemo.ui.adapters.LoadStateAdapter
import com.example.rickandmortydemo.ui.viemodels.ListViewModel
import com.example.rickandmortydemo.utils.isInternetAvailable
import com.example.rickandmortydemo.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
@ExperimentalPagingApi
class ListFragment : ViewBindingFragment<FragmentListBinding>(FragmentListBinding::inflate),
    AdaptersListener {

    private val viewModel by viewModels<ListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val itemDivider = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.recyclerView.addItemDecoration(itemDivider)
        binding.bindState(
            pagingData = viewModel.pagingDataFlow
        )
    }

    override fun onClickItem(item: Character) {
        if (isInternetAvailable(requireContext())) {
            val action = ListFragmentDirections.actionListFragmentToDetailFragment(item.id)
            findNavController().navigate(action)
        } else {
            toast(R.string.internet_connection_error)
        }
    }

    private fun FragmentListBinding.bindState(
        pagingData: Flow<PagingData<Character>>
    ) {
        val listAdapter = ListAdapter()
        listAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        listAdapter.setOnClickListener(this@ListFragment)
        val header = LoadStateAdapter { listAdapter.retry() }
        recyclerView.adapter = listAdapter.withLoadStateHeaderAndFooter(
            header = header,
            footer = LoadStateAdapter { listAdapter.retry() }
        )
        bindList(
            header = header,
            menuAdapter = listAdapter,
            pagingData = pagingData
        )
    }

    private fun FragmentListBinding.bindList(
        header: LoadStateAdapter,
        menuAdapter: ListAdapter,
        pagingData: Flow<PagingData<Character>>
    ) {
        retryButton.setOnClickListener { menuAdapter.retry() }

        if (!isInternetAvailable(requireContext())) {
            toast(R.string.connection_error)
        }

        lifecycleScope.launch {
            pagingData.collectLatest {
                menuAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            menuAdapter.loadStateFlow.collect { loadState ->
                header.loadState = loadState.mediator
                    ?.refresh
                    ?.takeIf { it is LoadState.Error && menuAdapter.itemCount > 0 }
                    ?: loadState.prepend

                val isListEmpty =
                    loadState.refresh is LoadState.NotLoading && menuAdapter.itemCount == 0
                emptyList.isVisible = isListEmpty
                recyclerView.isVisible =
                    loadState.source.refresh is LoadState.NotLoading || loadState.mediator?.refresh is LoadState.NotLoading
                progressBar.isVisible = loadState.mediator?.refresh is LoadState.Loading
                retryButton.isVisible =
                    loadState.mediator?.refresh is LoadState.Error && menuAdapter.itemCount == 0
                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    Toast.makeText(
                        requireContext(),
                        "\uD83D\uDE28 Wooops ${it.error}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

}
