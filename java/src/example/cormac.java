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

    int black = 0;

    rain[] rains; // Array to hold rains

    public void settings() {
        size(800, 800);
    }

    boolean changeBackground = false;
    int startTime;

    int mode;


    public void setup() {
        minim = new Minim(this);
        ap = minim.loadFile("The Chemical Brothers - We've Got To Try.mp3");
        ap.play();
        ab = ap.mix;

        centerX = width / 2;
        centerY = height / 2;

        // Initialize array of rain drops
        rains = new rain[125]; // Adjust the number of rain drops as needed
        for (int i = 0; i < rains.length; i++) {
            rains[i] = new rain(random(width), random(0, 500), random(10, 30)); // Random x and y positions, random size
        }


        startTime = millis(); // Record the start time
    }

    public void draw() {
    // Analyze the amplitude of the audio
    float level = ab.level();

    // Check if the amplitude level is above a certain threshold
    if (level > 0.15) {
        changeBackground = true;
    }
    else{
        changeBackground = false;
    }

    // Clear the background
    if (changeBackground) {
        background(0, 0, 80); // Change background to white
    } else {
        background(black); // Keep the background black
    }



    // Draw and update rains
    for (rain rain : rains) {
        rain.update();
        rain.display();
    }

    if (changeBackground) {
        stroke(255, 255, 0);
        fill(255, 255, 0); // Yellow color
        triangle(300,50,400,50,300,400);
        triangle(375,350,450, 350, 350, 700 );
        rect(300, 350,125,50 );

        // Draw waveform with a yellow line responsive to the audio
        stroke(255, 255, 0); // Set stroke colour to yellow
        noFill(); // No fill for the waveform
        beginShape();
        for (int i = 0; i < ab.size() - 1; i += 5) {
            float x = map(i, 0, ab.size(), -width/2, width/2); // Offset by half the width
            float y = map(ab.get(i), -1, 1, height, 0) * level; // Adjust y position based on audio level
            vertex(x + width/2, y + 100); // Offset x back to the center
        }
        endShape();

        stroke(255, 255, 0); // Set stroke color to red for the second waveform
        beginShape();
        for (int i = 0; i < ab.size() - 1; i += 5) {
            float x = map(i, 0, ab.size(), -width/2, width/2); // Offset by half the width
            float y = map(ab.get(i), -1, 1, 0, height) * level; // Adjust y position based on audio level
            vertex(x + width/2, y + height/2); // Offset y by half the height and x back to the center
        }
        endShape();

    }

        // drawing of clouds
        stroke(128, 128, 128);
        fill(120, 120, 120);
        ellipse(0, 0, 250, 150);
        ellipse(400, 0, 250, 150);
        ellipse(250, 0, 400, 100);
        ellipse(600, 0, 350, 200);
        ellipse(800, 0, 200, 250);

        //background drawing
        fill(black);
        stroke(black);
        rect(0,700,800,100);
}

    class rain {
        float x, y; // Position
        float diameter; // Size
        float speed; // Speed of falling

    
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
