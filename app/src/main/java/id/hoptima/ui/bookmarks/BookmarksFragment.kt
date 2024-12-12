package id.hoptima.ui.bookmarks

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import id.hoptima.R
import id.hoptima.databinding.FragmentBookmarksBinding
import id.hoptima.ui.common.BaseFragment
import id.hoptima.ui.common.PropertyAdapter
import kotlinx.coroutines.launch

class BookmarksFragment : BaseFragment() {
    private var _binding: FragmentBookmarksBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val inflater = TransitionInflater.from(context)
        enterTransition = inflater.inflateTransition(R.transition.fade)
        exitTransition = inflater.inflateTransition(R.transition.fade)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {
        postponeEnterTransition()

        val bookmarkAdapter = PropertyAdapter(R.id.action_bookmarks_to_detail) {
            viewModel.setPropertyBookmark(it, it.bookmarkedAt == null)
        }
        binding.rvBookmarks.adapter = bookmarkAdapter

        viewModel.getBookmarkedProperties().observe(viewLifecycleOwner) {
            bookmarkAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        bookmarkAdapter.addLoadStateListener {
            val isListEmpty = it.refresh is LoadState.NotLoading && bookmarkAdapter.itemCount == 0
            binding.svNoData.visibility = if (isListEmpty) View.VISIBLE else View.GONE
        }

        lifecycleScope.launch {
            bookmarkAdapter.onPagesUpdatedFlow.collect {
                startPostponedEnterTransition()
            }
        }
    }
}