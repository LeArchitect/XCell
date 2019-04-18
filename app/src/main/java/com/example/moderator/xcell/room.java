package com.example.moderator.xcell;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;

public class room extends AppCompatActivity {
    private ArrayList<String> rooms = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);
        android.support.v7.widget.Toolbar tb = (android.support.v7.widget.Toolbar) findViewById(R.id.custom_bar);
        setSupportActionBar(tb);
/*
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle("Rooms");
*/
       initRecylerView();
    }
    private void initRecylerView()
    {
        rooms.add("Kitchen");rooms.add("Livingroom");rooms.add("Bedroom");rooms.add("Bathroom");rooms.add("Bedroom 2");
        RecyclerView conf_list = findViewById(R.id.rooms_list);
        room_adapter adapter = new room_adapter(rooms, this);
        conf_list.setAdapter(adapter);
        conf_list.setLayoutManager(new LinearLayoutManager(this));
    }


}
