package in.kestone.eventbuddy.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

public class ToolbarNormalTextView extends androidx.appcompat.widget.AppCompatTextView {


    public ToolbarNormalTextView(Context context) {
        super( context );
        this.setTypeface( SetTypeface.getFont( getContext() ) );
    }

    public ToolbarNormalTextView(Context context, AttributeSet attrs) {
        super( context, attrs );
        this.setTypeface( SetTypeface.getFont( getContext() ) );
    }

    public ToolbarNormalTextView(Context context, AttributeSet attrs, int defStyle) {
        super( context, attrs, defStyle );
        this.setTypeface( SetTypeface.getFont( getContext() ) );
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw( canvas );
    }
}
