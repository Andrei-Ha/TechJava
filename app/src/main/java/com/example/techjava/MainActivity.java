package com.example.techjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(getApplicationContext());
        databaseHelper.create_db();
    }
    public void Search(View view){
        String str = "";
        EditText editText = (EditText) findViewById(R.id.etID);
        TextView textView = (TextView) findViewById(R.id.tvResult);
        db = databaseHelper.open();

        String str_comm = "select * from " + DatabaseHelper.TABLE + " Where _id = " + editText.getText();
        userCursor = db.rawQuery(str_comm, null);
        if(userCursor.getCount() > 0){
            userCursor.moveToFirst();
            str += "Вероятная причина отказа:\n" + userCursor.getString(userCursor.getColumnIndex(databaseHelper.COL_CAUSE)) + "\n";
            str += "Функциональное назначение отказавшего узла:\n" + userCursor.getString(userCursor.getColumnIndex(databaseHelper.COL_PURPOSE)) + "\n";
            str += "Метод устранения:\n" + userCursor.getString(userCursor.getColumnIndex(databaseHelper.COL_METHOD)) + "\n";
            str += "Последствия отказа:\n" + userCursor.getString(userCursor.getColumnIndex(databaseHelper.COL_CONSEQUENCES)) + "\n";
            // передача результата в новое activity
            Intent intent = new Intent(this, MessageActivity.class);
            intent.putExtra("message", str);
            startActivity(intent);
        }
        else {
            textView.setText("Нет данных");
        }
        userCursor.close();
        db.close();

    }
}