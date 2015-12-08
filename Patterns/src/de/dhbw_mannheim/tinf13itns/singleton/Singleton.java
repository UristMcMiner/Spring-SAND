package de.dhbw_mannheim.tinf13itns.singleton;

public interface Singleton {

	public static Singleton getSingleton() {
		return SingletonCreator.getInstance();
	};
	
	public abstract void doSomething();
//	default public void doSomething() {
//		SingletonCreator.getInstance().doSomething();
//	}

	
}
