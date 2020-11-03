package pl.subtelny.core.api.repository;

public abstract class Loader<RESULT> {

	public abstract LoaderResult<RESULT> perform();

}
