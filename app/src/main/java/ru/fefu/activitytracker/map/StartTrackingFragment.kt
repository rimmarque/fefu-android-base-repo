package ru.fefu.activitytracker.map

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import ru.fefu.activitytracker.App
import ru.fefu.activitytracker.BaseFragment
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.adapters.NewActivityAdapter
import ru.fefu.activitytracker.databinding.FragmentStartTrakingBinding
import ru.fefu.activitytracker.lists.ListActivityTypes
import ru.fefu.activitytracker.room.ActivityRoom

class StartTrackingFragment :
        BaseFragment<FragmentStartTrakingBinding>(R.layout.fragment_start_traking) {
    private val listActivityTypes = ListActivityTypes()
    private val adapterTypes = NewActivityAdapter(listActivityTypes.getItem())


    companion object {
        fun newInstance(): StartTrackingFragment {
            return StartTrackingFragment()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.rcViewListTypes) {
            adapter = adapterTypes
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        binding.buttonStart.setOnClickListener {
            val endTime = System.currentTimeMillis()
            val startTime = endTime - (600000..86400000).random()
            App.INSTANCE.db.activityDao().insert (
                ActivityRoom (
                    0,
                    adapterTypes.selected,
                    startTime,
                    endTime,
                    mutableListOf<Pair<Double, Double>>()
                )
            )

            parentFragmentManager.beginTransaction().apply {
                replace(
                    R.id.fragment_container_map,
                    ProcessTrackingFragment.newInstance(),
                    "process_tracking"
                )
                addToBackStack(null)
                commit()
            }
        }

//        adapterTypes.setItemClickListener {
//
//        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }



}