package com.example.madpractical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent receivingEnd = getIntent();

        User user = new User();

        user.name = receivingEnd.getStringExtra("randomNumber");
        user.description = receivingEnd.getStringExtra("randomDescription");
        user.followed = receivingEnd.getBooleanExtra("followed", false);

        TextView name = findViewById(R.id.name);
        TextView description = findViewById(R.id.description);

        name.setText(user.name);
        description.setText(user.description);

        Button buttonFollow = findViewById(R.id.button_follow);

        checkUserFollow(user, buttonFollow);

        buttonFollow.setOnClickListener(view -> {
            user.followed = !user.followed;

            checkUserFollow(user, buttonFollow);

            if (!user.followed) {
                Toast.makeText(this, "Unfollowed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Followed", Toast.LENGTH_SHORT).show();
            }

        });

        Button buttonMessage = findViewById(R.id.button_message);
        buttonMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activityMessageGroup = new Intent(MainActivity.this, MessageGroup.class);
                startActivity(activityMessageGroup);
            }
        });

    }

    private void checkUserFollow(User user, Button buttonFollow) {
        if (user.followed) {
            buttonFollow.setText("UNFOLLOW");

        } else {
            buttonFollow.setText("FOLLOW");
        }
    }

}