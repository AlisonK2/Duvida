package util;

// Importing libraries
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

public class Computer {
    // ------------------------ Attributes ------------------------
    private Card used_card;
    private int match_score;
    private int round_score;
    Random random = new Random();
    private ArrayList<Card> hand = new ArrayList<>();

    // ------------------------ Construtor ------------------------
    public Computer() {}

    // ------------------------ Getters ------------------------
    public int getMatch_score() {
        return match_score;}

    public int getRound_score() {
        return round_score;}
    
    public Card getUsed_card() {
        return used_card;}

    public ArrayList<Card> getHand() {
        return hand;}

    // ------------------------ Setters ------------------------
    public void setMatch_score(int match_score) {
        this.match_score = match_score;}

    public void setRound_score(int round_score) {
        this.round_score = round_score;}

    public void setUsed_card(Card used_card) {
        this.used_card = used_card;}

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;}

    // ------------------------ Method ------------------------
    public void play(Card player_card, Double difficulty) {
        // Instantiating variables
        int n;
        int i;
        ArrayList<Card> pc_cards = hand;

        Collections.sort(pc_cards);  
        n = Math.random() < difficulty ? 0 : 1;

        if (pc_cards.size() > 2){
            if (n == 0){
                setUsed_card(pc_cards.get(n));
                hand.remove(n);
            } else {
                if (pc_cards.get(2) != null){
                    i = random.nextInt(2) + 1;
                    setUsed_card(pc_cards.get(i));
                    hand.remove(i);
                } else {
                    setUsed_card(pc_cards.get(n));
                    hand.remove(n);
                }
            }
        } else {
            setUsed_card(pc_cards.get(0));
            hand.remove(0);
        }
    }
}
