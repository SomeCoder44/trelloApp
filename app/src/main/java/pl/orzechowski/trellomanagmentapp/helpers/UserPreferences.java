package pl.orzechowski.trellomanagmentapp.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.ArrayMap;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import pl.orzechowski.trellomanagmentapp.R;
import pl.orzechowski.trellomanagmentapp.models.TrelloList;

public class UserPreferences {
    private static final String
            TAG = "UserPreferences",
            KEY_LISTS = TAG + ".KEY_LISTS";

    private static UserPreferences instance;
    private SharedPreferences preferences;

    private UserPreferences(Context context) {
        preferences = context.getSharedPreferences(context.getString(R.string.app_name).trim() + TAG, Context.MODE_PRIVATE);
    }

    public static UserPreferences getInstance() {
        return instance;
    }

    public static void initInstance(Context context) {
        instance = new UserPreferences(context);
    }

    public void saveLists(List<TrelloList> lists) {
        if (lists == null) return;
        preferences.edit().putString(KEY_LISTS, JSONConverter.getJsonFromObject(lists)).apply();
    }

    public List<TrelloList> getLists() {
        String json = preferences.getString(KEY_LISTS, null);
        if (json != null) {
            List<TrelloList> lists = JSONConverter.getObjectFromJson(json, new TypeToken<List<TrelloList>>(){});
            if (lists != null) return lists;
        }
        return new ArrayList<>();
    }
}
