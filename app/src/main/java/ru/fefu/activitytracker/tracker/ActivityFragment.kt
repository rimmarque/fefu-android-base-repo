package ru.fefu.activitytracker.tracker

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TableLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.adapters.ViewPagerAdapter
import ru.fefu.activitytracker.databinding.FragmentActivityBinding
import ru.fefu.activitytracker.map.NewActivity

class ActivityFragment : Fragment() {
    private lateinit var adapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TableLayout
    private val _binding: FragmentActivityBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myFragment = inflater.inflate(R.layout.fragment_activity, container, false)
        val tabLayout = myFragment.findViewById<TabLayout>(R.id.tab_layout)
        val viewPager2 = myFragment.findViewById<ViewPager2>(R.id.pager)
        val adapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        viewPager2.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager2) {tab, position->
            when(position) {
                0->{
                    tab.text = "Моя"
                }
                1->{
                    tab.text = "Пользователей"
                }
            }
        }.attach()

        val btnFab = myFragment.findViewById<ImageButton>(R.id.button_fab)
        btnFab.setOnClickListener {
            startActivity(Intent(requireActivity(), NewActivity::class.java))
        }

        return myFragment
    }

}
