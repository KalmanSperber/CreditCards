package com.learn2develop.creditcards;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by User on 11/11/2015.
 */
public class ActivityAdopter  extends RecyclerView.Adapter<ActivityAdopter.MyHolder> {

    LayoutInflater layoutInflater;
    List<CreditCard> cc = Collections.emptyList();

    public ActivityAdopter(Context context, List<CreditCard> cc) {

        layoutInflater = LayoutInflater.from(context);
        this.cc = cc;

    }

    @Override
    public ActivityAdopter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.row_activity, parent, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(ActivityAdopter.MyHolder holder, int position) {
        final CreditCard creditCard = cc.get(position);

        holder.name.setText(creditCard.activity);
        holder.date.setText("" + creditCard.balanceDue);



    }

    @Override
    public int getItemCount() {
        return cc.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        TextView name, date;


        public MyHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.textView18);
            date = (TextView) itemView.findViewById(R.id.textView19);
        }
    }
}


