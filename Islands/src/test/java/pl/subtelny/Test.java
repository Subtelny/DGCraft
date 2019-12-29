package pl.subtelny;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import org.checkerframework.checker.nullness.qual.Nullable;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.islands.utils.AlgorithmsUtil;

public class Test {

	@org.junit.Test
	public void test() {
		Cache<AccountId, Optional<String>> cache = Caffeine.newBuilder().build();

		AccountId accountId = AccountId.of(UUID.randomUUID());
		Optional<String> s = cache.get(accountId, accountId1 -> {
			System.out.println("uno");
			return Optional.empty();
		});

		System.out.println(s);


		s = cache.get(accountId, accountId1 -> {
			System.out.println("asd");
			return Optional.empty();
		});
		System.out.println(s);
	}

	public void bound(int minX, int maxX, int minY, int maxY) {

		Integer[] next = new Integer[]{2,2};
		while (true) {
			int x = next[0];
			int y = next[1];

			if(x > maxX || x < minX || y > maxY || y < minY) {
				break;
			}
			System.out.println(String.format("x:%s, y:%s", x,y));
			next = AlgorithmsUtil.nextCoordsSpirally(x,y);
		}

	}

}
