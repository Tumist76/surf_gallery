package ru.tumist.surfgallery.presentation.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.redmadrobot.inputmask.MaskedTextChangedListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.tumist.surfgallery.R
import ru.tumist.surfgallery.databinding.FragmentAuthScreenBinding
import ru.tumist.surfgallery.presentation.auth.state.AuthErrorType
import ru.tumist.surfgallery.presentation.auth.state.AuthScreenState
import ru.tumist.surfgallery.presentation.auth.state.FieldValidationErrorType
import ru.tumist.surfgallery.presentation.auth.viewModel.AuthScreenViewModel
import ru.tumist.surfgallery.service.utils.collectOnLifecycle

class AuthScreen : Fragment() {

    private var _binding: FragmentAuthScreenBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthScreenViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createPhoneMask()
        viewModel.state.collectOnLifecycle(this) { stateListener(it) }
        binding.authButtonConfirm.setOnClickListener { viewModel.authenticate() }
        binding.authPasswordEt.doOnTextChanged { password, _, _, _ ->
            viewModel.setPassword(password.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun stateListener(state: AuthScreenState) {
        when (state) {
            is AuthScreenState.AuthError -> {
                handleAuthError(state)
            }
            AuthScreenState.Authorized -> handleAuthorized()
            is AuthScreenState.InvalidEntry -> {
                handleInvalidEntry(state)
            }
            AuthScreenState.Loading -> handleLoading()
            AuthScreenState.Idle -> {}
        }
    }

    private fun handleLoading() {
        binding.authButtonConfirm.isLoading = true
        binding.authScreenLoaderBlocker.isGone = false
    }

    private fun handleAuthError(state: AuthScreenState.AuthError) {
        stopLoading()

        var errorText = ""
        if (state.error == AuthErrorType.CONNECTION_ERROR) {
            errorText = resources.getString(R.string.connection_error_snackbar_text)
        }
        if (state.error == AuthErrorType.CREDENTIALS_INVALID) {
            errorText = resources.getString(R.string.auth_credentials_invalid_error_text)
        }
        Snackbar.make(
            binding.root, errorText, Snackbar.LENGTH_LONG
        ).setAnchorView(binding.authButtonConfirm).show()
    }

    private fun handleAuthorized() {
        stopLoading()
        findNavController().navigate(R.id.action_authScreen_to_mainScreenContainerFragment)
    }

    private fun handleInvalidEntry(state: AuthScreenState.InvalidEntry) {
        when (state.phoneError) {
            FieldValidationErrorType.EMPTY -> binding.authPhoneTil.error =
                resources.getString(R.string.auth_empty_field)
            FieldValidationErrorType.INVALID -> binding.authPhoneTil.error =
                resources.getString(R.string.auth_incorrect_phone_format)
            null -> binding.authPhoneTil.error = null
        }
        when (state.passwordError) {
            FieldValidationErrorType.EMPTY -> binding.authPasswordTil.error =
                resources.getString(R.string.auth_empty_field)
            FieldValidationErrorType.INVALID -> binding.authPasswordTil.error =
                resources.getString(R.string.auth_incorrect_password_format)
            null -> binding.authPasswordTil.error = null
        }
    }

    private fun stopLoading() {
        binding.authButtonConfirm.isLoading = false
        binding.authScreenLoaderBlocker.isGone = true
    }

    private fun createPhoneMask() {
        MaskedTextChangedListener.Companion.installOn(
            binding.authPhoneEt,
            AUTH_PHONE_MASK,
            object : MaskedTextChangedListener.ValueListener {
                override fun onTextChanged(
                    maskfilled: Boolean,
                    extractedValue: String,
                    formattedValue: String
                ) {
                    viewModel.setPhone(extractedValue)
                }
            }
        )
    }

    companion object {
        private const val AUTH_PHONE_MASK = "+ 7 ([000]) [000] [00] [00]"
    }
}