package util;

public class Suit {
    // ------------------------ Attributes ------------------------
    private String name;
    private int power;

    // ------------------------ Construtores ------------------------
    public Suit (){}

    public Suit (String name, int power){
        this.name = name;
        this.power = power;
    }

    // ------------------------ Getters ------------------------
    public String getName() {
        return name;}

    public int getPower() {
        return power;}

    // ------------------------ Setters ------------------------
    public void setName(String name) {
        this.name = name;}

    public void setPower(int power) {
        this.power = power;}

    // ------------------------ Method ------------------------
    @Override
    public String toString() {
        return "Suit [name=" + name + ", power=" + power + "]";
    }
}