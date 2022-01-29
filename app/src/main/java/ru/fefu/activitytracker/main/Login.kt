package ru.fefu.activitytracker.main

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.API.Token
import ru.fefu.activitytracker.App
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.fefu.activitytracker.API.LoginViewModel
import ru.fefu.activitytracker.API.Result
import ru.fefu.activitytracker.tracker.MainActivity

class Login : AppCompatActivity() {
    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel.dataFlow
            .onEach {
                if (it is Result.Success<Token>) {
                    App.INSTANCE.sharedPrefs.edit().putString("token", it.result.token).apply()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else if (it is Result.Errors<Token>) {
                    Toast.makeText(this, it.errors.toString(), Toast.LENGTH_LONG).show()
                    println(it.errors.toString())
                }
            }
            .launchIn(lifecycleScope)

        val btnEnter = findViewById<Button>(R.id.buttonEnter)
        btnEnter.setOnClickListener {
            val login = findViewById<TextInputLayout>(R.id.login_entrance).editText?.text.toString()
            val password = findViewById<TextInputLayout>(R.id.password_entrance).editText?.text.toString()

            viewModel.login(login, password)
        }
    }


    fun back(view: View) {
        onBackPressed()
    }
}
