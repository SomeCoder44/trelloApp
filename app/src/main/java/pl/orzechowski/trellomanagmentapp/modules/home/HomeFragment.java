package pl.orzechowski.trellomanagmentapp.modules.home;

import android.os.Bundle;
import android.view.View;

import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import pl.orzechowski.trellomanagmentapp.R;
import pl.orzechowski.trellomanagmentapp.base.BindingFragment;
import pl.orzechowski.trellomanagmentapp.databinding.FragmentHomeBinding;
import pl.orzechowski.trellomanagmentapp.helpers.JSONConverter;
import pl.orzechowski.trellomanagmentapp.models.TrelloBoard;
import pl.orzechowski.trellomanagmentapp.models.TrelloList;
import pl.orzechowski.trellomanagmentapp.modules.list.ListFragment;

public class HomeFragment extends BindingFragment<FragmentHomeBinding, HomeViewModel> {

    public static final String TAG = "HomeFragment";
    public static final String ARG = TAG + ".ARG";
    private ViewPager mViewPager;
    private List<TrelloList> lists = new ArrayList<>();

    public static HomeFragment newInstance(List<TrelloList> lists) {
        Bundle args = new Bundle();
        args.putString(ARG, JSONConverter.getJsonFromObject(lists));
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String json = getArguments().getString(ARG);
            lists = JSONConverter.getObjectFromJson(json, new TypeToken<List<TrelloList>>() {
            });
        }
    }

    @Override
    protected void bindData(FragmentHomeBinding binding) {
        binding.setViewModel(viewModel);
        viewModel.init(this);
        bind(binding.getRoot());
        setupViews();
    }

    private void bind(View root) {
        mViewPager = root.findViewById(R.id.main_viewpager);
    }

    private void setupViews() {
        if (getActivity() == null) return;
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.refreshDrawableState();
        mViewPager.setOffscreenPageLimit(1);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_home;
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
    protected Class<HomeViewModel> getViewModelClass() {
        return HomeViewModel.class;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @NotNull
        @Override
        public Fragment getItem(int position) {
            return ListFragment.newInstance(lists.get(position), position);
        }

        @Override
        public int getCount() {
            return lists.size();
        }
    }
}
