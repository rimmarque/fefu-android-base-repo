package ru.fefu.activitytracker.map

import android.app.Activity
import android.app.FragmentManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.fefu.activitytracker.App
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.ActivityNewBinding
import ru.fefu.activitytracker.room.ActivityRoom

class NewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarNewActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        supportFragmentManager.beginTransaction().apply {
            add(
                R.id.fragment_container_map,
                StartTrackingFragment.newInstance(),
                "start_tracking"
            )
            commit()
        }

    }

}