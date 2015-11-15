package com.learn2develop.creditcards;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by User on 11/8/2015.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    LayoutInflater layoutInflater;
    List<CreditCard> cc = Collections.emptyList();

    public MyAdapter(Context context, List<CreditCard> cc) {

        layoutInflater = LayoutInflater.from(context);
        this.cc = cc;

    }

    @Override
    public MyAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.row, parent, false);

        return new MyHolder(view);
    }
    public void removeItem(Context context, CreditCard ccPosition) {
        SQLiteDatabase sqLiteDatabase = new SQLHelper(context).getWritableDatabase();

        sqLiteDatabase.delete(SQLHelper.TABLE_CREDIT_CARDS, SQLHelper.COLUMN_ID + " =? ",new String[]{ccPosition.id});
        sqLiteDatabase.close();

        cc.remove(ccPosition);
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(final MyAdapter.MyHolder holder, final int position) {
        final CreditCard creditCard = cc.get(position);
        if ((position + 1) % 2 == 0){
            holder.itemView.setBackgroundColor(Color.GRAY);
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
        holder.name.setText(creditCard.cardName);
        holder.number.setText("" + creditCard.cardNumber);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CardDetails.class);
                intent.putExtra("key", creditCard.id);
                v.getContext().startActivity(intent);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                holder.itemView.setBackgroundColor(Color.BLUE);

                cc.remove(position);
                notifyDataSetChanged();

                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return cc.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        TextView name, number;


        public MyHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.textView13);
            number = (TextView) itemView.findViewById(R.id.textView14);
        }
    }
}

