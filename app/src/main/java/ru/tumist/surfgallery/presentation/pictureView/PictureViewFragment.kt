package ru.tumist.surfgallery.presentation.pictureView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import ru.tumist.surfgallery.R
import ru.tumist.surfgallery.databinding.FragmentPictureViewBinding
import ru.tumist.surfgallery.domain.model.dateToViewString

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PictureViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PictureViewFragment : Fragment() {
    private var _binding: FragmentPictureViewBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<PictureViewFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPictureViewBinding.inflate(inflater, container, false)
        val model = args.pictureModel
        binding.pictureIv.load(model.photoUrl)
        binding.titleTv.text = model.title
        binding.dateTv.text = model.dateToViewString()
        binding.contentTv.text = model.content
        binding.backIconIv.setOnClickListener { activity?.onBackPressed() }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}