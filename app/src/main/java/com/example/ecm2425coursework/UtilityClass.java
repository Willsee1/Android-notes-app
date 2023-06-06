package com.example.ecm2425coursework;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;

public class UtilityClass {
    // Class designed for aiding other classes by completing smaller tasks

    static CollectionReference gettingNoteCollectionRef(){
        // Collects each individual id from each note from the current users documents
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("Notes").document(currentUser.getUid()).collection("Personal Notes");
    }

    static String dateBecomesString(Timestamp timestamp){
        return new SimpleDateFormat("MM/dd/yyyy").format(timestamp.toDate());
        // Date format for each note as a string
    }

}
