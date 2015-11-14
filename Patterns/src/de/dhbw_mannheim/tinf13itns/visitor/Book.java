package de.dhbw_mannheim.tinf13itns.visitor;

public class Book implements Item {

    private int price;
    private String isbnNumber;
     
    public Book(int cost, String isbn){
        this.price=cost;
        this.isbnNumber=isbn;
    }
     
    public int getPrice() {
        return price;
    }
 
    public String getIsbnNumber() {
        return isbnNumber;
    }
 
    @Override
    public int accept(ShoppingCartVisitor visitor) {
        return visitor.visit(this);
    }
}
