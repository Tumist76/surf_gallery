package ru.tumist.surfgallery.presentation.favorites

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ru.tumist.surfgallery.R
import ru.tumist.surfgallery.databinding.FragmentFavoritesPageBinding
import ru.tumist.surfgallery.presentation.mainScreen.state.MainScreenState
import ru.tumist.surfgallery.presentation.mainScreen.viewModel.MainScreenSharedViewModel
import ru.tumist.surfgallery.service.utils.collectOnLifecycle

class FavoritesPageFragment : Fragment() {

    private var _binding: FragmentFavoritesPageBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: MainScreenSharedViewModel by sharedViewModel<MainScreenSharedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.state.collectOnLifecycle(this) { stateListener(it) }

    }

    private fun stateListener(state: MainScreenState) {
        when (state) {
            MainScreenState.Empty -> handleEmpty()
            MainScreenState.Error -> handleError()
            is MainScreenState.Loaded -> handleLoaded()
            else -> handleLoading()
        }
    }

    private fun handleEmpty() {
        Snackbar.make(
            binding.root, "Пусто", Snackbar.LENGTH_LONG
        ).show()
    }

    private fun handleLoaded() {
        Snackbar.make(
            binding.root, "Загрузились", Snackbar.LENGTH_LONG
        ).show()
    }

    private fun handleLoading() {
        Snackbar.make(
            binding.root, "Грузимся", Snackbar.LENGTH_LONG
        ).show()
    }

    private fun handleError() {
        Snackbar.make(
            binding.root, "Ошибочка", Snackbar.LENGTH_LONG
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}