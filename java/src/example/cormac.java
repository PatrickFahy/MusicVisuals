package example;

import ie.tudublin.Visual;
import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;

public class cormac extends Visual {
    Minim minim;
    AudioPlayer ap;
    AudioInput ai;
    AudioBuffer ab;

    float angle = 0;
    float radius = 0;
    float centerX, centerY;
    float amplitude = 0;

    rain[] rains; // Array to hold rains

    public void settings() {
        size(800, 800);
    }

    boolean changeBackground = false;
    int startTime;


    public void setup() {
        minim = new Minim(this);
        ap = minim.loadFile("The Chemical Brothers - We've Got To Try.mp3");
        ap.play();
        ab = ap.mix;

        centerX = width / 2;
        centerY = height / 2;

        // Initialize array of rains
        rains = new rain[125]; // Adjust the number of rains as needed
        for (int i = 0; i < rains.length; i++) {
            rains[i] = new rain(random(width), -50, random(10, 30)); // Random x position, start above the screen, random size
        }

        startTime = millis(); // Record the start time
    }

    public void draw() {
        // Check if 8 seconds have elapsed
        if (millis() - startTime >= 8000) {
            changeBackground = true;
        }
    
        // Clear the background
        if (changeBackground) {
            background(255); // Change background to white
        } else {
            background(0); // Keep the background black
        }
    
        // Draw and update rains
        for (rain rain : rains) {
            rain.update();
            rain.display();
        }
    
        // drawing of clouds
        stroke(128, 128, 128);
        fill(120, 120, 120);
        ellipse(0, 0, 250, 150);
        ellipse(400, 0, 250, 150);
        ellipse(250, 0, 400, 100);
        ellipse(600, 0, 350, 200);
        ellipse(800, 0, 200, 250);
    
        // Draw yellow rectangle after 8 seconds
        if (changeBackground) {
            fill(255, 255, 0); // Yellow color
            rect(0, 750, 800, 50); // Draw yellow rectangle
        }
    }

    class rain {
        float x, y; // Position
        float diameter; // Size
        float speed; // Speed of falling
        float hue; // Hue of the rain
    
        rain(float x, float y, float diameter) {
            this.x = x;
            this.y = y;
            this.diameter = diameter;
            speed = random(3, 6); // Random falling speed
        }
    
        void update() {
            y += speed; // Move the rain down
            if (y > height + diameter) { // If rain goes below the screen, reset its position
                y = -diameter;
                x = random(width);
            }
        }
    
        void display() {
            stroke(0, 0, 255); // Set stroke color to blue
            strokeWeight(2); // Set stroke weight to make the lines visible
            line(x, y, x + 10, y + diameter); // Draw a vertical line
        }
        
    }
    
    
}
