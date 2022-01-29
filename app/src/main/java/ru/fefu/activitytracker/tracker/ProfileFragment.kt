package ru.fefu.activitytracker.tracker

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.fefu.activitytracker.API.ProfileViewModel
import ru.fefu.activitytracker.BaseFragment
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.FragmentProfileBinding
import ru.fefu.activitytracker.API.Result
import ru.fefu.activitytracker.API.User
import ru.fefu.activitytracker.App
import ru.fefu.activitytracker.main.WelcomeScreen

class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    private var _binding: FragmentProfileBinding? = null
    override val binding get() = _binding!!
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        binding.clickTextChangePassword.setOnClickListener {
            val manager = activity?.supportFragmentManager?.findFragmentByTag("profileFragment")?.childFragmentManager
            manager?.beginTransaction()?.apply {
                manager.fragments.forEach(::hide)
                replace(
                    R.id.fragment_container_profile_switch,
                    ChangePasswordFragment(),
                    "profile_change"
                )
                addToBackStack(null)
                commit()
            }
        }

        viewModel.logoutUser
            .onEach {
                if (it is Result.Success<Unit>) {
                    App.INSTANCE.sharedPrefs.edit().remove("token").apply()
                    val intent = Intent(requireActivity(), WelcomeScreen::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                else if (it is Result.Errors<Unit>) {
                    Toast.makeText(requireContext(), it.errors.toString(), Toast.LENGTH_LONG).show()
                }
            }
            .launchIn(lifecycleScope)

        viewModel.profile
            .onEach {
                if (it is Result.Success<User>) {
                    binding.loginProfile.editText?.setText(it.result.login)
                    binding.nameProfile.editText?.setText(it.result.name)
                }
                else if (it is Result.Errors<User>) {
                    Toast.makeText(requireContext(), it.errors.toString(), Toast.LENGTH_LONG).show()
                    println(it.errors.toString())
                }
            }
            .launchIn(lifecycleScope)
        viewModel.getProfile()

        binding.buttonExit.setOnClickListener {
            viewModel.logout()
        }
    }
}