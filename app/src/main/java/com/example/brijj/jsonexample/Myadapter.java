package com.example.brijj.jsonexample;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by brijj on 08-06-2018.
 */

public class Myadapter extends RecyclerView.Adapter<Myadapter.myviewholder>  {
        ArrayList<Post> arrayList;
        Context context;
        FirebaseDatabase firebaseDatabase;
        public final static int TEXT_TYPE=0;
        public final static int IMAGE_TEXT=1;
    private int state=0;
  long date=System.currentTimeMillis();
  SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");
    String dt=sdf.format(date);


       public Myadapter(ArrayList<Post> arrayList, Context context)
       {
           this.arrayList=arrayList;
           this.context=context;
       }
    @Override
    public Myadapter.myviewholder onCreateViewHolder(final ViewGroup parent, int viewType)
    {
        View view;
      myviewholder holder;
        switch (viewType)
        {
            case TEXT_TYPE:view=LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
                           return new myviewholder(view);
        }


            return null;

    }

    @Override
    public void onBindViewHolder(final myviewholder holder, final int position)
    {
          Post obj=arrayList.get(position);
         setAnimation(holder.itemView,position);
         switch (holder.getItemViewType())
         {
             case 0:
                 holder.username.setText(obj.getUname());
                 holder.content.setText(obj.getData());
                 holder.dte.setText(dt);
                 break;

         }


         //holder.id.setText(FirebaseAuth.getInstance());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Toast.makeText(view.getContext(),"Item clicked",Toast.LENGTH_SHORT).show();
            }
        });
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                //holder.like.setBackgroundColor(R.color.black);
                if (state==0)
                {   state++;
                    Toast.makeText(view.getContext(),"liked",Toast.LENGTH_SHORT).show();
                    holder.like.setImageResource(R.drawable.fill);
                }
                else
                {    state--;
                    Toast.makeText(view.getContext(),"Disliked",Toast.LENGTH_SHORT).show();
                    holder.like.setImageResource(R.drawable.like);
                }
            }
        });
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"item clicked",Toast.LENGTH_SHORT).show();
            }

        });
        holder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // AlertDialog.Builder builder=new AlertDialog.Builder(context);
                //  builder.setTitle("Do you want to delete this item");
                //  builder.
                int a=holder.getAdapterPosition();
                //holder.getAdapterPosition();
                arrayList.remove(holder.getPosition());
                notifyItemRemoved(holder.getPosition());
                // int i=firebaseDatabase.getInstance().getReference()
                String i= firebaseDatabase.getInstance().getReference().child("posts").push().getKey();
                firebaseDatabase.getInstance().getReference().child("posts").child(i).removeValue();
                Toast.makeText(context,"item long  clicked",Toast.LENGTH_SHORT).show();
                return true;
            }
        });



    }


    private void setAnimation(View itemView, int position) {
        AlphaAnimation animation=new AlphaAnimation(0.0f,1.0f);
        animation.setDuration(1000);
        itemView.setAnimation(animation);
    }

    @Override
    public int getItemViewType(int position)
    {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }



    public class myviewholder extends  RecyclerView.ViewHolder
    {   TextView username,content,dte,id;
        public CardView card;
     ImageView like;


        public myviewholder(View itemView) {
            super(itemView);
            username=(TextView)itemView.findViewById(R.id.username);
            content=(TextView)itemView.findViewById(R.id.content);
            card=(CardView)itemView.findViewById(R.id.card);
            dte=(TextView)itemView.findViewById(R.id.date);
            like=(ImageView)itemView.findViewById(R.id.like);
            id=(TextView)itemView.findViewById(R.id.id);
        }
    }
}
