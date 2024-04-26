package ie.tudublin;

import processing.core.PApplet;

public class Cormac_lightning extends poly {

    public Cormac_lightning(ProjectVisual v){
        super(v);
    }

    @Override
    public void render()
    {
        Cormac_lightning_draw();
    }

    boolean changeBackground = false;

    public void Cormac_lightning_draw(){
        v.colorMode(PApplet.HSB, 360, 100, 100); // Set color mode to HSB

        v.background(0);
        float level = v.ab.level();

        // Clear the background
        if (changeBackground) {
            v.background(240, 48, 36); // Change background to white
        } else {
            v.background(0); // Keep the background black
        }

        if (level > 0.15) {
            changeBackground = true;
        }
        else{
            changeBackground = false;
        }

        if (changeBackground) {
            v.noStroke();
            v.fill(60, 100, 100); // Yellow color in HSB
            v.triangle(300,50,400,50,300,400);
            v.triangle(375,350,450, 350, 350, 700 );
            v.rect(363, 375,125,50 );
    
            v.triangle(700, 50, 800, 50, 700, 400); // Moved over 400 units
            v.triangle(775, 350, 850, 350, 750, 700); // Moved over 400 units
            v.rect(763, 375, 125, 50); // Moved over 400 units

            v.triangle(1100, 50, 1200, 50, 1100, 400); // Moved over 400 units again
            v.triangle(1175, 350, 1250, 350, 1150, 700); // Moved over 400 units again
            v.rect(1163, 375, 125, 50); // Moved over 400 units again



            // Draw waveform with a yellow line responsive to the audio
            v.stroke(60, 100, 100); // Yellow color in HSB
            v.noFill(); // No fill for the waveform
            v.beginShape();
            for (int i = 0; i < v.ab.size() - 1; i += 5) {
                float x = PApplet.map(i, 0, v.ab.size(), (v.width *-1)/2, v.width/2); // Offset by half the width
                float y = PApplet.map(v.ab.get(i), -1, 1, v.height, 0) * level; // Adjust y position based on audio level
               v.vertex(x + v.width/2, y + 100); // Offset x back to the center
            }
            v.endShape();
    
            v.stroke(60, 100, 100); // Yellow color in HSB for the second waveform
            v.beginShape();
            for (int i = 0; i < v.ab.size() - 1; i += 5) {
                float x = PApplet.map(i, 0, v.ab.size(), (v.width *-1)/2, v.width/2); // Offset by half the width
                float y = PApplet.map(v.ab.get(i), -1, 1, 0, v.height) * level; // Adjust y position based on audio level
                v.vertex(x + v.width/2, y + v.height/2); // Offset y by half the height and x back to the center
            }
            v.endShape();
    
        }

        
    
            // drawing of clouds
            v.stroke(192, 10, 90); // Gray color in HSB
            v.fill(192, 10, 90); // Gray color in HSB
            v.ellipse(0, 0, 250, 150);
            v.ellipse(400, 0, 250, 180);
            v.ellipse(250, 0, 400, 130);
            v.ellipse(600, 0, 350, 200);
            v.ellipse(800, 0, 260, 230);
            v.ellipse(1000, 0, 300, 200);
            v.ellipse(1200, 0, 250, 270);
            v.ellipse(1400, 0, 360, 225);
            v.ellipse(1600, 0, 280, 200);
            v.ellipse(1800, 0, 200, 175);

            // Draw blue lines falling like rain
        v.stroke(240, 100, 70); // Blue color in HSB
        v.strokeWeight(2); // Set the stroke weight
        for (int i = 0; i < v.width; i += 5) {
            float y = PApplet.map(v.random(0, 1), 0, 1, 0, v.height); // Randomize y-coordinate
            v.line(i, y, i + 8, y + 10); // Draw a vertical line
        }
    
            //background drawing
            v.fill(0); // Black color
            v.stroke(0); // Black color
            v.rect(0,800,v.width * 2,200);
    }
}
