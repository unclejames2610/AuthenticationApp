package com.example.authenticationapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContentView(R.layout.activity_main)
        val buttonSignUp = findViewById<Button>(R.id.buttonSignUp)
        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val textViewResponse = findViewById<TextView>(R.id.textViewResponse)
        val textViewSignInLink = findViewById<TextView>(R.id.textViewSignInLink)
        val textViewSignUpLink = findViewById<TextView>(R.id.textViewSignUpLink)
        val textViewForgotPassword = findViewById<TextView>(R.id.textViewForgotPassword)

        textViewForgotPassword.setOnClickListener {
            val intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intent)
        }

        textViewSignInLink.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
        buttonSignUp.setOnClickListener {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            if (editTextEmail.text.toString().isEmpty() || editTextPassword.text.toString().isEmpty())
                textViewResponse.text = "Email Address or Password is not provided"
            else{
                auth.createUserWithEmailAndPassword(
                    editTextEmail.text.toString(), editTextPassword.text.toString()
                ).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful){
                        auth.currentUser?.sendEmailVerification()
                                ?.addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        textViewResponse.text = "Sign Up successful. Email verification is sent."
                                        val intent = Intent(this, SignInActivity::class.java)
                                        startActivity(intent)
                                        finish()
//                        val user = auth.currentUser
//                        updateUI(user)
                                    }
                                }
                    }
                    else{
                        textViewResponse.text = "Sign Up Failed"
//                        updateUI(null)
                    }

                }
            }
        }
    }

//    private fun updateUI(currentUser: FirebaseUser?){
//
//    }
}