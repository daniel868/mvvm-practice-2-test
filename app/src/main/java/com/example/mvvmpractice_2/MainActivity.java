package com.example.mvvmpractice_2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mvvmpractice_2.Entity.Note;
import com.example.mvvmpractice_2.MVVM.NoteViewModel;
import com.example.mvvmpractice_2.UI.NoteAdapter;
import com.example.mvvmpractice_2.UI.OnItemClickListener;
import com.example.mvvmpractice_2.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.example.mvvmpractice_2.Constants.DESCRIPTION_EXTRA;
import static com.example.mvvmpractice_2.Constants.ID_EXTRA;
import static com.example.mvvmpractice_2.Constants.PRIORITY_EXTRA;
import static com.example.mvvmpractice_2.Constants.TITLE_EXTRA;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private static final int ADD_NOTE_CODE = 1;
    private static final int EDIT_NODE_CODE = 2;

    private NoteViewModel viewModel;
    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecycle();
        floatingActionButton = findViewById(R.id.add_note);
        floatingActionButton.setOnClickListener(this);

        viewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        viewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteAdapter.setNoteList(notes);
            }
        });
    }

    private void initRecycle() {
        recyclerView = findViewById(R.id.recycleView);
        noteAdapter = new NoteAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(noteAdapter);
        swipeCallback().attachToRecyclerView(recyclerView);

        noteAdapter.setListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                intent.putExtra(TITLE_EXTRA, note.getTitle());
                intent.putExtra(DESCRIPTION_EXTRA, note.getDescription());
                intent.putExtra(PRIORITY_EXTRA, note.getPriority());
                intent.putExtra(ID_EXTRA, note.getId());
                startActivityForResult(intent, EDIT_NODE_CODE);
            }
        });
    }


    private ItemTouchHelper swipeCallback() {
        return new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.deleteRepository(noteAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        startActivityForResult(new Intent(this, AddEditNoteActivity.class), ADD_NOTE_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE_CODE && resultCode == RESULT_OK) {
            String title = data.getStringExtra(TITLE_EXTRA);
            String description = data.getStringExtra(Constants.DESCRIPTION_EXTRA);
            int priority = data.getIntExtra(Constants.PRIORITY_EXTRA, 1);
            Note note = new Note(title, description, priority);
            viewModel.insertRepository(note);
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_NODE_CODE && resultCode == RESULT_OK) {
            int id = data.getIntExtra(ID_EXTRA, -1);
            if (id == -1) {
                Toast.makeText(this, "Can't update the note", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(TITLE_EXTRA);
            String description = data.getStringExtra(Constants.DESCRIPTION_EXTRA);
            int priority = data.getIntExtra(Constants.PRIORITY_EXTRA, 1);
            Note note = new Note(title,description,priority);
            note.setId(id);
            viewModel.updateRepository(note);
            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteAllNotes: {
                viewModel.deleteAllRepository();
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
