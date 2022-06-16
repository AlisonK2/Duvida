package util;

import java.io.Serializable;

public class InitialData implements Serializable {
    private String name;
    private int game_mode;
    private int pack_type;
    private Double bot_difficulty;
    
    // ------------------------ Construtores ------------------------
    public InitialData() {}

    public InitialData(String name, int game_mode, int pack_type, Double bot_difficulty) {
        this.name = name;
        this.game_mode = game_mode;
        this.pack_type = pack_type;
        this.bot_difficulty = bot_difficulty;
    }

    // ------------------------ Getters ------------------------
    public String getName() {
        return name;
    }

    public int getGame_mode() {
        return game_mode;
    }

    public int getPack_type() {
        return pack_type;
    }

    public Double getBot_difficulty() {
        return bot_difficulty;
    }
    
    // ------------------------ Setters ------------------------
    public void setName(String name) {
        this.name = name;
    }

    public void setGame_mode(int game_mode) {
        this.game_mode = game_mode;
    }

    public void setPack_type(int pack_type) {
        this.pack_type = pack_type;
    }

    public void setBot_difficulty(Double bot_difficulty) {
        this.bot_difficulty = bot_difficulty;
    }   
}