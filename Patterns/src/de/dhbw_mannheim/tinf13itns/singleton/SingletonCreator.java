package de.dhbw_mannheim.tinf13itns.singleton;

public class SingletonCreator implements Singleton {
	
	private static Singleton instance = new SingletonCreator(); 

	public static Singleton getInstance() {
		return instance;
	}

	@Override
	public void doSomething() {
		// TODO Auto-generated method stub
		
	}
}
