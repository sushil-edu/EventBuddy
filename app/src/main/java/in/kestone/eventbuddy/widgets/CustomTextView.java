package in.kestone.eventbuddy.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

public class CustomTextView extends androidx.appcompat.widget.AppCompatTextView {


    public CustomTextView(Context context) {
        super( context );
        this.setTypeface( SetTypeface.getFont( getContext() ) );
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super( context, attrs );
        this.setTypeface( SetTypeface.getFont( getContext() ) );
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super( context, attrs, defStyle );
        this.setTypeface( SetTypeface.getBoldFont( getContext() ) );
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw( canvas );
    }
}
