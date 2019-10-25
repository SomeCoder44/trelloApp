package pl.orzechowski.trellomanagmentapp.network;

import android.content.Context;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.orzechowski.trellomanagmentapp.R;
import pl.orzechowski.trellomanagmentapp.models.TrelloBoard;
import pl.orzechowski.trellomanagmentapp.models.TrelloCard;
import pl.orzechowski.trellomanagmentapp.network.responses.BaseListResponse;
import pl.orzechowski.trellomanagmentapp.network.responses.BaseResponse;
import pl.orzechowski.trellomanagmentapp.network.responses.MBase;
import retrofit2.Call;
import retrofit2.Callback;

public class Connection {

    private static Connection INSTANCE;
    protected Context context;
    protected Client<Service> mClient;

    private Connection(Context context) {
        this.context = context;
        mClient = new Client<>(Service.class);
    }

    public static Connection init(Context context) {
        if (INSTANCE == null) {
            synchronized (Connection.class) {
                if (INSTANCE == null)
                    INSTANCE = new Connection(context);
            }
        }
        return INSTANCE;
    }

    public static Connection get() {
        return INSTANCE;
    }

    private static RequestBody get(String str) {
        if (str == null)
            return null;
        return RequestBody.create(MediaType.parse("multipart/form-data"), str);
    }

    private static MultipartBody.Part get(File file, String name) {
        if (file == null)
            return null;
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return MultipartBody.Part.createFormData(name, file.getName(), body);
    }

    public Client getClient() {
        return mClient;
    }


    public void getBoard(BaseCallback<TrelloBoard> callback) {
        if (context == null) return;
        Call<TrelloBoard> call = mClient.getService().getBoard(
                context.getString(R.string.board_id),
                "all",
                "all",
                context.getString(R.string.api_key),
                context.getString(R.string.token));

        call.enqueue(callback);
    }

    public void getCards(Callback<List<TrelloCard>> callback, String listId) {
        if (context == null) return;
        Call<List<TrelloCard>> call = mClient.getService().getCards(
                listId,
                context.getString(R.string.api_key),
                context.getString(R.string.token));

        call.enqueue(callback);
    }

    public void deleteCard(BaseCallback<BaseResponse<MBase>> callback, String cardId) {
        if (context == null) return;

        Call<BaseResponse<MBase>> call = mClient.getService().deleteCard(
                cardId,
                context.getString(R.string.api_key),
                context.getString(R.string.token));

        call.enqueue(callback);
    }

    public void updateCard(BaseCallback<BaseResponse<MBase>> callback, String cardId, String name, String desc, String listId) {
        if (context == null) return;

        Call<BaseResponse<MBase>> call = mClient.getService().updateCard(
                cardId,
                name,
                desc,
                listId,
                context.getString(R.string.api_key),
                context.getString(R.string.token));

        call.enqueue(callback);
    }

    public void addCard(BaseCallback<BaseResponse<MBase>> callback, String name, String desc, String listId) {
        if (context == null) return;

        Call<BaseResponse<MBase>> call = mClient.getService().addCard(
                name,
                desc,
                listId,
                "all",
                context.getString(R.string.api_key),
                context.getString(R.string.token));

        call.enqueue(callback);
    }

}