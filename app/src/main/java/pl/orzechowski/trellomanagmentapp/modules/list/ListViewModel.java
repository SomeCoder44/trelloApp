package pl.orzechowski.trellomanagmentapp.modules.list;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pl.orzechowski.trellomanagmentapp.MainActivity;
import pl.orzechowski.trellomanagmentapp.R;
import pl.orzechowski.trellomanagmentapp.base.BaseViewModel;
import pl.orzechowski.trellomanagmentapp.helpers.MHelper;
import pl.orzechowski.trellomanagmentapp.interfaces.ActivityProvider;
import pl.orzechowski.trellomanagmentapp.interfaces.OnItemClickListener;
import pl.orzechowski.trellomanagmentapp.models.TrelloCard;
import pl.orzechowski.trellomanagmentapp.models.TrelloList;
import pl.orzechowski.trellomanagmentapp.modules.card.CardFragment;
import pl.orzechowski.trellomanagmentapp.network.Connection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListViewModel extends BaseViewModel implements OnItemClickListener<TrelloCard> {

    public final ObservableField<String> listName = new ObservableField<>("");
    public final ObservableField<ListAdapter> adapter = new ObservableField<>();
    public final ObservableField<RecyclerView.LayoutManager> lm = new ObservableField<>();
    public final ObservableInt colorRes = new ObservableInt(R.color.white);
    public final ObservableBoolean addButtonVisibility = new ObservableBoolean(false);

    private TrelloList trelloList;
    private ListAdapter listAdapter;
    private List<TrelloCard> cards = new ArrayList<>();
    private int position;

    public void init(ActivityProvider activityProvider, TrelloList trelloList, int position) {
        super.init(activityProvider);
        this.trelloList = trelloList;
        this.position = position;

        setup(activityProvider);
    }

    private void setup(ActivityProvider activityProvider) {
        if (activityProvider == null) return;

        listName.set(trelloList.getName());
        colorRes.set(MHelper.getColorRes(getActivity(), position));

        addButtonVisibility.set(position == 0);
        listAdapter = new ListAdapter(this, activityProvider, MHelper.getColorRes(getActivity(), position));
        adapter.set(listAdapter);
        lm.set(new LinearLayoutManager(activityProvider.getActivity()));

        getCards();
    }

    public void getCards() {
        Connection.get().getCards(new Callback<List<TrelloCard>>() {
            @Override
            public void onResponse(Call<List<TrelloCard>> call, Response<List<TrelloCard>> response) {
                if (response.body() != null) {
                    List<TrelloCard> list = response.body();
                    cards = list;
                    if (listAdapter != null) listAdapter.setData(list);
                }
            }

            @Override
            public void onFailure(Call<List<TrelloCard>> call, Throwable t) {
            }
        }, trelloList.getId());
    }

    @Override
    public void onItemClick(TrelloCard item) {
        if (getActivity() != null && item != null && trelloList != null) {
            ((MainActivity) getActivity()).attach(CardFragment.newInstance(trelloList, item), CardFragment.TAG, true);
        }
    }

    public void onAdd() {
        if (getActivity() != null && trelloList != null)
            ((MainActivity) getActivity()).attach(CardFragment.newInstance(trelloList), CardFragment.TAG, true);
    }
}
