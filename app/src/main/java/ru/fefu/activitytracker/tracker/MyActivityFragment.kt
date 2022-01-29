package ru.fefu.activitytracker.tracker

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import ru.fefu.activitytracker.App
import ru.fefu.activitytracker.BaseFragment
import ru.fefu.activitytracker.adapters.ItemAdapter
import ru.fefu.activitytracker.lists.ListItem
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.FragmentMyActivityBinding
import ru.fefu.activitytracker.room.ActivityTypes
import java.time.*

class MyActivityFragment :
    BaseFragment<FragmentMyActivityBinding>(R.layout.fragment_my_activity) {

    private val listRepository = mutableListOf<ListItem>()
    private val listRepositoryDate = mutableListOf<ListItem>()
    private val adapterItems = ItemAdapter(listRepositoryDate)

    val map = mapOf(1 to "Январь", 2 to "Февраль", 3 to "Март",
        4 to "Апрель", 5 to "Май", 6 to "Июнь",
        7 to "Июль", 8 to "Август", 9 to "Сентябрь",
        10 to "Октябрь", 11 to "Ноябрь", 12 to "Декабрь")

    companion object {
        fun getNoun(number: Long, one: String, two: String, five: String) : String {
            var n = number
            n %= 100
            if (n in 5..20) {
                return five
            }
            n %= 10
            if (n == 1L) {
                return one
            }
            if (n in 2..4) {
                return two
            }
            return five
        }

        fun getActivityType(number: Int): String {
            if (number == 0) return ActivityTypes.BICYCLE.type
            else if (number == 1) return ActivityTypes.RUNNING.type
            else return ActivityTypes.STEP.type
        }
    }

    private fun fillDate(activities: List<ListItem>) {
        val today = LocalDate.now()
        var date = ListItem.Date("")
        for (activity in activities) {
            if (activity is ListItem.Item) {
                if (today.isEqual(activity.endTime.toLocalDate())) {
                    if (date.date != "Сегодня") {
                        date = ListItem.Date("Сегодня")
                        listRepositoryDate.add(date)
                    }
                } else {
                    if (date.date != "%s %d года"
                            .format(map.get(activity.endTime.monthValue), activity.endTime.year)) {
                        date = ListItem.Date("%s %d года"
                            .format(map.get(activity.endTime.monthValue), activity.endTime.year))
                        listRepositoryDate.add(date)
                    }
                }

            }
            listRepositoryDate.add(activity)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.rcView) {
            adapter = adapterItems
            layoutManager = LinearLayoutManager(requireContext())
        }

        App.INSTANCE.db.activityDao().getAll().observe(viewLifecycleOwner) {
            listRepository.clear()
            listRepositoryDate.clear()
            for (activity in it) {
                val id = activity.id
                val type = getActivityType(activity.type)
                val startDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(activity.startTime), ZoneId.systemDefault())
                val endDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(activity.endTime), ZoneId.systemDefault())

                val duration = Duration.between(startDate, endDate)
                var seconds: Long = Math.abs(duration.getSeconds())
                val hours = seconds / 3600
                seconds -= hours * 3600
                val minutes = seconds / 60
                val hours_ = getNoun(hours, "час", "часа", "часов")
                val minutes_ = getNoun(minutes, "минута", "минуты", "минут")
                var time = ""
                if (hours > 0) {
                    time = "%d %s %d %s".format(hours, hours_, minutes, minutes_)
                } else {
                    time = "%d %s".format(minutes, minutes_)
                }

                var date = ""
                if (LocalDate.now().isEqual(endDate.toLocalDate())) {
                    date = Duration.between(endDate, LocalDateTime.now()).toHours().toString() +
                            getNoun(Duration.between(endDate, LocalDateTime.now()).toHours(), " час", " часа", " часов") +
                            " назад"
                }
                else {
                    date = "%d.%d.%d".format(endDate.dayOfMonth, endDate.monthValue, endDate.year)
                }

                val distance = "1000 м"
                listRepository.add(ListItem.Item(id, distance, time, date, type, startDate, endDate))
            }
            fillDate(listRepository)
            adapterItems.notifyDataSetChanged()

            if (listRepository.isEmpty()) {
                binding.text.visibility = View.VISIBLE
            } else {
                binding.text.visibility = View.GONE
            }
        }




        adapterItems.setItemClickListener {
            val Activity_ = listRepositoryDate[it] as ListItem.Item
            val idActivity = Activity_.id
            val manager = activity?.supportFragmentManager?.findFragmentByTag("activityFragment")?.childFragmentManager
            manager?.beginTransaction()?.apply {
                manager.fragments.forEach(::hide)
                replace(
                    R.id.activity_fragment_switch_container,
                    MyActivityDetailsFragment.newInstance(idActivity)
                )
                addToBackStack(null)
                commit()
            }
        }
    }
}