package in.kestone.eventbuddy.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.LocalStorage;
import in.kestone.eventbuddy.data.SharedPrefsHelper;
import in.kestone.eventbuddy.feedbackDB.FeedbackDbClient;
import in.kestone.eventbuddy.feedbackDB.SaveFeedback;
import in.kestone.eventbuddy.model.feedback_model.Datum;

class FeedbackAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<Datum> feedbackMdls;
    private String type;
    private View view;


    public FeedbackAdapter(FragmentActivity activity, ArrayList<Datum> feedback, String type) {
        this.context = activity;
        this.feedbackMdls = feedback;
        this.type = type;
    }

    private static void hideKeyboard(Activity activity) {
        InputMethodManager imm =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 99) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_button, parent, false);
            return new HolderButton(view);
        } else {
            if (viewType == 0) {
                view = LayoutInflater.from(context).inflate(R.layout.layout_radio, parent, false);
                return new HolderRadio(view);
            } else if (viewType == 1) {
                view =
                        LayoutInflater.from(context).inflate(R.layout.layout_checkbox, parent, false);
                return new HolderCheckBox(view);
            } else {
                view = LayoutInflater.from(context).inflate(R.layout.layout_textbox, parent, false);
                return new HolderTextBox(view);
            }
        }

    }

//    @Override
//    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
//        Datum data = feedbackMdls.get(position);
//        if (data.getQType().equalsIgnoreCase("radio")) {
//            holder.tvRadioQuestion.setText(String.format("Q%s. %s", data.getQId(), data.getQuestion()));
//            holder.listRadioOptions.setAdapter(new ListRadioAdapter(context, data.getOptions(), "radio"));
//            holder.tvCheckBoxQuestion.setVisibility(View.GONE);
//            holder.listCheckboxOption.setVisibility(View.GONE);
//            holder.tvTextBoxQuestion.setVisibility(View.GONE);
//            holder.etComment.setVisibility(View.GONE);
//        } else if (data.getQType().equalsIgnoreCase("checkbox")) {
//            holder.tvCheckBoxQuestion.setText(String.format("Q%s. %s", data.getQId(), data.getQuestion()));
//            holder.listCheckboxOption.setAdapter(new ListRadioAdapter(context, data.getOptions(), "checkbox"));
//            holder.tvRadioQuestion.setVisibility(View.GONE);
//            holder.listRadioOptions.setVisibility(View.GONE);
//            holder.tvTextBoxQuestion.setVisibility(View.GONE);
//            holder.etComment.setVisibility(View.GONE);
//        } else if (data.getQType().equalsIgnoreCase("textbox")) {
//            holder.tvTextBoxQuestion.setText(String.format("Q%s. %s", data.getQId(), data.getQuestion()));
//            holder.etComment.setHint(data.getOptions().get(0).getOption());
//            holder.tvRadioQuestion.setVisibility(View.GONE);
//            holder.listRadioOptions.setVisibility(View.GONE);
//            holder.tvCheckBoxQuestion.setVisibility(View.GONE);
//            holder.listCheckboxOption.setVisibility(View.GONE);
//        }
//
//    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) != 99) {
            Datum data = feedbackMdls.get(position);
            SaveFeedback sf = new SaveFeedback();
            sf.setQId(data.getQId());
            save(sf);
            if (getItemViewType(position) == 0) {
                ((HolderRadio) holder).setRadioItems(data);
            } else if (getItemViewType(position) == 1) {
                ((HolderCheckBox) holder).setCheckBoxItem(data);
            } else {
                ((HolderTextBox) holder).setTextBoxItem(data);
                ((HolderTextBox) holder).etComment.setOnEditorActionListener((v, actionId, event) -> {
                    if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                        if (((HolderTextBox) holder).etComment.getText().length() > 0) {
                            update((((HolderTextBox) holder).etComment.getText().toString()), data.getQId());
                        }
                        hideKeyboard((Activity) context);
                    }
                    return false;
                });

            }
        } else {
            ((HolderButton) holder).setButtonVisibility(true);
        }
    }

    @Override
    public int getItemCount() {
        return feedbackMdls.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == feedbackMdls.size()) {
            return 99;
        } else {
            if (feedbackMdls.get(position).getQType().equalsIgnoreCase("radio")) {
                return 0;
            } else if (feedbackMdls.get(position).getQType().equalsIgnoreCase("checkbox")) {
                return 1;
            } else {
                return 2;
            }
        }

    }

    private void getAll() {
        ArrayList<SaveFeedback> list = new ArrayList<>();
        class SaveTask extends AsyncTask<Void, ArrayList<SaveFeedback>, ArrayList<SaveFeedback>> {

            @Override
            protected ArrayList<SaveFeedback> doInBackground(Void... voids) {
                return (ArrayList<SaveFeedback>) FeedbackDbClient.getInstance(context).getDatabase()
                        .feedbackDao()
                        .getSavedFeedback();
            }

            @Override
            protected void onPostExecute(ArrayList<SaveFeedback> saveFeedbacks) {
                super.onPostExecute(saveFeedbacks);

                list.addAll(saveFeedbacks);
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("EventID", LocalStorage.getEventID(context));
                    jsonObject.put("UserID", new SharedPrefsHelper(context).getUserId());
                    JSONArray ary = new JSONArray();
                    for (SaveFeedback f : list) {
                        JSONObject obj = new JSONObject();
                        obj.put("Que", f.getQId());
                        obj.put("Ans", f.getAnswer());
                        ary.put(obj);
                    }
                    jsonObject.put("feedback", ary);

                    Log.e("Save data", jsonObject.toString());


                } catch (JSONException e) {

                }
            }
        }

        SaveTask st = new SaveTask();
        st.execute();
    }

    private void save(SaveFeedback sf) {
        ArrayList<SaveFeedback> list = new ArrayList<>();
        class SaveTask extends AsyncTask<Void, Void, Void> {
            SaveFeedback sf;

            public SaveTask(SaveFeedback sf) {
                this.sf = sf;
            }

            @Override
            protected Void doInBackground(Void... voids) {
                FeedbackDbClient.getInstance(context).getDatabase()
                        .feedbackDao()
                        .insert(sf);
                return null;
            }

            @Override
            protected void onPostExecute(Void v) {
                super.onPostExecute(v);
            }
        }

        SaveTask st = new SaveTask(sf);
        st.execute();
    }

    private void update(String ans, String qID) {
        ArrayList<SaveFeedback> list = new ArrayList<>();
        class SaveTask extends AsyncTask<Void, Void, Void> {
            String ans, qID;

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

    public class HolderRadio extends RecyclerView.ViewHolder {

        LinearLayout layoutRadio;
        TextView tvRadioQuestion;
        RecyclerView listRadioOptions;

        public HolderRadio(@NonNull View itemView) {
            super(itemView);
            layoutRadio = itemView.findViewById(R.id.layoutRadio);
            tvRadioQuestion = itemView.findViewById(R.id.tvRadioQue);
            listRadioOptions = itemView.findViewById(R.id.listRadioOptions);
            listRadioOptions.setLayoutManager(new LinearLayoutManager(context));
            listRadioOptions.setHasFixedSize(true);

        }

        private void setRadioItems(Datum data) {
            tvRadioQuestion.setText(String.format("Q%s. %s", data.getQId(), data.getQuestion()));
            listRadioOptions.setAdapter(new ListRadioAdapter(context, data.getOptions(), "radio", data.getQId()));
        }
    }

    public class HolderCheckBox extends RecyclerView.ViewHolder {

        LinearLayout layoutCheckBox;
        TextView tvCheckBoxQuestion;
        RecyclerView listCheckboxOption;

        public HolderCheckBox(@NonNull View itemView) {
            super(itemView);

            layoutCheckBox = itemView.findViewById(R.id.layoutCheckBox);
            tvCheckBoxQuestion = itemView.findViewById(R.id.tvCheckBoxQue);
            tvCheckBoxQuestion.setTypeface(null, Typeface.BOLD);
            listCheckboxOption = itemView.findViewById(R.id.listCheckbox);

            listCheckboxOption.setLayoutManager(new LinearLayoutManager(context));
            listCheckboxOption.setHasFixedSize(true);


        }

        public void setCheckBoxItem(Datum data) {
            tvCheckBoxQuestion.setText(String.format("Q%s. %s", data.getQId(), data.getQuestion()));
            listCheckboxOption.setAdapter(new ListRadioAdapter(context, data.getOptions(), "checkbox", data.getQId()));
        }
    }

    public class HolderTextBox extends RecyclerView.ViewHolder {

        LinearLayout layoutTextBox;
        TextView tvTextBoxQuestion;
        EditText etComment;

        public HolderTextBox(@NonNull View itemView) {
            super(itemView);

            layoutTextBox = itemView.findViewById(R.id.layoutTextBox);
            tvTextBoxQuestion = itemView.findViewById(R.id.tvTextBoxQue);
            etComment = itemView.findViewById(R.id.etComment);

        }

        public void setTextBoxItem(Datum data) {
            tvTextBoxQuestion.setText(String.format("Q%s. %s", data.getQId(), data.getQuestion()));
            etComment.setHint(data.getOptions().get(0).getOption());
        }
    }

    public class HolderButton extends RecyclerView.ViewHolder {

        Button btnFeedbackSubmit;

        public HolderButton(@NonNull View itemView) {
            super(itemView);

            btnFeedbackSubmit = itemView.findViewById(R.id.btnFeedbackSubmit);
            btnFeedbackSubmit.setVisibility(View.GONE);
            btnFeedbackSubmit.setOnClickListener(v -> getAll());

        }

        public void setButtonVisibility(boolean b) {
            if (b == true) {
                btnFeedbackSubmit.setVisibility(View.VISIBLE);
            } else {
                btnFeedbackSubmit.setVisibility(View.GONE);
            }
        }
    }

}
