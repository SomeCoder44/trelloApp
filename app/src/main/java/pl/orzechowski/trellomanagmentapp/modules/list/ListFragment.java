package pl.orzechowski.trellomanagmentapp.modules.list;

import android.os.Bundle;

import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.Nullable;
import pl.orzechowski.trellomanagmentapp.R;
import pl.orzechowski.trellomanagmentapp.base.BindingFragment;
import pl.orzechowski.trellomanagmentapp.databinding.FragmentListBinding;
import pl.orzechowski.trellomanagmentapp.helpers.JSONConverter;
import pl.orzechowski.trellomanagmentapp.models.TrelloCard;
import pl.orzechowski.trellomanagmentapp.models.TrelloList;

public class ListFragment extends BindingFragment<FragmentListBinding, ListViewModel> {

    public static final String TAG = "ListFragment";
    public static final String ARG = TAG + ".ARG";
    public static final String ARG_2 = TAG + ".ARG_2";
    private TrelloList trelloList;
    private int position;

    public static ListFragment newInstance(TrelloList trelloList, int position) {
        Bundle args = new Bundle();
        args.putString(ARG, JSONConverter.getJsonFromObject(trelloList));
        args.putInt(ARG_2, position);
        ListFragment fragment = new ListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCallToRefresh(TrelloCard ignored) {
        if (viewModel != null) viewModel.getCards();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String json = getArguments().getString(ARG);
            trelloList = JSONConverter.getObjectFromJson(json, new TypeToken<TrelloList>() {
            });
            position = getArguments().getInt(ARG_2, 0);
        }
    }

    @Override
    protected void bindData(FragmentListBinding binding) {
        binding.setViewModel(viewModel);
        viewModel.init(this, trelloList, position);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_list;
    }

    @Override
    public int getBackPressType() {
        return 0;
    }

    @Override
    public int getHomeType() {
        return 1;
    }

    @Override
    protected Class<ListViewModel> getViewModelClass() {
        return ListViewModel.class;
    }
}
