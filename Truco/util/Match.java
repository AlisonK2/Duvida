package util;

// Importing libraries
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

public class Match {
    // Instantiating variables
    private Card mesa;
    private Card manilha;
    private int game_mode;
    private Double difficulty;
    private Player player = new Player();
    private Computer pc = new Computer();
    private Random random = new Random();
    private Pack pack_instance = new Pack();
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

    public Card getManilha() {
        return manilha;
    }

    // ------------------------ Setters ------------------------
    public void setDifficulty(Double difficulty) {
        this.difficulty = difficulty;}  
    
    public void setGame_mode(int game_mode) {
        this.game_mode = game_mode;}

    public void setRound_pack() {
        this.round_pack = match_pack;}

    public void setMatch_pack(int pack_type) {
        if (pack_type == 1) {
            this.match_pack = pack_instance.getCLEAN_PACK();
            setRound_pack();
        } else {
            this.match_pack = pack_instance.getDIRTY_PACK();
            setRound_pack();
        }
    } 
    
    public void setPlayer(Player player) {
        this.player = player;}

    // ------------------------ Methods ------------------------
    public Hand start_match() {
        embaralhar();
        dar_cartas(player.getHand(), pc.getHand());
        
        return new Hand(mesa, manilha, player.getHand());
    }

    public void embaralhar() {
        for (int i = 0; i < (random.nextInt(10) + 1); i++){
            Collections.shuffle(this.round_pack);
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
        mesa = round_pack.get(6);
        setting_manilha(mesa);

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
                player.setRound_score(player.getRound_score() + 1);
                return 0;
            } else {
                pc.setRound_score(player.getRound_score() + 1);
                return 1;
            }
        } else if (usedCard1.getSymbol() == manilha.getSymbol() || usedCard2.getSymbol() == manilha.getSymbol()) {
            if (usedCard1.getSymbol() == manilha.getSymbol()){
                player.setRound_score(player.getRound_score() + 1);
                return 0;
            } else {
                pc.setRound_score(player.getRound_score() + 1);
                return 1;
            }
        } else {
            if ((usedCard1.getPower() + usedCard1.getSuit().getPower()) > (usedCard2.getPower() + usedCard2.getSuit().getPower())){
                player.setRound_score(player.getRound_score() + 1);
                return 0;
            } else {
                pc.setRound_score(player.getRound_score() + 1);
                return 1;
            }
        }
    }
}