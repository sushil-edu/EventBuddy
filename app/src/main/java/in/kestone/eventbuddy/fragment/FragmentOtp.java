package in.kestone.eventbuddy.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import java.util.HashSet;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.Altdialog.CustomDialog;
import in.kestone.eventbuddy.Eventlistener.OnVerifiedListener;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.model.app_config.ListEvent;
import in.kestone.eventbuddy.widgets.CustomEditText;
import in.kestone.eventbuddy.widgets.CustomTextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOtp extends Fragment {

    View v;
    @BindView(R.id.tv_otp_title)
    CustomTextView otp_title;

    @BindView(R.id.et_otp1)
    CustomEditText otp1;
    @BindView(R.id.et_otp2)
    CustomEditText otp2;
    @BindView(R.id.et_otp3)
    CustomEditText otp3;
    @BindView(R.id.et_otp4)
    CustomEditText otp4;
    @BindView(R.id.et_otp5)
    CustomEditText otp5;
    @BindView(R.id.et_otp6)
    CustomEditText otp6;

    @BindView(R.id.tv_verify)
    CustomTextView tv_verify;

    @BindView(R.id.layout_verify)
    LinearLayout layout_verify;

    String err_msg = "";

    OnVerifiedListener onVerifiedListener;
    CustomDialog customDialog;
    HashSet hs;

    public FragmentOtp() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate( R.layout.fragment_otp, container, false );

        ButterKnife.bind( this, v );

        initialiseView();
        return v;
    }

    private void initialiseView() {

        onVerifiedListener = (OnVerifiedListener) getActivity();
        otp_title.setText( ListEvent.getAppConf().getEvent().getOTP().getWelcomeText() );
        tv_verify.setText( ListEvent.getAppConf().getEvent().getOTP().getButtonLabel() );
        err_msg = ListEvent.getAppConf().getEvent().getOTP().getErrorMessage();

        otp1.addTextChangedListener( new GenericTextWatcher( otp1 ) );
        otp2.addTextChangedListener( new GenericTextWatcher( otp2 ) );
        otp3.addTextChangedListener( new GenericTextWatcher( otp3 ) );
        otp4.addTextChangedListener( new GenericTextWatcher( otp4 ) );
        otp5.addTextChangedListener( new GenericTextWatcher( otp5 ) );
        otp6.addTextChangedListener( new GenericTextWatcher( otp6 ) );


        customDialog = new CustomDialog();
        hs = new HashSet();


        layout_verify.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!otp1.getText().toString().isEmpty() && !otp2.getText().toString().isEmpty() &&
                        !otp3.getText().toString().isEmpty() && !otp4.getText().toString().isEmpty() &&
                        !otp5.getText().toString().isEmpty() && !otp6.getText().toString().isEmpty()) {
                    if (Integer.parseInt( otp1.getText().toString() ) == 9 && Integer.parseInt( otp2.getText().toString() )
                            == 9 && Integer.parseInt( otp3.getText().toString() ) == 9 && Integer.parseInt( otp4.getText().toString() ) == 9 &&
                            Integer.parseInt( otp5.getText().toString() ) == 9 && Integer.parseInt( otp6.getText().toString() ) == 9) {
                        onVerifiedListener.onVerified( "verified" );
                    } else {
                        customDialog.showInvalidPopUp( getActivity(), "Invalid Input", err_msg );
                        otp1.getText().clear();
                        otp2.getText().clear();
                        otp3.getText().clear();
                        otp4.getText().clear();
                        otp5.getText().clear();
                        otp6.getText().clear();
                        otp1.requestFocus();

                    }
                } else {
                    customDialog.showInvalidPopUp( getActivity(), "Invalid Input", err_msg );
                    otp1.getText().clear();
                    otp2.getText().clear();
                    otp3.getText().clear();
                    otp4.getText().clear();
                    otp5.getText().clear();
                    otp6.getText().clear();
                    otp1.requestFocus();
                }
            }
        } );

    }


    private class GenericTextWatcher implements TextWatcher {
        View view;

        public GenericTextWatcher(CustomEditText otp) {
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
            String text = editable.toString();
            switch (view.getId()) {
                case R.id.et_otp1:
                    if (text.length() == 1)
                        otp2.requestFocus();
                    else if (text.length() == 0)
                        otp1.requestFocus();
                    break;
                case R.id.et_otp2:
                    if (text.length() == 1)
                        otp3.requestFocus();
                    else if (text.length() == 0)
                        otp1.requestFocus();
                    break;
                case R.id.et_otp3:
                    if (text.length() == 1)
                        otp4.requestFocus();
                    else if (text.length() == 0)
                        otp2.requestFocus();
                    break;
                case R.id.et_otp4:
                    if (text.length() == 1)
                        otp5.requestFocus();
                    else if (text.length() == 0)
                        otp3.requestFocus();
                    break;
                case R.id.et_otp5:
                    if (text.length() == 1)
                        otp6.requestFocus();
                    else if (text.length() == 0)
                        otp4.requestFocus();
                    break;
                case R.id.et_otp6:
                    if (text.length() == 0)
                        otp5.requestFocus();
                    else otp6.clearFocus();

                    break;
            }
            if (!otp1.getText().toString().isEmpty() && !otp2.getText().toString().isEmpty() && !otp3.getText().toString().isEmpty()
                    && !otp4.getText().toString().isEmpty() && !otp5.getText().toString().isEmpty() && !otp6.getText().toString().isEmpty()) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService( Activity.INPUT_METHOD_SERVICE );
                imm.hideSoftInputFromWindow( v.getWindowToken(), 0 );
            } else {
//                otp1.requestFocus();
            }
        }
    }
}
