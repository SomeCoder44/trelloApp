package pl.orzechowski.trellomanagmentapp.modules.card;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import pl.orzechowski.trellomanagmentapp.R;
import pl.orzechowski.trellomanagmentapp.base.BaseViewModel;
import pl.orzechowski.trellomanagmentapp.helpers.MHelper;
import pl.orzechowski.trellomanagmentapp.helpers.UserPreferences;
import pl.orzechowski.trellomanagmentapp.interfaces.ActivityProvider;
import pl.orzechowski.trellomanagmentapp.models.TrelloCard;
import pl.orzechowski.trellomanagmentapp.models.TrelloList;
import pl.orzechowski.trellomanagmentapp.network.BaseCallback;
import pl.orzechowski.trellomanagmentapp.network.Connection;
import pl.orzechowski.trellomanagmentapp.network.responses.BaseResponse;
import pl.orzechowski.trellomanagmentapp.network.responses.MBase;

public class CardViewModel extends BaseViewModel {

    public final ObservableField<String> cardName = new ObservableField<>();
    public final ObservableField<String> cardDesc = new ObservableField<>();
    public final ObservableBoolean listChoiceVisibility = new ObservableBoolean(false);
    public final ObservableBoolean optionsVisibility = new ObservableBoolean(true);
    public final ObservableInt colorRes = new ObservableInt(R.color.white);

    private TrelloCard trelloCard;
    private TrelloList trelloList;

    public void init(ActivityProvider activityProvider, TrelloList trelloList, TrelloCard trelloCard) {
        super.init(activityProvider);

        this.trelloList = trelloList;
        this.trelloCard = trelloCard;
        setup();
    }

    private void setup() {
        if (trelloCard == null) {
            optionsVisibility.set(false);
            return;
        }
        optionsVisibility.set(true);
        cardName.set(trelloCard.getName());
        cardDesc.set(trelloCard.getDesc());

       setupColor();
    }

    private void setupColor() {
        int index = 0;
        List<TrelloList> lists = UserPreferences.getInstance().getLists();
        for (int i = 0; i < lists.size(); i++) {
            if (trelloList.getId().equals(lists.get(i).getId())) {
                index = i;
                break;
            }
        }
        colorRes.set(MHelper.getColorRes(getActivity(), index));
    }

    public void updateColor(TrelloList chosen) {
        listChoiceVisibility.set(false);
        if (chosen != null) {
            this.trelloList = chosen;
            setupColor();
        }
    }

    public void onSave() {
        if (trelloCard == null) {
            Connection.get().addCard(new BaseCallback<BaseResponse<MBase>>() {
                @Override
                public void onMError(String message) {}

                @Override
                protected void onMSuccess(BaseResponse<MBase> response) {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> EventBus.getDefault().post(new TrelloCard()));
                        getActivity().onBackPressed();
                    }
                }
            }, cardName.get(), cardDesc.get(), trelloList.getId());
        } else {
            Connection.get().updateCard(new BaseCallback<BaseResponse<MBase>>() {
                @Override
                public void onMError(String message) { }

                @Override
                protected void onMSuccess(BaseResponse<MBase> response) {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> EventBus.getDefault().post(new TrelloCard()));
                        getActivity().onBackPressed();
                    }
                }
            }, trelloCard.getId(), cardName.get(), cardDesc.get(), trelloList.getId());
        }
    }

    public void onDelete() {
        Connection.get().deleteCard(new BaseCallback<BaseResponse<MBase>>() {
            @Override
            public void onMError(String message) { }

            @Override
            protected void onMSuccess(BaseResponse<MBase> response) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> EventBus.getDefault().post(new TrelloCard()));
                    getActivity().onBackPressed();
                }
            }
        }, trelloCard.getId());
    }

    public void onChangeParent() {
        MHelper.hideKeyboard(getActivity());
        listChoiceVisibility.set(!listChoiceVisibility.get());
    }
}
