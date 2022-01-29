package ru.fefu.activitytracker.main

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.w3c.dom.Text
import ru.fefu.activitytracker.API.RegisterViewModel
import ru.fefu.activitytracker.API.Token
import ru.fefu.activitytracker.API.Result
import ru.fefu.activitytracker.App
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.tracker.MainActivity

class Registration : AppCompatActivity() {
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]

        viewModel.dataFlow
            .onEach {
                if (it is Result.Success<Token>) {
                    App.INSTANCE.sharedPrefs.edit().putString("token", it.result.token).apply()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                else if (it is Result.Errors<Token>) {
                    Toast.makeText(this, it.errors.toString(), Toast.LENGTH_LONG).show()
                    println(it.errors.toString())
                }
            }
            .launchIn(lifecycleScope)

        val btnRegistration = findViewById<Button>(R.id.buttonRegistration)
        btnRegistration.setOnClickListener {
            val login = findViewById<TextInputLayout>(R.id.login_registration).editText?.text.toString()
            val name = findViewById<TextInputLayout>(R.id.name_registration).editText?.text.toString()
            val password = findViewById<TextInputLayout>(R.id.password_registration).editText?.text.toString()
            val repeatPassword = findViewById<TextInputLayout>(R.id.repeat_password_registration).editText?.text.toString()
            val gender: Int
            if (findViewById<RadioButton>(R.id.radioButtonMale).isChecked) {
                gender = 0
            } else if (findViewById<RadioButton>(R.id.radioButtonFemale).isChecked) {
                gender = 1
            } else {
                gender = 2
            }

            if (password == repeatPassword) {
                viewModel.register(login, name, password, gender)
            } else {
                Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun back(view: View) {
        onBackPressed()
    }
}