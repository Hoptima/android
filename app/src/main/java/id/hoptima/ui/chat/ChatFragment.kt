package id.hoptima.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.hoptima.R
import id.hoptima.data.Result
import id.hoptima.databinding.FragmentChatBinding
import id.hoptima.ui.common.BaseFragment
import id.hoptima.ui.common.CompactPropertyAdapter

class ChatFragment : BaseFragment() {
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
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

        binding.btnNewChat.setOnClickListener {
            navController.popBackStack()
            navController.navigate(R.id.navigation_chat)
        }

        binding.btnGetRecommendation.setOnClickListener {
            val query = binding.etQuery.text.toString()
            if (query.isEmpty()) {
                toast(getString(R.string.criteria_required))
                return@setOnClickListener
            }

            viewModel.setQuery(query)
        }

        val propertyAdapter = CompactPropertyAdapter(R.id.action_chat_to_detail) {
            viewModel.setPropertyBookmark(it, it.bookmarkedAt == null)
        }

        viewModel.query.observe(viewLifecycleOwner) {
            binding.apply {
                rvRecommendations.adapter = propertyAdapter

                etQuery.text?.clear()
                etQuery.isEnabled = false
                btnGetRecommendation.isEnabled = false

                tvQuery.text = it
                llInput.visibility = View.VISIBLE
            }
        }

        viewModel.recommendations.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Result.Success -> {
                    val data = it.data
                    val isDataEmpty = data.isEmpty()

                    binding.apply {
                        val resultMessageId =
                            if (isDataEmpty) R.string.recommendation_not_found else R.string.recommendation_found
                        tvResultMessage.text = getString(resultMessageId)

                        if (!isDataEmpty) {
                            propertyAdapter.submitList(it.data)
                            rvRecommendations.visibility = View.VISIBLE
                        }

                        flResult.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                    }
                }

                is Result.Error -> {
                    binding.apply {
                        tvResultMessage.text = getString(R.string.recommendation_error)
                        flResult.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                    }
                }
            }
        }
    }
}