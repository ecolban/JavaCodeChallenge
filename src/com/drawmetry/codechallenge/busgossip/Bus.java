package com.drawmetry.codechallenge.busgossip;

public class Bus {

    private final int id;
    private boolean[] gossips;
    private final String[] route;
    private boolean completedGossip;
    private boolean delay = false;
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

    public void exchangeGossip(Bus other, boolean includeDelay) {
        boolean exchangeHappened = false;
        if (completedGossip != other.completedGossip) {
            completedGossip = other.completedGossip = true;
            exchangeHappened = true;
        } else if (!completedGossip) {
            boolean complete = true;
            for (int i = 0; i < gossips.length; i++) {
                if (gossips[i] != other.gossips[i]) {
                    gossips[i] = other.gossips[i] = true;
                    exchangeHappened = true;
                }
                complete = complete && gossips[i];
            }
            completedGossip = other.completedGossip = complete;
        }
        if (includeDelay && exchangeHappened) {
            delay = true;
            other.delay = true;
        }
    }

    public void advance() {
        if (delay) {
            delay = false;
        } else {
            busStop = (busStop + 1) % route.length;
        }
    }

    String getBusStop() {
        return route[busStop];
    }

    boolean getCompleted() {
        return completedGossip;
    }

}