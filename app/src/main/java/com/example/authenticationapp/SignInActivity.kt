package com.example.authenticationapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContentView(R.layout.sign_in)
        val textViewSignUpLink = findViewById<TextView>(R.id.textViewSignUpLink)
        val buttonSignIn = findViewById<Button>(R.id.buttonSignIn)
        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val textViewResponse = findViewById<TextView>(R.id.textViewResponse)

        textViewSignUpLink.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        buttonSignIn.setOnClickListener {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            if (editTextEmail.text.toString().isEmpty() || editTextPassword.text.toString().isEmpty())
                textViewResponse.text = "Email Address or Password is not provided"
            else{
                auth.signInWithEmailAndPassword(
                        editTextEmail.text.toString(), editTextPassword.text.toString())
                        .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful){
                        textViewResponse.text = "Sign In successful."
                        val user = auth.currentUser
                        updateUI2(user, editTextEmail.text.toString())
                    }
                    else{
                        textViewResponse.text = "Invalid Email or Password"


                    }

                }
            }
        }
    }
    private fun updateUI2(currentUser: FirebaseUser?, emailAdd: String){
        if(currentUser != null){
            if (currentUser.isEmailVerified) {
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("emailAddress", emailAdd)
                startActivity(intent)
                finish()
            }
            else
                Toast.makeText(this, "Email Address is not verified. Please verify your email", Toast.LENGTH_LONG).show()


        }

    }

}