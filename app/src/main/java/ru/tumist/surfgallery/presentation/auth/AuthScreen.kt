package ru.tumist.surfgallery.presentation.auth

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import ru.tumist.surfgallery.R
import ru.tumist.surfgallery.databinding.FragmentAuthScreenBinding
import ru.tumist.surfgallery.presentation.auth.viewModel.AuthScreenViewModel

class AuthScreen : Fragment() {

    private var _binding: FragmentAuthScreenBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = AuthScreen()
    }

    private lateinit var viewModel: AuthScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAuthScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AuthScreenViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.authButtonConfirm.setOnClickListener {
            binding.authButtonConfirm.isLoading = true
            Snackbar.make(
                binding.root,
                resources.getString(R.string.auth_credentials_invalid_error_text),
                Snackbar.LENGTH_LONG
            ).setAnchorView(binding.authButtonConfirm).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}