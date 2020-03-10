package homework1;

import java.util.Random;

public class RandomValues {
	private int setMin, max, min, m, n;

	public RandomValues(int setMin) {
		this.setMin = setMin;
	}
	
	public void getRandomValues() {
		m = randomNumber(m); n = randomNumber(n);
	}
	
	public long getM() {
		return m;
	}
	
	public long getN() {
		return n;
	}
	
	int randomNumber(int r) {
		Random random = new Random();
		final int size = 500;
		min = (r <= 0) ? size + size*setMin : r; int x = min + size; max = (x < max) ? max : x;
		return random.nextInt((max - min) + 1) + min;
	}
}
