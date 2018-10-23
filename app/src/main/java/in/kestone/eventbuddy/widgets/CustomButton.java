package in.kestone.eventbuddy.widgets;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.Button;

public class CustomButton extends Button {
    public CustomButton(Context context, AttributeSet attrs) {
        super( context, attrs );
        init();
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super( context, attrs, defStyleAttr );
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super( context, attrs, defStyleAttr, defStyleRes );
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            setTypeface( SetTypeface.getFont( getContext() ) );
        }
    }
}
