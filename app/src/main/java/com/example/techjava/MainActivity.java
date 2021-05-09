package com.example.techjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    int int_active;
    EditText etAddress = (EditText) findViewById(R.id.etAddress);
    EditText etNumber = (EditText) findViewById(R.id.etNumber);
    EditText etI11 = (EditText) findViewById(R.id.etI11);
    EditText etI12 = (EditText) findViewById(R.id.etI12);
    EditText etI21 = (EditText) findViewById(R.id.etI21);
    EditText etI22 = (EditText) findViewById(R.id.etI22);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(getApplicationContext());
        databaseHelper.create_db();
        int_active = R.id.etAddress;
        SetBtnListeners();
        RadioGroup radGrp = (RadioGroup)findViewById(R.id.radios);
        radGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int id) {
                TextView tvResult = (TextView) findViewById(R.id.tvResult);
                switch (id) {
                    case R.id.rbAddress:
                        etAddress.setText("");
                        int_active = R.id.etAddress;
                        break;
                    case R.id.rbNumber:
                        etNumber.setText("");
                        int_active = R.id.etNumber;
                        break;
                    case R.id.rbI11:
                        etI11.setText("");
                        int_active = R.id.etI11;
                        break;
                    case R.id.rbI12:
                        etI12.setText("");
                        int_active = R.id.etI12;
                        break;
                    case R.id.rbI21:
                        etI21.setText("");
                        int_active = R.id.etI21;
                        break;
                    case R.id.rbI22:
                        etAddress.setText("");
                        int_active = R.id.etI22;
                        break;
                    default:
                        break;
                }
            }
        });
    }
    public int StrToInt(String str){
        if(str.isEmpty())
            return 0;
        return Integer.parseInt(str);
    }
    public void Search(View view){
        int address, number, i11, i12, i21, i22;
        address = StrToInt(()).getText().toString());
        number = StrToInt(((EditText) findViewById(R.id.etNumber)).getText().toString());
        i11 = StrToInt(((EditText) findViewById(R.id.etI11)).getText().toString());
        i12 = StrToInt(((EditText) findViewById(R.id.etI12)).getText().toString());
        i21 = StrToInt(((EditText) findViewById(R.id.etI21)).getText().toString());
        i22 = StrToInt(((EditText) findViewById(R.id.etI22)).getText().toString());
        String str = "";
        TextView textView = (TextView) findViewById(R.id.tvResult);
        db = databaseHelper.open();

        String str_comm = "select * from " + DatabaseHelper.TABLE +
                String.format("  Where address = %d and number = %d and i11 = %d and i12 = %d and i21 = %d and i22 = %d ", address, number, i11, i12, i21, i22) ;
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
    public void SetBtnListeners(){
        Button btn0 = (Button) findViewById(R.id.btn0);
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditText) findViewById(int_active)).append("0");
            }
        });
        Button btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditText) findViewById(int_active)).append("1");
            }
        });
        Button btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditText) findViewById(int_active)).append("2");
            }
        });
        Button btn3 = (Button) findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditText) findViewById(int_active)).append("3");
            }
        });
        Button btn4 = (Button) findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditText) findViewById(int_active)).append("4");
            }
        });
        Button btn5 = (Button) findViewById(R.id.btn5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditText) findViewById(int_active)).append("5");
            }
        });
        Button btn6 = (Button) findViewById(R.id.btn6);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditText) findViewById(int_active)).append("6");
            }
        });
        Button btn7 = (Button) findViewById(R.id.btn7);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditText) findViewById(int_active)).append("7");
            }
        });
        Button btn8 = (Button) findViewById(R.id.btn8);
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditText) findViewById(int_active)).append("8");
            }
        });
        Button btn9 = (Button) findViewById(R.id.btn9);
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditText) findViewById(int_active)).append("9");
            }
        });
        Button btnCE = (Button) findViewById(R.id.btnCE);
        btnCE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditText) findViewById(int_active)).setText("");
            }
        });
    }
}