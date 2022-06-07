package util;

// Importing libraries
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

public class Match {
    // Instantiating variables
    private int game_mode;
    private Double difficulty;
    Player player = new Player();
    Computer pc = new Computer();
    Pack pack_instance = new Pack();
    private Random random = new Random();
    private ArrayList<Card> match_pack = new ArrayList<>();
    private ArrayList<Card> round_pack = new ArrayList<>();

    // ------------------------ Construtor ------------------------
    public Match() {}

    // ------------------------ Getters ------------------------
    public Double getDifficulty() {
        return difficulty;}

    public int getGame_mode() {
        return game_mode;}

    public ArrayList<Card> getMatch_pack() {
        return match_pack;}

    public ArrayList<Card> getRound_pack() {
        return round_pack;}

    public Player getPlayer() {
        return player;}

    public Computer getPc() {
        return pc;}

    // ------------------------ Setters ------------------------
    public void setDifficulty(Double difficulty) {
        this.difficulty = difficulty;}  
    
    public void setGame_mode(int game_mode) {
        this.game_mode = game_mode;}

    public void setRound_pack() {
        this.round_pack = match_pack;}

    public void setMatch_clean_pack() {
        this.match_pack = pack_instance.getCLEAN_PACK();} 

    public void setMatch_dirty_pack() {
        this.match_pack = pack_instance.getDIRTY_PACK();} 
    
    public void setPlayer(Player player) {
        this.player = player;}

    // ------------------------ Methods ------------------------
    public void embaralhar() {
        for (int i = 0; i < (random.nextInt(10) + 1); i++){
            Collections.shuffle(round_pack);
        }
    }

    public void dar_cartas(ArrayList<Card> player_hand, ArrayList<Card> computer_hand) {
        for (int i = 0; i < 6; i++){
            if (i % 2 == 0){
                player_hand.add(round_pack.get(i));
            } else {
                computer_hand.add(round_pack.get(i));
            }
        }

        player.setHand(player_hand);
        pc.setHand(computer_hand);
    }

    public Card setting_manilha(Card mesa) {
        int k = 0;
        boolean n = true;
        Card manilha = new Card();

        do {
            if (match_pack.get(k).getPower() == (mesa.getPower() + 10)){
                manilha = match_pack.get(k);
                n = false;
            }
            
            k++;
        } while (n == true);

        return manilha;
    }
    
    public int who_is_the_winner (Card usedCard1, Card usedCard2, Card manilha){
        if (usedCard1.getSymbol() == manilha.getSymbol() && usedCard2.getSymbol() == manilha.getSymbol()){
            if ((usedCard1.getPower() + usedCard1.getSuit().getPower()) > (usedCard2.getPower() + usedCard2.getSuit().getPower())){
                return 1;
            } else {
                return 2;
            }
        } else if (usedCard1.getSymbol() == manilha.getSymbol() || usedCard2.getSymbol() == manilha.getSymbol()) {
            if (usedCard1.getSymbol() == manilha.getSymbol()){
                return 1;
            } else {
                return 2;
            }
        } else {
            if ((usedCard1.getPower() + usedCard1.getSuit().getPower()) > (usedCard2.getPower() + usedCard2.getSuit().getPower())){
                return 1;
            } else {
                return 2;
            }
        }
    }
}