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