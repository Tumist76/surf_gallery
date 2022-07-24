package ru.tumist.surfgallery.presentation.mainContainer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.component.KoinComponent
import org.koin.java.KoinJavaComponent.get
import org.koin.java.KoinJavaComponent.inject
import ru.tumist.surfgallery.R
import ru.tumist.surfgallery.databinding.FragmentMainScreenContainerBinding
import ru.tumist.surfgallery.presentation.mainScreen.viewModel.MainScreenSharedViewModel
import ru.tumist.surfgallery.service.ApplicationState
import ru.tumist.surfgallery.service.utils.findTopNavController
import ru.tumist.surfgallery.service.utils.runOnUiThread

class MainScreenContainerFragment : Fragment() {

    private var _binding: FragmentMainScreenContainerBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: MainScreenSharedViewModel by sharedViewModel()
    private val applicationState: ApplicationState by inject(ApplicationState::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainScreenContainerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHost = childFragmentManager.findFragmentById(R.id.main_screen_nav_host_fragment) as NavHostFragment
        val navController = navHost.navController
        navController.addOnDestinationChangedListener{ _, dest, _ ->
            Log.w("NavConroller", "New destination - ${dest.toString()}")
        }
        binding.bottomNavigationView.setupWithNavController(navController)
        applicationState.onUnauthenticated = this::onUnauthenticated
    }

    private fun onUnauthenticated() {
        sharedViewModel.clearLocalData()
        runOnUiThread { findTopNavController().navigate(R.id.authScreen) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}