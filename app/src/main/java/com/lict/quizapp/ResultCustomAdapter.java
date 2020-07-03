package com.lict.quizapp;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;

public class ResultCustomAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> question_list;
    private ArrayList<String> choice_1_list;
    private ArrayList<String> choice_2_list;
    private ArrayList<String> choice_3_list;
    private ArrayList<String> choice_4_list;
    private ArrayList<String> right_Answer_list;
    private ArrayList<String> current_Answer_list;
    private ArrayList<String> explanation_list;
    private LayoutInflater inflater;

    public ResultCustomAdapter(Context applicationContext, ArrayList<String> question_list, ArrayList<String> choice_1_list, ArrayList<String> choice_2_list, ArrayList<String> choice_3_list, ArrayList<String> choice_4_list, ArrayList<String> right_Answer_list, ArrayList<String> current_Answer_list, ArrayList<String> explanation_list) {
        this.context = applicationContext;
        this.question_list = question_list;
        this.choice_1_list = choice_1_list;
        this.choice_2_list = choice_2_list;
        this.choice_3_list = choice_3_list;
        this.choice_4_list = choice_4_list;
        this.right_Answer_list = right_Answer_list;
        this.current_Answer_list = current_Answer_list;
        this.explanation_list = explanation_list;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return question_list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint({"ViewHolder", "InflateParams", "SetTextI18n"})
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            view = inflater.inflate(R.layout.sample_list_result, null);
        }

        AppCompatTextView quiz_no = view.findViewById(R.id.quiz_no);
        AppCompatTextView question_Tv = view.findViewById(R.id.question_tv);

        AppCompatTextView choice_A = view.findViewById(R.id.choice_A);
        AppCompatTextView choice_B = view.findViewById(R.id.choice_B);
        AppCompatTextView choice_C = view.findViewById(R.id.choice_C);
        AppCompatTextView choice_D = view.findViewById(R.id.choice_D);

        AppCompatTextView choice_1_Tv = view.findViewById(R.id.choice_1_tv);
        AppCompatTextView choice_2_Tv = view.findViewById(R.id.choice_2_tv);
        AppCompatTextView choice_3_Tv = view.findViewById(R.id.choice_3_tv);
        AppCompatTextView choice_4_Tv = view.findViewById(R.id.choice_4_tv);

        final CardView explanation_panel = view.findViewById(R.id.explanation_panel);
        final AppCompatTextView explanation = view.findViewById(R.id.explanation_tv);
        final AppCompatButton explanation_btn = view.findViewById(R.id.explanation_btn);

        quiz_no.setText("Q." + (i+1));
        question_Tv.setText(question_list.get(i));
        choice_1_Tv.setText(choice_1_list.get(i));
        choice_2_Tv.setText(choice_2_list.get(i));
        choice_3_Tv.setText(choice_3_list.get(i));
        choice_4_Tv.setText(choice_4_list.get(i));
        explanation.setText(explanation_list.get(i));

        LinearLayoutCompat[] choices = new LinearLayoutCompat[]{view.findViewById(R.id.choice_1), view.findViewById(R.id.choice_2), view.findViewById(R.id.choice_3), view.findViewById(R.id.choice_4)};

        if(current_Answer_list.get(i).toUpperCase().equals(right_Answer_list.get(i).toUpperCase()) || current_Answer_list.get(i).equals("null")){
            Show_Answer(choices, right_Answer_list.get(i).toUpperCase(), R.color.right_ans_color);
        }
        else {
            Show_Answer(choices, right_Answer_list.get(i).toUpperCase(), R.color.right_ans_color);
            Show_Answer(choices, current_Answer_list.get(i).toUpperCase(), R.color.wrong_ans_color);
        }

        explanation.setTag(i);
        explanation_btn.setTag(i);
        explanation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((int)explanation.getTag() == (int)explanation_btn.getTag()){
                    Common.Bounce_Animation(context, v, 0.1, 10, 1000);
                    if(explanation_panel.getVisibility()==View.GONE){
                        explanation_panel.setVisibility(View.VISIBLE);
                    }
                    else {
                        explanation_panel.setVisibility(View.GONE);
                    }
                }
            }
        });

        Common.Set_Font(context, new View[]{quiz_no, question_Tv, choice_A, choice_1_Tv, choice_B, choice_2_Tv, choice_C, choice_3_Tv,
                choice_D, choice_4_Tv, explanation, explanation_btn},"pf_regular.ttf");

        return view;
    }

    private void Show_Answer(LinearLayoutCompat[] choice, String option, int color) {
        switch (option){
            case "A":
                choice[0].setBackgroundResource(color);
                break;
            case "B":
                choice[1].setBackgroundResource(color);
                break;
            case "C":
                choice[2].setBackgroundResource(color);
                break;
            case "D":
                choice[3].setBackgroundResource(color);
                break;
        }
    }
}

