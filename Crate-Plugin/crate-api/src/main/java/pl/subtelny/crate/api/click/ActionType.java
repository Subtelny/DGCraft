package pl.subtelny.crate.api.click;

public enum ActionType {

    LEFT_CLICK,
    OTHER;

    public boolean isClick() {
        return LEFT_CLICK.equals(this);
    }

}
