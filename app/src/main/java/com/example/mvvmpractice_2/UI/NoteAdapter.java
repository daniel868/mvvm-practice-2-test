package com.example.mvvmpractice_2.UI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvmpractice_2.Entity.Note;
import com.example.mvvmpractice_2.R;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.Holder> {
    private List<Note> noteList = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(noteList.get(position));
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
        notifyDataSetChanged();
    }

    public Note getNoteAt(int position) {
        return noteList.get(position);
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;

        public Holder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Note note = getNoteAt(getAdapterPosition());
                    if (listener != null && getAdapterPosition()!=RecyclerView.NO_POSITION) {
                        listener.onItemClick(note);
                    }
                }
            });
        }

        public void bind(Note note) {
            textViewTitle.setText(note.getTitle());
            textViewDescription.setText(note.getDescription());
            textViewPriority.setText(String.valueOf(note.getPriority()));
        }
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
