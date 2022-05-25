package com.example.madpractical;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Random random = new Random();

        UserDBHandler dbHandler = new UserDBHandler(this);

        for (int i = 0; i < 20; i++) {
            User user = new User();
            user.name = "Name" + random.nextInt();
            user.description = "Description " + random.nextInt();
            user.followed = random.nextBoolean();

            dbHandler.addUser(user);
        }

        ArrayList<User> userArrayList = dbHandler.getUsers();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        UserAdapter mAdapter = new UserAdapter(userArrayList);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


    }
}

