package de.dhbw_mannheim.tinf13itns.singleton;

public class SingletonImpl implements Singleton {
	
	public SingletonImpl() {
		Singleton.getSingleton();
	}
	
	public void doSomething() {
		System.out.println("OK");
	}

}
