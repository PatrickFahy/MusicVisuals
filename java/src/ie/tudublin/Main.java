package ie.tudublin;

import example.Assignment_Test;
/*
import example.CubeVisual;
import example.CubeVisual1;
import example.MyVisual;
import example.RotatingAudioBands;
<<<<<<< HEAD
import example.RotatingBands;

=======
*/
>>>>>>> rubens-music-branch

public class Main {

    public void startUI() {
        String[] a = { "MAIN" };
<<<<<<< HEAD
        processing.core.PApplet.runSketch(a, new RotatingBands());
=======
        processing.core.PApplet.runSketch(a, new Assignment_Test());
>>>>>>> rubens-music-branch
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.startUI();
    }
}