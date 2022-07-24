package ru.tumist.surfgallery.presentation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isGone
import coil.load
import com.redmadrobot.inputmask.helper.Mask
import com.redmadrobot.inputmask.model.CaretString
import org.koin.java.KoinJavaComponent
import ru.tumist.surfgallery.R
import ru.tumist.surfgallery.databinding.FragmentProfilePageBinding
import ru.tumist.surfgallery.domain.model.UserInfo
import ru.tumist.surfgallery.service.ApplicationState

class ProfilePageFragment : Fragment() {
    private var _binding: FragmentProfilePageBinding? = null
    private val binding get() = _binding!!

    private val applicationState: ApplicationState by KoinJavaComponent.inject(ApplicationState::class.java)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfilePageBinding.inflate(inflater, container, false)
        if (applicationState.authInfo != null) {
            initFields(applicationState.authInfo!!.userInfo)
        }
        binding.profileLogoutBtn.setOnClickListener { confirmLogout() }

        return binding.root
    }

    private fun initFields(model: UserInfo) {
        hideEmptyFields(model)
        binding.avatarIv.load(model.avatar)
        binding.profileNameTv.text = model.firstName
        binding.profileSurnameTv.text = model.lastName
        binding.profileDescTv.text = model.about
        binding.profileCityField.fieldValue = model.city ?: ""
        if (model.phone != null) {
            val mask = Mask(AUTH_PHONE_MASK).apply(
                CaretString(
                    model.phone,
                    0,
                    CaretString.CaretGravity.FORWARD(false)
                )
            )
            binding.profilePhoneField.fieldValue = mask.formattedText.string
        }
        binding.profileEmailField.fieldValue = model.email ?: ""
    }


    private fun hideEmptyFields(model: UserInfo) {
        if (model.firstName.isNullOrEmpty()) binding.profileNameTv.isGone = true
        if (model.lastName.isNullOrEmpty()) binding.profileSurnameTv.isGone = true
        if (model.about.isNullOrEmpty()) binding.profileDescTv.isGone = true
        if (model.city.isNullOrEmpty()) binding.profileCityField.isGone = true
        if (model.phone.isNullOrEmpty()) binding.profilePhoneField.isGone = true
        if (model.email.isNullOrEmpty()) binding.profileEmailField.isGone = true
    }

    private fun confirmLogout() {
        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(
                    R.string.dialog_confirm
                ) { _, _ -> applicationState.onUnauthenticated() }
                setNegativeButton(R.string.dialog_cancel
                ) { _, _ -> }
            }.setMessage(R.string.logout_dialog_text)
            builder.create()
        }
        alertDialog?.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val AUTH_PHONE_MASK = "+ 7 ([000]) [000] [00] [00]"
    }
}