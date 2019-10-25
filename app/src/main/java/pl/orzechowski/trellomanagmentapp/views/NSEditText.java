package pl.orzechowski.trellomanagmentapp.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;
import pl.orzechowski.trellomanagmentapp.R;
import pl.orzechowski.trellomanagmentapp.helpers.FontFactory;
import pl.orzechowski.trellomanagmentapp.helpers.MHelper;


public class NSEditText extends AppCompatEditText {

    private Drawable errorDrawable = null;


    public NSEditText(Context context) {
        super(context);
    }

    public NSEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        initFont(context, attrs, 0);
    }

    public NSEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initFont(context, attrs, defStyleAttr);
    }

    private Drawable getErrorDrawable() {
        if (errorDrawable == null) {
            errorDrawable = getResources().getDrawable(R.drawable.ic_error);
            errorDrawable.setBounds(0, 0, MHelper.convertDpToPx(getContext(), 14), MHelper.convertDpToPx(getContext(), 14));
        }

        return errorDrawable;
    }

    private void initFont(Context context, AttributeSet attrs, int defStyleAttr) {

        if (isInEditMode()) {
            return;
        }

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NSTextView, defStyleAttr, 0);

        if (a != null) {
            String font = a.getString(R.styleable.NSTextView_ns_font);
            Typeface typeFace = FontFactory.getInstance().getFont(font, context);

            if (typeFace != null) {
                setTypeface(typeFace);
            }

        }

        a.recycle();
    }

    @Override
    public void setError(CharSequence error, Drawable icon) {


        Drawable drawable = null;

        if (error != null) {
            drawable = getErrorDrawable();
        }

        super.setError(error, drawable);
    }
}
