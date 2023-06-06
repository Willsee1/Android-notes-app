package com.example.ecm2425coursework;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class adapterForNote extends FirestoreRecyclerAdapter<Save_Note, adapterForNote.ViewHolder> {
    Context context;


    public adapterForNote(@NonNull FirestoreRecyclerOptions<Save_Note> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Save_Note note) {
        holder.title.setText(note.Title);
        holder.content.setText(note.Content);
        holder.date.setText(UtilityClass.dateBecomesString(note.timestamp));

        // gains information needed to edit the selected note
        holder.itemView.setOnClickListener((v)->{
            Intent intent = new Intent(context,Note.class);
            intent.putExtra("Title",note.Title);
            intent.putExtra("Content",note.Content);
            String documentId = this.getSnapshots().getSnapshot(position).getId();
            intent.putExtra("documentId",documentId);
            context.startActivity(intent);
        });

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recylerview_notes,parent,false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView title,content,date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Content displayed about each note on the main page
            title = itemView.findViewById(R.id.recyler_note_title);
            content = itemView.findViewById(R.id.recyler_note_content);
            date = itemView.findViewById(R.id.recyler_note_date);
        }
    }

}
