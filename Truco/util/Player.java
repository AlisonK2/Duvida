package util;

// Importing library
import java.util.ArrayList;

public class Player {
    // ------------------------ Attributes ------------------------
    private String name;
    private int match_score;
    private int round_score;
    private Card used_card;
    private ArrayList<Card> hand = new ArrayList<>();

    // ------------------------ Construtores ------------------------
    public Player() {}

    public Player (String name){
        this.name = name;
        match_score = 0;
        round_score = 0;
    }

    // ------------------------ Getters ------------------------
    public String getName() {
        return name;}

    public int getMatch_score() {
        return match_score;}

    public int getRound_score() {
        return round_score;}
    
    public ArrayList<Card> getHand() {
        return hand;}
    
    public Card getUsed_card() {
        return used_card;}

    // ------------------------ Setters ------------------------
    public void setName(String name) {
        this.name = name;}

    public void setMatch_score(int match_score) {
        this.match_score = match_score;}

    public void setRound_score(int round_score) {
        this.round_score = round_score;}

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;}

    public void setUsed_card(Card used_card) {
        this.used_card = used_card;}  
}