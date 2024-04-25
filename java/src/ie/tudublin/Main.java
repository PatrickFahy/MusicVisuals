package ie.tudublin;

import example.patrick;

public class Main {

    public void startUI() {
        String[] a = { "MAIN" };
        processing.core.PApplet.runSketch(a, new patrick());
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.startUI();
    }
}