package ru.fefu.activitytracker.map

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import ru.fefu.activitytracker.BaseFragment
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.FragmentProcessTrackingBinding
import ru.fefu.activitytracker.lists.ActivityType

class ProcessTrackingFragment :
BaseFragment<FragmentProcessTrackingBinding>(R.layout.fragment_process_tracking) {
    private var _binding: FragmentProcessTrackingBinding? = null
    override val binding get() = _binding!!
//    private val item = item_

    companion object {
        fun newInstance(): ProcessTrackingFragment {
            return ProcessTrackingFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnFinish: ImageButton = view.findViewById(R.id.button_finish)
        btnFinish.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.buttonPause.setOnClickListener {

        }

        binding.buttonFinish.setOnClickListener {

        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProcessTrackingBinding.inflate(inflater, container, false)
        return binding.root
    }

}