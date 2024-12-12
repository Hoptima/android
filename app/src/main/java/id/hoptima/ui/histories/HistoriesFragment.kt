package id.hoptima.ui.histories

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import id.hoptima.R
import id.hoptima.databinding.FragmentHistoriesBinding
import id.hoptima.ui.common.BaseFragment
import id.hoptima.ui.common.PropertyAdapter
import kotlinx.coroutines.launch

class HistoriesFragment : BaseFragment() {
    private var _binding: FragmentHistoriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val inflater = TransitionInflater.from(context)
        enterTransition = inflater.inflateTransition(R.transition.slide_right)
        exitTransition = inflater.inflateTransition(R.transition.fade)
    }

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
        postponeEnterTransition()

        binding.btnBack.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        val historyAdapter = PropertyAdapter(R.id.action_histories_to_detail) {
            viewModel.setPropertyBookmark(it, it.bookmarkedAt == null)
        }
        binding.rvHistories.adapter = historyAdapter

        viewModel.getProperties().observe(viewLifecycleOwner) {
            historyAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        historyAdapter.addLoadStateListener {
            val isListEmpty = it.refresh is LoadState.NotLoading && historyAdapter.itemCount == 0
            binding.svNoData.visibility = if (isListEmpty) View.VISIBLE else View.GONE
        }

        lifecycleScope.launch {
            historyAdapter.onPagesUpdatedFlow.collect {
                startPostponedEnterTransition()
            }
        }
    }
}