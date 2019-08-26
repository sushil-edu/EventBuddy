package in.kestone.eventbuddy.view.speaker;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.List;

import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.model.DateParser;

public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.MyHolder> {
    Context context;
    List<DateParser> dateParserList;
    BottomSheetBehavior bottomSheetBehavior;


    public TimeSlotAdapter(Activity mainActivity, List<DateParser> strDate, BottomSheetBehavior bottomSheetBehavior) {
        this.context = mainActivity;
        this.dateParserList = strDate;
        this.bottomSheetBehavior = bottomSheetBehavior;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.time_item, null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, final int position) {
        DateParser dp = dateParserList.get(position);

        holder.tvDate.setText(dp.getTime());
        holder.tvDate.setOnClickListener(v -> {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        });

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
            tvDate.setTextSize(12);
            layoutDate = view.findViewById(R.id.layoutDate);


        }
    }
}
