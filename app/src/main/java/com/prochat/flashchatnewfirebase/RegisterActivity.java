package com.prochat.flashchatnewfirebase;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    // Constants
    public static final String CHAT_PREFS = "ChatPrefs";
    public static final String DISPLAY_NAME_KEY = "username";

    // TODO: Add member variables here:
    // UI references.
    private AutoCompleteTextView mEmailView;
    private TextInputEditText mPasswordView;
    private TextInputEditText mConfirmPasswordView;
    private AutoCompleteTextView mUsernameView;
    // Firebase instance variables
    private FirebaseAuth Auth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // views
        mUsernameView = findViewById(R.id.register_username);
        mEmailView = findViewById(R.id.register_email);
        mPasswordView =  findViewById(R.id.register_password);
        mConfirmPasswordView  =  findViewById(R.id.register_confirm_password);


        // Keyboard sign in action
        mConfirmPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.integer.register_form_finished || id == EditorInfo.IME_NULL) {
                    attemptRegistration();
                    return true;
                }
                return false;
            }
        });

        // TODO: Get hold of an instance of FirebaseAuth
        Auth = FirebaseAuth.getInstance();

    }

    // Executed when Sign Up button is pressed.

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void signUp(View v) {
        attemptRegistration();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void attemptRegistration() {

        // Reset errors displayed in the form.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = Objects.requireNonNull(mPasswordView.getText()).toString();

        boolean cancel = false;
        View focusView = null;
        Log.d("FlashChat", "TextUtils.isEmpty(password): " + TextUtils.isEmpty(password));
        Log.d("FlashChat", "TextUtils.isEmpty(password) && !isPasswordValid(password): " + (TextUtils.isEmpty(password) && isPasswordValid(password)));


        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }
        else {
            // TODO: Call create FirebaseUser() here
            createFirebaseUser();


        }
    }

    private boolean isEmailValid(String email) {
        // You can add more checking logic here.
        return (email.contains("@"));
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private boolean isPasswordValid(String password) {
        //TODO: Add own logic to check for a valid password (minimum 6 characters)
        String confirmPassword = Objects.requireNonNull(mConfirmPasswordView.getText()).toString();
        return !confirmPassword.equals(password) || password.length() <= 4;

    }

    // TODO: Create a Firebase user


        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        private void createFirebaseUser(){
            String mEmail = mEmailView.getText().toString();
            String mPassword = Objects.requireNonNull(mPasswordView.getText()).toString();
            Auth.createUserWithEmailAndPassword(mEmail,mPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d("FlashChat", "createUser onComplete: " + task.isSuccessful());
                    if(!task.isSuccessful()){
                        Log.d("FlashChat","User creation failed",task.getException());
                        showErrorDialog();
                    }else{
                        saveDisplayName();
                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                        finish();
                        startActivity(intent);
                    }
                }
            });

        }



    // TODO: Save the display name to Shared Preferences
        private void saveDisplayName(){
            FirebaseUser user = Auth.getCurrentUser();
            String displayName = mUsernameView.getText().toString();

            if (user !=null) {
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(displayName)
                        .build();

                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("FlashChat", "User name updated.");
                                }
                            }
                        });

            }

     /*    String displayName= mUsernameView.getText().toString();
         SharedPreferences prefs = getSharedPreferences(CHAT_PREFS,MODE_PRIVATE);
         prefs.edit().putString(DISPLAY_NAME_KEY,displayName).apply();*/
    }

    // TODO: Create an alert dialog to show in case registration failed
        private void showErrorDialog(){
         new AlertDialog.Builder(this)
                 .setTitle("Oops")
                 .setMessage("Registration Attempt failed")
                 .setPositiveButton(android.R.string.ok,null)
                 .setIcon(android.R.drawable.ic_dialog_alert).show(); //android.R access the android resources
        }



}
