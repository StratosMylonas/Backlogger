package com.stratos.backlogger;

import android.app.AlertDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoGamesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoGamesFragment extends Fragment {

    DatabaseManager db;
    ListView listView;
    FloatingActionButton addBtn;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VideoGamesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VideoGamesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VideoGamesFragment newInstance(String param1, String param2) {
        VideoGamesFragment fragment = new VideoGamesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video_games, container, false);
        addBtn = view.findViewById(R.id.add_btn);
        db = new DatabaseManager(getActivity());
        ArrayList<VideoGame> videoGames = get_videoGames();

        addBtn.setOnClickListener(v -> {
            Dialog addGame = new Dialog(getActivity());
            addGame.setContentView(R.layout.add_game);
            addGame.setTitle("Add a new game to your backlog");
            addGame.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            addGame.show();

            TextInputEditText name = addGame.findViewById(R.id.add_game_title);
            TextInputEditText genre = addGame.findViewById(R.id.add_game_genre);
            TextInputEditText platform = addGame.findViewById(R.id.add_game_platform);
            TextInputEditText yearOfRelease = addGame.findViewById(R.id.add_game_release_year);
            yearOfRelease.setInputType(InputType.TYPE_CLASS_NUMBER);
            TextInputEditText durationHours = addGame.findViewById(R.id.add_game_duration_hours);
            durationHours.setInputType(InputType.TYPE_CLASS_NUMBER);
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

                if (nameStr.matches("") || genreStr.matches("") || platformStr.matches("")) {
                    Toast toast = Toast.makeText(getActivity(), "Please fulfill all needed fields", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    if (!yearOfReleaseStr.matches(""))
                        yearOfReleaseInt = Integer.parseInt(yearOfReleaseStr);
                    else yearOfReleaseInt = 0;

                    if (!durationHoursStr.matches(""))
                        durationHoursInt = Integer.parseInt(durationHoursStr);
                    else durationHoursInt = 0;

                    videoGame = new VideoGame(0, nameStr, genreStr, platformStr, yearOfReleaseInt, durationHoursInt);

                    boolean ret = db.insertVideoGame(videoGame);
                    if (ret) {
                        Toast.makeText(getActivity(), "Added new game", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Error adding game. Please try again", Toast.LENGTH_SHORT).show();
                    }
                    addGame.dismiss();
                    requireActivity().recreate();
                }
            });

            btnCancel.setOnClickListener(view1 -> addGame.dismiss());
        });

        TextView emptyPromptTxt = view.findViewById(R.id.emptyPromptTxt);
        if (videoGames.size() != 0) {
            emptyPromptTxt.setVisibility(View.INVISIBLE);
            ArrayList<Integer> ids = new ArrayList<>();
            ArrayList<String> mainTitle = new ArrayList<>();
            ArrayList<String> subTitle = new ArrayList<>();
            ArrayList<String> platform = new ArrayList<>();
            ArrayList<Integer> imgId = new ArrayList<>();
            for (int i = 0; i < videoGames.size(); i++) {
                ids.add(videoGames.get(i).getId());
                mainTitle.add(videoGames.get(i).getTitle());
                subTitle.add(videoGames.get(i).getGenre());
                platform.add(videoGames.get(i).getPlatform());
                imgId.add(R.drawable.generic_video_game);
            }
            CustomListAdapter adapter = new CustomListAdapter(getActivity(), mainTitle, subTitle, platform, imgId);
            listView = view.findViewById(R.id.list);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener((adapterView, view13, i, l) -> {
                Dialog addGame = new Dialog(getActivity());
                addGame.setContentView(R.layout.add_game);
                addGame.setTitle("Add a new game to your backlog");
                addGame.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                addGame.show();

                TextInputEditText name = addGame.findViewById(R.id.add_game_title);
                TextInputEditText genre = addGame.findViewById(R.id.add_game_genre);
                TextInputEditText platform1 = addGame.findViewById(R.id.add_game_platform);
                TextInputEditText yearOfRelease = addGame.findViewById(R.id.add_game_release_year);
                yearOfRelease.setInputType(InputType.TYPE_CLASS_NUMBER);
                TextInputEditText durationHours = addGame.findViewById(R.id.add_game_duration_hours);
                durationHours.setInputType(InputType.TYPE_CLASS_NUMBER);
                Button btnOk = addGame.findViewById(R.id.add_game_btn_ok);
                Button btnCancel = addGame.findViewById(R.id.add_game_btn_cancel);

                int id = videoGames.get(i).getId();
                name.setText(videoGames.get(i).getTitle());
                genre.setText(videoGames.get(i).getGenre());
                platform1.setText(videoGames.get(i).getPlatform());
                if (videoGames.get(i).getYearOfRelease() != 0)
                    yearOfRelease.setText(String.valueOf(videoGames.get(i).getYearOfRelease()));
                if (videoGames.get(i).getDurationHours() != 0)
                    durationHours.setText(String.valueOf(videoGames.get(i).getDurationHours()));

                btnOk.setOnClickListener(view14 -> {
                    VideoGame videoGame;
                    String nameStr, genreStr, platformStr, yearOfReleaseStr, durationHoursStr;
                    int yearOfReleaseInt, durationHoursInt;

                    nameStr = Objects.requireNonNull(name.getText()).toString();
                    genreStr = Objects.requireNonNull(genre.getText()).toString();
                    platformStr = Objects.requireNonNull(platform1.getText()).toString();
                    yearOfReleaseStr = Objects.requireNonNull(yearOfRelease.getText()).toString();
                    durationHoursStr = Objects.requireNonNull(durationHours.getText()).toString();

                    if (nameStr.matches("") || genreStr.matches("") || platformStr.matches("")) {
                        Toast toast = Toast.makeText(getActivity(), "Please fulfill all needed fields", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        if (!yearOfReleaseStr.matches(""))
                            yearOfReleaseInt = Integer.parseInt(yearOfReleaseStr);
                        else yearOfReleaseInt = 0;

                        if (!durationHoursStr.matches(""))
                            durationHoursInt = Integer.parseInt(durationHoursStr);
                        else durationHoursInt = 0;

                        videoGame = new VideoGame(id, nameStr, genreStr, platformStr, yearOfReleaseInt, durationHoursInt);

                        boolean ret = db.updateVideoGame(videoGame);
                        if (ret) {
                            Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Error updating game. Please try again", Toast.LENGTH_SHORT).show();
                        }
                        addGame.dismiss();
                        requireActivity().recreate();
                    }
                });
                btnCancel.setOnClickListener(view1 -> addGame.dismiss());
            });
            listView.setOnItemLongClickListener((adapterView, v, i, l) -> {
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Delete?");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Yes",
                        (dialog, which) -> {
                            int vgId = videoGames.get(i).getId();
                            boolean ret = db.deleteVideoGame(vgId);
                            if (ret) {
                                Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Error deleting game. Please try again", Toast.LENGTH_SHORT).show();
                            }

                            requireActivity().recreate();
                        });
                alertDialog.show();
                return true;
            });
        } else {
            emptyPromptTxt.setVisibility(View.VISIBLE);
        }

        return view;
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