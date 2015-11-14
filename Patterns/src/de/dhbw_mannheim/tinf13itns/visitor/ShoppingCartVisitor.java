package de.dhbw_mannheim.tinf13itns.visitor;

public interface ShoppingCartVisitor {

	int visit(Book book);
    int visit(Fruit fruit);
}
