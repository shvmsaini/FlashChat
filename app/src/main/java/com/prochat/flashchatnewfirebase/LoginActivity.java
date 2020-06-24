package com.prochat.flashchatnewfirebase;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import static com.google.firebase.auth.FirebaseAuth.getInstance;


public class LoginActivity extends AppCompatActivity {
    private AutoCompleteTextView mEmailView;
    private TextInputEditText mPasswordView;

    // TODO: Grab an instance of FirebaseAuth
    private FirebaseAuth Auth = getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // TODO: Add member variables here:
        // UI references.
        keepLoggedIn();

        mEmailView =findViewById(R.id.login_email);
        mPasswordView = findViewById(R.id.login_password);

        //Keyboard sign in action
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.integer.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

    }

    // Executed when Sign in button pressed
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void signInExistingUser(View v)   {
        // TODO: Call attemptLogin() here
        attemptLogin();

    }

    // Executed when Register button pressed
    public void registerNewUser(View v) {
        Intent intent = new Intent(this, com.prochat.flashchatnewfirebase.RegisterActivity.class);
        finish();
        startActivity(intent);
    }

    // TODO: Complete the attemptLogin() method
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void attemptLogin() {
        String email = mEmailView.getText().toString();
        String password = Objects.requireNonNull(mPasswordView.getText()).toString();
        if(email.equals("")||password.equals("")){
            View focusView;
            if (email.equals("")){
                focusView = mEmailView;
            } else {
                focusView = mPasswordView;
            }
            Toast.makeText(this,"Email and password are required",Toast.LENGTH_SHORT).show();
            focusView.requestFocus();
            return;
        }
        Toast.makeText(this,"Login in progress...",Toast.LENGTH_SHORT).show();

        // TODO: Use FirebaseAuth to sign in with email & password
         Auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
             @Override
             public void onComplete(@NonNull Task<AuthResult> task) {
                 Log.d("FlashChat","signInWithEmail() onComplete: " + task.isSuccessful());
                 if(!task.isSuccessful()){
                    Log.d("FlashChat","Problem signing in:"+task.getException());
                    showErrorDialog();
                 }
                 else{

                     Intent intent = new Intent(LoginActivity.this,MainChatActivity.class);
                     finish();

                     startActivity(intent);
                 }
             }
         });


    }
    private void keepLoggedIn(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            Intent i = new Intent(LoginActivity.this, MainChatActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {
            // User is signed out
            Log.d("FlashChat", "onAuthStateChanged:signed_out");
        }

    }

    // TODO: Show error on screen with an alert dialog
    private void showErrorDialog(){
        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage("Login Attempt failed")
                .setPositiveButton(android.R.string.ok,null)
                .setIcon(android.R.drawable.ic_dialog_alert).show(); //android.R access the android resources
    }


}