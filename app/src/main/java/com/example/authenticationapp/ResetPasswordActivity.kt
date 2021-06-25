package com.example.authenticationapp

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reset_password)

        val editTextEmailReset = findViewById<EditText>(R.id.editTextEmailReset)
        val buttonReset = findViewById<Button>(R.id.buttonReset)
        val textViewResetResponse = findViewById<TextView>(R.id.textViewResetResponse)

        buttonReset.setOnClickListener {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            if (editTextEmailReset.text.toString().isEmpty())
                textViewResetResponse.text = "Email Address is not provided"
            else{
                auth.sendPasswordResetEmail(editTextEmailReset.text.toString()).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful){
                        val user = auth.currentUser
                        textViewResetResponse.text = "Reset Password Link is mailed"
                    }else
                        textViewResetResponse.text = "Password Reset mail could not be sent"
                }
            }

        }

    }
}