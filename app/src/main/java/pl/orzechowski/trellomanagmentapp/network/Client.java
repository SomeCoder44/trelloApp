package pl.orzechowski.trellomanagmentapp.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Client<T> {

    public static final String API_URL = "https://api.trello.com/1/";
    private static final String TAG = "Client";
    protected Gson gson;

    private T syncService;

    public Client(Class<T> serviceClass) {
        gson = createGson();


        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(API_URL);
        builder.addConverterFactory(GsonConverterFactory.create(gson));
        builder.client(getHttpClient());

        Retrofit restAdapter = builder.build();
        syncService = restAdapter.create(serviceClass);
    }

    public static Connection get() {
        return Connection.get();
    }

    protected Gson createGson() {

        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
    }

    public Gson getGson() {
        return gson;
    }

    private OkHttpClient getHttpClient() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.readTimeout(90 * 1000, TimeUnit.MILLISECONDS);
        okHttpClient.connectTimeout(15 * 1000, TimeUnit.MILLISECONDS);
        okHttpClient.addNetworkInterceptor(getLoggingInterceptor());
        okHttpClient.addInterceptor((getInterceptor()));

        return okHttpClient.build();
    }

    private Interceptor getInterceptor() {
        return chain -> {
            Request.Builder builder = chain.request().newBuilder();
            return chain.proceed(builder.build());
        };
    }

    private HttpLoggingInterceptor getLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    public T getService() {
        return syncService;
    }


}
