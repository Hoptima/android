package id.hoptima.ui.histories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import id.hoptima.R
import id.hoptima.databinding.FragmentHistoriesBinding
import id.hoptima.ui.common.BaseFragment
import id.hoptima.ui.common.PropertyAdapter

class HistoryFragment : BaseFragment() {
    private var _binding: FragmentHistoriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onResume() {
        super.onResume()
        toggleNavViewVisibility(false)
    }

    override fun onPause() {
        super.onPause()
        toggleNavViewVisibility(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {
        binding.btnBack.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        val bookmarkAdapter = PropertyAdapter(R.id.action_histories_to_detail) {
            viewModel.setPropertyBookmark(it, it.bookmarkedAt == null)
        }
        binding.rvBookmarks.adapter = bookmarkAdapter

        viewModel.getProperties().observe(viewLifecycleOwner) {
            bookmarkAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        bookmarkAdapter.addLoadStateListener {
            val isListEmpty = it.refresh is LoadState.NotLoading && bookmarkAdapter.itemCount == 0
            binding.svNoData.visibility = if (isListEmpty) View.VISIBLE else View.GONE
        }
    }
}