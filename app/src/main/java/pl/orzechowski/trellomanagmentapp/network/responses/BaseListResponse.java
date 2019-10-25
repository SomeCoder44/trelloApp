package pl.orzechowski.trellomanagmentapp.network.responses;

import java.util.List;

public class BaseListResponse<T> extends MBase {
    private List<T> items;

    public List<T> getResult() {
        return items;
    }
}
