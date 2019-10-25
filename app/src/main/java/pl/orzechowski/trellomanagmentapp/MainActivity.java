package pl.orzechowski.trellomanagmentapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import pl.orzechowski.trellomanagmentapp.base.BaseActivity;
import pl.orzechowski.trellomanagmentapp.base.BindingFragment;
import pl.orzechowski.trellomanagmentapp.databinding.ActivityMainBinding;
import pl.orzechowski.trellomanagmentapp.helpers.AlertDialogManager;
import pl.orzechowski.trellomanagmentapp.helpers.MHelper;
import pl.orzechowski.trellomanagmentapp.interfaces.ActivityProvider;


public class MainActivity extends BaseActivity implements ActivityProvider {

    private MainViewModel mainViewModel = new MainViewModel();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel.init(this);
        mainViewModel.onCreate();
        binding.setViewModel(mainViewModel);
        bind(binding.getRoot());
        setupViews();

    }

    private void bind(View view) {

    }

    public void setupViews() {

    }

    public BindingFragment getCurrentFragment() {
        return (BindingFragment) getSupportFragmentManager().findFragmentById(getIdFragmentContainer());
    }

    public void refreshHomeButton() {
        mainViewModel.refreshHomeButton();
    }


    public void attach(Fragment nextFragment, String tag, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(R.animator.slide_fragment_in, R.animator.slide_fragment_out);
        ft.addToBackStack(tag);

        if (addToBackStack) {
            ft.add(getIdFragmentContainer(), nextFragment, tag);
        } else {
            ft.replace(getIdFragmentContainer(), nextFragment);
        }
        ft.commit();

    }

    @Override
    public void onResume() {
        super.onResume();
        mainViewModel.onResume();
    }


    @Override
    public int getIdFragmentContainer() {
        return R.id.main_fragment_id;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void onBackPressed() {
        int backPressType;
        MHelper.hideKeyboard(this);
        BindingFragment baseFragment;
        Fragment fragment = getSupportFragmentManager().findFragmentById(getIdFragmentContainer());
        if (fragment instanceof BindingFragment) {
            baseFragment = (BindingFragment) fragment;
            backPressType = baseFragment.getBackPressType();
        } else {
            backPressType = 1;
        }

        switch (backPressType) {
            case 0:
                AlertDialogManager.get().show(this, getString(R.string.close_app), getCloseAppClickListener());
                break;
            case 1: {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStack();
                } else {
                    super.onBackPressed();
                }
            }
            break;

            case 2: {
                if (onBackPressInterface.backPress()) {
                    super.onBackPressed();
                }
            }
            break;
            case 3: {
                super.onBackPressed();
                super.onBackPressed();
            }
            break;
        }

        new Handler().postDelayed(this::refreshHomeButton, 200);
    }
}
