package in.kestone.eventbuddy.view.networking;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.model.DateParser;

public class DatePickerAdapter extends RecyclerView.Adapter<DatePickerAdapter.MyHolder> {
    Context context;
    List<DateParser> dateParserList;
    int selectedPos = -1;
    String type;
    BottomSheetDialog dialog;
    DateSelection dateSelection;
    String selectedDate;

    public DatePickerAdapter(Activity mainActivity, List<DateParser> strDate, String type, BottomSheetDialog dialog, DateSelection dateSelection, String selectedDate) {
        this.context = mainActivity;
        this.dateParserList = strDate;
        this.type = type;
        this.dialog = dialog;
        this.dateSelection = dateSelection;
        this.selectedDate = selectedDate;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (type.equalsIgnoreCase("date")) {
            View view = LayoutInflater.from(context).inflate(R.layout.date_item, null);
            return new MyHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.time_item, null);
            return new MyHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, final int position) {
        DateParser dp = dateParserList.get(position);
        if (type.equals("date")) {
            holder.tvDate.setText(dp.getFullDate());
        } else {
            holder.tvDate.setText(dp.getTime());
            holder.tvDate.setTextSize(12);
        }
        holder.itemView.setOnClickListener(view -> {
            selectedPos = position;
            notifyDataSetChanged();
            dateSelection.onDateSelected(holder.tvDate.getText().toString(), type, position);
            dialog.dismiss();

        });
        if (selectedPos != -1) {
            if (selectedPos == position) {
                holder.layoutDate.setBackgroundResource(R.drawable.outline_fill);
                holder.tvDate.setTextColor(ContextCompat.getColor(context, R.color.white));

            } else {
                holder.layoutDate.setBackgroundResource(R.drawable.outline);
                holder.tvDate.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));

            }
        }
        if (selectedDate != null) {
            if (selectedDate.equalsIgnoreCase(dp.getFullDate()) || selectedDate.equalsIgnoreCase(dp.getTime())) {
                holder.layoutDate.setBackgroundResource(R.drawable.outline_fill);
                holder.tvDate.setTextColor(ContextCompat.getColor(context, R.color.white));


            } else {
                holder.layoutDate.setBackgroundResource(R.drawable.outline);
                holder.tvDate.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));

            }
        } else {
            holder.layoutDate.setBackgroundResource(R.drawable.outline);
            holder.tvDate.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));

        }

        Log.e("Selected date ", selectedDate);
    }


    @Override
    public int getItemCount() {
        return dateParserList.size();
    }

    public interface DateSelection {
        void onDateSelected(String date, String type, int pos);
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tvDate;
        LinearLayout layoutDate;

        public MyHolder(@NonNull View view) {
            super(view);

            tvDate = view.findViewById(R.id.tvDate);
            layoutDate = view.findViewById(R.id.layoutDate);


        }
    }
}
