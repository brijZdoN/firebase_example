package com.example.brijj.jsonexample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Home extends Fragment {
    ArrayList<Post> arrayList;
    RecyclerView recyclerView;
    FloatingActionButton fab;
    Myadapter myadapter;
    adapter ad;
    Toolbar toolbar;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    String username;
    public static  int count;
    ProgressBar progressBar;

    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler);
        fab = (FloatingActionButton) v.findViewById(R.id.f);
        toolbar = v.findViewById(R.id.toolbar);
       progressBar=v.findViewById(R.id.progress);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        arrayList = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
      // firebaseDatabase.setPersistenceEnabled(true);


         firebaseDatabase.getInstance().getReference().child("posts").addChildEventListener(new ChildEventListener() {
             @Override
             public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                 Post post =dataSnapshot.getValue(Post.class);
                 post.setKey(dataSnapshot.getKey());
                 String data = post.getData();
                 String username = post.getUname();
                 String uri=post.getUri();
                 Post user = new Post(username, data,uri);
                 arrayList.add(user);
                 myadapter.notifyDataSetChanged();
                 progressBar.setVisibility(View.GONE);

             }

             @Override
             public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

             }

             @Override
             public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

             }

             @Override
             public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });

        firebaseDatabase.getInstance().getReference()
                                      .child("Users")
                                      .child(firebaseUser.getUid())
                                      .addValueEventListener(new ValueEventListener()
                                      {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User us = dataSnapshot.getValue(User.class);
                username = us.getUsername();
                Snackbar.make(getView(), us.getUsername(), Snackbar.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Unable to fatch the data", Toast.LENGTH_SHORT).show();

            }
        });



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add a = new add();
                Bundle bundle = new Bundle();
                bundle.putString("uname", username);
                a.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.righttoleft, R.anim.righttoleftout).add(R.id.m, a).addToBackStack(null).commit();
            }
        });
        //myadapter = new Myadapter(arrayList, getContext());
        ad=new adapter(arrayList,getContext());
        //  setdata();
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(ad);



        return v;
    }




}
