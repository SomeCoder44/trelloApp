package pl.orzechowski.trellomanagmentapp.base;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.view.View;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.PropertyChangeRegistry;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import pl.orzechowski.trellomanagmentapp.interfaces.ActivityProvider;
import pl.orzechowski.trellomanagmentapp.interfaces.OIFragmentActivityCommunication;

interface IViewModel {

    void onAttach(OIFragmentActivityCommunication communication);

    void onDetach();

}

public class BaseViewModel extends ViewModel implements IViewModel, LifecycleObserver, Observable {

    public static BaseViewModel base;
    public final ObservableField<String> emptyListText = new ObservableField<>("Brak zgłoszeń");
    public final ObservableField<Boolean> showEmptyList = new ObservableField<>(false);
    public final ObservableField<Boolean> showShouldLogin = new ObservableField<>(false);
    public final ObservableBoolean isNightMode = new ObservableBoolean(false);
    private final ObservableField<ChangeActivity> startActivity = new ObservableField<>();
    private ActivityProvider activityProvider;
    private PropertyChangeRegistry registry = new PropertyChangeRegistry();
    private OIFragmentActivityCommunication delegate;

    public static <T extends BaseViewModel> T get(Fragment fragment, Class<T> cls) {
        T t = ViewModelProviders.of(fragment).get(cls);
        base = t;
        fragment.getLifecycle().addObserver(t);
        return t;
    }

    public static <T extends BaseViewModel> T get(LifecycleActivity activity, Class<T> cls) {
        T t = ViewModelProviders.of(activity).get(cls);
        base = t;

        activity.getLifecycle().addObserver(t);
        return t;
    }

    public void init(ActivityProvider activityProvider) {
        this.activityProvider = activityProvider;
        int nightModeFlags = activityProvider.getActivity().getResources().getConfiguration().uiMode &
                Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
            isNightMode.set(true);
        } else isNightMode.set(false);
    }

    @Override
    public void onAttach(OIFragmentActivityCommunication communication) {
        this.delegate = communication;
    }

    @Override
    public void onDetach() {
        this.delegate = null;
    }

    protected void setStartActivity(Class<?> activity, boolean finishPrevious) {
        this.startActivity.set(new ChangeActivity(activity, finishPrevious));
    }

    protected void setFragment(Fragment fragment) {
        if (this.delegate == null) return;

        this.delegate.getIChangeFragment().setFragment(
                fragment,
                true
        );
    }

    protected void setFragment(Fragment fragment, List<View> sharedElementList) {
        if (this.delegate == null) return;

        this.delegate.getIChangeFragment().setFragment(
                fragment,
                true,
                sharedElementList
        );
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        registry.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        registry.remove(callback);
    }

    protected void notifyChange(int propertyId) {
        registry.notifyChange(this, propertyId);
    }

    @Override
    protected void onCleared() {
        // TODO lifecycle unregister
//        Activity activity = activityProvider.getActivity();
//        if (base != null && activity != null)  ((LifecycleActivity) activity).getLifecycle().removeObserver(base);
    }

    public void login() {

    }

    @Nullable
    public Activity getActivity() {
        return activityProvider == null? null : activityProvider.getActivity();
    }

    @Nullable
    public Context getContext() {
        return activityProvider == null? null : activityProvider.getActivity();
    }

}