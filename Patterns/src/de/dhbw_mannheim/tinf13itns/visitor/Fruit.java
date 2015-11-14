package de.dhbw_mannheim.tinf13itns.visitor;

public class Fruit implements Item {

    private int pricePerKg;
    private int weight;
    private String name;
     
    public Fruit(int priceKg, int wt, String nm){
        this.pricePerKg=priceKg;
        this.weight=wt;
        this.name = nm;
    }
     
    public int getPricePerKg() {
        return pricePerKg;
    }
 
 
    public int getWeight() {
        return weight;
    }
 
    public String getName(){
        return this.name;
    }
     
    @Override
    public int accept(ShoppingCartVisitor visitor) {
        return visitor.visit(this);
    }
}
