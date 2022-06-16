package util;

import server.Attend;
import java.util.ArrayList;
import java.io.Serializable;

public class Threads implements Serializable {
    private ArrayList<Attend> threads;

    // ------------------------ Construtores ------------------------
    public Threads() {}

    public Threads(ArrayList<Attend> threads) {
        this.threads = threads;
    }

    // ------------------------ Getters ------------------------
    public ArrayList<Attend> getThreads() {
        return threads;
    }

    // ------------------------ Setters ------------------------
    public void setThreads(ArrayList<Attend> threads) {
        this.threads = threads;
    }
}