package com.example.ecm2425coursework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {

    EditText emailEditText,passwordEditText;
    Button LoginButton;
    TextView createAccTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        emailEditText = findViewById(R.id.eMail_text);
        passwordEditText = findViewById(R.id.passWord_text);
        LoginButton = findViewById(R.id.Login);
        createAccTextView = findViewById(R.id.createAcc_button);


        LoginButton.setOnClickListener((v)-> loginUser() );
        createAccTextView.setOnClickListener((v)-> startActivity(new Intent(LoginPage.this, AccountCreation.class)));
    }

   void loginUser() {
       String email = emailEditText.getText().toString();
       String password = passwordEditText.getText().toString();


       boolean validData = dataValidation(email, password);
       if (!validData) {
           return;
       }

       LogIntoFirebaseAccount(email, password);
   }

   void LogIntoFirebaseAccount(String email, String password){
       FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
       firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
               if (task.isSuccessful()) {
                   // If login is successful
                   if(firebaseAuth.getCurrentUser().isEmailVerified()) {
                       // Sends user to main activity
                       startActivity(new Intent(LoginPage.this, MainActivity.class));
                   }
               } else {
                   // If login fails
                   Toast.makeText(LoginPage.this, "Unverified email address", Toast.LENGTH_SHORT).show();
               }

           }
       });

   }


    boolean dataValidation(String email, String password){

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Invalid Email, try again !");
            return false;
        }
        if(password.length()<7){
            passwordEditText.setError("Password needs to be longer than 7 characters");
            return false;
        }

        return true;
    }
}
