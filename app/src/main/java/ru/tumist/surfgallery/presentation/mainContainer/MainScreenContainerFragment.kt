package ru.tumist.surfgallery.presentation.mainContainer

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.tumist.surfgallery.R
import ru.tumist.surfgallery.presentation.mainContainer.viewModel.MainScreenContainerViewModel

class MainScreenContainerFragment : Fragment() {

    companion object {
        fun newInstance() = MainScreenContainerFragment()
    }

    private lateinit var viewModel: MainScreenContainerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_screen_container, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainScreenContainerViewModel::class.java)
        // TODO: Use the ViewModel
    }

}