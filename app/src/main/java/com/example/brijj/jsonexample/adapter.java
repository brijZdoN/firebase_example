package com.example.brijj.jsonexample;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{  ArrayList<Post> arrayList;
   Context context;
   public static final int IMAGE_TYPE=2;
   public static final int TEXT_TYPE=1;
    public adapter(ArrayList<Post> arrayList, Context context)
    {
        this.arrayList=arrayList;
        this.context=context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {   View view;
        switch(viewType)
            {
                case TEXT_TYPE:view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
                               return new viewholder1(view);
                case IMAGE_TYPE:view=LayoutInflater.from(parent.getContext()).inflate(R.layout.imagerow,parent,false);
                                return new viewholderwithimage(view);
            }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {        Post post=arrayList.get(position);
                   switch (holder.getItemViewType())
                   {
                       case TEXT_TYPE:
                                    viewholder1 textholder=(viewholder1)holder;
                                    textholder.username.setText(post.getUname());
                                    textholder.content.setText(post.getData());
                                    break;
                       case IMAGE_TYPE:viewholderwithimage imageholder=(viewholderwithimage)holder;
                                       imageholder.username.setText(post.getUname());
                                       imageholder.content.setText(post.getData());
                           Picasso.with(context).load(post.getUri()).fit().into(imageholder.imagedata);

                   }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
  public class viewholder1 extends RecyclerView.ViewHolder
  {
      TextView username,content,dte,id;
      public CardView card;
      ImageView like;

      public viewholder1(View itemView) {
          super(itemView);
          username=(TextView)itemView.findViewById(R.id.username);
          content=(TextView)itemView.findViewById(R.id.content);
          card=(CardView)itemView.findViewById(R.id.card);
          dte=(TextView)itemView.findViewById(R.id.date);
          like=(ImageView)itemView.findViewById(R.id.like);
          id=(TextView)itemView.findViewById(R.id.id);
      }
  }
  public class viewholderwithimage extends RecyclerView.ViewHolder
  {   TextView username,content,likecount,commentcount,date;
  ImageView imagedata,like,comment;
  CardView card;
      public viewholderwithimage(View itemView)
      {
          super(itemView);
          card=itemView.findViewById(R.id.card);
          username=itemView.findViewById(R.id.username);
          content=itemView.findViewById(R.id.content);
          date= itemView.findViewById(R.id.date);
          imagedata=itemView.findViewById(R.id.postimage);
          like=itemView.findViewById(R.id.like);
          //likecount=itemView.findViewById(R.id.likecount);
        //  comment=itemView.findViewById(R.id.comment);
        //  commentcount=itemView.findViewById(R.id.commentcount);
      }
  }
    @Override
    public int getItemViewType(int position)
    {   if(arrayList.get(position).getUri()!=null)

            return IMAGE_TYPE;

        else if (arrayList.get(position).getUri()==null)

               return TEXT_TYPE;

        else return Integer.parseInt(null);
    }
}
