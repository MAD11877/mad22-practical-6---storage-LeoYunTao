package com.example.madpractical;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    ArrayList<User> userArrayList;

    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView randomName;
        TextView randomDescription;

        public UserViewHolder(View itemView) {
            super(itemView);
            randomName = itemView.findViewById(R.id.randomName);
            randomDescription = itemView.findViewById(R.id.randomDescription);
        }
    }

    public UserAdapter(ArrayList<User> input) {
        userArrayList = input;
    }

    @Override
    public int getItemViewType(int position) {
        User user = userArrayList.get(position);
        return user.name.charAt(user.name.length() - 1) == '7' ? 0 : 1;
    }

    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int viewID;
        if (viewType == 1) {
            viewID = R.layout.recyclerview_list;
        } else  {
            viewID = R.layout.recyclerview_image;
        }

        return new UserViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                viewID,
                parent,
                false));
    }

    public void onBindViewHolder(UserViewHolder holder, int position) {
        User user = userArrayList.get(position);
        String name = user.name;
        String description = user.description;
        boolean followed = user.followed;
        holder.randomName.setText(name);
        holder.randomDescription.setText(description);

        ImageView imageIcon = holder.itemView.findViewById(R.id.imageIcon);

        imageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Profile").setMessage(holder.randomName.getText())
                        .setPositiveButton("VIEW", (dialogInterface, i) -> {
                            Intent activityMain = new Intent(view.getContext(), MainActivity.class);
                            activityMain.putExtra("randomNumber", holder.randomName.getText());
                            activityMain.putExtra("randomDescription", holder.randomDescription.getText());
                            activityMain.putExtra("followed", followed);
                            view.getContext().startActivity(activityMain);
                        })
                        .setNegativeButton("CLOSE", (dialogInterface, i) -> {

                        });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    public int getItemCount() {
        return userArrayList.size();
    }
}
