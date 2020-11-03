package pl.subtelny.core.api.worldedit;

import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import org.apache.commons.lang.Validate;
import org.primesoft.asyncworldedit.api.utils.IFuncParamEx;
import org.primesoft.asyncworldedit.api.worldedit.ICancelabeEditSession;

public abstract class WorldEditAction implements IFuncParamEx<Integer, ICancelabeEditSession, MaxChangedBlocksException> {

    private Operation operation;

    @Override
    public Integer execute(ICancelabeEditSession session) throws IllegalStateException {
        session.enableQueue();
        session.setFastMode(true);

        try {
            operation = getOperation(session);
            Operations.complete(operation);
        } catch (WorldEditException e) {
            throw new IllegalStateException(e.getMessage());
        } finally {
            session.flushSession();
        }
        return 1;
    }

    protected abstract Operation getOperation(ICancelabeEditSession session);

    public void stopOperation() {
        Validate.notNull(operation, "Operation is null");
        operation.cancel();
    }

}
