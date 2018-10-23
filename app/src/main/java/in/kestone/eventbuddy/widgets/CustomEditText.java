package in.kestone.eventbuddy.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

public class CustomEditText extends android.support.v7.widget.AppCompatEditText {


    private Context context;
    private AttributeSet attrs;
    private int defStyle;

    public CustomEditText(Context context) {
        super(context);
        this.context=context;
        init();
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        this.attrs=attrs;
        init();
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context=context;
        this.attrs=attrs;
        this.defStyle=defStyle;
        init();
    }

    private void init() {
        this.setTypeface(SetTypeface.getFont(getContext()));
    }
    @Override
    public void setTypeface(Typeface tf, int style) {
        super.setTypeface(SetTypeface.getFont(getContext()), style);
    }

    @Override
    public void setTypeface(Typeface tf) {
        super.setTypeface(SetTypeface.getFont( getContext() ));
    }
}
