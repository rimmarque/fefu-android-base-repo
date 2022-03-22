package ru.fefu.activitytracker

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import ru.fefu.activitytracker.adapters.ItemAdapter
import ru.fefu.activitytracker.databinding.FragmentUsersActivityBinding
import ru.fefu.activitytracker.lists.ListItem
import ru.fefu.activitytracker.lists.UsersListRepository

class UsersFragmentActivity : BaseFragment<FragmentUsersActivityBinding>(R.layout.fragment_users_activity) {
    private val usersListRepository = UsersListRepository()
    private val adapterItems = ItemAdapter(usersListRepository.getItem())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.rcView) {
            adapter = adapterItems
            layoutManager = LinearLayoutManager(requireContext())
        }

        adapterItems.setItemClickListener {
            val manager = activity?.supportFragmentManager?.findFragmentByTag("activityFragment")?.childFragmentManager
            manager?.beginTransaction()?.apply {
                manager.fragments.forEach(::hide)
                replace(
                    R.id.activity_fragment_flow_container,
                    UsersActivityDetailsFragment.newInstance(usersListRepository.getItem()[it] as ListItem.Item),
                    "tadUsers"
                )
                addToBackStack(null)
                commit()
            }
        }
    }
}