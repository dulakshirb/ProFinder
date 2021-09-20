package com.nibm.profinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class Featured extends AppCompatActivity {

    Button categories, profile, settings;
    private RecyclerView recyclerView;
    ProAdapter adapter;
    DatabaseReference dbref;
//    TextView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_featured);

        dbref = FirebaseDatabase.getInstance().getReference();
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Pro> options = new FirebaseRecyclerOptions
                .Builder<Pro>().setQuery(FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("type").equalTo("Pro"), Pro.class).build();
        adapter = new ProAdapter(options);
        recyclerView.setAdapter(adapter);

        categories = (Button)findViewById(R.id.btnFloat_categories);
        profile = (Button)findViewById(R.id.btnFloat_profile);
        settings = (Button)findViewById(R.id.btnFloat_settings);

        categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Featured.this, Categories.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Featured.this, Profile.class);
                startActivity(intent);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Featured.this,Settings.class);
                startActivity(intent);
            }
        });

//
//        search = (TextView) findViewById(R.id.searchInput);
//
//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String txtSearch = search.getText().toString();
//                searchPro(txtSearch);
//            }
//        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

//    private void searchPro(String txt_search){
//
//        Query query = FirebaseDatabase.getInstance().getReference().child("Users")
//                .orderByChild("type").equalTo("Pro")
//                .startAt(txt_search)
//                .endAt(txt_search + "\uf8ff");
//
//        FirebaseRecyclerOptions<Pro> options = new FirebaseRecyclerOptions.Builder<Pro>()
//                .setQuery(query, Pro.class)
//                .setLifecycleOwner(this)
//                .build();
//
//        FirebaseRecyclerAdapter<Pro, ProAdapter.ProViewholder> searchAdapter = new FirebaseRecyclerAdapter<Pro, ProAdapter.ProViewholder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull ProAdapter.ProViewholder holder, int position, @NonNull Pro model) {
//                holder.name.setText(model.getName());
//                holder.designation.setText(model.getDesignation());
//                Glide.with(holder.img.getContext()).load(model.getPimage()).into(holder.img);
//
//                holder.v.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(v.getContext(), ProView.class);
//                        intent.putExtra("UsersKey",getRef(position).getKey());
//                        v.getContext().startActivity(intent);
//
//                    }
//                });
//            }
//
//            @NonNull
//            @Override
//            public ProAdapter.ProViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_item,parent,false);
//                ProAdapter.ProViewholder holder = new ProAdapter.ProViewholder(view);
//                return holder;
//            }
//        };
//        searchAdapter.startListening();
//        recyclerView.setAdapter(searchAdapter);
//    }

}