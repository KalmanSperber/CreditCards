package com.learn2develop.creditcards;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class AllCards extends AppCompatActivity {

    List<CreditCard> creditCardList = new ArrayList<>();
    RecyclerView recyclerView;
    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_cards);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        adapter = new MyAdapter(this, creditCardList);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        // Select All Query
        String selectQuery = "SELECT  * FROM " + SQLHelper.TABLE_CREDIT_CARDS;

        SQLiteDatabase db = new SQLHelper(this).getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CreditCard contact = new CreditCard();
                //contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.id = cursor.getString(0);
                contact.cardName = cursor.getString(1);
                contact.cardNumber = (Integer.parseInt(cursor.getString(2)));
                // Adding contact to list
                creditCardList.add(contact);
            } while (cursor.moveToNext());
        }
        db.close();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.START | ItemTouchHelper.END,
                ItemTouchHelper.START | ItemTouchHelper.END) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.removeItem(AllCards.this, creditCardList.get(viewHolder.getAdapterPosition()));

            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

}
