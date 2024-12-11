package id.hoptima.ui.common

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import id.hoptima.R

@AndroidEntryPoint
abstract class BaseFragment : Fragment() {
    protected val viewModel: MainViewModel by viewModels()
    protected lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
    }

    protected fun toggleNavViewVisibility(isVisible: Boolean) {
        val navView = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        navView?.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    protected fun toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}