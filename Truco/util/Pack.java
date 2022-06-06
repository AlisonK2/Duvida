package util;

// Importing library
import java.util.ArrayList;

public class Pack {
    // ------------------------ Attributes ------------------------
    public ArrayList<Card> CLEAN_PACK = setting_CLEAN_PACK();
    public ArrayList<Card> DIRTY_PACK = setting_DIRTY_PACK();
    final static private String[] SUITS_NAMES = {"Club", "Heart", "Spade", "Diamond"};
    final static private String[] SYMBOLS_CLEAN_PACK = {"3", "2", "A", "K", "J", "Q"};
    final static private String[] SYMBOLS_DIRTY_PACK = {"3", "2", "A", "K", "J", "Q", "7", "6", "5", "4"};

    // ------------------------ Construtor ------------------------
    public Pack() {}

    // ------------------------ Getters ------------------------
    public ArrayList<Card> getCLEAN_PACK() {
        return CLEAN_PACK;}

    public ArrayList<Card> getDIRTY_PACK() {
        return DIRTY_PACK;}

    // ------------------------ Setters ------------------------
    public ArrayList<Card> setting_CLEAN_PACK() {
        ArrayList<Card> clean_pack = new ArrayList<>();
        int symbol_power = 100;
        int suit_power = 4;

        for (String symbol : SYMBOLS_CLEAN_PACK){
            for (String suit_name : SUITS_NAMES){
                Suit suit = new Suit(suit_name, suit_power);
                Card new_card = new Card(suit, symbol, symbol_power);
                clean_pack.add(new_card);
                suit_power -= 1;
            }
            symbol_power -= 10;
            suit_power = 4;
        }
        return clean_pack;
    }

    public ArrayList<Card> setting_DIRTY_PACK() {
        ArrayList<Card> dirty_pack = new ArrayList<>();
        int symbol_power = 100;
        int suit_power = 4;

        for (String symbol : SYMBOLS_DIRTY_PACK){
            for (String suit_name : SUITS_NAMES){
                Suit suit = new Suit(suit_name, suit_power);
                Card new_card = new Card(suit, symbol, symbol_power);
                dirty_pack.add(new_card);
                suit_power -= 1;
            }
            symbol_power -= 10;
            suit_power = 4;
        }
        return dirty_pack;
     }
}