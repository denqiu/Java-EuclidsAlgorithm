package setup;

public abstract class TimeAlgorithm {
	public abstract Object algorithm();
	public abstract String algorithmName();
	private Object result;
	private long timeElapsed;
	
	public TimeAlgorithm() {
		long startTime = System.nanoTime();
		result = algorithm();
		timeElapsed = System.nanoTime() - startTime;
	}
	
	@Override
	public String toString() {
		return algorithmName() + " -> " + timeElapsed + " nanoseconds";
	}
	
	public Object getResult() {
		return result;
	} 
	
	public long getTime() {
		return timeElapsed;
	}
}
