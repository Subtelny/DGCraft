package pl.subtelny.repository;

import java.util.List;

public interface LoadAction<RESULT> {

	RESULT perform();

	List<RESULT> performList();

}
