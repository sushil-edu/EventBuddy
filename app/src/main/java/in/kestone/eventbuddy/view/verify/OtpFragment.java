package in.kestone.eventbuddy.view.verify;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.Altdialog.CustomDialog;
import in.kestone.eventbuddy.Eventlistener.OnVerifiedListener;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.LocalStorage;
import in.kestone.eventbuddy.model.app_config_model.ListEvent;
import in.kestone.eventbuddy.widgets.CustomButton;
import in.kestone.eventbuddy.widgets.CustomEditText;
import in.kestone.eventbuddy.widgets.CustomTextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class OtpFragment extends Fragment {

    View v;
    @BindView(R.id.tv_otp_title)
    CustomTextView otp_title;

    @BindView(R.id.et_otp1)
    CustomEditText otp1;

    @BindView(R.id.tv_verify)
    CustomButton tv_verify;

    @BindView( R.id.image_background )
    ImageView imageBackGround;

    String err_msg = "", err_header = "";

    OnVerifiedListener onVerifiedListener;
    CustomDialog customDialog;


    public OtpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate( R.layout.fragment_otp, container, false );

        ButterKnife.bind( this, v );

        initialiseView();

        if(LocalStorage.getEventID( getActivity() )!=0){
            Picasso.with( getActivity() ).load(  "http://eventsbuddy.in/beta/".concat( LocalStorage.getBackground( getActivity() ) ))
                    .into( imageBackGround );
        }
        return v;
    }

    private void initialiseView() {

        onVerifiedListener = (OnVerifiedListener) getActivity();
        otp_title.setText( ListEvent.getAppConf().getEvent().getOTP().getWelcomeText() );
        tv_verify.setText( ListEvent.getAppConf().getEvent().getOTP().getButtonLabel() );
        err_msg = ListEvent.getAppConf().getEvent().getOTP().getErrorMessage();
        err_header = ListEvent.getAppConf().getEvent().getOTP().getmErrorHeader();

        otp1.addTextChangedListener( new GenericTextWatcher( otp1 ) );
        customDialog = new CustomDialog();


        tv_verify.setOnClickListener(view -> {
            if (!otp1.getText().toString().isEmpty() && otp1.getText().length() == 6) {
                onVerifiedListener.onVerified( "verified", otp1.getText().toString() );
            } else {
                customDialog.showInvalidPopUp( getActivity(), err_header, err_msg );
                otp1.getText().clear();
                otp1.requestFocus();
            }
        });

    }


    private class GenericTextWatcher implements TextWatcher {
        View view;

        GenericTextWatcher(CustomEditText otp) {
            this.view = otp;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.length() == 6) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService( Activity.INPUT_METHOD_SERVICE );
                imm.hideSoftInputFromWindow( view.getWindowToken(), 0 );
            }
        }
    }
}
