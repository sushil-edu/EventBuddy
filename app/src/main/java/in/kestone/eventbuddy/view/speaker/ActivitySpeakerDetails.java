package in.kestone.eventbuddy.view.speaker;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.Altdialog.CustomDialog;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.common.CalculateTimeSlot;
import in.kestone.eventbuddy.common.LocalStorage;
import in.kestone.eventbuddy.data.SharedPrefsHelper;
import in.kestone.eventbuddy.model.DateParser;
import in.kestone.eventbuddy.model.app_config_model.ListEvent;
import in.kestone.eventbuddy.model.app_config_model.Menu;
import in.kestone.eventbuddy.model.speaker_model.SpeakerDetail;
import in.kestone.eventbuddy.view.main.MainActivity;

public class ActivitySpeakerDetails extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.nameTv)
    TextView nameTv;
    @BindView(R.id.organizationTv)
    TextView organizationTv;
    @BindView(R.id.designationTv)
    TextView designationTv;
    //    @BindView(R.id.mTitleTv)
//    CustomTextView mTitleTv;
    @BindView(R.id.detailsTv)
    TextView detailsTv;
    @BindView(R.id.mScheduleBtn)
    TextView mScheduleBtn;
    @BindView(R.id.tvCancel)
    TextView tvCancel;
    @BindView(R.id.tvReschedule)
    TextView tvReschedule;
    @BindView(R.id.profileIv)
    CircularImageView profileIv;
    @BindView(R.id.layoutSpeakerSlot)
    LinearLayout layoutSpeakerSlot;
    @BindView(R.id.rvTimeSlot)
    RecyclerView rvTimeSlot;
    @BindView(R.id.viewIndicator)
    View indicator;
    @BindView(R.id.tvTimeSlotTitle)
    TextView timeSlotTitle;

    String tag, type;

    TextView mTitleTv;
    TextView subTitleTv;
    SpeakerDetail speakerDetail;
    BottomSheetBehavior bottomSheetBehavior;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        setContentView(R.layout.bottomsheet_speater_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTitleTv = toolbar.findViewById(R.id.mTitleTv);
        subTitleTv = toolbar.findViewById(R.id.subTitleTv);
        mTitleTv.setText("Profile Details");
        subTitleTv.setVisibility(View.GONE);

        Bundle bundle = getIntent().getExtras();
        speakerDetail = (SpeakerDetail) bundle.getSerializable("data");

        ButterKnife.bind(this);

        type = speakerDetail.getUserType();
        organizationTv.setText(speakerDetail.getOrganization());
        designationTv.setText(speakerDetail.getDesignation());
        nameTv.setText(speakerDetail.getFirstName().concat(" ").concat(speakerDetail.getLastName()));
        detailsTv.setText(speakerDetail.getProfileDescription());
        tag = speakerDetail.getUserType();

        if (speakerDetail.getImage().contains(LocalStorage.getImagePath(ActivitySpeakerDetails.this))) {
            Picasso.get()
                    .load(speakerDetail.getImage())
                    .resize(100, 100)
                    .placeholder(R.drawable.default_user_grey)
                    .into(profileIv);
        } else {
            Picasso.get()
                    .load(LocalStorage.getImagePath(ActivitySpeakerDetails.this)
                            .concat(speakerDetail.getImage()))
                    .resize(100, 100)
                    .placeholder(R.drawable.default_user_grey)
                    .into(profileIv);

        }


        mScheduleBtn.setOnClickListener(this);
        tvReschedule.setOnClickListener(this);
        tvCancel.setOnClickListener(this);

        timeSlotTitle.setTypeface(null, Typeface.BOLD);
        List<DateParser> dateParsers = new ArrayList<>();
        for (String sTime : CalculateTimeSlot.timeSlot(9, 20)) {
            DateParser dp = new DateParser();
            dp.setTime(sTime);
            dateParsers.add(dp);
        }
        rvTimeSlot.setLayoutManager(new GridLayoutManager(this, 3));
        bottomSheetBehavior = BottomSheetBehavior.from(layoutSpeakerSlot);
        rvTimeSlot.setAdapter(new TimeSlotAdapter(this, dateParsers, bottomSheetBehavior));
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // to collapse to show
                if (BottomSheetBehavior.STATE_DRAGGING == newState) {
                    indicator.animate().scaleX(0).scaleY(0).setDuration(300).start();
                } else if (BottomSheetBehavior.STATE_COLLAPSED == newState) {
                    indicator.animate().scaleX(1).scaleY(1).setDuration(300).start();
                }

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (speakerDetail.getUserID() != new SharedPrefsHelper(getApplicationContext()).getUserId()) {
            Intent intent = new Intent(this, MainActivity.class);

            switch (view.getId()) {
                case R.id.mScheduleBtn:
                    if (isNetworkingEnable()) {
                        Bundle bundle = new Bundle();
                        intent.putExtra("Name", nameTv.getText().toString());
                        intent.putExtra("Type", type);
                        bundle.putSerializable("Data", speakerDetail);
                        intent.putExtra("Tag", CONSTANTS.SCHEDULE);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        CustomDialog.showInvalidPopUp(ActivitySpeakerDetails.this, "", "Networking temporary disable ");
                    }

                    break;
                case R.id.tvReschedule:
                    if (isNetworkingEnable()) {
                        Bundle bundles = new Bundle();
                        intent.putExtra("Name", nameTv.getText().toString());
                        intent.putExtra("Type", type);
                        intent.putExtra("Tag", CONSTANTS.RESCHEDULE);
                        bundles.putSerializable("Data", speakerDetail);
                        intent.putExtras(bundles);
                        startActivity(intent);
                    } else {
                        CustomDialog.showInvalidPopUp(ActivitySpeakerDetails.this, "", "Networking temporary disable ");
                    }
                    break;
                case R.id.tvCancel:
                    onBackPressed();
                    break;
            }
        } else {

            if (view.getId() == R.id.tvCancel) {
                onBackPressed();
            } else {
                CustomDialog.showInvalidPopUp(ActivitySpeakerDetails.this, "", "You should not send meeting request yourself");
            }
        }

//        finish();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {

                Rect outRect = new Rect();
                layoutSpeakerSlot.getGlobalVisibleRect(outRect);

                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY()))
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        }
        return super.dispatchTouchEvent(event);
    }

    private boolean isNetworkingEnable() {
        ArrayList<Menu> menuList = new ArrayList<>();
        menuList.addAll(ListEvent.getAppConf().getEvent().getMenu());
        for (int i = 0; i < menuList.size(); i++) {
            if (CONSTANTS.NETWORKING.equalsIgnoreCase(menuList.get(i).getMenutitle()))
                return true;
        }
        return false;
    }


}
