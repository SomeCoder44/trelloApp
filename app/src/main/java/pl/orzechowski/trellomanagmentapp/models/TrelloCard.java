package pl.orzechowski.trellomanagmentapp.models;

import pl.orzechowski.trellomanagmentapp.network.responses.MBase;

public class TrelloCard extends MBase {
    private String id;
    private String name;
    private String desc;

    public TrelloCard() { }

    public String getId() {
        return id == null ? "" : id;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public String getDesc() {
        return desc == null ? "" : desc;
    }
}
