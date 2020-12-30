package com.example.android4a.presentation.register

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.android4a.domain.entity.User
import com.example.android4a.presentation.list.DogListActivity
import com.example.android4a.presentation.main.MainViewModel
import com.example.android4a.R
import com.example.android4a.presentation.main.RegisterError
import com.example.android4a.presentation.main.RegisterSuccess
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.view.*
import org.koin.android.ext.android.inject
import java.util.*


class RegisterActivity : AppCompatActivity(){
    private val registerViewModel : RegisterViewModel by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        validButton()

    }

    private fun validButton(){
        registerViewModel.registerLiveData.observe(this, Observer {
            when (it) {
                is RegisterSuccess -> {
                    //Toast.makeText(this, "Inscription success!", Toast.LENGTH_LONG).show()
                    Thread.sleep(2000)
                    val intent = Intent(this@RegisterActivity, DogListActivity::class.java)
                    this@RegisterActivity.startActivity(intent)
                }
                is RegisterError -> {

                    AlertDialog.Builder(this)
                        .setTitle("Error !")
                        .setMessage("Email address already exists\nPlease register with another email address")
                        .setNegativeButton("OK") { dialog, which ->
                            dialog.dismiss()
                        }
                        .show()
                }
            }
        })

        valider_button.setOnClickListener{
            val newUserEmail = new_username.text.toString().trim()
            val newUserPassword = new_password.text.toString().trim()
            val newUser = User(newUserEmail, newUserPassword)

            if (newUserEmail != "" || newUserPassword != ""){
                if (newUserPassword.length > 5){
                    registerViewModel.onClickedRegister(newUser)
                    findViewById<EditText>(R.id.new_username).text = null
                }else{
                    AlertDialog.Builder(this)
                        .setTitle("Error !")
                        .setMessage("Password at least 6 characters.")
                        .setNegativeButton("OK") { dialog, which ->
                            dialog.dismiss()
                        }
                        .show()
                }

            }
            findViewById<EditText>(R.id.new_password).text = null

        }


    }
}
