package util;

import java.io.Serializable;

public class Result implements Serializable {
    private int winner;
    private Player player;
    private Player player2_or_computer;

    // ------------------------ Construtores ------------------------
    public Result() {}

    public Result(Player player, Player player2_or_computer, int winner) {
        this.player = player;
        this.player2_or_computer = player2_or_computer;
        this.winner = winner;
    }

    // ------------------------ Getters ------------------------
    public int getWinner() {
        return winner;
    }

    public Player getPlayer() {
        return player;
    }

    public Player getPlayer2_or_computer() {
        return player2_or_computer;
    }

    // ------------------------ Setters ------------------------
    public void setWinner(int winner) {
        this.winner = winner;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setplayer2_or_computer(Player player2_or_computer) {
        this.player2_or_computer = player2_or_computer;
    }
}