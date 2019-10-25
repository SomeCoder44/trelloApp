package pl.orzechowski.trellomanagmentapp.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import pl.orzechowski.trellomanagmentapp.R;
import pl.orzechowski.trellomanagmentapp.interfaces.OnBackPressInterface;


public abstract class BaseActivity extends LifecycleActivity {

    protected OnBackPressInterface onBackPressInterface;

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void setOnBackPressInterface(OnBackPressInterface onBackPressInterface) {
        this.onBackPressInterface = onBackPressInterface;
    }

    public void attach(Fragment nextFragment, String tag, boolean addToBackStack) {
        final int FADE_DEFAULT_TIME = 800;

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment previousFragment = fragmentManager.findFragmentById(getIdFragmentContainer());
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
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void hideBottomNavigationView() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public DialogInterface.OnClickListener getCloseAppClickListener() {
        return (dialog, which) -> finish();
    }


    public abstract int getIdFragmentContainer();
}
