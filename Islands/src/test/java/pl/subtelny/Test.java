package pl.subtelny;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import pl.subtelny.identity.BasicIdentity;
import pl.subtelny.islands.repository.island.synchronizer.Synchronizer;

import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Test extends Synchronizer<Integer> {

    @org.junit.Test
    public void test() {
    	Cache<Long, TestIsland> islandCache = Caffeine.newBuilder().build();
    	Cache<UUID, TestIslander> islanderCache = Caffeine.newBuilder().build();

    	TestIslander islander = new TestIslander();
    	islander.setId(UUID.randomUUID());
    	islanderCache.put(islander.getId(), islander);

    	TestIsland island = new TestIsland();
    	island.setId(1L);
    	islandCache.put(island.getId(), island);

    	islander.setTestIsland(island);
    	island.addIslander(islander);

		TestIsland ifPresent = islandCache.getIfPresent(island.getId());
		TestIslander ifPresent1 = islanderCache.getIfPresent(islander.getId());
		islandCache.invalidate(1L);

		System.out.println("Island: " + ifPresent + " -" + ifPresent.getIslanders());
		System.out.println("Islander: " + ifPresent1 + " -" + ifPresent1.getTestIsland());


	}

    private class TestIsland extends BasicIdentity<Long> {

    	private List<TestIslander> islanders = new ArrayList<>();

		public List<TestIslander> getIslanders() {
			return islanders;
		}

		public void addIslander(TestIslander islander) {
			islanders.add(islander);
		}
	}

	private class TestIslander extends BasicIdentity<UUID> {

    	private TestIsland testIsland;

		public TestIsland getTestIsland() {
			return testIsland;
		}

		public void setTestIsland(TestIsland testIsland) {
			this.testIsland = testIsland;
		}
	}

}
