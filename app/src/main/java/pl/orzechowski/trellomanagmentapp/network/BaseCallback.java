package pl.orzechowski.trellomanagmentapp.network;

import org.jetbrains.annotations.NotNull;

import okhttp3.Headers;
import pl.orzechowski.trellomanagmentapp.BuildConfig;
import pl.orzechowski.trellomanagmentapp.interfaces.ConstantsValues;
import pl.orzechowski.trellomanagmentapp.network.responses.MBase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BaseCallback<T extends MBase> implements Callback<T> {

    public abstract void onMError(String message);

    protected abstract void onMSuccess(T response);

    protected String getDefaultErrorMessage() {
        return ConstantsValues.DEFAULT_ERROR;
    }


    @Override
    public void onResponse(@NotNull Call<T> t, @NotNull Response<T> response) {
        T body = response.body();

        int code = response.code();

        if (body == null) {
            onMError(getDefaultErrorMessage());
            return;
        }

        if (code == 200) {
            onMSuccess(body);
        } else {
            onMError(body.getMessage());


        }
    }

    @Override
    public void onFailure(@NotNull Call<T> t, @NotNull Throwable error) {
        if (error.getCause() != null && BuildConfig.DEBUG) error.getCause().printStackTrace();

        onMError(getDefaultErrorMessage());
    }
}
