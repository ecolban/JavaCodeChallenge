package com.drawmetry.codechallenge.busgossip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Solver {

    public static void main(String[] args) throws IOException {
        InputStream is = Solver.class.getResourceAsStream("input2.txt");
        System.out.println(new Solver().solve(is));
    }

    private Bus[] busses;

    public String solve(InputStream input) throws IOException {
        busses = loadData(input);
        for (int minute = 1; minute <= 480; minute++) {
            if (exchangeGossip()) {
                return "" + minute;
            }
            for (Bus b : busses) {
                b.advance();
            }
        }
        return "never";
    }

    private Bus[] loadData(final InputStream source) throws IOException {
        Bus[] busses;
        int busCount = 0;
        final List<String> routes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(source))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                routes.add(line);
                busCount++;
            }
        }
        busses = new Bus[busCount];
        for (int i = 0; i < busCount; i++) {
            busses[i] = new Bus(i, busCount, routes.get(i));
        }
        return busses;
    }

    private boolean exchangeGossip() {
        boolean completed = true;
        for (int i = 0; i < busses.length; i++) {
            for (int j = i + 1; j < busses.length; j++) {
                if (busses[i].getBusStop().equals(busses[j].getBusStop())) {
                    busses[i].exchangeGossip(busses[j], false);
                }
            }
            completed = completed && busses[i].getCompleted();
        }
        return completed;
    }

}
