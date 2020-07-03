package com.lict.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteDB extends SQLiteOpenHelper{

    private static final String DATABASE_NAME="Quiz.db";
    private static final int VERSION_NUMBER = 1;

    private static final String QUIZ_TABLE_NAME ="quiz_table";
    private static final String QUIZ_ID ="_quizId";
    private static final String QUESTION = "question";
    private static final String CHOICE_1 = "choice_1";
    private static final String CHOICE_2 = "choice_2";
    private static final String CHOICE_3 = "choice_3";
    private static final String CHOICE_4 = "choice_4";
    private static final String ANSWER = "answer";
    private static final String EXPLANATION = "explanation";

    private static final String CREATE_QUIZ_TABLE = "CREATE TABLE "+ QUIZ_TABLE_NAME +"( "+ QUIZ_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+ QUESTION +" VARCHAR(255),"+
            CHOICE_1 +" VARCHAR(255), " + CHOICE_2 +" VARCHAR(255), "+ CHOICE_3 +" VARCHAR(255), " + CHOICE_4 +" VARCHAR(255), " + ANSWER +" VARCHAR(255), "+ EXPLANATION +" VARCHAR(1000));";
    private static final String DROP_QUIZ_TABLE = "DROP TABLE IF EXISTS "+ QUIZ_TABLE_NAME;
    private static final String SELECT_ALL_QUIZZES = "SELECT * FROM "+ QUIZ_TABLE_NAME;
    private static final String DELETE_ALL_QUIZZES = "DELETE FROM "+ QUIZ_TABLE_NAME;


    public static final String HISTORY_TABLE_NAME ="history_table";
    public static final String HISTORY_ID ="_historyId";
    public static final String CATEGORY_HISTORY = "category_history";
    public static final String TIME_HISTORY = "time_history";
    public static final String QUESTION_HISTORY = "question_history";
    public static final String MARKS_HISTORY = "marks_history";

    private static final String CREATE_HISTORY_TABLE = "CREATE TABLE "+ HISTORY_TABLE_NAME +"( "+ HISTORY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            CATEGORY_HISTORY +" VARCHAR(25)," + TIME_HISTORY +" VARCHAR(25), " + QUESTION_HISTORY +" VARCHAR(25), " + MARKS_HISTORY +" VARCHAR(25));";
    private static final String DROP_HISTORY_TABLE = "DROP TABLE IF EXISTS "+ HISTORY_TABLE_NAME;
    private static final String SELECT_ALL_HISTORIES = "SELECT * FROM "+ HISTORY_TABLE_NAME;
    private static final String DELETE_ALL_HISTORIES = "DELETE FROM "+ HISTORY_TABLE_NAME;

    private Context context;


    public MySQLiteDB(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUMBER);
        this.context = context;
    }


    @Override public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(CREATE_QUIZ_TABLE);
            db.execSQL(CREATE_HISTORY_TABLE);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }


    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try{
            db.execSQL(DROP_QUIZ_TABLE);
            db.execSQL(DROP_HISTORY_TABLE);
            onCreate(db);
        }
        catch (Exception ex){
            Common.Custom_Toast(context, "Error: "+ex.getMessage());
            ex.printStackTrace();
        }
    }


    /* Insert Data */
    public void insertQuiz(String question, String choice_1, String choice_2, String choice_3, String choice_4, String answer, String explanation){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(QUESTION, question);
        contentValues.put(CHOICE_1, choice_1);
        contentValues.put(CHOICE_2, choice_2);
        contentValues.put(CHOICE_3, choice_3);
        contentValues.put(CHOICE_4, choice_4);
        contentValues.put(ANSWER, answer);
        contentValues.put(EXPLANATION, explanation);
        sqLiteDatabase.insert(QUIZ_TABLE_NAME, null, contentValues);
    }

    public void insertHistory(String category, String time, String question, String marks){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORY_HISTORY, category);
        contentValues.put(TIME_HISTORY, time);
        contentValues.put(QUESTION_HISTORY, question);
        contentValues.put(MARKS_HISTORY, marks);
        sqLiteDatabase.insert(HISTORY_TABLE_NAME, null, contentValues);
    }


    /* Read Data */
    public Cursor displayQuiz(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.rawQuery(SELECT_ALL_QUIZZES,null);
    }

    public Cursor displayHistory(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.rawQuery(SELECT_ALL_HISTORIES,null);
    }


    /* Update Data */
    public void updateData(String id, String question, String choice_1, String choice_2, String choice_3, String choice_4, String answer, String explanation){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(QUIZ_ID, id);
        contentValues.put(QUESTION, question);
        contentValues.put(CHOICE_1, choice_1);
        contentValues.put(CHOICE_2, choice_2);
        contentValues.put(CHOICE_3, choice_3);
        contentValues.put(CHOICE_4, choice_4);
        contentValues.put(ANSWER, answer);
        contentValues.put(EXPLANATION, explanation);
        sqLiteDatabase.update(QUIZ_TABLE_NAME,contentValues,QUIZ_ID + " = ?", new String[]{id});
    }

    public void updateHistory(String id, String category, String time, String question, String marks){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(QUIZ_ID, id);
        contentValues.put(CATEGORY_HISTORY, category);
        contentValues.put(TIME_HISTORY, time);
        contentValues.put(QUESTION_HISTORY, question);
        contentValues.put(MARKS_HISTORY, marks);
        sqLiteDatabase.update(HISTORY_TABLE_NAME,contentValues,QUIZ_ID + " = ?", new String[]{id});
    }


    /* Delete Data */
    public void deleteQuiz(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL(DELETE_ALL_QUIZZES);
        if(Stored_Data.QuizID.size() != 0) Stored_Data.QuizID.clear();
        if(Stored_Data.Question.size() != 0) Stored_Data.Question.clear();
        if(Stored_Data.Choice_1.size() != 0) Stored_Data.Choice_1.clear();
        if(Stored_Data.Choice_2.size() != 0) Stored_Data.Choice_2.clear();
        if(Stored_Data.Choice_3.size() != 0) Stored_Data.Choice_3.clear();
        if(Stored_Data.Choice_4.size() != 0) Stored_Data.Choice_4.clear();
        if(Stored_Data.Answer.size() != 0) Stored_Data.Answer.clear();
        if(Stored_Data.Explanation.size() != 0) Stored_Data.Explanation.clear();
    }

    public void deleteQuizItem(String id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(QUIZ_TABLE_NAME, QUIZ_ID +" = ?", new String[]{id});
    }

    public void deleteHistory(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL(DELETE_ALL_HISTORIES);
    }

    public void deleteHistoryItem(String id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(HISTORY_TABLE_NAME, QUIZ_ID +" = ?", new String[]{id});
    }
}
