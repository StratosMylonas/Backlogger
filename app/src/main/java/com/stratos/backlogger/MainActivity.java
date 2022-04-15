package com.stratos.backlogger;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    DatabaseManager db;
    ListView listView;
    AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseManager(this);
        ArrayList<VideoGame> videoGames = get_videoGames();

        FloatingActionButton addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(view -> {
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

                videoGame = new VideoGame(0, nameStr, genreStr, platformStr, yearOfReleaseInt, durationHoursInt);

                if (nameStr.matches("") || genreStr.matches("") || yearOfReleaseStr.matches("") || durationHoursStr.matches("")) {
                    Toast toast = Toast.makeText(MainActivity.this, "Please fulfill all fields", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    boolean ret = db.insertVideoGame(videoGame);
                    if (ret){
                        Toast.makeText(MainActivity.this, "Added new game", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Error adding game. Please try again", Toast.LENGTH_SHORT).show();
                    }

                    finish();
                    overridePendingTransition( 0, 0);
                    startActivity(getIntent());
                    overridePendingTransition( 0, 0);
                }
                addGame.dismiss();
            });

            btnCancel.setOnClickListener(view1 -> addGame.dismiss());
        });

        TextView emptyPromptTxt = findViewById(R.id.emptyPromptTxt);
        if (videoGames.size() != 0) {
            emptyPromptTxt.setVisibility(View.INVISIBLE);
            ArrayList<Integer> ids = new ArrayList<>();
            ArrayList<String> mainTitle = new ArrayList<>();
            ArrayList<String> subTitle = new ArrayList<>();
            ArrayList<Integer> imgId = new ArrayList<>();
            for (int i=0; i<videoGames.size(); i++){
                ids.add(videoGames.get(i).getId());
                mainTitle.add(videoGames.get(i).getTitle());
                subTitle.add(videoGames.get(i).getGenre());
                imgId.add(R.drawable.generic_video_game);
            }
            CustomListAdapter adapter = new CustomListAdapter(this, mainTitle, subTitle, imgId);
            listView = MainActivity.this.findViewById(R.id.list);
            listView.setAdapter(adapter);
            listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Delete?");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Yes",
                        (dialog, which) -> {
                            int vgId = videoGames.get(i).getId();
                            boolean ret = db.deleteVideoGame(vgId);
                            if (ret){
                                Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(MainActivity.this, "Error deleting game. Please try again", Toast.LENGTH_SHORT).show();
                            }

                            finish();
                            overridePendingTransition( 0, 0);
                            startActivity(getIntent());
                            overridePendingTransition( 0, 0);
                        });
                alertDialog.show();
                return true;
            });
        }
        else{
            emptyPromptTxt.setVisibility(View.VISIBLE);
        }
    }

    protected ArrayList<VideoGame> get_videoGames() {
        ArrayList<VideoGame> videoGames = new ArrayList<>();
        Cursor cursor = db.getAllVideoGames();

        if (cursor.moveToFirst()) {
            do {
                videoGames.add(new VideoGame(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        Integer.parseInt(cursor.getString(4)),
                        Integer.parseInt(cursor.getString(5))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return videoGames;
    }
}