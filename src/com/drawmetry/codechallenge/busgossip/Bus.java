package com.drawmetry.codechallenge.busgossip;

public class Bus {

    private final int id;
    private boolean[] gossips;
    private final String[] route;
    private boolean completedGossip;
    private int busStop = 0;

    public Bus(int id, int busCount, String route) {
        this.id = id;
        this.route = route.split("\\s+");
        gossips = new boolean[busCount];
        gossips[id] = true;
        completedGossip = busCount == 1;
    }

    public int getId() {
        return id;
    }

    public void exchangeGossip(Bus other) {
        if (completedGossip != other.completedGossip) {
            completedGossip = other.completedGossip = true;
        } else if (!completedGossip) {
            boolean complete = true;
            for (int i = 0; i < gossips.length; i++) {
                if (gossips[i] != other.gossips[i]) {
                    gossips[i] = other.gossips[i] = true;
                }
                complete = complete && gossips[i];
            }
            completedGossip = other.completedGossip = complete;
        }
    }

    public void advance() {
        busStop = (busStop + 1) % route.length;
    }

    String getBusStop() {
        return route[busStop];
    }

    boolean getCompleted() {
        return completedGossip;
    }

}