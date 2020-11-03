package pl.subtelny.core.api.repository;

public class LoaderResult<RESULT> {

    private final RESULT loadedData;

    public LoaderResult(RESULT loadedData) {
        this.loadedData = loadedData;
    }

	public RESULT getLoadedData() {
		return loadedData;
	}
}
