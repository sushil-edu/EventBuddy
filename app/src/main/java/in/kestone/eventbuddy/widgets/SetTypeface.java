package in.kestone.eventbuddy.widgets;

import android.content.Context;
import android.graphics.Typeface;

public class SetTypeface {
    public static Typeface getFont(Context c)
    {
        return Typeface.createFromAsset(c.getAssets(),"font/arial.ttf");
    }
    public static Typeface getBoldFont(Context c)
    {
        return Typeface.createFromAsset(c.getAssets(), "font/arial_bold.ttf" );
    }

}
