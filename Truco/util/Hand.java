package util;

import java.util.ArrayList;
import java.io.Serializable;

public class Hand implements Serializable {
    private Card mesa;
    private Card manilha;
    private String player2name;
    private ArrayList<Card> hand;

    // ------------------------ Construtores ------------------------
    public Hand() {}

    public Hand(Card mesa, Card manilha, ArrayList<Card> hand, String player2name) {
        this.mesa = mesa;
        this.manilha = manilha;
        this.hand = hand;
        this.player2name = player2name;
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

    public String getPlayer2name() {
        return player2name;
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
    
    public void setPlayer2name(String player2name) {
        this.player2name = player2name;
    }
}