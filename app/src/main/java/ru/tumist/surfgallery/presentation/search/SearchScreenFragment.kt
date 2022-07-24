package ru.tumist.surfgallery.presentation.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.tumist.surfgallery.R

class SearchScreenFragment : Fragment() {

    companion object {
        fun newInstance() = SearchScreenFragment()
    }

    private lateinit var viewModel: SearchScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_screen, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchScreenViewModel::class.java)
        // TODO: Use the ViewModel
    }

}