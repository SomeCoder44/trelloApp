package pl.orzechowski.trellomanagmentapp.base;


import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleRegistry;

public class LifecycleFragment extends Fragment {
    private LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

    @Override
    public LifecycleRegistry getLifecycle() {
        return mLifecycleRegistry;
    }
}
