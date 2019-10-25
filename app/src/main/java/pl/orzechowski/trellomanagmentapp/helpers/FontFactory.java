package pl.orzechowski.trellomanagmentapp.helpers;

import android.content.Context;
import android.graphics.Typeface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class FontFactory {
    private static FontFactory instance = new FontFactory();
    private HashMap<String, Typeface> fontMap = new HashMap<String, Typeface>();

    private ArrayList<String> names = new ArrayList<>();

    private FontFactory() {

    }

    public static FontFactory getInstance() {
        return instance;
    }

    public Typeface getFont(String font, Context context) {

        if(font == null) return null;

        Typeface typeface = fontMap.get(font);

        if (typeface == null) {

            try {
                typeface = Typeface.createFromAsset(context.getResources().getAssets(), "fonts/" + font);
            } catch (Exception ignored) { }

            if(typeface != null) {
                fontMap.put(font, typeface);
            }

        }
        return typeface;
    }
}