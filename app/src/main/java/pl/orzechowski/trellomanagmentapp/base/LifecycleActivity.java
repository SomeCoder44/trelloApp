package pl.orzechowski.trellomanagmentapp.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

public class LifecycleActivity extends AppCompatActivity implements LifecycleOwner {
    private LifecycleRegistry registry = new LifecycleRegistry(LifecycleActivity.this);

    @Override
    public Lifecycle getLifecycle() {
        return getRegistry();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getRegistry().handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    }

    @Override
    public void onStart() {
        super.onStart();

        getRegistry().handleLifecycleEvent(Lifecycle.Event.ON_START);
    }

    @Override
    public void onResume() {
        super.onResume();

        getRegistry().handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
    }

    @Override
    public void onPause() {
        super.onPause();

        getRegistry().handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
    }

    @Override
    public void onStop() {
        super.onStop();

        getRegistry().handleLifecycleEvent(Lifecycle.Event.ON_STOP);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        getRegistry().handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    }

    private LifecycleRegistry getRegistry() {
        if (registry == null) {
            registry = new LifecycleRegistry(LifecycleActivity.this);
        }
        return registry;
    }
}

