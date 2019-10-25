package pl.orzechowski.trellomanagmentapp.network.responses;


public class BaseResponse<T> extends MBase {
    private T items;

    public T getResult() {
        return items;
    }
}
