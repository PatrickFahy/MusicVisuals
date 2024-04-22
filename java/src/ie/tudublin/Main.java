package ie.tudublin;

import example.Assignment_Test;
import example.MyVisual;
import example.CubeVisual;
import example.CubeVisual1;
import example.MyVisual;
import example.jeff;
import example.RotatingAudioBands;
import example.RotatingBands;
import example.Cormac;
public class Main {

    public void startUI() {
        String[] a = { "MAIN" };
        processing.core.PApplet.runSketch(a, new Cormac());
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.startUI();
    }
}