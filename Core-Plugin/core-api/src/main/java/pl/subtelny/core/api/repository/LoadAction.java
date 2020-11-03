package pl.subtelny.core.api.repository;

import java.util.List;

public interface LoadAction<RESULT> {

	RESULT perform();

	List<RESULT> performList();

}
