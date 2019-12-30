package pl.subtelny.islands.repository.loader;

public abstract class Loader<REQUEST, RESULT> {

	public abstract RESULT perform();

}
