package ru.fefu.activitytracker.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.fefu.activitytracker.tracker.ActivityFragment
import ru.fefu.activitytracker.tracker.MyActivityFragment
import ru.fefu.activitytracker.tracker.UsersActivityFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = 2
    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0->{
                MyActivityFragment()
            }
            1->{
                UsersActivityFragment()
            }
            else->{
                ActivityFragment()
            }
        }
    }

}