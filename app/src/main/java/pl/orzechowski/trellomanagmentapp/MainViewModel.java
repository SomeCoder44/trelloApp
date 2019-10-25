package pl.orzechowski.trellomanagmentapp;

import android.graphics.drawable.Drawable;
import android.widget.Toast;

import java.util.List;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import pl.orzechowski.trellomanagmentapp.base.BaseViewModel;
import pl.orzechowski.trellomanagmentapp.helpers.UserPreferences;
import pl.orzechowski.trellomanagmentapp.interfaces.ActivityProvider;
import pl.orzechowski.trellomanagmentapp.models.TrelloBoard;
import pl.orzechowski.trellomanagmentapp.models.TrelloCard;
import pl.orzechowski.trellomanagmentapp.modules.home.HomeFragment;
import pl.orzechowski.trellomanagmentapp.network.BaseCallback;
import pl.orzechowski.trellomanagmentapp.network.Connection;
import pl.orzechowski.trellomanagmentapp.network.responses.BaseListResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends BaseViewModel {

    public final ObservableField<String> boardName = new ObservableField<>("");
    public final ObservableInt iconRes = new ObservableInt();

    private MainActivity activity;

    @Override
    public void init(ActivityProvider activityProvider) {
        super.init(activityProvider);
        this.activity = (MainActivity) activityProvider.getActivity();
        getBoard();
    }

    private void getBoard() {
        Connection.get().getBoard(new BaseCallback<TrelloBoard>() {
            @Override
            public void onMError(String message) { }

            @Override
            protected void onMSuccess(TrelloBoard response) {
                if (response != null) {
                    boardName.set(response.getName());
                    UserPreferences.getInstance().saveLists(response.getLists());
                    if (getActivity() != null) ((MainActivity) getActivity()).attach(HomeFragment.newInstance(response.getLists()), HomeFragment.TAG, true);
                }
            }
        });

    }

    public void refreshHomeButton() {
        switch (activity.getCurrentFragment().getHomeType()) {
            case 1:
                setExitHome();
                break;
            case 2:
                setArrowHome();
                break;
            default:
                setExitHome();
                break;
        }
    }

    private void setArrowHome() {
        iconRes.set(activity.getCurrentFragment().getArrowIcon());
    }

    private void setExitHome() {
        iconRes.set(activity.getCurrentFragment().getExitIcon());
    }

    public void onMenuClicked() {
        if (getActivity() != null) getActivity().onBackPressed();
    }
}
