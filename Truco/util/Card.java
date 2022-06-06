package util;

public class Card implements Comparable <Card>{
    // ------------------------ Attributes ------------------------
    private Suit suit;
    private String symbol;
    private int power;

    // ------------------------ Construtores ------------------------
    public Card() {}

    public Card (Suit suit, String symbol, int power){
        this.suit = suit;
        this.symbol = symbol;
        this.power = power;
    }

    // ------------------------ Getters ------------------------
    public Suit getSuit() {
        return suit;}

    public String getSymbol() {
        return symbol;}

    public int getPower() {
        return power;}
        
    // ------------------------ Setters ------------------------
    public void setSuit(Suit suit) {
        this.suit = suit;}
            
    public void setSymbol(String symbol) {
        this.symbol = symbol;}
            
    public void setPower(int power) {
        this.power = power;}
    
    // ------------------------ Methods ------------------------
    @Override
    public String toString() {
        return "Card [power=" + power + ", suit=" + suit + ", symbol=" + symbol + "]";}

    @Override
    public int compareTo(Card card) {
        if (this.power + this.suit.getPower() > card.power + card.suit.getPower()) { 
            return -1; 
            } if (this.power + this.suit.getPower() < card.power + card.suit.getPower()) { 
            return 1; 
            }
        return 0;
    }
}