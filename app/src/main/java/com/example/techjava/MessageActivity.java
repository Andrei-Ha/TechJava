package com.example.techjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

public class MessageActivity extends AppCompatActivity {
    private static final boolean DEBUG_MODE = true;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_exit) {
            //finishAndRemoveTask();
            finishAffinity();
            System.exit(0);
        }
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 10 * 1024 * 1024); //the 100MB is the new size
        } catch (Exception e) {
            if (DEBUG_MODE) {
                e.printStackTrace();
            }
        }
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int fotoWidth = 0, fotoHeight = 0;
        float k = 0;

        databaseHelper = new DatabaseHelper(getApplicationContext());
        databaseHelper.create_db();
        Intent intent = getIntent();
        String message = intent.getStringExtra("message");
        String str = "";

        db = databaseHelper.open();

        String str_comm = "select * from " + DatabaseHelper.TABLE + "  Where _id = " + message;
        userCursor = db.rawQuery(str_comm, null);
        if(userCursor.getCount() > 0) {
            userCursor.moveToFirst();
            str += FormInp("Адрес", userCursor.getString(userCursor.getColumnIndex(databaseHelper.COL_ADDRESS)));
            str += FormInp("Число", userCursor.getString(userCursor.getColumnIndex(databaseHelper.COL_NUMBER)));
            str += FormInp("Инф Н1 1СТ", userCursor.getString(userCursor.getColumnIndex(databaseHelper.COL_I11)));
            str += FormInp("Инф Н1 2СТ", userCursor.getString(userCursor.getColumnIndex(databaseHelper.COL_I12)));
            str += FormInp("Инф Н2 1СТ", userCursor.getString(userCursor.getColumnIndex(databaseHelper.COL_I21)));
            str += FormInp("Инф Н2 2СТ", userCursor.getString(userCursor.getColumnIndex(databaseHelper.COL_I22)));
            str += "<br/>";
            str += "<h3>Вероятная причина отказа:</h3><p>" + userCursor.getString(userCursor.getColumnIndex(databaseHelper.COL_CAUSE)) + "</p>";
            str += "<h3>Функциональное назначение отказавшего узла:</h3><p>" + userCursor.getString(userCursor.getColumnIndex(databaseHelper.COL_PURPOSE)) + "</p>";
            str += "<h3>Метод устранения:</h3><p>" + userCursor.getString(userCursor.getColumnIndex(databaseHelper.COL_METHOD)) + "</p>";
            str += "<h3>Последствия отказа:</h3><p>" + userCursor.getString(userCursor.getColumnIndex(databaseHelper.COL_CONSEQUENCES)) + "</p>";
            String strImageName = userCursor.getString(userCursor.getColumnIndex(databaseHelper.COL_IMAGE_NAME));
            byte[] imageData = new byte[1];
            String imageSize = "";
            LinearLayout myLayout = (LinearLayout)findViewById(R.id.llResult);
            //str += "<p>LayoutID: " + myLayout.getId() + "</p>";
            if (strImageName != null) {
                imageData = userCursor.getBlob(userCursor.getColumnIndex(databaseHelper.COL_IMAGE_DATA));
                imageSize = userCursor.getString(userCursor.getColumnIndex(databaseHelper.COL_IMAGE_SIZE));
                String[] sizes = imageSize.split(";");
                if (imageData != null) {
                    int sum = 0, size = 0;
                    ImageView[] imageArray = new ImageView[sizes.length];
                    for (int i = 0; i < sizes.length; i++) {
                        size = Integer.parseInt(sizes[i]);
                        imageArray[i] = new ImageView(this);
                        imageArray[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.MATCH_PARENT));
                        imageArray[i].setPadding(8,8,8,8);
                        //imageArray[i].setScaleType(ImageView.ScaleType.FIT_CENTER);
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = true;

                        BitmapFactory.decodeByteArray(imageData, sum, size, options);
                        k = (float)screenWidth / options.outWidth;
                        imageArray[i].setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeByteArray(imageData, sum, size),(int)(options.outWidth * k),(int) (options.outHeight * k), false));
                        myLayout.addView(imageArray[i]);
                        sum += size;
                    }
                }
            }
        }
        userCursor.close();
        db.close();
        TextView textView = (TextView) findViewById(R.id.tvMessage);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(str, Html.FROM_HTML_MODE_COMPACT));
        } else {
            textView.setText(Html.fromHtml(str));
        }

    }
    public String FormInp(String name, String param) {
        return  Integer.parseInt(param) > 0 ? "<p>" + name + ": <b>" + param + "</b></p>" : "";
    }
    public void  GoBack(View view){
        finish();
    }
}