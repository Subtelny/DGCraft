package pl.subtelny.repository;

public interface LoadAction<RESULT> {

	LoaderResult<RESULT> perform();

}
