package ru.tumist.surfgallery.presentation.gallery

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.tumist.surfgallery.R
import ru.tumist.surfgallery.presentation.gallery.viewModel.GalleryScreenViewModel

class GalleryScreenFragment : Fragment() {

    companion object {
        fun newInstance() = GalleryScreenFragment()
    }

    private lateinit var viewModel: GalleryScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gallery_screen, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GalleryScreenViewModel::class.java)
        // TODO: Use the ViewModel
    }

}