package homework1;

import homework1.RandomValues;
import setup.Data;
import setup.TimeAlgorithm;
import thread.Run;

public class Problem1EuclidsAlgorithm {
	private final int size = 10000, threadSize = 10, load = size / threadSize;
	private int thread;
	private String[][] write = new String[2][size];
	
	public static void main(String[] args) {
		new Problem1EuclidsAlgorithm();
	}
	
	public Problem1EuclidsAlgorithm() {
		Data data = new Data(getClass());
		String i = "Iteration", r = "Recursion", c = "Consecutive Integer Checking";
		data.write("Size of m,Time in nanoseconds (" + i + " - " + r + " - " + c +"):", true);
		for (thread = 1; thread <= threadSize; thread++) {
			new Thread(new Run() {
				private final int a = thread;
				@Override
				public void start() {
					TimeAlgorithm iteration = null, recursion = null, consecutiveIntegerChecking = null;
					RandomValues randomValues = new RandomValues(load * (a - 1));
					int max = load * a, min = max - load + 1;
					while (min <= max) {
						randomValues.getRandomValues();
						long m = randomValues.getM(), n = randomValues.getN();
						iteration = new TimeAlgorithm() {
							@Override
							public String algorithm() {
								return "" + iteration(m, n);
							}
							@Override
							public String algorithmName() {
								return i;
							}
						}; 
						if (iteration.getResult() != "1") {
							recursion = new TimeAlgorithm() {
								@Override
								public String algorithm() {
									return "" + recursion(m, n);
								}
								@Override
								public String algorithmName() {
									return r;
								}
							};
							if (iteration.getResult().equals(recursion.getResult())) {
								consecutiveIntegerChecking = new TimeAlgorithm() {
									@Override
									public String algorithm() {
										return "" + consecutiveIntegerChecking(m, n);
									}
									@Override
									public String algorithmName() {
										return c;
									}
								};
								if (recursion.getResult().equals(consecutiveIntegerChecking.getResult())) {
									System.out.println(min);
									String[] result = {m + "," + iteration.getTime() + "," + recursion.getTime() + "," + consecutiveIntegerChecking.getTime() + ((min == size) ? "" : ":"), min + ". GCD(m = " + m + ", n = " + n + ") = " + recursion.getResult() + "\n" + iteration.toString() + "\n" + recursion.toString() + "\n" + consecutiveIntegerChecking.toString() + ((min == size) ? "" : "\n")};
									for (int t = 0; t < result.length; t++) {
										write[t][min-1] = result[t];
									}
									min++; continue;
								} else {
									System.out.println("Recursion result = " + recursion.getResult() + " != Consecutive Integer Checking result = " + consecutiveIntegerChecking.getResult());
								}
							} else {
								System.out.println("Iteration result = " + iteration.getResult() + " != Recursion result = " + recursion.getResult());
							}
							System.exit(0);
						}
					}
					synchronized (write) {
						if (write != null) {
							long start = System.nanoTime();
							for (int w = 0; w < write.length; w++) {
								if (w == 0) {
									String time = null;
									int b = 0;
									while (b < size) {
										time = write[w][b];
										if (time != null) {
											b++; data.write(time, true);
											System.out.println(time + " -> " + b + " -> " + ((System.nanoTime() - start) / 60000000000L) + " minutes");
										}
									}
								} else {
									System.out.println("writing output...");
									data.write(write[w], false);
									System.out.println("done writing");
								}	
							}
							//data.createGraph();
							write = null;
						}
					}
				}
			}).start();	
		}
	}

	public long iteration(long m, long n) {//o(n)
		long r;
		while (n != 0) {
			r = m % n; m = n; n = r;
		}
		return m;
	}
	
	public long recursion(long m, long n) {//o(n)
		return (n == 0) ? m : recursion(n, m % n);
	}
	
	public long consecutiveIntegerChecking(long m, long n) {//o(log n)
		long r = Math.min(m, n); 
		while (m % r != 0 || n % r != 0) {
			r--;
		}
		return r;
	}
}
