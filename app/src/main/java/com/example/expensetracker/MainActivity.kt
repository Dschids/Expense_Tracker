package com.example.expensetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.expensetracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var _main_binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _main_binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_main_binding.root)

        var SP = getSharedPreferences(getString(R.string.my_set_file), MODE_PRIVATE)
        var myEdit = SP.edit()

//        myEdit.apply {
//            putString("username", "")
//            putString("password", "")
//            commit()
//        }


        val toExpensesMain = Intent(this@MainActivity, ExpensesMain::class.java)

        _main_binding.btnLogin.setOnClickListener {

            var enteredName = _main_binding.etUserName.text.toString().lowercase()
            var enteredPass = _main_binding.etPassword.text.toString()

            // check if username and password exist
            if (passwordExists()){
                if (checkUserNameUserPass(enteredName, enteredPass)){

                    startActivity(toExpensesMain)
                }
            } else {
                if (fieldsFilled(enteredName, enteredPass)){
                    myEdit.apply{
                        putString("username", _main_binding.etUserName.text.toString().lowercase())
                        putString("password", _main_binding.etPassword.text.toString())
                        commit()
                    }
                    startActivity(toExpensesMain)
                }

            }
        }
    }

    // checks whether a username and password are saved in shared preferences
    private fun passwordExists(): Boolean {
        var SP = getSharedPreferences(getString(R.string.my_set_file), MODE_PRIVATE)
        val userName = SP.getString("username", "")
        val userPass = SP.getString("password", "")

        return !(userName!!.isEmpty() && userPass!!.isEmpty())
    }

    /* check whether the entered username and password match the saved user name and password
    and return a boolean
     */
    private fun checkUserNameUserPass(enteredName: String, enteredPass: String): Boolean{
        var SP = getSharedPreferences(getString(R.string.my_set_file), MODE_PRIVATE)
        val userName = SP.getString("username", "")
        val userPass = SP.getString("password", "")
        var nameMatch = false
        var passMatch = false

        // check if enteredname is same as what is saved in shared preference
        if (enteredName == userName){
            nameMatch = true
            // check if enteredpass is same as what is saved in shared preferences
            if (enteredPass == userPass){
                passMatch = true
            }else {
                if (enteredPass.isEmpty()){
                    Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(this, "Incorrect password", Toast.LENGTH_SHORT).show()
                }
            }
        }else {
            if (enteredName.isEmpty()){
                Toast.makeText(this, "Please enter a user name", Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(this, "Unknown user name", Toast.LENGTH_SHORT).show()
            }
        }

        return (nameMatch && passMatch)
    }


    // check if username and password have anything in them and returns a boolean
    private fun fieldsFilled(enteredName: String, enteredPass: String): Boolean {
        var nameFilled = false
        var passFilled = false

        // check if something is entered in username field
        if (enteredName.isEmpty()){
            Toast.makeText(this, "Please enter a user name", Toast.LENGTH_SHORT).show()
        }else {
            nameFilled = true
            // check if something is in the password field
            if (enteredPass.isEmpty()){
                Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show()
            }else {
                passFilled = true
            }
        }


        return (nameFilled && passFilled)
    }
}