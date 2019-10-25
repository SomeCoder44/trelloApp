package pl.orzechowski.trellomanagmentapp.base;

public class ChangeActivity {

    private Class<?> cls;
    private boolean finishPrevious;

    public ChangeActivity(Class<?> cls) {
        this(cls, false);
    }

    public ChangeActivity(Class<?> cls, boolean finishPrevious) {
        this.cls = cls;
        this.finishPrevious = finishPrevious;
    }

    public Class<?> getCls() {
        return this.cls;
    }

    public boolean shouldFinishPrevoius() {
        return this.finishPrevious;
    }
}
