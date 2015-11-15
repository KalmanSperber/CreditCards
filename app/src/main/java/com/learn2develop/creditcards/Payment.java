package com.learn2develop.creditcards;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Payment extends AppCompatActivity {

    TextView message;
    EditText editText;
    Button button;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        message = (TextView) findViewById(R.id.textView15);
        editText = (EditText) findViewById(R.id.editText4);
        button = (Button) findViewById(R.id.button9);
        editText.setVisibility(View.GONE);
        button.setVisibility(View.GONE);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.getText().toString().isEmpty()) {
                    buttonClick(Double.parseDouble(editText.getText().toString()));
                }
            }
        });
        bundle = getIntent().getExtras();

        message.setText("Your balance is $" + bundle.getDouble("keyBalance"));

    }
    public void otherAmount(View v){
        editText.setVisibility(View.VISIBLE);
        button.setVisibility(View.VISIBLE);
    }
    public void wholeBalance(View view){
        buttonClick(bundle.getDouble("keyBalance"));
    }

    private void buttonClick(double balance) {
        SQLiteDatabase db = new SQLHelper(this).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SQLHelper.COLUMN_BALANCE, (bundle.getDouble("keyBalance") - balance));
        values.put(SQLHelper.COLUMN_AVAILABLE, (bundle.getDouble("keyAvailable") + balance));
        db.update(SQLHelper.TABLE_CREDIT_CARDS, values, SQLHelper.COLUMN_ID + " = ?",
                new String[]{bundle.getString("key")});
        ContentValues values2 = new ContentValues();
        values2.put(SQLHelper.COLUMN_ACTIVITY_ID, bundle.getString("key"));
        values2.put(SQLHelper.COLUMN_ACTIVITY, "A payment of $" + balance
                + " was made");
        // Inserting Row
        values2.put(SQLHelper.COLUMN_ACTIVITY_DATE, "" + System.currentTimeMillis());

        db.insert(SQLHelper.TABLE_ACTIVITY, null, values2);
        db.close();

        finish();
    }

}
