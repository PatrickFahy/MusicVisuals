package ie.tudublin;

import example.Assignment_Test;
import example.MyVisual;
import example.jeff;
import example.CubeVisual;
import example.CubeVisual1;
import example.MyVisual;

import example.RotatingAudioBands;
import example.RotatingBands;
public class Main {

    public void startUI() {
        String[] a = { "MAIN" };
        processing.core.PApplet.runSketch(a, new jeff());
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.startUI();
    }
}