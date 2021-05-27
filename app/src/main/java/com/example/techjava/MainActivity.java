package com.example.techjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;

import android.database.CursorWindow;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {
    private static final boolean DEBUG_MODE = true;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    int int_etActive;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_exit) {
            finishAndRemoveTask();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 10 * 1024 * 1024); //the 100MB is the new size
        } catch (Exception e) {
            if (DEBUG_MODE) {
                e.printStackTrace();
            }
        }

        databaseHelper = new DatabaseHelper(getApplicationContext());
        databaseHelper.create_db();
        int_etActive = R.id.etAddress;
        SetBtnListeners();
        SetEditTextListeners();
    }
    public int StrToInt(String str){
        if(str.isEmpty())
            return 0;
        return Integer.parseInt(str);
    }
    public void Search(View view){
        int address, number, i11, i12, i21, i22;
        address = StrToInt(((EditText) findViewById(R.id.etAddress)).getText().toString());
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
            // передача результата в новое activity
        if(userCursor.getCount() > 0){
            userCursor.moveToFirst();
            str = userCursor.getString(userCursor.getColumnIndex(databaseHelper.COLUMN_ID));
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

    public void SetETActive(View v){
        ClearTVResult();
        EditText et = (EditText) v;
            switch (et.getId()) {
                case R.id.etAddress:
                    EditText etAddress = (EditText) findViewById(R.id.etAddress);
                    etAddress.setText("");
                    int_etActive = R.id.etAddress;
                    break;
                case R.id.etNumber:
                    EditText etNumber = (EditText) findViewById(R.id.etNumber);
                    etNumber.setText("");
                    int_etActive = R.id.etNumber;
                    break;
                case R.id.etI11:
                    EditText etI11 = (EditText) findViewById(R.id.etI11);
                    etI11.setText("");
                    int_etActive = R.id.etI11;
                    break;
                case R.id.etI12:
                    EditText etI12 = (EditText) findViewById(R.id.etI12);
                    etI12.setText("");
                    int_etActive = R.id.etI12;
                    break;
                case R.id.etI21:
                    EditText etI21 = (EditText) findViewById(R.id.etI21);
                    etI21.setText("");
                    int_etActive = R.id.etI21;
                    break;
                case R.id.etI22:
                    EditText etI22 = (EditText) findViewById(R.id.etI22);
                    etI22.setText("");
                    int_etActive = R.id.etI22;
                    break;
                default:
                    break;
            }
    }
    public void ClearTVResult(){
        TextView tv = (TextView) findViewById(R.id.tvResult);
        tv.setText("");
    }
    public void SetEditTextListeners(){
        View.OnFocusChangeListener focusListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    SetETActive(v);
            }
        };
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditText) v).setText("");
                ClearTVResult();
            }
        };
        EditText etAddress = (EditText) findViewById(R.id.etAddress);
        etAddress.requestFocus();
        etAddress.setShowSoftInputOnFocus(false);
        etAddress.setOnFocusChangeListener(focusListener);
        etAddress.setOnClickListener(clickListener);
        EditText etNumber = (EditText) findViewById(R.id.etNumber);
        etNumber.setShowSoftInputOnFocus(false);
        etNumber.setOnFocusChangeListener(focusListener);
        etNumber.setOnClickListener(clickListener);
        EditText etI11 = (EditText) findViewById(R.id.etI11);
        etI11.setShowSoftInputOnFocus(false);
        etI11.setOnFocusChangeListener(focusListener);
        etI11.setOnClickListener(clickListener);
        EditText etI12 = (EditText) findViewById(R.id.etI12);
        etI12.setShowSoftInputOnFocus(false);
        etI12.setOnFocusChangeListener(focusListener);
        etI12.setOnClickListener(clickListener);
        EditText etI21 = (EditText) findViewById(R.id.etI21);
        etI21.setShowSoftInputOnFocus(false);
        etI21.setOnFocusChangeListener(focusListener);
        etI21.setOnClickListener(clickListener);
        EditText etI22 = (EditText) findViewById(R.id.etI22);
        etI22.setShowSoftInputOnFocus(false);
        etI22.setOnFocusChangeListener(focusListener);
        etI22.setOnClickListener(clickListener);
    }
    public void SetBtnListeners(){
        Button btn0 = (Button) findViewById(R.id.btn0);
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Append("0");
            }
        });
        Button btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Append("1");            }
        });
        Button btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Append("2");            }
        });
        Button btn3 = (Button) findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Append("3");            }
        });
        Button btn4 = (Button) findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Append("4");            }
        });
        Button btn5 = (Button) findViewById(R.id.btn5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Append("5");            }
        });
        Button btn6 = (Button) findViewById(R.id.btn6);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Append("6");            }
        });
        Button btn7 = (Button) findViewById(R.id.btn7);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Append("7");            }
        });
        Button btn8 = (Button) findViewById(R.id.btn8);
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Append("8");            }
        });
        Button btn9 = (Button) findViewById(R.id.btn9);
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Append("9");
            }
        });
        Button btnCE = (Button) findViewById(R.id.btnCE);
        btnCE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditText) findViewById(int_etActive)).setText("");
                ClearTVResult();
            }
        });
    }
    public void Append(String str){
        EditText et = (EditText) findViewById(int_etActive);
        if(int_etActive == R.id.etAddress){
            if(et.getText().length() < 3)
                et.append(str);
        }
        else{
            if(et.getText().length() < 2)
                et.append(str);
        }
    }
}