package pl.orzechowski.trellomanagmentapp.base;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import pl.orzechowski.trellomanagmentapp.MainActivity;
import pl.orzechowski.trellomanagmentapp.R;
import pl.orzechowski.trellomanagmentapp.interfaces.ActivityProvider;
import pl.orzechowski.trellomanagmentapp.interfaces.OnBackPressInterface;

public abstract class BindingFragment<B extends ViewDataBinding, VM extends BaseViewModel> extends ViewModelFragment<VM>
        implements OnBackPressInterface,
        ActivityProvider {

    private Context context;
    private Bundle savedInstanceState;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        B binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false);
        bindData(binding);
        this.savedInstanceState = savedInstanceState;

        return binding.getRoot();
    }

    public Bundle getBunde() {
        return savedInstanceState;
    }

    protected abstract void bindData(B binding);

    @LayoutRes
    protected abstract int getLayoutRes();

    @Override
    public Context getContext() {
        return context != null
                ? context : super.getContext();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).setOnBackPressInterface(this);
        }
        setHasOptionsMenu(true);
        callSetupNavigationItem();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            if (getActivity().getClass() == MainActivity.class)
                ((MainActivity) getActivity()).refreshHomeButton();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }


    public void attach(Fragment fragment, String tag, boolean addToBackStack) {
        if (getActivity() != null && getActivity().getClass() == MainActivity.class) {
            ((MainActivity) getActivity()).attach(fragment, tag, addToBackStack);
        }
    }

    public void onBackScreen() {
        callSetupNavigationItem();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean backPress() {
        return false;
    }

    /*
        0 - finish()
        1 - onBackPress()
        2 - BackPressInterface
        3 - doubleBackPress
     */
    public abstract int getBackPressType();

    /*
        1 - exit
        2 - arrow
     */
    public abstract int getHomeType();

    @Override
    public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {
        int id = enter ? R.animator.slide_fragment_in : R.animator.slide_fragment_out;
        final Animator anim = AnimatorInflater.loadAnimator(getActivity(), id);
        if (enter) {
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    onAnimationEnded();
                }
            });
        }
        return anim;
    }

    public void onAnimationEnded() {
    }

    public void callSetupNavigationItem() {
    }

    private Fragment getActiveFragment() {
        if (getFragmentManager() == null) return null;
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            return null;
        }
        String tag = getFragmentManager().getBackStackEntryAt(getFragmentManager().getBackStackEntryCount() - 1).getName();
        return getFragmentManager().findFragmentByTag(tag);
    }

    public int getExitIcon() {
        return R.drawable.ic_exit;
    }

    public int getArrowIcon() {
        return R.drawable.ic_arrow_back;
    }

}
