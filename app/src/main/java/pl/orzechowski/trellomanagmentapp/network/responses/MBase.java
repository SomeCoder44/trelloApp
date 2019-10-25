package pl.orzechowski.trellomanagmentapp.network.responses;


public class MBase {

    private final static String STATUS_OK = "OK";
    private final static String STATUS_SUCCESS = "SUCCESS";
    private final static String STATUS_ERROR = "ERROR";

    private int status;
    private String message;

    public boolean isSuccess() {
        return status == 200;
    }

    public boolean isError() {
        return status != 200;
    }

    public String getMessage() {
        return message == null ? "" : message;
    }
}
