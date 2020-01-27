package com.npdevs.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Add extends AppCompatActivity {
    private Button add;
    private EditText title,description;
    private long time = System.currentTimeMillis();
    private DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        add=findViewById(R.id.add);
        title=findViewById(R.id.title);
        description=findViewById(R.id.description);
        database = FirebaseDatabase.getInstance().getReference("todo");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t=title.getText().toString();
                String d=description.getText().toString();
                if(t.isEmpty() || d.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Do not leave fields empty!",Toast.LENGTH_LONG).show();
                    return;
                }
                Todo todo = new Todo(t,d,time+"");
                String uploadId = time+"";
                database.child(uploadId).setValue(todo);
                finish();
            }
        });
    }
}
