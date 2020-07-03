package com.lict.quizapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import java.util.ArrayList;
import java.util.HashMap;

public class HistoryCustomAdapter extends SimpleAdapter {

    private Context context;
    private ArrayList<HashMap<String, String>> data;
    private LayoutInflater inflater;

    public HistoryCustomAdapter(Context context, ArrayList<HashMap<String, String>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater.from(context));
    }

    @SuppressLint("SetTextI18n")
    public View getView(int position, View view, ViewGroup parent){
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            view = inflater.inflate(R.layout.sample_list_history, null);
        }

        AppCompatTextView mock_no = view.findViewById(R.id.mock_no_tv);
        AppCompatTextView category = view.findViewById(R.id.category_tv);

        AppCompatButton time = view.findViewById(R.id.time_tv);
        AppCompatTextView questions = view.findViewById(R.id.questions);
        AppCompatTextView marks = view.findViewById(R.id.marks);

        mock_no.setText("Mock\n" + (position+1));
        category.setText(data.get(position).get(MySQLiteDB.CATEGORY_HISTORY));
        time.setText(data.get(position).get(MySQLiteDB.TIME_HISTORY));
        questions.setText(data.get(position).get(MySQLiteDB.QUESTION_HISTORY));
        marks.setText(data.get(position).get(MySQLiteDB.MARKS_HISTORY));

        Common.Set_Font(context, new View[]{mock_no, category, time, questions, marks, view.findViewById(R.id.questions_tv), view.findViewById(R.id.marks_tv)},"pf_regular.ttf");

        return view;
    }
}

