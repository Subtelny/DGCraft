package pl.subtelny.islands.utils;

import static java.lang.Math.abs;

public class AlgorithmsUtil {

	public static Integer[] nextCoordsSpirally(int x, int y) {
		if (x == y) {
			if (x > 0)
				return new Integer[]{x - 1, y};
			if (x < 0)
				return new Integer[]{x + 1, y};
			return new Integer[]{1, 0};
		}

		if (x == -y) {
			if (x < 0)
				return new Integer[]{x, y - 1};
			if (x > 0)
				return new Integer[]{x + 1, y};
		}

		if (x - y > 0) {
			if (abs(x) > abs(y)) {
				return new Integer[]{x, y + 1};
			} else {
				return new Integer[]{x + 1, y};
			}
		}
		if (x - y < 0) {
			if (abs(x) > abs(y)) {
				return new Integer[]{x, y - 1};
			} else {
				return new Integer[]{x - 1, y};
			}
		}
		return new Integer[]{0, 0};
	}

}
