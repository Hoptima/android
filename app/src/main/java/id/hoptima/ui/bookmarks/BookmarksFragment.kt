package id.hoptima.ui.bookmarks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.hoptima.R
import id.hoptima.databinding.FragmentBookmarksBinding

class BookmarksFragment : Fragment() {
    private var _binding: FragmentBookmarksBinding? = null
    private val binding get() = _binding!!

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

        val bookmarkAdapter = BookmarkAdapter()
        binding.rvBookmarks.apply {
            adapter = bookmarkAdapter
        }

        val sampleData = Property(
            "Perumahan Ohio City",
            "Cleveland, Ohio",
            "Rp 1.5m",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin auctor sapien lorem, non ultrices massa consectetur vel. Interdum et malesuada fames ac ante ipsum primis in faucibus. In mattis tortor eu nisl pulvinar, at feugiat leo finibus. Ut accumsan est felis, ac mollis massa volutpat vitae. Nulla eu nunc sit amet neque bibendum vehicula. Mauris dapibus, risus eu faucibus aliquet, leo orci aliquam tellus, et blandit magna quam nec libero. Quisque vulputate dictum urna, nec pharetra purus viverra nec. Duis aliquam viverra sapien in iaculis. Pellentesque ut nisi et massa consectetur hendrerit sit amet eu diam. Aliquam sagittis tincidunt nibh ac luctus. Aenean urna urna, hendrerit sit amet risus eget, interdum pretium magna.",
            "3",
            "2",
            "1",
            "100 m²",
            "200 m²",
            R.drawable.home_sample
        )
        val list = (1..10).map { sampleData }
        bookmarkAdapter.submitList(list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}