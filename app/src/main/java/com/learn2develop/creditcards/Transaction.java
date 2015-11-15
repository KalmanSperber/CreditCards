package com.learn2develop.creditcards;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class Transaction extends AppCompatActivity {

    EditText amountText, detailsText;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        amountText = (EditText) findViewById(R.id.editText5);
        detailsText = (EditText) findViewById(R.id.editText6);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        bundle = getIntent().getExtras();

    }
    public void enter(View view){
        if (!amountText.getText().toString().isEmpty() && !detailsText.getText().toString().isEmpty()){
            SQLiteDatabase db = new SQLHelper(this).getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(SQLHelper.COLUMN_BALANCE, bundle.getDouble("keyBalance") + Double.parseDouble(amountText.getText().toString()));
            values.put(SQLHelper.COLUMN_AVAILABLE, (bundle.getDouble("keyAvailable") - Double.parseDouble(amountText.getText().toString())));
            db.update(SQLHelper.TABLE_CREDIT_CARDS, values, SQLHelper.COLUMN_ID + " = ?",
                    new String[]{bundle.getString("key")});

            ContentValues values2 = new ContentValues();
            values2.put(SQLHelper.COLUMN_ACTIVITY_ID, bundle.getString("key"));
            values2.put(SQLHelper.COLUMN_ACTIVITY, "A transaction of $" + amountText.getText().toString()
                    + " was made for "  + detailsText.getText().toString());
            values2.put(SQLHelper.COLUMN_ACTIVITY_DATE, "" + System.currentTimeMillis());
            // Inserting Row
            db.insert(SQLHelper.TABLE_ACTIVITY, null, values2);
            db.close();

            finish();
        }
    }

}
