package pl.subtelny.repository;

import java.util.List;

public class LoaderResult<RESULT> {

    private final List<RESULT> loadedData;

    public LoaderResult(List<RESULT> loadedData) {
        this.loadedData = loadedData;
    }

    public List<RESULT> getLoadedData() {
        return loadedData;
    }

}
