package com.johnriggsdev.tictactoex.utils;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

public class BusDriver extends Bus {
    private static Bus bus;

    private BusDriver() {}

    public static Bus getBus() {
        if (bus == null) {
            bus = new Bus(ThreadEnforcer.MAIN);
        }
        return bus;
    }
}