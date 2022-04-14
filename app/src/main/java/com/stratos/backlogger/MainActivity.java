package com.stratos.backlogger;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
        ArrayList<VideoGame> videoGames = get_videoGames();

        FloatingActionButton addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener((View.OnClickListener) view -> {
            Dialog addGame = new Dialog(this);
            addGame.setContentView(R.layout.add_game);
            addGame.setTitle("Add a new game to your backlog");
            addGame.show();

            TextInputEditText name = addGame.findViewById(R.id.add_game_title);
            TextInputEditText genre = addGame.findViewById(R.id.add_game_genre);
            TextInputEditText platform = addGame.findViewById(R.id.add_game_platform);
            TextInputEditText yearOfRelease = addGame.findViewById(R.id.add_game_release_year);
            TextInputEditText durationHours = addGame.findViewById(R.id.add_game_duration_hours);
            Button btnOk = addGame.findViewById(R.id.add_game_btn_ok);
            Button btnCancel = addGame.findViewById(R.id.add_game_btn_cancel);

            btnOk.setOnClickListener(view12 -> {
                VideoGame videoGame;
                String nameStr, genreStr, platformStr, yearOfReleaseStr, durationHoursStr;
                int yearOfReleaseInt, durationHoursInt;

                nameStr = Objects.requireNonNull(name.getText()).toString();
                genreStr = Objects.requireNonNull(genre.getText()).toString();
                platformStr = Objects.requireNonNull(platform.getText()).toString();
                yearOfReleaseStr = Objects.requireNonNull(yearOfRelease.getText()).toString();
                durationHoursStr = Objects.requireNonNull(durationHours.getText()).toString();

                yearOfReleaseInt = Integer.parseInt(yearOfReleaseStr);
                durationHoursInt = Integer.parseInt(durationHoursStr);

                videoGame = new VideoGame(nameStr, genreStr, platformStr, yearOfReleaseInt, durationHoursInt);

                if (nameStr.matches("") || genreStr.matches("") || yearOfReleaseStr.matches("") || durationHoursStr.matches("")) {
                    Toast toast = Toast.makeText(MainActivity.this, "Please fulfill all fields", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    boolean ret = db.insertVideoGames(videoGame);
                    if (ret){
                        Toast toast = Toast.makeText(MainActivity.this, "Added new game", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else{
                        Toast toast = Toast.makeText(MainActivity.this, "Error adding game. Please try again", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                    finish();
                    startActivity(getIntent());
                }
                addGame.dismiss();
            });

            btnCancel.setOnClickListener(view1 -> addGame.dismiss());
        });

        if (videoGames.size() != 0) {
            ArrayList<String> mainTitle = null, subTitle = null;
            ArrayList<Integer> imgId = null;
            for (int i=0; i<videoGames.size(); i++){
                mainTitle.add("str");
                subTitle.add("myl");
                imgId.add(R.drawable.generic_video_game);
            }
            CustomListAdapter adapter = new CustomListAdapter(this, mainTitle, subTitle, imgId);
            listView = findViewById(R.id.list);
            listView.setAdapter(adapter);
        }
    }

    protected ArrayList<VideoGame> get_videoGames() {
        // on below line we are creating a
        // database for reading our database.

        // on below line we are creating a new array list.
        ArrayList<VideoGame> videoGames = new ArrayList<>();
        Cursor cursor = db.getAllVideoGames();

        // moving our cursor to first position.
        if (cursor.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                videoGames.add(new VideoGame(cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        Integer.parseInt(cursor.getString(4)),
                        Integer.parseInt(cursor.getString(5))));
            } while (cursor.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursor.close();
        return videoGames;
    }
}