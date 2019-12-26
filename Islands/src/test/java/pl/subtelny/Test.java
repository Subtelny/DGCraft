package pl.subtelny;

import java.util.Arrays;
import pl.subtelny.islands.utils.AlgorithmsUtil;

public class Test {

	@org.junit.Test
	public void test() {
		Integer[] next = AlgorithmsUtil.getNext(0, 0);

		int minX = 0;
		int minY = 0;
		int maxX = 4;
		int maxY = 4;

		bound(minX,maxX,minY,maxY);

		System.out.println("next " + Arrays.toString(next));

		next = AlgorithmsUtil.getNext(next[0], next[1]);
		System.out.println("next " + Arrays.toString(next));
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
			next = AlgorithmsUtil.getNext(x,y);
		}

	}

}
