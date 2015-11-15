package com.learn2develop.creditcards;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class CardDetails extends AppCompatActivity {

    TextView cardName, credit, available, balance, due;
    String id;
    double showBalance, availableBalance;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        cardName = (TextView) findViewById(R.id.textView4);
        credit = (TextView) findViewById(R.id.textView6);
        available = (TextView) findViewById(R.id.textView8);
        balance = (TextView) findViewById(R.id.textView10);
        due = (TextView) findViewById(R.id.textView12);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_2);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Bundle bundle = getIntent().getExtras();

        SQLiteDatabase db = new SQLHelper(this).getReadableDatabase();

        Cursor cursor = db.query(SQLHelper.TABLE_CREDIT_CARDS, new String[] {SQLHelper.COLUMN_ID,
                        SQLHelper.COLUMN_NAME, SQLHelper.COLUMN_CREDIT, SQLHelper.COLUMN_AVAILABLE, SQLHelper.COLUMN_BALANCE,
                SQLHelper.COLUMN_DUE}, SQLHelper.COLUMN_ID + "=?",
                new String[] { bundle.getString("key") }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            try {
                id = cursor.getString(0);
                cardName.setText("" + cursor.getString(1));
                credit.setText("" + cursor.getString(2));
                available.setText("" + cursor.getString(3));
                balance.setText("" + cursor.getString(4));
                due.setText("" + cursor.getString(5));
                if (cursor.getString(4) != null) {
                    showBalance = Double.parseDouble(cursor.getString(4));
                }
                if (cursor.getString(4) != null) {
                    availableBalance = Double.parseDouble(cursor.getString(3));
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("l", e.getMessage() + "");
                cardName.setText("" + e.getMessage());
                available.setText("" + cursor.getCount());
            }
            db.close();
        }

    }
    public void transaction(View v){
        Intent intent = new Intent(this, Transaction.class);
        intent.putExtra("key", id);
        intent.putExtra("keyBalance", showBalance);
        intent.putExtra("keyAvailable", availableBalance);

        startActivity(intent);

    }
    public void payment(View v){
        Intent intent = new Intent(this, Payment.class);
        intent.putExtra("key", id);
        intent.putExtra("keyBalance", showBalance);
        intent.putExtra("keyAvailable", availableBalance);
        startActivity(intent);

    }
    public void activities(View v){
        List<CreditCard> creditCardList = new ArrayList<>();
        //String selectQuery = "SELECT  * FROM " + SQLHelper.TABLE_ACTIVITY;

        SQLiteDatabase db = new SQLHelper(this).getWritableDatabase();
        Cursor cursor = db.query(SQLHelper.TABLE_ACTIVITY, new String[]{SQLHelper.COLUMN_ACTIVITY,
        SQLHelper.COLUMN_ACTIVITY_DATE},SQLHelper.COLUMN_ACTIVITY_ID +
                " =? ",new String[]{id},null, null, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CreditCard contact = new CreditCard();
                //contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.activity = cursor.getString(0);
                if (cursor.getString(1) != null) {
                    contact.balanceDue = new Date(Long.parseLong(cursor.getString(1)));

                }
                // contact.cardNumber = (Integer.parseInt(cursor.getString(2)));
                // Adding contact to list
                creditCardList.add(contact);
            } while (cursor.moveToNext());
            db.close();
            recyclerView.setAdapter(new ActivityAdopter(this, creditCardList));
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

    }



}
