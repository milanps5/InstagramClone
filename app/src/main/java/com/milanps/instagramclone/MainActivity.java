package com.milanps.instagramclone;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {
    TextView logInTextView;
    EditText usernameEditText;
    EditText passwordEditText;
    Boolean singUpModeActive = true;

    public void showUserList() {
        Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent event) {
        if (i == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
            signUpClicked(view);
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.logInTextView) {
            Log.i("Switch", "Was tapped");

            Button signUpBotton = findViewById(R.id.signUpButton);

            if (singUpModeActive) {
                singUpModeActive = false;
                signUpBotton.setText("Log In");
                logInTextView.setText("Sign Up");
            } else {
                singUpModeActive = true;
                signUpBotton.setText("Sign Up");
                logInTextView.setText("Log In");
            }
        } else if (view.getId() == R.id.logoImageView || view.getId() == R.id.backgroundLayout) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void signUpClicked(View view) {

        if (usernameEditText.getText().toString().matches("") || passwordEditText.getText().toString().matches("")) {
            Toast.makeText(this, "A username and a password are required.", Toast.LENGTH_SHORT).show();
        } else {
            if (singUpModeActive) {
                ParseUser user = new ParseUser();
                user.setUsername(usernameEditText.getText().toString());
                user.setPassword(passwordEditText.getText().toString());
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Log.i("Sign up", "Success");
                            showUserList();
                        } else {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {
                            Log.i("LogIn", "OK!");
                            showUserList();
                        } else {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logInTextView = findViewById(R.id.logInTextView);
        logInTextView.setOnClickListener(this);

        ImageView logoImageView = findViewById(R.id.logoImageView);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        ConstraintLayout backgroundLayout = findViewById(R.id.backgroundLayout);

        passwordEditText.setOnKeyListener(this);
        logoImageView.setOnClickListener(this);
        backgroundLayout.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null) {
            showUserList();
        }
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
