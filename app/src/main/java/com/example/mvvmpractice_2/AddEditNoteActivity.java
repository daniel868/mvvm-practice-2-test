package com.example.mvvmpractice_2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mvvmpractice_2.Constants;

import static com.example.mvvmpractice_2.Constants.DESCRIPTION_EXTRA;
import static com.example.mvvmpractice_2.Constants.ID_EXTRA;
import static com.example.mvvmpractice_2.Constants.PRIORITY_EXTRA;
import static com.example.mvvmpractice_2.Constants.TITLE_EXTRA;

public class AddEditNoteActivity extends AppCompatActivity {
    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPicker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        numberPicker = findViewById(R.id.number_picker_priority);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        if (getIntent().hasExtra(ID_EXTRA)) {
            setTitle("Edit note");
            setData(getIntent().getStringExtra(TITLE_EXTRA),
                    getIntent().getStringExtra(DESCRIPTION_EXTRA),
                    getIntent().getIntExtra(PRIORITY_EXTRA, 1));
        } else {
            setTitle("Add note");
        }
    }

    private void setData(String title, String description, int priority) {
        editTextDescription.setText(description);
        editTextTitle.setText(title);
        numberPicker.setValue(priority);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveButton: {
                saveNote();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void saveNote() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int priority = numberPicker.getValue();
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
            return;
        }
        Intent data = new Intent();
        data.putExtra(Constants.TITLE_EXTRA, title);
        data.putExtra(Constants.DESCRIPTION_EXTRA, description);
        data.putExtra(Constants.PRIORITY_EXTRA, priority);

        int id = getIntent().getIntExtra(ID_EXTRA, -1);
        if (id != -1) {
            data.putExtra(ID_EXTRA, id);
        }
        setResult(RESULT_OK, data);
        finish();
    }
}
