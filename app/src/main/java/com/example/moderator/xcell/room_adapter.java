package com.example.moderator.xcell;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;

public class room_adapter extends RecyclerView.Adapter<room_adapter.myViewHolder>{

    private ArrayList<String> rooms = new ArrayList<>();
    private Context mcontext;

    public room_adapter(ArrayList<String> rooms, Context mcontext) {
        this.rooms = rooms;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.room_item, viewGroup, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder myViewHolder, final int i) {
        myViewHolder.room_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mcontext, com.example.moderator.xcell.devices.class);
                myIntent.putExtra("room_name",rooms.get(i));
                myIntent.putExtra("source", "rooms");
                mcontext.startActivity(myIntent);
            }
        });
        if(i != (rooms.size()-1))
        {
            myViewHolder.parentLayout.removeView(myViewHolder.add_room);
        }
        else
        {
        myViewHolder.add_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rooms.add("New Room");
                Toast.makeText(mcontext," New Room is added", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });
        }


        myViewHolder.room_name.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                myViewHolder.viewSwitcher.showNext();
                Toast.makeText(mcontext,"This is a long click ", Toast.LENGTH_LONG).show();
                return true;
            }
        });

        myViewHolder.room_name.setText(rooms.get(i));
        myViewHolder.delete_room.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mcontext);
                alertDialogBuilder.setMessage("Are you sure You want to delete " +rooms.get(i));
                alertDialogBuilder.setNegativeButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                String x = rooms.get(i);
                                rooms.remove(x);
                                Toast.makeText(mcontext,x  + " is deleted", Toast.LENGTH_LONG).show();
                                notifyDataSetChanged();
                            }
                        });
                alertDialogBuilder.setPositiveButton("Cancel",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }

        });


    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        ViewSwitcher viewSwitcher;
        TextView room_name;
        EditText edit_room_name;
        LinearLayout parentLayout;
        ImageButton delete_room, add_room;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            viewSwitcher = itemView.findViewById(R.id.vs_room);
            room_name = itemView.findViewById(R.id.room_name);
            edit_room_name = itemView.findViewById(R.id.edit_room_name);
            parentLayout = itemView.findViewById(R.id.parent_layout_room);
            delete_room = itemView.findViewById(R.id.room_delete);
            add_room = itemView.findViewById(R.id.add_room);
        }
    }
}
