package util;

import java.util.ArrayList;
import java.io.Serializable;

public class Hand implements Serializable {
    private Card mesa;
    private Card manilha;
    private ArrayList<Card> hand;

    // ------------------------ Construtores ------------------------
    public Hand() {}

    public Hand(Card mesa, Card manilha, ArrayList<Card> hand) {
        this.mesa = mesa;
        this.manilha = manilha;
        this.hand = hand;
    }

    // ------------------------ Getters ------------------------
    public Card getMesa() {
        return mesa;
    }
    
    public Card getManilha() {
        return manilha;
    }
    
    public ArrayList<Card> getHand() {
        return hand;
    }

    // ------------------------ Setters ------------------------
    public void setMesa(Card mesa) {
        this.mesa = mesa;
    }
    
    public void setManilha(Card manilha) {
        this.manilha = manilha;
    }   
    
    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }
}