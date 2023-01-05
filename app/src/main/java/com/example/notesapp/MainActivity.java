package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.notesapp.adapter.NotesAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import db.DbHelper;
import model.Notes;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    NotesAdapter notesAdapter;
    ArrayList<Notes> notesArrayList = new ArrayList<>();
    DbHelper dbHelper;
    FloatingActionButton addButton;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.rview);
        addButton=findViewById(R.id.imageAddNoteMain);
        searchView = findViewById(R.id.searchView);
        searchView.setFocusable(false);
        dbHelper = new DbHelper(this);
        notesArrayList=dbHelper.getAllNotes();

        updateRecycler(notesArrayList);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateActivity.class);
                startActivity(intent);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    private void filter(String newText) {
        ArrayList<Notes> filteredArrayList = new ArrayList<>();
        for (Notes eachNote : notesArrayList){
            if(eachNote.getTitle().toLowerCase().contains(newText.toLowerCase()) ||
            eachNote.getSubtitle().toLowerCase().contains(newText.toLowerCase())){
                filteredArrayList.add(eachNote);
            }
        }
        notesAdapter.filterArrayList(filteredArrayList);
    }

    private void updateRecycler(ArrayList<Notes> notesArrayList) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        );
        notesAdapter = new NotesAdapter(MainActivity.this,notesArrayList);
        recyclerView.setAdapter(notesAdapter);

    }
}