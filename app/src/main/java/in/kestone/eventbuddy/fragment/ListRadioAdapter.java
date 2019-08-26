package in.kestone.eventbuddy.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.feedbackDB.FeedbackDbClient;
import in.kestone.eventbuddy.model.feedback_model.Option;

public class ListRadioAdapter extends RecyclerView.Adapter<ListRadioAdapter.HolderOption> {
    private Context context;
    private List<Option> options;
    private int selectedOption = -1;
    private String type, qId;
    private StringBuilder sb = new StringBuilder();


    ListRadioAdapter(Context context, List<Option> options, String type, String qId) {
        this.context = context;
        this.options = options;
        this.type = type;
        this.qId = qId;
    }

    @NonNull
    @Override
    public HolderOption onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (type.equalsIgnoreCase("radio")) {
            view = LayoutInflater.from(context).inflate(R.layout.radio_item_cell, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.checkbox_item_cell, parent, false);
        }

        return new HolderOption(view, type);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderOption holder, int position) {
        Option option = options.get(position);

        if (type.equalsIgnoreCase("radio")) {
            holder.tvOption.setText(option.getOption());
            holder.itemView.setOnClickListener(v -> {
                selectedOption = position;
                notifyDataSetChanged();

                save(option.getOption(), qId);

            });
            if (selectedOption != -1) {
                if (selectedOption == position) {
                    holder.imageRadio.setImageResource(R.drawable.ic_radio_checked);
                    //adding to database

                } else {
                    holder.imageRadio.setImageResource(R.drawable.ic_radio_unchecked);
                }
            }

        } else {
            holder.checkBox.setText(option.getOption());
            holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    sb.append(option.getOption()).append(",");
                    save(String.valueOf(sb), qId);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    private void save(String ans, String qID) {
        class SaveTask extends AsyncTask<Void, Void, Void> {
            private String ans, qID;

            public SaveTask(String and, String qID) {
                this.ans = and;
                this.qID = qID;
            }

            @Override
            protected Void doInBackground(Void... voids) {
                FeedbackDbClient.getInstance(context).getDatabase()
                        .feedbackDao()
                        .update(ans, qID);
                return null;
            }

            @Override
            protected void onPostExecute(Void v) {
                super.onPostExecute(v);
            }
        }

        SaveTask st = new SaveTask(ans, qID);
        st.execute();
    }

    public class HolderOption extends RecyclerView.ViewHolder {
        ImageView imageRadio;
        TextView tvOption;
        CheckBox checkBox;


        public HolderOption(View view, String type) {
            super(view);
            if (type.equalsIgnoreCase("radio")) {
                imageRadio = view.findViewById(R.id.radio);
                tvOption = view.findViewById(R.id.tvOption);

            } else {
                checkBox = view.findViewById(R.id.checkbox);
            }
        }
    }

}
