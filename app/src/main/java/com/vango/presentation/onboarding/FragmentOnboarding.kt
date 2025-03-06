package com.vango.presentation.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vango.databinding.FragmentOnboardingBinding


class FragmentOnboarding : Fragment() {

    private var _binding : FragmentOnboardingBinding? = null
    private val binding get() = _binding!!

    companion object{
        fun newInstance(
            imageResource: Int
        ) : FragmentOnboarding {
            val fragment = FragmentOnboarding()
            val arg = Bundle().apply {
                putInt("image_resource", imageResource)
            }
            fragment.arguments = arg
            return fragment

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            binding.ivImage.setImageResource(it.getInt("image_resource"))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}