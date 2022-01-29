package ru.fefu.activitytracker.tracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.FragmentProfileSwitchBinding

class ProfileFragmentSwitch: Fragment() {
    private var _binding: FragmentProfileSwitchBinding? = null

    private val binding get() = _binding!!

    companion object {
        fun newInstance(): ActivityFragmentSwitch {
            return ActivityFragmentSwitch()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileSwitchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            childFragmentManager.beginTransaction().apply {
                add(
                    R.id.fragment_container_profile_switch,
                    ProfileFragment(),
                    "profile"
                )
                commit()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}