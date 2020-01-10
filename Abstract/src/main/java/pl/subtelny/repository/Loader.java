package pl.subtelny.repository;

public abstract class Loader<RESULT> {

	public abstract LoaderResult<RESULT> perform();

}
