package ru.tumist.surfgallery.presentation.auth

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.tumist.surfgallery.R
import ru.tumist.surfgallery.presentation.auth.viewModel.AuthScreenViewModel

class AuthScreen : Fragment() {

    companion object {
        fun newInstance() = AuthScreen()
    }

    private lateinit var viewModel: AuthScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auth_screen, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AuthScreenViewModel::class.java)
        // TODO: Use the ViewModel
    }

}