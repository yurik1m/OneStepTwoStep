package com.example.onesteptwostep;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Recycler_Adapter extends RecyclerView.Adapter<Recycler_Adapter.viewHolder> {
    Context context;
    String[] data;
    boolean[] memo;
    int[] num;

    public Recycler_Adapter(Context context, String[] data, boolean[] memo, int[] num){
        super();
        this.context=context;
        this.data=data;
        this.memo = memo;
        this.num = num;
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView day;
        LinearLayout thumblay;
        ImageView thumb1;
        ImageView thumb2;
        ImageView thumb3;
        View memoyes;

        public viewHolder(View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.item_day);
            thumblay = itemView.findViewById(R.id.thumblay);
            thumb1 = itemView.findViewById(R.id.thumb1);
            thumb2 = itemView.findViewById(R.id.thumb2);
            thumb3 = itemView.findViewById(R.id.thumb3);
            memoyes = itemView.findViewById(R.id.memoyes);
        }
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtype){
        View view = LayoutInflater.from(context).inflate(R.layout.itemcalendar, parent, false);

        return new viewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.day.setText(data[position]);

    }

    public void onBind(@NonNull viewHolder holder, boolean checkplus, int checknum){
        if (checkplus == true){
            holder.thumblay.setVisibility(View.VISIBLE);
            if(checknum >=5){
                holder.thumb1.setImageResource(R.drawable.thumb_full);
                holder.thumb2.setImageResource(R.drawable.thumb_full);
                holder.thumb3.setImageResource(R.drawable.thumb_full);
            }
            else if(checknum >=2){
                holder.thumb1.setImageResource(R.drawable.thumb_full);
                holder.thumb2.setImageResource(R.drawable.thumb_full);
            }
            else holder.thumb1.setImageResource(R.drawable.thumb_full);
        }
    }

    public void onBind(@NonNull viewHolder holder, boolean memoplus){
        if(memoplus == true) holder.memoyes.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }




}
