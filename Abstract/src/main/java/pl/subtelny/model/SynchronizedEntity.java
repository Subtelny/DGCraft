package pl.subtelny.model;

public abstract class SynchronizedEntity {

	private volatile boolean fullyLoaded;

	public boolean isFullyLoaded() {
		return fullyLoaded;
	}

	public void setFullyLoaded(boolean fullyLoaded) {
		this.fullyLoaded = fullyLoaded;
	}
}
