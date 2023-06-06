package com.example.ecm2425coursework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class AccountCreation extends AppCompatActivity {

    EditText emailEditText,passwordEditText,secondPasswordEditText;
    Button createAccountButton;
    TextView logInButtonTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);

        emailEditText = findViewById(R.id.eMail_text);
        passwordEditText = findViewById(R.id.passWord_text);
        secondPasswordEditText = findViewById(R.id.secondpassWord_text);
        createAccountButton = findViewById(R.id.createAccount);
        logInButtonTextView = findViewById(R.id.logIn_button);

        // Results of pressing buttons found on the page
        createAccountButton.setOnClickListener(v-> createAccount());
        logInButtonTextView.setOnClickListener(v-> finish());

    }

    void createAccount(){
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String secondPassword = secondPasswordEditText.getText().toString();



        boolean validData = dataValidation(email, password, secondPassword);
        if(!validData){
            return;
        }

        createFirebaseAccount(email,password);

    }

    void createFirebaseAccount(String email, String password) {
        // Creates the users account in firebase

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(AccountCreation.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            // Result if account is created
                            Toast.makeText(AccountCreation.this, "Account is created and waiting for verification", Toast.LENGTH_SHORT).show();
                            firebaseAuth.getCurrentUser().sendEmailVerification();
                            firebaseAuth.signOut();
                            finish();
                        }else{
                            // Result if account is not created
                            Toast.makeText(AccountCreation.this, "Account failed to create, try again", Toast.LENGTH_SHORT).show();
                        }


                    }
                }
        );


    }


    boolean dataValidation(String email, String password, String secondPassword){

        // Checks if the users sign up details match what is expected and required
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Invalid Email, try again !");
            return false;
        }
        // Password validation
        if(password.length()<7){
            passwordEditText.setError("Password needs to be longer than 7 characters");
            return false;
        }
        if(!password.equals(secondPassword)){
            secondPasswordEditText.setError("Passwords do not match");
            return false;
        }
        return true;
    }
}