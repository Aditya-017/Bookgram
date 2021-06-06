package com.internshala.bookgram.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.internshala.bookgram.GlideApp;
import com.internshala.bookgram.R;
import com.internshala.bookgram.model.Book;



import java.util.ArrayList;

public class DashboardRecyclerAdapter extends RecyclerView.Adapter<DashboardRecyclerAdapter.DashboardViewHolder> {
    Context context;
    ArrayList<Book> itemList;
    public DashboardRecyclerAdapter(Context context, ArrayList<Book> itemList) {
    this.context=context;
    this.itemList=itemList;

    }

    @NonNull
    @Override
    public DashboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//responsible for creating initial 10 view holders
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_dashboard_single_row,parent,false);
        return new DashboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  DashboardViewHolder holder, int position) {//responsible for recycling and reusing viewHolders
        final Book book= itemList.get(position);
        holder.bookName.setText(book.bookName);
        holder.bookAuthor.setText(book.bookAuthor);
        holder.bookCost.setText(book.bookCost);
        holder.publisher.setText(book.bookPublisher);
      //  holder.bookImg.setImageResource(book.bookImage);
     //  Glide.with(context).load(book.bookImage).into(holder.bookImg);
        GlideApp.with(context)
                .load(book.bookImage)
                .into(holder.bookImg);
                       System.out.println(book.bookImage);

        holder.cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked on "+book.bookName, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class DashboardViewHolder extends RecyclerView.ViewHolder{// this is to get a reffrence of views(of recycler_dashboard_single_row)
        TextView bookName;                                    // so as to set their values in onBindViewHolder
        TextView bookAuthor;
        TextView bookCost;
        TextView publisher;
        ImageView bookImg;

        ConstraintLayout cl;

        public DashboardViewHolder(@NonNull View itemView) {
            super(itemView);
            bookName=itemView.findViewById(R.id.txtBookName);
            bookAuthor=itemView.findViewById(R.id.txtAuthorName);
            bookCost=itemView.findViewById(R.id.txtPrice);
            publisher=itemView.findViewById(R.id.txtPublisher);
            bookImg=itemView.findViewById(R.id.imgBook);
            cl=itemView.findViewById(R.id.llContent);

        }
    }
}