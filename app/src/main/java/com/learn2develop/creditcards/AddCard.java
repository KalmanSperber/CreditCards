package com.learn2develop.creditcards;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class AddCard extends AppCompatActivity {
    EditText editText, editText2, editText3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    public void submit(View v){
        if (!editText.getText().toString().isEmpty() && !editText2.getText().toString().isEmpty()
                && !editText3.getText().toString().isEmpty() ){
            SQLiteDatabase db = new SQLHelper(this).getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(SQLHelper.COLUMN_NAME, editText.getText().toString());
            values.put(SQLHelper.COLUMN_NUMBER, editText2.getText().toString());
            values.put(SQLHelper.COLUMN_CREDIT, editText3.getText().toString());
            values.put(SQLHelper.COLUMN_AVAILABLE, editText3.getText().toString());
            values.put(SQLHelper.COLUMN_BALANCE, "0");

            // Inserting Row
            db.insert(SQLHelper.TABLE_CREDIT_CARDS, null, values);

            //db.close(); // Closing database connection
            String selectQuery= "SELECT * FROM " + SQLHelper.TABLE_CREDIT_CARDS+" ORDER BY " + SQLHelper.COLUMN_ID + " DESC LIMIT 1";

            Cursor cursor = db.rawQuery(selectQuery, null);
            String str = "";
            if(cursor.moveToFirst())
                str  =  cursor.getString(0);
            cursor.close();
                Intent intent = new Intent(this, CardDetails.class);
                intent.putExtra("key", str);
                startActivity(intent);
            finish();

        }
    }

}
