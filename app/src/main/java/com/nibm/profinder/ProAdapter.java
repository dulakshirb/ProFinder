package com.nibm.profinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProAdapter extends FirebaseRecyclerAdapter<Pro, ProAdapter.ProViewholder> {

    public ProAdapter(@NonNull FirebaseRecyclerOptions<Pro> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull ProViewholder holder, int position, @NonNull Pro model) {
        holder.name.setText(model.getName());
        holder.designation.setText(model.getDesignation());
        Glide.with(holder.img.getContext()).load(model.getPimage()).into(holder.img);

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                context.startActivity(new Intent(context, ProView.class));
              //  v.getContext().startActivity(new Intent(v.getContext(), ProView.class));
                Intent intent = new Intent(v.getContext(), ProView.class);
                intent.putExtra("UsersKey",getRef(position).getKey());
                v.getContext().startActivity(intent);

            }
        });
    }

    @NonNull
    @Override
    public ProViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_item,parent,false);
        return new ProViewholder(view);
    }


    static class ProViewholder extends RecyclerView.ViewHolder {
        TextView name, designation;
        CircleImageView img;
        View v;

        public ProViewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txt_featuredProName);
            designation = itemView.findViewById(R.id.txt_featuredProDesignation);
            img = itemView.findViewById(R.id.img_featuredPro);
            v = itemView;
        }
    }
}
