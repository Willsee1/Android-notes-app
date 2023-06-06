package com.example.ecm2425coursework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton createNote;
    RecyclerView recyclerView;
    ImageButton menu_btn;
    adapterForNote adapterForNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNote = findViewById(R.id.create_note_btn);
        recyclerView = findViewById(R.id.notes_list);
        menu_btn = findViewById(R.id.menu_button);

        // OnClickListeners showing what the app does when the buttons on page are pressed
        createNote.setOnClickListener((v)-> startActivity(new Intent(MainActivity.this, Note.class)));
        menu_btn.setOnClickListener((v)-> openMenu() );
        theRecyclerView();

    }

    void openMenu(){
        // Opens a popup menu when pressed to allow the user to logout
        PopupMenu popupMenu =  new PopupMenu(MainActivity.this,menu_btn);
        popupMenu.getMenu().add("Logout");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getTitle()=="Logout"){
                    // Logs the User out if correct button is pressed
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(MainActivity.this,LoginPage.class));
                    finish();
                    return true;
                }
                return false;
            }
        });

    }

    void theRecyclerView(){
        // Recycler view that orders notes by latest creation date
        Query query = UtilityClass.gettingNoteCollectionRef().orderBy("timestamp",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Save_Note> options = new  FirestoreRecyclerOptions.Builder<Save_Note>()
                .setQuery(query,Save_Note.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterForNote = new adapterForNote(options, this);
        recyclerView.setAdapter(adapterForNote);


    }

    @Override
    protected void onStart() {
        super.onStart();
        adapterForNote.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterForNote.stopListening();
    }

    @Override
    protected void onResume() {
        // Means notes get updated after user adds a new note
        super.onResume();
        adapterForNote.notifyDataSetChanged();
        // does not get used??
    }
}