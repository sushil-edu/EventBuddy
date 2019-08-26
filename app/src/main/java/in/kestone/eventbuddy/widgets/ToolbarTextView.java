package in.kestone.eventbuddy.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

public class ToolbarTextView extends androidx.appcompat.widget.AppCompatTextView {


    public ToolbarTextView(Context context) {
        super( context );
        this.setTypeface( SetTypeface.getBoldFont( getContext() ) );
    }

    public ToolbarTextView(Context context, AttributeSet attrs) {
        super( context, attrs );
        this.setTypeface( SetTypeface.getBoldFont( getContext() ) );
    }

    public ToolbarTextView(Context context, AttributeSet attrs, int defStyle) {
        super( context, attrs, defStyle );
        this.setTypeface( SetTypeface.getBoldFont( getContext() ) );
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw( canvas );
    }
}
