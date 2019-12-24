package pl.subtelny;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.concurrent.ExecutionException;

public class Test {

	@org.junit.Test
	public void test() throws ExecutionException {
		LoadingCache<Long, Car> cache = CacheBuilder.newBuilder().build(new CacheLoader<Long, Car>() {

			boolean smt = false;

			@Override
			public Car load(Long aLong) {
				Car car = new Car();
				car.setName("AUDI");
				if (smt) {
					car.setName("BMW");
				}
				smt = true;
				return car;
			}
		});

		Garage garage = new Garage();
		garage.setCar(cache.get(1L));

		System.out.println(garage.getCar().getName() + " - name of car");

		cache.invalidate(1L);

		System.out.println(garage.getCar().getName() + " - name of car #2");

		Car car2 = cache.get(1L);
		System.out.println(car2.getName() + " car2");
	}

	private class Car {

		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	private class Garage {

		private Car car;

		public Car getCar() {
			return car;
		}

		public void setCar(Car car) {
			this.car = car;
		}
	}

}
