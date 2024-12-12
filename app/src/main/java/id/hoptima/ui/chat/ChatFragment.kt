package id.hoptima.ui.chat

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import id.hoptima.R
import id.hoptima.data.Result
import id.hoptima.databinding.FragmentChatBinding
import id.hoptima.ui.common.BaseFragment
import id.hoptima.ui.common.CompactPropertyAdapter
import id.hoptima.util.AnimationUtil

class ChatFragment : BaseFragment() {
    private var _binding: FragmentChatBinding? = null
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
        if (viewModel.isChatGuideShown) {
            binding.flGuide.alpha = 1f
        } else {
            viewModel.isChatGuideShown = true
            AnimationUtil.fadeIn(binding.flGuide).apply {
                duration = 500
                startDelay = 2000
            }.start()
        }

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
            postponeEnterTransition()
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
                    binding.apply {
                        flResult.visibility = View.VISIBLE
                        toggleLoading(true)
                    }
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

                            (view?.parent as? ViewGroup)?.doOnPreDraw {
                                startPostponedEnterTransition()
                            }
                        }

                        toggleLoading(false)
                    }
                }

                is Result.Error -> {
                    binding.apply {
                        tvResultMessage.text = getString(R.string.recommendation_error)
                        toggleLoading(false)
                    }
                }
            }
        }
    }

    private fun toggleLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.btnNewChat.isEnabled = !isLoading
    }
}