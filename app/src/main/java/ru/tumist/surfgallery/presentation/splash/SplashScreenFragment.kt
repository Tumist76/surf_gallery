package ru.tumist.surfgallery.presentation.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.tumist.surfgallery.R
import ru.tumist.surfgallery.databinding.FragmentSplashScreenBinding
import ru.tumist.surfgallery.presentation.splash.state.SplashScreenState
import ru.tumist.surfgallery.presentation.splash.viewModel.SplashScreenViewModel
import ru.tumist.surfgallery.service.utils.collectOnLifecycle

class SplashScreenFragment : Fragment() {
    private val viewModel: SplashScreenViewModel by viewModel()

    private var _binding: FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAuthInfo();
        viewModel.state.collectOnLifecycle(this) {
            when (it) {
                SplashScreenState.Authorized -> navigateToScreen(isAuthorized = true)
                SplashScreenState.Unauthorized -> navigateToScreen(isAuthorized = false)
                else -> {}
            }
        }
    }

    private fun navigateToScreen(isAuthorized: Boolean) {
        Handler(Looper.getMainLooper()).postDelayed({
            if (isAuthorized) {
                findNavController().navigate(R.id.action_splashScreenFragment_to_galleryScreenFragment)
            } else {
                findNavController().navigate(R.id.action_splashScreenFragment_to_authScreen)
            }
        }, 1000);
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}