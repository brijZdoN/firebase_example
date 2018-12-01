package com.example.brijj.jsonexample;


import android.app.ProgressDialog;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class Signin extends Fragment implements View.OnClickListener {
    private FirebaseAuth firebaseAuth;
    String TAG = "signin";
    TextInputEditText mail, pass;
    TextView logo, forgot;
    ProgressDialog progressDialog;
    ImageView emailogo, passwordlogo;
    Toolbar toolbar;

    Button signin, registration;

    public Signin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_signin, container, false);
        logo = (TextView) v.findViewById(R.id.logo);
        toolbar = v.findViewById(R.id.toolbar);
        toolbar.setTitle("Sign In");
        progressDialog = new ProgressDialog(getActivity());
        emailogo = (ImageView) v.findViewById(R.id.mailicon);
        passwordlogo = (ImageView) v.findViewById(R.id.passwordicon);
        mail = (TextInputEditText) v.findViewById(R.id.mail);
        pass = (TextInputEditText) v.findViewById(R.id.pass);
        signin = (Button) v.findViewById(R.id.signin);
        signin.setOnClickListener(this);
        registration = (Button) v.findViewById(R.id.register);
        registration.setOnClickListener(this);
        forgot = (TextView) v.findViewById(R.id.forgot);
        forgot.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            getActivity().finish();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signin://code for signin
                Log.d(TAG, "signin called");
                userLogin();
                break;
            case R.id.register:
                Log.d(TAG, "register called");
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.righttoleft, R.anim.righttoleftout).add(R.id.f, new Sign_up()).addToBackStack(null).commit();
                break;
            case R.id.forgot:
                Toast.makeText(getContext(), "Forgott password clicked", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    private void userLogin() {
        String email = mail.getText().toString().trim();
        String password = pass.getText().toString().trim();
        Log.d(TAG, "inside login");
        //checking if email and passwords are empty
        if (TextUtils.isEmpty(email)) {
            mail.setError("enter email");

            // Toast.makeText(getActivity(), "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }
        if (!email.endsWith("@gmail.com")) {
            mail.setError("Format error");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            pass.setError("enter password");
            // Toast.makeText(getActivity(), "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setTitle("Logging in");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signin ");


                        if (task.isSuccessful()) {
                            String add = firebaseAuth.getCurrentUser().getEmail();
                            Log.d(TAG, add);
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                            getActivity().overridePendingTransition(R.anim.righttoleft, R.anim.righttoleftout);
                            progressDialog.dismiss();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Signin Failed ", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}

