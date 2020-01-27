package com.npdevs.project2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button add;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    List<SampleItem> msampleItem = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add = findViewById(R.id.add);
        databaseReference = FirebaseDatabase.getInstance().getReference("todo");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Add.class);
                startActivity(intent);
            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                msampleItem.clear();
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    Todo post = postSnapshot.getValue(Todo.class);
                    assert post != null;
                    msampleItem.add(new SampleItem(post.getTitle(),post.getDescription(),post.getTime()));
                    Log.e("ashu", msampleItem.size()+"");
                }
                recyclerView = findViewById(R.id.recycler_view);
                recyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                RecyclerView.Adapter adapter = new MainAdapter(msampleItem);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

        private List<SampleItem> samples;

        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView title,description,time;
            private CardView card;

            ViewHolder(View view) {
                super(view);
                title = view.findViewById(R.id.title);
                description = view.findViewById(R.id.description);
                time=view.findViewById(R.id.time);
                card=view.findViewById(R.id.card);
            }
        }

        MainAdapter(List<SampleItem> samples) {
            this.samples = samples;
            Log.e("nsp",samples.size()+"");
        }

        @NonNull
        @Override
        public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.item_main_iter, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
            holder.title.setText("Title: "+samples.get(position).getTitle());
            holder.description.setText("Description:\n"+samples.get(position).getDescription());
            holder.time.setText(samples.get(position).getTime());
            holder.card.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    TextView t=view.findViewById(R.id.time);
                    String s=t.getText().toString();
                    System.out.println(s);
                    databaseReference.child(s).setValue(null);

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            msampleItem.clear();
                            for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                            {
                                Todo post = postSnapshot.getValue(Todo.class);
                                assert post != null;
                                msampleItem.add(new SampleItem(post.getTitle(),post.getDescription(),post.getTime()));
                                Log.e("ashu", msampleItem.size()+"");
                            }

                            recyclerView = findViewById(R.id.recycler_view);
                            recyclerView.setHasFixedSize(true);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                            recyclerView.setLayoutManager(layoutManager);
                            RecyclerView.Adapter adapter = new MainAdapter(msampleItem);
                            recyclerView.setAdapter(adapter);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    return false;
                }
            });
        }

        @Override
        public int getItemCount() {
            return samples.size();
        }
    }
}
