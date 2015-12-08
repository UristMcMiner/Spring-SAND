package de.dhbw_mannheim.tinf13itns.singleton;

public class DemoSingleton {

	public static void main(String[] args) {
		Singleton singleton = new SingletonImpl();
		singleton.doSomething();
	}

}
