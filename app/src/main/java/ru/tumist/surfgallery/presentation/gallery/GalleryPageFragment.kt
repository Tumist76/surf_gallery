package ru.tumist.surfgallery.presentation.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.resources.MaterialResources.getDimensionPixelSize
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ru.tumist.surfgallery.R
import ru.tumist.surfgallery.databinding.FragmentGalleryPageBinding
import ru.tumist.surfgallery.domain.model.PictureModel
import ru.tumist.surfgallery.presentation.mainScreen.state.MainScreenState
import ru.tumist.surfgallery.presentation.mainScreen.viewModel.MainScreenSharedViewModel
import ru.tumist.surfgallery.presentation.views.pictures.MarginItemDecoration
import ru.tumist.surfgallery.presentation.views.pictures.PicturesAdapter
import ru.tumist.surfgallery.service.utils.collectOnLifecycle
import ru.tumist.surfgallery.service.utils.findTopNavController

class GalleryPageFragment : Fragment() {

    private var _binding: FragmentGalleryPageBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: MainScreenSharedViewModel by sharedViewModel<MainScreenSharedViewModel>()

    private val picturesAdapter = PicturesAdapter(this::onFavoriteClicked, this::onPictureItemTap)

    private fun onFavoriteClicked(item: PictureModel, isFavorite: Boolean) {
        sharedViewModel.setFavorite(item, isFavorite)
    }

    private fun onPictureItemTap(item: PictureModel) {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        sharedViewModel.state.collectOnLifecycle(this) { stateListener(it) }
        sharedViewModel.init()
    }

    private fun stateListener(state: MainScreenState) {
        when (state) {
            MainScreenState.Empty -> handleEmpty()
            MainScreenState.Error -> handleError()
            is MainScreenState.Loaded -> handleLoaded(state)
            else -> handleLoading()
        }
    }

    private fun initViews() {
        val layoutManager = GridLayoutManager(activity, 2)
        binding.picturesSwipeRefresh.setOnRefreshListener { sharedViewModel.getPictures() }
        binding.searchIconIv.setOnClickListener {
            findTopNavController().navigate(R.id.searchScreenFragment)
        }
        binding.picturesRv.addItemDecoration(
            MarginItemDecoration(
                verticalItemPadding = resources.getDimensionPixelSize(R.dimen.vertical_grid_view_padding),
                horizontalItemPadding = resources.getDimensionPixelSize(R.dimen.horizontal_grid_view_padding),
                horizontalMargins = resources.getDimensionPixelSize(R.dimen.horizontal_view_padding),
                spanCount = 2,
            )
        )
        binding.picturesRv.adapter = picturesAdapter
        binding.picturesRv.layoutManager = layoutManager
    }

    private fun handleLoaded(state: MainScreenState.Loaded) {
        picturesAdapter.submitList(state.pictures)
        binding.picturesSwipeRefresh.isRefreshing = false
    }

    private fun handleEmpty() {
        Snackbar.make(
            binding.root, "Пусто", Snackbar.LENGTH_LONG
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