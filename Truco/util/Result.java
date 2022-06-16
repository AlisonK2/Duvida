package util;

import java.io.Serializable;

public class Result implements Serializable {
    private int winner;
    private Player player;
    private Computer computer;

    // ------------------------ Construtores ------------------------
    public Result() {}

    public Result(Player player, Computer computer, int winner) {
        this.player = player;
        this.computer = computer;
        this.winner = winner;
    }

    // ------------------------ Getters ------------------------
    public int getWinner() {
        return winner;
    }

    public Player getPlayer() {
        return player;
    }

    public Computer getComputer() {
        return computer;
    }

    // ------------------------ Setters ------------------------
    public void setWinner(int winner) {
        this.winner = winner;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setComputer(Computer computer) {
        this.computer = computer;
    }
}