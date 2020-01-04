package pl.subtelny.islands.model;

public abstract class Synchronizeable {

	private boolean fullyLoaded;

	public boolean isFullyLoaded() {
		return fullyLoaded;
	}

	public void setFullyLoaded(boolean fullyLoaded) {
		this.fullyLoaded = fullyLoaded;
	}
}
