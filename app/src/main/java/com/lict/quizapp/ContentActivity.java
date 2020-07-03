package com.lict.quizapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.provider.Settings;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListPopupWindow;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

@SuppressLint({"SetTextI18n", "DefaultLocale"})
public class ContentActivity extends AppCompatActivity {

    private AppCompatTextView timer_Tv, question_Tv, quiz_No_Tv;
    private RelativeLayout start_Panel, quiz_Panel;
    private LinearLayoutCompat choice_1, choice_2, choice_3, choice_4;
    private AppCompatTextView choice_1_Tv, choice_2_Tv, choice_3_Tv, choice_4_Tv;
    private AppCompatTextView choice_A, choice_B, choice_C, choice_D;
    private AppCompatButton start_Btn, next_Btn;
    private int quiz_No = 0;
    private int points = 0;
    private boolean isExamStart = false, isExamFinish = false;
    private CountDownTimer Move_Timer;
    private boolean isTimerOn = false;
    private AlertDialog warningDialog, quitDialog;
    private ScrollListView result_List, history_List;
    private ArrayList<String> Current_Question = new ArrayList<>();
    private ArrayList<String> Current_Choice_1 = new ArrayList<>();
    private ArrayList<String> Current_Choice_2 = new ArrayList<>();
    private ArrayList<String> Current_Choice_3 = new ArrayList<>();
    private ArrayList<String> Current_Choice_4 = new ArrayList<>();
    private ArrayList<String> Right_Answer = new ArrayList<>();
    private ArrayList<String> Current_Answer = new ArrayList<>();
    private ArrayList<String> Current_Explanation = new ArrayList<>();
    private FloatingActionButton home_Btn;
    private AppCompatImageButton quit_Btn;
    private AppCompatTextView exam_Marks, exam_Time, exam_Warnings, exam_Instructions;
    private AppCompatEditText exam_Category, exam_Questions;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        Id_Setup();
        ActionBar_Items();
        Font_Setup();
        Data_Setup();

        exam_Category.setText(Stored_Data.Category_List.get(0));

        exam_Questions.setText(String.valueOf(Stored_Data.Question_Limit));

        exam_Marks.setText(String.valueOf(Stored_Data.Question_Limit));

        exam_Time.setText(TimeFormater(Stored_Data.Time_Limit) + " min");

        exam_Warnings.setText(String.valueOf(Stored_Data.Warning_Limit));

        findViewById(R.id.instructions_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(exam_Instructions.getVisibility() == View.GONE){
                    exam_Instructions.setVisibility(View.VISIBLE);
                    ((AppCompatImageButton) findViewById(R.id.hide_btn)).setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_less));
                    findViewById(R.id.start_panel_scrollView).post(new Runnable() {
                        @Override
                        public void run() {
                            ((NestedScrollView) findViewById(R.id.start_panel_scrollView)).fullScroll(NestedScrollView.FOCUS_DOWN);
                        }
                    });
                }
                else {
                    exam_Instructions.setVisibility(View.GONE);
                    ((AppCompatImageButton) findViewById(R.id.hide_btn)).setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_more));
                }
            }
        });

        start_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(start_Btn.getText().equals(getResources().getString(R.string.clear))){
                    Custom_Dialog("Are you sure, you want to clear all records?", "Yes, "+ getResources().getString(R.string.clear),"clear");
                }
                else {
                    Start_Quiz();
                }
            }
        });

        home_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Restart_Quiz();
            }
        });

        quit_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Custom_Dialog("Are you sure you want to quit exam?", getApplicationContext().getString(R.string.quit),"quit");
            }
        });
    }

    private void Id_Setup() {
        Common.Root_Layout = findViewById(R.id.drawer_layout);
        timer_Tv = findViewById(R.id.timer_tv);
        start_Panel = findViewById(R.id.start_panel);
        quiz_Panel = findViewById(R.id.quiz_panel);
        quiz_No_Tv = findViewById(R.id.quiz_no);
        question_Tv = findViewById(R.id.question_tv);
        start_Btn = findViewById(R.id.start_btn);
        next_Btn = findViewById(R.id.next_btn);
        result_List = findViewById(R.id.result_listView);
        history_List = findViewById(R.id.history_listView);
        home_Btn = findViewById(R.id.home_btn);
        quit_Btn = findViewById(R.id.quit_btn);

        exam_Category = findViewById(R.id.exam_category);
        exam_Questions = findViewById(R.id.exam_questions);
        exam_Marks = findViewById(R.id.exam_marks);
        exam_Time = findViewById(R.id.exam_time);
        exam_Warnings  = findViewById(R.id.exam_warnings);
        exam_Instructions = findViewById(R.id.exam_instructions);

        choice_1 = findViewById(R.id.choice_1);
        choice_2 = findViewById(R.id.choice_2);
        choice_3 = findViewById(R.id.choice_3);
        choice_4 = findViewById(R.id.choice_4);

        choice_A = findViewById(R.id.choice_A);
        choice_B = findViewById(R.id.choice_B);
        choice_C = findViewById(R.id.choice_C);
        choice_D = findViewById(R.id.choice_D);

        choice_1_Tv = findViewById(R.id.choice_1_tv);
        choice_2_Tv = findViewById(R.id.choice_2_tv);
        choice_3_Tv = findViewById(R.id.choice_3_tv);
        choice_4_Tv = findViewById(R.id.choice_4_tv);
    }

    private void ActionBar_Items(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setDisplayShowCustomEnabled(true);
            @SuppressLint("InflateParams") View view = LayoutInflater
                    .from(actionBar.getThemedContext())
                    .inflate(R.layout.layout_action_bar, null);

            ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT,
                    ActionBar.LayoutParams.MATCH_PARENT);

            actionBar.setCustomView(view, params);
            actionBar.setElevation(10);

            AppCompatImageButton imageButton = view.findViewById(R.id.action_bar_btn);
            AppCompatTextView appName_Tv = view.findViewById(R.id.appName_tv);
            Common.Set_Font(getApplicationContext(), new View[]{appName_Tv},"vinque.ttf");
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Common.Root_Layout.isDrawerOpen(GravityCompat.START)) {
                        Common.Root_Layout.closeDrawer(GravityCompat.START);  /*Close Nav ActionBar_Items*/
                    }
                    else {
                        Common.Root_Layout.openDrawer(GravityCompat.START);  /*Open Nav ActionBar_Items*/
                    }
                }
            });
        }
    }

    private void Font_Setup(){
        Common.Set_Font(getApplicationContext(), new View[]{start_Btn, next_Btn, question_Tv, quiz_No_Tv, timer_Tv, choice_1_Tv,
                choice_2_Tv, choice_3_Tv, choice_4_Tv, choice_A, choice_B, choice_C, choice_D}, "pf_regular.ttf");

        Common.Set_Font(getApplicationContext(), new View[]{exam_Category, exam_Questions, exam_Marks, exam_Time, exam_Warnings, exam_Instructions, findViewById(R.id.exam_category_tv), findViewById(R.id.exam_questions_tv),
                findViewById(R.id.exam_marks_tv), findViewById(R.id.exam_time_tv),findViewById(R.id.exam_warnings_tv), findViewById(R.id.exam_instructions_tv)}, "pf_regular.ttf");
    }

    private void Data_Setup(){
        final MySQLiteDB mySQLiteDB = new MySQLiteDB(getApplicationContext());

        if((mySQLiteDB.displayQuiz().getCount() == 0)){
            for (int i = 0; i<Stored_Data.Quizzes.length; i++){
                mySQLiteDB.insertQuiz(Stored_Data.Quizzes[i][0], Stored_Data.Quizzes[i][1], Stored_Data.Quizzes[i][2], Stored_Data.Quizzes[i][3], Stored_Data.Quizzes[i][4], Stored_Data.Quizzes[i][5], Stored_Data.Quizzes[i][6]);
            }
            Common.Set_Stored_Data_Count(getApplicationContext(), Stored_Data.Quizzes.length);
        }
        else {
            if(Stored_Data.Quizzes.length != Common.Get_Stored_Data_Count(getApplicationContext())){
                mySQLiteDB.deleteQuiz();
                for (int i = 0; i<Stored_Data.Quizzes.length; i++){
                    mySQLiteDB.insertQuiz(Stored_Data.Quizzes[i][0], Stored_Data.Quizzes[i][1], Stored_Data.Quizzes[i][2], Stored_Data.Quizzes[i][3], Stored_Data.Quizzes[i][4], Stored_Data.Quizzes[i][5], Stored_Data.Quizzes[i][6]);
                }
                Common.Set_Stored_Data_Count(getApplicationContext(), Stored_Data.Quizzes.length);
            }
            else {
                if(Common.Get_Is_Data_Modified(getApplicationContext())){
                    Store_Temporary_Data();
                    for (int i = 0; i<Stored_Data.Quizzes.length; i++){
                        mySQLiteDB.updateData(Stored_Data.QuizID.get(i),
                                (Stored_Data.Question.get(i).equals(Stored_Data.Quizzes[i][0])? Stored_Data.Question.get(i) : Stored_Data.Quizzes[i][0]),
                                (Stored_Data.Choice_1.get(i).equals(Stored_Data.Quizzes[i][1])? Stored_Data.Choice_1.get(i) : Stored_Data.Quizzes[i][1]),
                                (Stored_Data.Choice_2.get(i).equals(Stored_Data.Quizzes[i][2])? Stored_Data.Choice_2.get(i) : Stored_Data.Quizzes[i][2]),
                                (Stored_Data.Choice_3.get(i).equals(Stored_Data.Quizzes[i][3])? Stored_Data.Choice_3.get(i) : Stored_Data.Quizzes[i][3]),
                                (Stored_Data.Choice_4.get(i).equals(Stored_Data.Quizzes[i][4])? Stored_Data.Choice_4.get(i) : Stored_Data.Quizzes[i][4]),
                                (Stored_Data.Answer.get(i).equals(Stored_Data.Quizzes[i][5])? Stored_Data.Answer.get(i) : Stored_Data.Quizzes[i][5]),
                                (Stored_Data.Explanation.get(i).equals(Stored_Data.Quizzes[i][6])? Stored_Data.Explanation.get(i) : Stored_Data.Quizzes[i][6]));
                    }
                    Common.Set_Is_Data_Modified(getApplicationContext(), false);
                }
            }
        }

        Store_Temporary_Data();
    }

    public void Store_Temporary_Data(){
        if(Stored_Data.QuizID.size() != 0) Stored_Data.QuizID.clear();
        if(Stored_Data.Question.size() != 0) Stored_Data.Question.clear();
        if(Stored_Data.Choice_1.size() != 0) Stored_Data.Choice_1.clear();
        if(Stored_Data.Choice_2.size() != 0) Stored_Data.Choice_2.clear();
        if(Stored_Data.Choice_3.size() != 0) Stored_Data.Choice_3.clear();
        if(Stored_Data.Choice_4.size() != 0) Stored_Data.Choice_4.clear();
        if(Stored_Data.Answer.size() != 0) Stored_Data.Answer.clear();
        if(Stored_Data.Explanation.size() != 0) Stored_Data.Explanation.clear();

        try (Cursor cursor = new MySQLiteDB(getApplicationContext()).displayQuiz()) {
            while (cursor.moveToNext()) {
                Stored_Data.QuizID.add(cursor.getString(0));
                Stored_Data.Question.add(cursor.getString(1));
                Stored_Data.Choice_1.add(cursor.getString(2));
                Stored_Data.Choice_2.add(cursor.getString(3));
                Stored_Data.Choice_3.add(cursor.getString(4));
                Stored_Data.Choice_4.add(cursor.getString(5));
                Stored_Data.Answer.add(cursor.getString(6));
                Stored_Data.Explanation.add(cursor.getString(7));
            }
        }
    }

    public void Go_For_Exam(View view) {
        Next_Quiz();
    }

    private void Start_Quiz() {
        isExamStart = true;
        Quiz_Setup();
        Question_Setup(quiz_No);
        Choices_Setup(quiz_No);

        for(LinearLayoutCompat choice : new LinearLayoutCompat[]{choice_1, choice_2, choice_3, choice_4}){
            choice.setOnClickListener(new OptionChoose());
            Common.Bounce_Animation(getApplicationContext(), choice, 0.1, 10, 1500);
        }

        Start_Timer();

        timer_Tv.setVisibility(View.VISIBLE);
        quiz_Panel.setVisibility(View.VISIBLE);
        Common.Open_Animation(getApplicationContext(), quiz_Panel, 400);
        Common.Bounce_Animation(getApplicationContext(), findViewById(R.id.quiz_panel_layout), 0.1, 10, 1000);
        start_Panel.setVisibility(View.GONE);
        next_Btn.setText(getResources().getString(R.string.skip));
    }

    @SuppressLint("ResourceType")
    public void Open_Popup(View view) {
        final ListPopupWindow popupWindow = new ListPopupWindow(this);
        ArrayList<HashMap<String, String>> data = new ArrayList<>();

        switch (view.getId()){
            case R.id.category_panel:
                for(String category: Stored_Data.Category_List){
                    HashMap<String, String> map = new HashMap<>();
                    map.put("Category_Key", category);
                    data.add(map);
                }

                PopupMenu_Setup(data, "Category_Key", findViewById(R.id.category_btn), popupWindow, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        exam_Category.setText(Stored_Data.Category_List.get(position));
                        Common.Bounce_Animation(getApplicationContext(), view, 0.1, 10, 1500);
                        popupWindow.dismiss();
                    }
                });
                break;

            case R.id.question_panel:
                for(int limit: Stored_Data.Question_Limit_List){
                    HashMap<String, String> map = new HashMap<>();
                    map.put("Question_Key", limit+" Questions");
                    data.add(map);
                }

                PopupMenu_Setup(data, "Question_Key", findViewById(R.id.question_btn), popupWindow, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Stored_Data.Question_Limit = Stored_Data.Question_Limit_List.get(position);
                        Stored_Data.Time_Limit = (long) Stored_Data.Time_Limit_List.get(position);
                        exam_Questions.setText(String.valueOf(Stored_Data.Question_Limit));
                        exam_Marks.setText(String.valueOf(Stored_Data.Question_Limit_List.get(position)));
                        exam_Time.setText(TimeFormater(Stored_Data.Time_Limit) + " min");
                        Common.Bounce_Animation(getApplicationContext(), view, 0.1, 10, 1000);
                        popupWindow.dismiss();
                    }
                });
                break;
        }
    }

    private void PopupMenu_Setup(ArrayList<HashMap<String, String>> data, String key, View anchor, ListPopupWindow popupWindow, AdapterView.OnItemClickListener listener) {
        popupWindow.setAdapter(new SimpleAdapter(this, data, R.layout.layout_popup, new String[]{key}, new int[]{R.id.message_tv}){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                AppCompatTextView text = view.findViewById(android.R.id.text1);
                Common.Set_Font(getApplicationContext(), new View[]{text}, "pf_regular.ttf");
                return view;
            }
        });

        popupWindow.setAnchorView(anchor);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(450);
        popupWindow.setHorizontalOffset(-450);
        popupWindow.setOnItemClickListener(listener);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_select));
        popupWindow.setModal(true);
        popupWindow.show();
    }

    public void Show_History(View view) {
        Cursor cursor = new MySQLiteDB(getApplicationContext()).displayHistory();

        if(cursor.getCount() != 0){
            ArrayList<HashMap<String, String>> data = new ArrayList<>();

            if(history_List.getVisibility() == View.GONE){
                while(cursor.moveToNext()){
                    HashMap<String, String> map = new HashMap<>();
                    map.put(MySQLiteDB.HISTORY_ID, cursor.getString(0));
                    map.put(MySQLiteDB.CATEGORY_HISTORY, cursor.getString(1));
                    map.put(MySQLiteDB.TIME_HISTORY, cursor.getString(2));
                    map.put(MySQLiteDB.QUESTION_HISTORY, cursor.getString(3));
                    map.put(MySQLiteDB.MARKS_HISTORY, cursor.getString(4));
                    data.add(map);

                    HistoryCustomAdapter adapter = new HistoryCustomAdapter(getApplicationContext(), data, R.layout.sample_list_history,
                            new String[]{MySQLiteDB.HISTORY_ID, MySQLiteDB.CATEGORY_HISTORY, MySQLiteDB.TIME_HISTORY, MySQLiteDB.QUESTION_HISTORY, MySQLiteDB.MARKS_HISTORY},
                            new int[]{R.id.mock_no_tv, R.id.category_tv, R.id.time_tv, R.id.questions, R.id.marks});

                    history_List.setAdapter(adapter);
                }

                findViewById(R.id.start_panel_scrollView).setVisibility(View.GONE);
                history_List.setVisibility(View.VISIBLE);
                start_Btn.setText(getResources().getString(R.string.clear));
            }
            else {
                findViewById(R.id.start_panel_scrollView).setVisibility(View.VISIBLE);
                history_List.setVisibility(View.GONE);
                start_Btn.setText(getResources().getString(R.string.begin));
            }
        }
        else {
            Custom_Dialog("Sorry! you haven't attended any exam yet to view records, do you want to attend now?", getResources().getString(R.string.yes) + ", Start","join");
        }
    }

    public void Clear_History() {
        new MySQLiteDB(getApplicationContext()).deleteHistory();
        findViewById(R.id.start_panel_scrollView).setVisibility(View.VISIBLE);
        history_List.setVisibility(View.GONE);
        history_List.setAdapter(null);
        start_Btn.setText(getResources().getString(R.string.begin));
    }

    private class OptionChoose implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            for(LinearLayoutCompat choice: new LinearLayoutCompat[]{choice_1, choice_2, choice_3, choice_4}){
                choice.setBackgroundResource(R.color.colorGray);
            }
            v.setBackgroundResource(R.drawable.bg_select);
            next_Btn.setText(getResources().getString(R.string.next));
            Common.Bounce_Animation(getApplicationContext(), v, 0.1, 10, 1000);
        }
    }

    private void Next_Quiz() {
        if(!isExamFinish){
            Match_Answer(quiz_No);
        }
        quiz_No++;
        if(quiz_No < Stored_Data.Question_Limit){
            for (LinearLayoutCompat choice: new LinearLayoutCompat[]{choice_1, choice_2, choice_3, choice_4}){
                choice.setBackgroundResource(R.color.colorGray);
                Common.Bounce_Animation(getApplicationContext(), choice, 0.1, 10, 1500);
            }
            Question_Setup(quiz_No);
            Choices_Setup(quiz_No);
            next_Btn.setText(getResources().getString(R.string.skip));
            if(quiz_No == (Stored_Data.Question_Limit-1)){
                isExamFinish = true;
            }
            Common.Bounce_Animation(getApplicationContext(), findViewById(R.id.quiz_panel_layout), 0.1, 10, 1000);
        }
        else {
            Custom_Dialog("Are you sure that you want to submit your answer's?", "Yes, " + getResources().getString(R.string.submit), "submit");
        }
    }

    private void Quiz_Setup(){
        for(int position : Stored_Data.Random_Questions()){
            Current_Question.add(Stored_Data.Question.get(position));
            Current_Choice_1.add(Stored_Data.Choice_1.get(position));
            Current_Choice_2.add(Stored_Data.Choice_2.get(position));
            Current_Choice_3.add(Stored_Data.Choice_3.get(position));
            Current_Choice_4.add(Stored_Data.Choice_4.get(position));
            Right_Answer.add(Stored_Data.Answer.get(position).toUpperCase());
            Current_Explanation.add(Stored_Data.Explanation.get(position));
        }
    }

    private void Question_Setup(int n) {
        question_Tv.setText((Current_Question.get(n)));
        quiz_No_Tv.setText("Q." + (quiz_No+1));
    }

    private void Choices_Setup(int n) {
        choice_1_Tv.setText(Current_Choice_1.get(n));
        choice_2_Tv.setText(Current_Choice_2.get(n));
        choice_3_Tv.setText(Current_Choice_3.get(n));
        choice_4_Tv.setText(Current_Choice_4.get(n));
    }

    private void Match_Answer(int n) {
        try {
            if(next_Btn.getText().equals(getResources().getString(R.string.skip))){
                Current_Answer.add("null");
            }
            else {
                for (LinearLayoutCompat choice: new LinearLayoutCompat[]{choice_1, choice_2, choice_3, choice_4}){
                    if(choice.getBackground().getConstantState() == getResources().getDrawable(R.drawable.bg_select).getConstantState()){
                        Current_Answer.add(choice.getTag().toString());
                        Stored_Data.Current_Question_Answered ++;
                        if(choice.getTag().equals(Right_Answer.get(n))){
                            points++;
                            break;
                        }
                    }
                }
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void Start_Timer() {
        Move_Timer = new CountDownTimer(Stored_Data.Time_Limit, 1000) {
            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                timer_Tv.setText(TimeFormater(millisUntilFinished));
                Stored_Data.Current_Time = millisUntilFinished;
            }
            public void onFinish() {
                Stored_Data.Current_Time = 0;
                Show_Result();
            }
        }.start();

        isTimerOn = true;
    }

    private void Stop_Timer() {
        if(isTimerOn){
            isTimerOn = false;
            Move_Timer.cancel();
        }
    }

    @SuppressLint("RestrictedApi")
    private void Restart_Quiz() {
        isExamStart = false;
        isExamFinish = false;
        start_Panel.setVisibility(View.VISIBLE);
        findViewById(R.id.start_panel_scrollView).setVisibility(View.VISIBLE);
        start_Btn.setText(getResources().getString(R.string.begin));
        findViewById(R.id.exam_instructions).setVisibility(View.GONE);
        ((AppCompatImageButton) findViewById(R.id.hide_btn)).setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_more));

        home_Btn.setVisibility(View.GONE);
        next_Btn.setText(getResources().getString(R.string.skip));

        Stop_Timer();
        timer_Tv.setVisibility(View.GONE);
        timer_Tv.setText("00:00");

        quiz_Panel.setVisibility(View.GONE);
        Current_Question.clear();
        Current_Choice_1.clear();
        Current_Choice_2.clear();
        Current_Choice_3.clear();
        Current_Choice_4.clear();
        Right_Answer.clear();
        Current_Answer.clear();
        Current_Explanation.clear();

        for (LinearLayoutCompat card: new LinearLayoutCompat[]{choice_1, choice_2, choice_3, choice_4}){
            card.setBackgroundResource(R.color.colorGray);
        }

        result_List.setVisibility(View.GONE);
        history_List.setVisibility(View.GONE);
        result_List.setAdapter(null);

        quiz_No = 0;
        points = 0;
        Stored_Data.Warning_Limit = 2;
        Stored_Data.Current_Time = 0;
        Stored_Data.Current_Question_Answered = 0;
    }

    @SuppressLint("DefaultLocale")
    private String TimeFormater(long millis) {
        int minutes = (int) ((millis/1000) / 60);
        int seconds = (int) ((millis/1000) % 60);
        return String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
    }

    @SuppressLint("RestrictedApi")
    private void Show_Result() {
        isExamStart = false;
        timer_Tv.setVisibility(View.GONE);
        Stop_Timer();

        Match_Answer(quiz_No);
        Result_Dialog(points, Stored_Data.Warning_Limit);

        quiz_Panel.setVisibility(View.GONE);
        result_List.setVisibility(View.VISIBLE);

        if(Current_Answer.size() != Stored_Data.Question_Limit){
            for(int i = Current_Answer.size(); i<Stored_Data.Question_Limit; i++){
                Current_Answer.add("null");
            }
        }

        ResultCustomAdapter adapter = new ResultCustomAdapter(getApplicationContext(), Current_Question, Current_Choice_1, Current_Choice_2, Current_Choice_3, Current_Choice_4, Right_Answer, Current_Answer, Current_Explanation);
        result_List.setAdapter(adapter);

        result_List.setOnBottomReachedListener(new ScrollListView.OnBottomReachedListener() {
            @Override
            public void onBottomReached() {
                if(home_Btn.getVisibility() == View.GONE){
                    home_Btn.setVisibility(View.VISIBLE);
                }
            }
        });

        result_List.setOnNotBottomReachedListener(new ScrollListView.OnNotBottomReachedListener() {
            @Override
            public void onNotBottomReached() {
                if(home_Btn.getVisibility() == View.VISIBLE){
                    home_Btn.setVisibility(View.GONE);
                }
            }
        });
    }

    private void Result_Dialog(int points, int warning) {
        View view = View.inflate(getApplicationContext(),R.layout.layout_dialog, null);

        if (warningDialog != null && warningDialog.isShowing()) warningDialog.dismiss();
        if (quitDialog != null && quitDialog.isShowing()) quitDialog.dismiss();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setCancelable(true);

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(alertDialog.getWindow()).getDecorView().setBackgroundColor(Color.TRANSPARENT);
        alertDialog.show();

        AppCompatTextView result = view.findViewById(R.id.message_tv);
        AppCompatImageButton cancel_Btn = view.findViewById(R.id.cancel_btn);

        Common.Set_Font(getApplicationContext(), new View[]{result}, "pf_regular.ttf");
        if(warning == 3){
            result.setText("Sorry! You've eliminated.\n\nPoints\n0" + "\n\nGrade\nF\n\nRemarks\nFail");
        }
        else {
            int marks = ((points / (Stored_Data.Question_Limit/10)) * 10);
            result.setText("Congratulations! You've successfully completed your exam.\n\nPoints\n"+ points +"\n\n"+Result(marks));
        }

        new MySQLiteDB(getApplicationContext()).insertHistory(
                exam_Category.getText().toString().trim() + "",
                TimeFormater(Stored_Data.Time_Limit - Stored_Data.Current_Time) + "",
                Stored_Data.Current_Question_Answered + "/" + exam_Questions.getText().toString().trim(),
                points + "/" + exam_Marks.getText().toString().trim()
        );

        cancel_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    private String Result(double marks){
        if(marks>=80 && marks<=100){
            return ("Grade\nA+\n\nRemarks\nExcellent");
        }
        else if(marks>=70 && marks<80){
            return ("Grade\nA\n\nRemarks\nVery Good");
        }
        else if(marks>=60 && marks<70){
            return ("Grade\nA-\n\nRemarks\nGood");
        }
        else if(marks>=50 && marks<60){
            return ("Grade\nB\n\nRemarks\nSatisfactory");
        }
        else if(marks>=40 && marks<50){
            return ("Grade\nC\n\nRemarks\nAverage");
        }
        else if(marks>=33 && marks<40){
            return ("Grade\nD\n\nRemarks\nPass");
        }
        else {
            return ("Grade\nF\n\nRemarks\nFail");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!(warningDialog != null && warningDialog.isShowing())) {
            Stored_Data.Warning_Limit--;
            if (Stored_Data.Warning_Limit < 0) {
                Stop_Timer();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (isExamStart && pm != null && pm.isScreenOn()) {
            if (!(warningDialog != null && warningDialog.isShowing())) {
                if (Stored_Data.Warning_Limit < 0) {
                    Result_Dialog(points, Stored_Data.Warning_Limit);
                    Restart_Quiz();
                }
                else {
                    if (quitDialog != null && quitDialog.isShowing()) quitDialog.dismiss();
                    Warning_Dialog(Stored_Data.Warning_Limit);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
       if(isExamStart){
           Custom_Dialog("Are you sure you want to quit exam?", getApplicationContext().getString(R.string.quit),"quit");
       }
       else {
           if(result_List.getVisibility() == View.VISIBLE){
               Restart_Quiz();
           }
           else if(history_List.getVisibility() == View.VISIBLE){
               findViewById(R.id.start_panel_scrollView).setVisibility(View.VISIBLE);
               history_List.setVisibility(View.GONE);
               start_Btn.setText(getResources().getString(R.string.begin));
           }
           else {
               Custom_Dialog("Are you sure you want to close this app?", getApplicationContext().getString(R.string.yes),"close");
           }
       }
    }

    private void Warning_Dialog(int warning) {
        View view = View.inflate(getApplicationContext(),R.layout.layout_dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setCancelable(true);

        warningDialog = alertDialogBuilder.create();
        warningDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(warningDialog.getWindow()).getDecorView().setBackgroundColor(Color.TRANSPARENT);
        warningDialog.show();

        AppCompatTextView message = view.findViewById(R.id.message_tv);
        AppCompatImageButton cancel_Btn = view.findViewById(R.id.cancel_btn);

        Common.Set_Font(getApplicationContext(), new View[]{message}, "pf_regular.ttf");

        message.setText("⚠\nWarning remaining: "+ warning +"\n\nOnce you have eliminated from exam, you will not be able to view the previous question.");

        cancel_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                warningDialog.dismiss();
            }
        });
    }

    private void Custom_Dialog(String text, String button_name, final String action) {
        View view = View.inflate(getApplicationContext(),R.layout.layout_dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setCancelable(true);

        quitDialog = alertDialogBuilder.create();
        quitDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(quitDialog.getWindow()).getDecorView().setBackgroundColor(Color.TRANSPARENT);
        quitDialog.show();

        AppCompatTextView message = view.findViewById(R.id.message_tv);
        AppCompatButton button = view.findViewById(R.id.quit_btn);

        message.setText(text);
        button.setText(button_name);

        Common.Set_Font(getApplicationContext(), new View[]{message, button}, "pf_regular.ttf");

        button.setVisibility(View.VISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quitDialog.dismiss();

                switch (action){
                    case "submit":
                        Show_Result();
                        break;

                    case "clear":
                        Clear_History();
                        break;

                    case "quit":
                        Restart_Quiz();
                        break;

                    case "close":
                        ContentActivity.super.onBackPressed();
                        break;

                    case "join":
                        Start_Quiz();
                        break;
                }
            }
        });

        AppCompatImageButton cancel_Btn = view.findViewById(R.id.cancel_btn);
        cancel_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quitDialog.dismiss();
            }
        });
    }

    public String fmt(double d) {
        if(d == (long) d)
            return String.format("%d", (long) d);
        else
            return String.format("%s",d);
    }

    private void Default_Fragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            /*HomeFragment home = new HomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content_id, home).commit();*/
        }
    }

    @Override public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int i = 0; i < permissions.length; i++) {

            switch (requestCode) {
                case 111:
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {

                        /* If user rejected the permission */
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

                            if (! shouldShowRequestPermissionRationale(permissions[i])) {

                                new AlertDialog.Builder(this, R.style.CustomDialogTheme)
                                        .setCancelable(true)
                                        .setTitle("Storage Permission")
                                        .setMessage("To share this app, allow "+ getResources().getString(R.string.app_name) +" access to your Storage Option. Tap Settings, Permission and turn Storage on")
                                        .setIcon(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_folder))
                                        .setPositiveButton("SETTINGS", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getApplicationContext().getPackageName()));
                                                intent.addCategory(Intent.CATEGORY_DEFAULT);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                getApplicationContext().startActivity(intent);
                                            }
                                        })
                                        .setNegativeButton("NOT NOW", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                /*You can code here, by default cancel dialog*/
                                            }
                                        })
                                        .create()
                                        .show();
                            }
                            else if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permissions[i])) {
                                /*If user did NOT check "never ask again*/
                            }
                        }
                    }
                    else {
                        Share_Apk();
                    }
                    break;
            }
        }
    }

    public boolean isPermissionGranted(int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
            else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
                return false;
            }
        }
        else {
            return true;
        }
    }

    private void Share_Apk() {
        try {
            File initialApkFile = new File(getPackageManager().getApplicationInfo(getPackageName(), 0).sourceDir);

            File tempFile = new File(getExternalCacheDir() + "/ExtractedApk");

            if (!tempFile.isDirectory())
                if (!tempFile.mkdirs())
                    return;

            tempFile = new File(tempFile.getPath() + "/" + getResources().getString(R.string.app_name) + ".apk");

            if (!tempFile.exists()) {
                if (!tempFile.createNewFile()) {
                    return;
                }
            }

            InputStream in = new FileInputStream(initialApkFile);
            OutputStream out = new FileOutputStream(tempFile);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();

            Intent share = new Intent();
            share.setAction(Intent.ACTION_SEND);
            share.setType("*/*");
            share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(tempFile));
            startActivity(Intent.createChooser(share, "Share " + getResources().getString(R.string.app_name) + " Via"));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void Share_App() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");

        String subject = getResources().getString(R.string.app_name);
        String body = "\nLet me recommend you this application\n\n" + "Get this app from play store  " + Uri.parse("https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());

        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        startActivity(Intent.createChooser(intent, "Share " + getResources().getString(R.string.app_name) + " Via"));
    }
}