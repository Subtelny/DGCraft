package pl.subtelny.core.api.worldedit;

import org.primesoft.asyncworldedit.api.blockPlacer.entries.JobStatus;

public enum OperationStatus {

    INITIALIZING,
    PREPARING,
    WAITING,
    PLACING_BLOCKS,
    DONE,
    CANCELED;

    public boolean isEnded() {
        switch (this) {
            case DONE:
            case CANCELED:
                return true;
        }
        return false;
    }

    public static OperationStatus fromJobStatus(JobStatus jobStatus) {
        switch (jobStatus) {
            case Initializing:
                return INITIALIZING;
            case Preparing:
                return PREPARING;
            case Waiting:
                return WAITING;
            case PlacingBlocks:
                return PLACING_BLOCKS;
            case Done:
                return DONE;
            case Canceled:
                return CANCELED;
            default:
                throw new IllegalStateException("Not found status");
        }
    }

}
