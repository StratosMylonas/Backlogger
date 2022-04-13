package com.stratos.backlogger;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.database.Cursor;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    DatabaseManager db;
    ListView listView;

//    String[] mainTitle = {"Gran Turismo 7", "Elden Ring"};
//    String[] subTitle = {"Racing Simulator", "Action RPG"};
//    Integer[] imgId = {R.drawable.gt7, R.drawable.elden};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseManager(this);
        ArrayList<String> mainTitle = get_col(2);
        ArrayList<String> subTitle = get_col(3);
        ArrayList<Integer> imgId = null;

        FloatingActionButton addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener((View.OnClickListener) view -> {
            Dialog addGame = new Dialog(this);
            addGame.setContentView(R.layout.add_game);
            addGame.setTitle("Add a new game to your backlog");
            addGame.show();

            TextInputEditText name = addGame.findViewById(R.id.add_game_title);
            TextInputEditText genre = addGame.findViewById(R.id.add_game_genre);
            TextInputEditText yearOfRelease = addGame.findViewById(R.id.add_game_release_year);
            TextInputEditText durationHours = addGame.findViewById(R.id.add_game_duration_hours);
            Button btnOk = addGame.findViewById(R.id.add_game_btn_ok);
            Button btnCancel = addGame.findViewById(R.id.add_game_btn_cancel);

            btnOk.setOnClickListener(view12 -> {
                String nameStr, genreStr, yearOfReleaseStr, durationHoursStr;
                int yearOfReleaseInt, durationHoursInt;

                nameStr = Objects.requireNonNull(name.getText()).toString();
                genreStr = Objects.requireNonNull(genre.getText()).toString();
                yearOfReleaseStr = Objects.requireNonNull(yearOfRelease.getText()).toString();
                durationHoursStr = Objects.requireNonNull(durationHours.getText()).toString();

                if (nameStr.matches("") || genreStr.matches("") || yearOfReleaseStr.matches("") || durationHoursStr.matches("")) {
                    Toast toast = Toast.makeText(MainActivity.this, "Please fulfill all fields", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    yearOfReleaseInt = Integer.parseInt(yearOfReleaseStr);
                    durationHoursInt = Integer.parseInt(durationHoursStr);

                    db.insertVideoGames(nameStr, genreStr, yearOfReleaseInt, durationHoursInt);

                    finish();
                    startActivity(getIntent());
                }
                addGame.dismiss();
            });

            btnCancel.setOnClickListener(view1 -> addGame.dismiss());
        });

        if (mainTitle != null) {
            CustomListAdapter adapter = new CustomListAdapter(this, mainTitle, subTitle, imgId);
            listView = findViewById(R.id.list);
            listView.setAdapter(adapter);
        }
    }

    protected ArrayList<String> get_col(int col) {
        //   arrayList = new ArrayList<String>();
        ArrayList<String> data = null;

        Cursor res = db.getAllVideoGames();
        TextView emptyPromptTxt = findViewById(R.id.emptyPromptTxt);
        if (res.getCount() == 0) {
            // show message
            emptyPromptTxt.setVisibility(View.VISIBLE);
            return null;
        } else {
            emptyPromptTxt.setVisibility(View.INVISIBLE);
        }

        while (res.moveToNext()) {
            data.add(res.getString(col));
        }

        if (res.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                data.add(new CourseModal(cursorCourses.getString(1),
                        cursorCourses.getString(4),
                        cursorCourses.getString(2),
                        cursorCourses.getString(3)));
            } while (cursorCourses.moveToNext());
            // moving our cursor to next.
        }

        return data;
    }
}