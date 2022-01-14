package com.example.quizgame;

import static com.example.quizgame.config.AM_NHAC_SCORE;
import static com.example.quizgame.config.AM_THUC_SCORE;
import static com.example.quizgame.config.DIA_LY_SCORE;
import static com.example.quizgame.config.LICH_SU_SCORE;
import static com.example.quizgame.config.PLAYER_NAME;
import static com.example.quizgame.config.PLAYER_STAR;
import static com.example.quizgame.config.VAN_HOA_SCORE;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment{

    FrameLayout Geo;
    FrameLayout Music;
    FrameLayout History;
    FrameLayout Literature;
    FrameLayout Food;
    TextView progress1;
    TextView progress2;
    TextView progress3;
    TextView progress4;
    TextView progress5;
    TextView name;
    TextView point;

    Helper dbHelper = new Helper(getActivity(), null, 1);
    SQLiteDatabase db;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Geo = view.findViewById(R.id.Geography);
        Music = view.findViewById(R.id.Music);
        History = view.findViewById(R.id.History);
        Literature = view.findViewById(R.id.Literature);
        Food = view.findViewById(R.id.Food);

        progress1 = view.findViewById(R.id.progress);
        progress2 = view.findViewById(R.id.progress2);
        progress3 = view.findViewById(R.id.progress3);
        progress4 = view.findViewById(R.id.progress4);
        progress5 = view.findViewById(R.id.progress5);
        name = view.findViewById(R.id.textViewEditableName);
        point = view.findViewById(R.id.textViewStarPointHome);

        Geo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pack = new Intent(getActivity(), Dialog.class);
                pack.putExtra("topic","Địa Lý");
                startActivity(pack);
            }
        });
        Music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pack = new Intent(getActivity(), Dialog.class);
                pack.putExtra("topic","Âm Nhạc");
                startActivity(pack);
            }
        });
        History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pack = new Intent(getActivity(), Dialog.class);
                pack.putExtra("topic","Lịch Sử");
                startActivity(pack);
            }
        });
        Literature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pack = new Intent(getActivity(), Dialog.class);
                pack.putExtra("topic","Văn Hóa");
                startActivity(pack);
            }
        });
        Food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pack = new Intent(getActivity(), Dialog.class);
                pack.putExtra("topic","Ẩm Thực");
                startActivity(pack);
            }
        });


        try {

            progress1.setText(DIA_LY_SCORE + "/15");
            progress2.setText(AM_NHAC_SCORE + "/15");
            progress3.setText(LICH_SU_SCORE + "/15");
            progress4.setText(VAN_HOA_SCORE + "/15");
            progress5.setText(AM_THUC_SCORE + "/15");

            name.setText(PLAYER_NAME +"");
            point.setText(PLAYER_STAR+ "");

        }
        catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(),"gain error",Toast.LENGTH_SHORT).show();
        }


        return view;
    }




}