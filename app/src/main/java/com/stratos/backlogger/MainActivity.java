package com.stratos.backlogger;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    DatabaseManager db;
    ListView listView;

    String[] mainTitle = {"Gran Turismo 7", "Elden Ring"};
    String[] subTitle = {"Racing Simulator", "Action RPG"};
    Integer[] imgId = {R.drawable.gt7, R.drawable.elden};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseManager(this);

        FloatingActionButton addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener((View.OnClickListener) view -> {
            TextInputEditText name = findViewById(R.id.add_game_title);
            TextInputEditText genre = findViewById(R.id.add_game_genre);
            TextInputEditText yearOfRelease = findViewById(R.id.add_game_release_year);
            TextInputEditText durationHours = findViewById(R.id.add_game_duration_hours);

            AlertDialog.Builder addGame = new AlertDialog.Builder(this, R.style.AddGameDialogStyle);
            addGame.setMessage("Add a new game to your backlog");
            addGame.setPositiveButton("Add", ((dialogInterface, i) -> {
                String nameStr, genreStr, yearOfReleaseStr, durationHoursStr;
                int yearOfReleaseInt, durationHoursInt;

                nameStr = Objects.requireNonNull(name.getText()).toString();
                genreStr = Objects.requireNonNull(genre.getText()).toString();
                yearOfReleaseStr = Objects.requireNonNull(yearOfRelease.getText()).toString();
                durationHoursStr = Objects.requireNonNull(durationHours.getText()).toString();

                if (nameStr.matches("") || genreStr.matches("") || yearOfReleaseStr.matches("") || durationHoursStr.matches("")){
                    Toast toast = Toast.makeText(this, "Please fulfill all fields", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    yearOfReleaseInt = Integer.parseInt(yearOfReleaseStr);
                    durationHoursInt = Integer.parseInt(durationHoursStr);

                    db.insertVideoGames(nameStr, genreStr, yearOfReleaseInt, durationHoursInt);

                    finish();
                    startActivity(getIntent());
                }
            }));

            addGame.show();
        });

        CustomListAdapter adapter = new CustomListAdapter(this, mainTitle, subTitle, imgId);
        listView = findViewById(R.id.list);
        listView.setAdapter(adapter);


    }
}