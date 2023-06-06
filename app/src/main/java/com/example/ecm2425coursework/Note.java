package com.example.ecm2425coursework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import kotlin.OverloadResolutionByLambdaReturnType;

public class Note extends AppCompatActivity {

    EditText noteTitle,noteContent;
    MaterialButton saveBtn;
    TextView pageHeaderTextView;
    String Title,Content,documentId;
    boolean editing = false;
    TextView deleteNotBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        noteTitle = findViewById(R.id.note_title);
        noteContent = findViewById(R.id.note_content);
        saveBtn = findViewById(R.id.savebtn);
        pageHeaderTextView = findViewById(R.id.page_header);
        deleteNotBtn = findViewById(R.id.delete_note_btn);


        // Data from adapterForNote
        Title = getIntent().getStringExtra("Title");
        Content = getIntent().getStringExtra("Content");
        documentId = getIntent().getStringExtra("documentId");

        if(documentId != null && documentId.isEmpty()){
            editing = true;
        }
        // Changes the create note template to an edit Note template
        noteTitle.setText(Title);
        noteContent.setText(Content);
        if(editing){
            pageHeaderTextView.setText("Edit Note");
        }

        saveBtn.setOnClickListener((v)->saveNote());
        deleteNotBtn.setOnClickListener((v)->deleteNote());

    }

    void deleteNote(){
        // Deleting the note from firebase
        DocumentReference documentReference;
            documentReference = UtilityClass.gettingNoteCollectionRef().document(documentId);
        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    // Note gets saved successfully
                    Toast.makeText(Note.this, "Note is deleted", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Note.this, MainActivity.class));
                }else{
                    // Note does not get saved
                    Toast.makeText(Note.this, "Note failed to delete", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    void saveNote(){
        String Title = noteTitle.getText().toString();
        String Content = noteContent.getText().toString();
        // Validates that notes are correctly filled out

        if (Title==null || Title.isEmpty()){
            noteTitle.setError("Notes require a title");
            return;

        }
        if (Content==null || Content.isEmpty()){
            noteContent.setError("Notes require Content");
            return;

        }
        Save_Note note = new Save_Note();
        note.setTitle(Title);
        note.setContent(Content);
        note.setTimestamp(Timestamp.now());

        noteToFirebase(note);

    }

    void noteToFirebase(Save_Note note){
        DocumentReference documentReference;
        if(editing){
            // Saves the note in the firebase database from edit Note Screen
            documentReference = UtilityClass.gettingNoteCollectionRef().document(documentId);
        }else{
            // Saves the newly created note in the firebase database
            documentReference = UtilityClass.gettingNoteCollectionRef().document();
        }



        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    // Note gets saved successfully
                    Toast.makeText(Note.this, "Note created", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Note.this, MainActivity.class));
                }else{
                    // Note does not get saved
                    Toast.makeText(Note.this, "Note failed to create, try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}