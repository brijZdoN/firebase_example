package com.example.brijj.jsonexample;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageButton;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

import static android.app.Activity.RESULT_OK;
import static java.security.AccessController.getContext;
public class add extends Fragment {
   ImageView postimage,add;
   EditText edit;
   public Uri uri;
    private static final int PICK_ACTION_IMAGE=1;
    Toolbar toolbar;
    String username;
    private StorageTask storageTask;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    public StorageReference storageReference;
     ProgressDialog  dialog;
    ImageButton btn;
    Bitmap image;


   DatabaseReference firebaseDatabase;
    public add(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     View v= inflater.inflate(R.layout.fragment_add, container, false);
        edit=(EditText)v.findViewById(R.id.editText);
        btn=(ImageButton)v.findViewById(R.id.image);
        toolbar=v.findViewById(R.id.toolbar);
        toolbar.setTitle("Add data");
        add=v.findViewById(R.id.add);
        dialog=new ProgressDialog(getContext());
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getImage();
                pickimage();
            }
        });
        postimage=v.findViewById(R.id.postimage);
        postimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // getImage();
                pickimage();
            }
        });
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        storageReference= FirebaseStorage.getInstance().getReference().child(firebaseUser.getUid()).child("upload");
        firebaseDatabase= FirebaseDatabase.getInstance().getReference().child("posts");
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn.setEnabled(true);
                Toast.makeText(getContext(), "btn clicked", Toast.LENGTH_SHORT).show();
                final String s=edit.getText().toString().trim();
                edit.clearFocus();
                edit.setFocusable(false);
                Bundle bundle=getArguments();
                final String user=bundle.getString("uname");
                if(!(storageTask!=null &&storageTask.isInProgress()))
                {    dialog.setTitle("Uploading");

                   dialog.show();
                   if(uri!=null)
                   {
                       upload(user,s);
                   }
                   else
                       { Post post =new Post(user,s);
                         firebaseDatabase.push().setValue(post);
                         dialog.hide();
                         getActivity().getSupportFragmentManager().beginTransaction().remove(add.this).commit();

                        }

                }
            }
        });
        return v ;
    }

    private void upload(final String user, final String s)
    {
        StorageReference ref=storageReference.child(String.valueOf(System.currentTimeMillis()));
       storageTask=ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
           @Override
           public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
           {
             Post post=new Post(user,s,taskSnapshot.getDownloadUrl().toString());
             String postid=firebaseDatabase.push().getKey();
             firebaseDatabase.child(postid).setValue(post);
             dialog.hide();
               getActivity().getSupportFragmentManager().beginTransaction().remove(add.this).commit();

           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               Toast.makeText(getContext(), "Unable to upload", Toast.LENGTH_SHORT).show();
               dialog.hide();
           }
       }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>()
       {
          @Override
         public void onProgress(UploadTask.TaskSnapshot taskSnapshot)
         {
            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
            dialog.setMessage("Uploaded " + ((int) progress) + "%...");
         }
       });
    }
private void pickimage()
  {
     final CharSequence[] items={"Take photo","Choose from gallery","cancel"};
     AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
    builder.setTitle("Choose photo");
    builder.setItems(items, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            switch (i)
            {
                case 0:Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent,0);
                        break;
                case 1:Intent intent1=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                         startActivityForResult(intent1,1);
                         break;
                case 2:dialogInterface.dismiss();
                       break;

            }

        }
    });
    builder.create().show();
  }


    private void getImage()
    {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent,"Select a file to upload"),PICK_ACTION_IMAGE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case 0:if(resultCode==RESULT_OK )
                       image=(Bitmap)data.getExtras().get("data");
                       postimage.setImageBitmap(image);
                       break;


            case 1:    if(requestCode==PICK_ACTION_IMAGE&&resultCode==RESULT_OK &&data!=null &&data.getData()!=null)
            {
                uri=data.getData();

                Picasso.with(getContext()).load(uri).into(postimage);
                break;
            }
        }

    }

}



