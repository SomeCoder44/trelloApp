package pl.orzechowski.trellomanagmentapp.interfaces;

import android.view.View;

import java.util.List;

import androidx.fragment.app.Fragment;


public interface IOChangeFragment {
    void setFragment(Fragment fragment, boolean add);

    void setFragment(Fragment fragment, boolean add, List<View> sharedElementList);
}
