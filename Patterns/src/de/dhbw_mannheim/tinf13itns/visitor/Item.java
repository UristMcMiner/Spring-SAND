package de.dhbw_mannheim.tinf13itns.visitor;

public interface Item {

	public int accept(ShoppingCartVisitor visitor);
}
