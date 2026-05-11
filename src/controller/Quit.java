package controller;

import util.Operation;

public class Quit implements Operation {

    public Quit() {
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}
