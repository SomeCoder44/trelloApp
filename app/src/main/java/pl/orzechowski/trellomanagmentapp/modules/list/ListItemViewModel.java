package pl.orzechowski.trellomanagmentapp.modules.list;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import pl.orzechowski.trellomanagmentapp.base.BaseViewModel;
import pl.orzechowski.trellomanagmentapp.models.TrelloCard;

public class ListItemViewModel extends BaseViewModel {

    public final ObservableField<String> cardName = new ObservableField<>("");
    public final ObservableInt colorRes = new ObservableInt();

    public void setItem(TrelloCard object, int colorRes) {
        if (object == null) return;
        this.cardName.set(object.getName());
        this.colorRes.set(colorRes);
    }
}
