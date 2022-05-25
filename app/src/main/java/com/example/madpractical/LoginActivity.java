package com.example.madpractical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        Button button = findViewById(R.id.buttonLogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText usernameEditText = findViewById(R.id.editTextUsername);
                EditText passwordEditText = findViewById(R.id.editTextPassword);

                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                DatabaseReference myRef = database.getReference("Users/" + username);

                // Read from the database
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        Map<String, Object> map = (HashMap<String, Object>) dataSnapshot.getValue();


                        String correctPassword = "";

                        try {
                            correctPassword = map.get("password").toString();
                        } catch (Exception e) {
                            Toast.makeText(LoginActivity.this, "USERNAME NOT FOUND", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (correctPassword.equals(password)) {
                            Intent intent = new Intent(LoginActivity.this, ListActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "INCORRECT PASSWORD", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("TAG", "Failed to read value.", error.toException());
                    }
                });

            }
        });

    }
}