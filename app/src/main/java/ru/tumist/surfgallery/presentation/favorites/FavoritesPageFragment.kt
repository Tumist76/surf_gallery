package ru.tumist.surfgallery.presentation.favorites

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.NonCancellable.cancel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ru.tumist.surfgallery.R
import ru.tumist.surfgallery.databinding.FragmentFavoritesPageBinding
import ru.tumist.surfgallery.domain.model.PictureModel
import ru.tumist.surfgallery.domain.repository.PicturesRepository
import ru.tumist.surfgallery.presentation.mainContainer.MainScreenContainerFragmentDirections
import ru.tumist.surfgallery.presentation.mainScreen.state.MainScreenState
import ru.tumist.surfgallery.presentation.mainScreen.viewModel.MainScreenSharedViewModel
import ru.tumist.surfgallery.presentation.views.pictures.MarginItemDecoration
import ru.tumist.surfgallery.presentation.views.pictures.PicturesAdapter
import ru.tumist.surfgallery.service.utils.collectOnLifecycle
import ru.tumist.surfgallery.service.utils.findTopNavController

class FavoritesPageFragment : Fragment() {

    private var _binding: FragmentFavoritesPageBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: MainScreenSharedViewModel by sharedViewModel()

    private lateinit var picturesAdapter: PicturesAdapter

    private fun onFavoriteClicked(item: PictureModel, isFavorite: Boolean) {
        sharedViewModel.setFavorite(item, isFavorite)
    }

    private fun onPictureItemTap(item: PictureModel) {
        val arguments =
            MainScreenContainerFragmentDirections.actionMainScreenContainerFragmentToPictureViewFragment(
                item
            )
        findTopNavController().navigate(arguments)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        sharedViewModel.state.collectOnLifecycle(this) { stateListener(it) }
    }

    private fun stateListener(state: MainScreenState) {
        when (state) {
            MainScreenState.Empty -> handleError()
            MainScreenState.Error -> handleError()
            is MainScreenState.Loaded -> handleLoaded(state)
            is MainScreenState.Loading -> handleLoading(state)
            else -> {}
        }
    }

    private fun initViews() {
        picturesAdapter = PicturesAdapter(
            this::onFavoriteClicked,
            this::onPictureItemTap,
            isFullView = true,
            imageHeight = resources.getDimensionPixelSize(R.dimen.favorite_item_image_height)
        )
        val layoutManager = GridLayoutManager(activity, 1)
        binding.galleryLoadErrorView.isVisible = false
        binding.galleryLoader.isVisible = true
        binding.galleryRefreshBtn.setOnClickListener { sharedViewModel.getPictures() }
        binding.picturesRv.addItemDecoration(
            MarginItemDecoration(
                verticalItemPadding = resources.getDimensionPixelSize(R.dimen.vertical_grid_view_padding),
                horizontalItemPadding = resources.getDimensionPixelSize(R.dimen.horizontal_grid_view_padding),
                horizontalMargins = resources.getDimensionPixelSize(R.dimen.horizontal_view_padding),
                spanCount = 1,
            )
        )
        binding.picturesRv.adapter = picturesAdapter
        binding.picturesRv.layoutManager = layoutManager
    }

    private fun handleLoaded(state: MainScreenState.Loaded) {
        if (state.isCompletedWithError) {
            Snackbar.make(
                binding.root,
                resources.getString(R.string.connection_error_snackbar_text),
                Snackbar.LENGTH_LONG
            ).show()
        }
        picturesAdapter.submitList(state.pictures.filter { it.isFavorite })
        binding.galleryLoader.isVisible = false
        binding.galleryLoadErrorView.isVisible = false
    }

    private fun handleLoading(state: MainScreenState.Loading) {
        if (state.pictures.isEmpty()) {
            binding.galleryLoader.isVisible = true
        }
        binding.galleryLoader.isVisible = false
        binding.galleryLoadErrorView.isVisible = false
        picturesAdapter.submitList(state.pictures.filter { it.isFavorite })
    }

    private fun handleError() {
        binding.galleryLoadErrorView.isVisible = true
        binding.galleryLoader.isVisible = false
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}