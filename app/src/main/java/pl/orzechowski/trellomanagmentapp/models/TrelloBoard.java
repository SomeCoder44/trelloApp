package pl.orzechowski.trellomanagmentapp.models;

import java.util.ArrayList;
import java.util.List;

import pl.orzechowski.trellomanagmentapp.network.responses.MBase;

public class TrelloBoard extends MBase {

    private String name;
    private String id;
    private List<TrelloList> lists;

    public TrelloBoard(String name, String id, List<TrelloList> lists) {
        this.name = name;
        this.id = id;
        this.lists = lists;
    }

    public String getName() {
        return name == null? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<TrelloList> getLists() {
        return lists == null ? new ArrayList<>() : lists;
    }

    public void setLists(List<TrelloList> lists) {
        this.lists = lists;
    }
}