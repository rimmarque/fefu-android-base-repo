package ru.fefu.activitytracker.tracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.fefu.activitytracker.BaseFragment
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.FragmentChangePasswordBinding

class ChangePasswordFragment :
    BaseFragment<FragmentChangePasswordBinding>(R.layout.fragment_change_password) {
    private var _binding: FragmentChangePasswordBinding? = null
    override val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar2.setNavigationOnClickListener() {
            activity?.onBackPressed()
        }
    }

}