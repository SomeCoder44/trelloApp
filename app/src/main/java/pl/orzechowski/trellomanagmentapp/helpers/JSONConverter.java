package pl.orzechowski.trellomanagmentapp.helpers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class JSONConverter {

    public static <T>  T getObjectFromJson(String json, TypeToken<T> typeToken){

        Gson gson = new Gson();
        Type type = typeToken.getType();
        return gson.fromJson(json, type);
    }

    public static String getJsonFromObject(Object src){
        Gson gson = new Gson();
        Type type = new TypeToken<Object>(){}.getType();
        return gson.toJson(src, type);
    }

}
