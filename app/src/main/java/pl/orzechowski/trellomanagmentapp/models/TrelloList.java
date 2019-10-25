package pl.orzechowski.trellomanagmentapp.models;

public class TrelloList {

    private String name;
    private String id;

    public String getName() {
        return name == null? "" : name;
    }

    public String getId() {
        return id == null? "" : id;
    }
}
