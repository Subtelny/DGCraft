package pl.subtelny.repository;

import java.util.List;

public interface LoadAction<RESULT> {

	LoaderResult<RESULT> perform();

	LoaderResult<List<RESULT>> performList();

}
