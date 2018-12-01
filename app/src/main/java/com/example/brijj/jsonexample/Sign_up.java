package com.example.brijj.jsonexample;




import android.app.ProgressDialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;


import java.util.concurrent.Executor;


/**
 * A simple {@link Fragment} subclass.
 */
public class Sign_up extends Fragment implements  View.OnClickListener {
    TextView logo;
    public String TAG="sign up";
    //private String email, password;
    ImageView emailogo, passwordlogo,userlogo,phlogo;
    // EditText mail;
    TextInputEditText mail,pass,name,phno;
    //EditText pass;
    Button signup;
    public ProgressDialog dialog;
    private FirebaseAuth firebaseAuth;
    Toolbar toolbar;
    public Sign_up() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sign_up, container, false);
        logo = (TextView) v.findViewById(R.id.logo);
        toolbar=v.findViewById(R.id.toolbar);
        toolbar.setTitle("Sign up");
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        emailogo = (ImageView) v.findViewById(R.id.mailicon);
        passwordlogo = (ImageView) v.findViewById(R.id.passwordicon);
        mail = (TextInputEditText) v.findViewById(R.id.mail);
        pass = (TextInputEditText) v.findViewById(R.id.pass);
        userlogo=(ImageView)v.findViewById(R.id.userlogo);
        phlogo=(ImageView)v.findViewById(R.id.phlogo);

        name=(TextInputEditText)v.findViewById(R.id.name);
        signup = (Button) v.findViewById(R.id.signup);
        phno=(TextInputEditText)v.findViewById(R.id.phone);
        signup.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(getActivity());
        return v;
    }



    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.signup: register();
                break;
            // case R.id.back:getActivity().getSupportFragmentManager().beginTransaction().remove(Sign_up.this).commit();
            //                     break;

        }
        Log.d(TAG,"onclicksuccess");
        // email = mail.getText().toString();
        //   password = pass.getText().toString();
        //  register(email,password);

    }

    private void register() {
        final String email = mail.getText().toString();
        String password = pass.getText().toString();
        final String uname=name.getText().toString().trim();
        final String phone=phno.getText().toString();
        Log.d(TAG,"register section");

        if (TextUtils.isEmpty(email)) {
            mail.setError("enter email");
            return;
        } else if (TextUtils.isEmpty(password)) {
            pass.setError("enter password");
            return;
        } else if (password.length() < 8) {
            pass.setError("min 8 characters");
            return;
        }else if (!email.endsWith("@gmail.com"))
        {
            mail.setError("Format error");
            return;
        }
        else if (TextUtils.isEmpty(uname))
        {
            name.setError("Enter userName");
            return;
        }
        else
        if (!(phone.length() ==10))
        {
            phno.setError("Enter valid number");
        }
        dialog.setTitle("Registering Please Wait...");
        dialog.setMessage("processing...");
        dialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Log.d(TAG,"create ");
                            User user=new User(uname,email,phone);
                            FirebaseDatabase.getInstance().getReference().child("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful())
                                            {
                                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                                startActivity(intent);
                                                getActivity().finish();
                                               getActivity().overridePendingTransition(R.anim.righttoleft, R.anim.righttoleftout);
                                                dialog.dismiss();
                                            }
                                            else
                                            {
                                                Toast.makeText(getActivity(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });


                        }
                        else
                        {
                            dialog.dismiss();
                            Toast.makeText(getContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();


                        }
                    }
                });




    }
}