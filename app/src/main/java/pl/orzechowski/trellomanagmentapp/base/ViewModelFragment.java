package pl.orzechowski.trellomanagmentapp.base;


import android.content.Context;

import androidx.databinding.Observable;
import pl.orzechowski.trellomanagmentapp.interfaces.OIFragmentActivityCommunication;

public abstract class ViewModelFragment<VM extends BaseViewModel> extends LifecycleFragment {
    protected VM viewModel;

    private Observable.OnPropertyChangedCallback propertyCallback = getViewModelChangedCallback();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        viewModel = createViewModel();
        onViewModelCreated(viewModel);
        viewModel.addOnPropertyChangedCallback(propertyCallback);

        if (context instanceof OIFragmentActivityCommunication) {
            viewModel.onAttach((OIFragmentActivityCommunication) context);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        viewModel.onDetach();
        viewModel.removeOnPropertyChangedCallback(propertyCallback);
    }

    protected Observable.OnPropertyChangedCallback getViewModelChangedCallback() {
        return new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                ViewModelFragment.this.onPropertyChanged(propertyId);
            }
        };
    }

    protected VM createViewModel() {
        return BaseViewModel.get(this, getViewModelClass());
    }

    protected abstract Class<VM> getViewModelClass();

    protected void onPropertyChanged(int propertyId) {
    }

    protected void onViewModelCreated(VM viewModel) {
    }
}
