package com.example.mvvmpractice_2.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mvvmpractice_2.Entity.Note;

import java.util.List;

@Dao
public interface NoteDAO { //TODO DAO = "Data Access Object"
    @Insert
    void insert(Note note); //TODO: for inserting note

    @Update
    void update(Note note); //TODO: update note

    @Delete
    void delete(Note note); //TODO: delete one note

    @Query("DELETE FROM note_table")
        //TODO: delete all note
    void deleteAllNotes();

    @Query("SELECT * FROM note_table ORDER BY priority DESC")
        //TODO: get all notes from DB
    LiveData<List<Note>> getAllNotes(); //TODO: observe data if change

}
