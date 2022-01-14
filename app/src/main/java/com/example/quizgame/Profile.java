package com.example.quizgame;

import static com.example.quizgame.config.JOIN_DATE;
import static com.example.quizgame.config.LEVEL_COMPLETED;
import static com.example.quizgame.config.PLAYER_NAME;
import static com.example.quizgame.config.PLAYER_STAR;
import static com.example.quizgame.config.QUESTION_ANSWERED;
import static com.example.quizgame.config.RATE;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {

    Helper dbHelper = new Helper(getContext(), null, 1);
    SQLiteDatabase db;

    ImageView Background;
    ImageView AvatarProfile;
    TextView ProfilePlayerName;
    TextView ProfilePlayerStar;
    TextView LevelsCompleted;
    TextView JoinDate;
    TextView QuestionsAnswered;
    TextView CorrectAnswers;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
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
        View view = inflater.inflate(R.layout.fragment_profile,container,false);

        Background = view.findViewById(R.id.imageViewBackground);
        AvatarProfile  = view.findViewById(R.id.imageViewAvatarProfile);
        ProfilePlayerName = view.findViewById(R.id.textViewEditableName);
        ProfilePlayerStar = view.findViewById(R.id.textViewStarPointProfile);
        LevelsCompleted = view.findViewById(R.id.textViewLevelsCompleted);
        JoinDate = view.findViewById(R.id.textViewJoinDate);
        QuestionsAnswered = view.findViewById(R.id.questionAnswered);
        CorrectAnswers = view.findViewById(R.id.percenrCorrectAnswer);

//        Toast.makeText(getContext(),PI.getName(),Toast.LENGTH_SHORT).show();
        ProfilePlayerName.setText(PLAYER_NAME);
        ProfilePlayerStar.setText(Integer.toString(PLAYER_STAR));
        LevelsCompleted.setText(Integer.toString(LEVEL_COMPLETED));
        JoinDate.setText(JOIN_DATE);
        QuestionsAnswered.setText(Integer.toString(QUESTION_ANSWERED));
        CorrectAnswers.setText(Integer.toString(RATE) + "%");

        return view;
    }
}