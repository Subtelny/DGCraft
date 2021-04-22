package pl.subtelny.crate.click;

public enum ActionType {

    LEFT_CLICK;

    boolean isClick() {
        return LEFT_CLICK.equals(this);
    }

}
