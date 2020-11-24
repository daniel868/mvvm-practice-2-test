package com.example.mvvmpractice_2.MVVM;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mvvmpractice_2.Entity.Note;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository noteRepository;
    private LiveData<List<Note>>allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteRepository = new NoteRepository(application);
        allNotes = noteRepository.getAllNotes();

    }
    public void insertRepository(Note note){
        noteRepository.insert(note);
    }

    public void updateRepository(Note note){
        noteRepository.update(note);
    }

    public void deleteRepository(Note note){
        noteRepository.delete(note);
    }

    public void deleteAllRepository(){
        noteRepository.deleteAll();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

}
