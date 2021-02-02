package pl.subtelny.core.api.worldedit;

import pl.subtelny.utilities.Callback;

import java.io.IOException;

public interface OperationSession {

    void perform() throws InterruptedException;

    void cancel();

    void setStateListener(Callback<OperationStatus> state);

}
