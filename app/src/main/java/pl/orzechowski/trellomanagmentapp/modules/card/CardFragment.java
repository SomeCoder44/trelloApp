package pl.orzechowski.trellomanagmentapp.modules.card;

import android.icu.util.ValueIterator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import pl.orzechowski.trellomanagmentapp.R;
import pl.orzechowski.trellomanagmentapp.base.BindingFragment;
import pl.orzechowski.trellomanagmentapp.databinding.FragmentCardBinding;
import pl.orzechowski.trellomanagmentapp.helpers.JSONConverter;
import pl.orzechowski.trellomanagmentapp.helpers.MHelper;
import pl.orzechowski.trellomanagmentapp.helpers.UserPreferences;
import pl.orzechowski.trellomanagmentapp.models.TrelloCard;
import pl.orzechowski.trellomanagmentapp.models.TrelloList;
import pl.orzechowski.trellomanagmentapp.modules.list.ListFragment;

public class CardFragment extends BindingFragment<FragmentCardBinding, CardViewModel> implements View.OnClickListener {

    public static final String TAG = "CardFragment";
    public static final String ARG = TAG + ".ARG";
    public static final String ARG_2 = TAG + ".ARG_2";
    private TrelloList trelloList;
    private TrelloCard trelloCard;
    private ViewGroup root;
    private LinearLayoutCompat container;

    public static CardFragment newInstance(TrelloList trelloList, TrelloCard trelloCard) {
        Bundle args = new Bundle();
        args.putString(ARG, JSONConverter.getJsonFromObject(trelloList));
        args.putString(ARG_2, JSONConverter.getJsonFromObject(trelloCard));
        CardFragment fragment = new CardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static CardFragment newInstance(TrelloList trelloList) {
        Bundle args = new Bundle();
        args.putString(ARG, JSONConverter.getJsonFromObject(trelloList));
        CardFragment fragment = new CardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String json = getArguments().getString(ARG);
            trelloList = JSONConverter.getObjectFromJson(json, new TypeToken<TrelloList>() {
            });
            String json2 = getArguments().getString(ARG_2);
            trelloCard = JSONConverter.getObjectFromJson(json2, new TypeToken<TrelloCard>() {
            });
        }
    }

    @Override
    protected void bindData(FragmentCardBinding binding) {
        binding.setViewModel(viewModel);
        viewModel.init(this, trelloList, trelloCard);

        bind(binding.getRoot());
        setupViews();
    }

    private void bind(View view) {
        root = view.findViewById(R.id.main_view);
        container = view.findViewById(R.id.popup_container);
    }

    private void setupViews() {
        if (getActivity() == null) return;

        List<TrelloList> lists = UserPreferences.getInstance().getLists();
        for (int i = 0; i < lists.size(); i++) {
            View listItem = LayoutInflater.from(getActivity()).inflate(R.layout.item_list_choice, root, false);
            TrelloList listObject = lists.get(i);
            if (listItem != null && listObject != null && container != null) {
                TextView listName = listItem.findViewById(R.id.list_name);
                View colorBox = listItem.findViewById(R.id.color_box);

                listName.setText(listObject.getName());
                colorBox.setBackgroundColor(MHelper.getColorRes(getActivity(), i));
                listItem.setTag(listObject);
                listItem.setOnClickListener(CardFragment.this);
                container.addView(listItem);
            }
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_card;
    }

    @Override
    public int getBackPressType() {
        return 1;
    }

    @Override
    public int getHomeType() {
        return 2;
    }

    @Override
    protected Class<CardViewModel> getViewModelClass() {
        return CardViewModel.class;
    }

    @Override
    public void onClick(View v) {
        if (v.getTag() != null) {
            TrelloList listObject = (TrelloList) v.getTag();
            viewModel.updateColor(listObject);
        }
    }
}
