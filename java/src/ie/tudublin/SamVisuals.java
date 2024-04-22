package ie.tudublin;

import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;

public class SamVisuals extends PApplet {
    Minim minim;
    AudioPlayer ap;
    AudioBuffer ab;

    float[] lerpedBuffer;
    float smoothedAmplitude = 0;

    VisualShape[] shapes;
    int screen_size = 800;
    boolean sceneActive = true;

    public void settings() {
        size(screen_size, screen_size, P3D);
    }

    public void setup() {
        minim = new Minim(this);
        ap = minim.loadFile("C:\\Users\\Sam O'Connor\\Desktop\\OOP_Project\\MusicVisuals\\java\\data\\The Chemical Brothers - We've Got To Try.mp3", screen_size);
        ap.play();
        ab = ap.mix;
        colorMode(HSB);

        lerpedBuffer = new float[width];
        shapes = new VisualShape[20]; // Allow for up to 20 shapes initially
        for (int i = 0; i < shapes.length; i++) {
            shapes[i] = new VisualShape(random(width), random(height), (int) random(3)); // Initialize with various shape types
        }
    }

    public void draw() {
        background(smoothedAmplitude * 255, 100, 100);

        float sum = 0;
        for (int i = 0; i < ab.size(); i++) {
            sum += abs(ab.get(i));
            lerpedBuffer[i] = lerp(lerpedBuffer[i], ab.get(i), 0.1f);
        }
        float average = sum / ab.size();
        smoothedAmplitude = lerp(smoothedAmplitude, average, 0.2f);

        for (VisualShape shape : shapes) {
            shape.update();
            shape.display();
        }

        if (sceneActive && millis() > 20000) { // Change scenes after 20 seconds
            sceneActive = false;
            changeScene();
        }
    }

    void changeScene() {
        for (int i = 0; i < shapes.length; i++) {
            shapes[i] = new VisualShape(random(width), random(height), (int) random(3)); // Reinitialize with new shape types
        }
    }

    class VisualShape {
        float x, y;
        float size;
        int type;
        float hue;

        VisualShape(float x, float y, int type) {
            this.x = x;
            this.y = y;
            this.size = random(10, 50);
            this.type = type;
            this.hue = random(255);
        }

        void update() {
            x += random(-1, 1) * smoothedAmplitude * 20;
            y += random(-1, 1) * smoothedAmplitude * 20;
            size = lerp(size, size + sin(frameCount * 0.1f) * 20, 0.05f);
        }

        void display() {
            stroke(hue, 255, 255);
            noFill();
            strokeWeight(2);
            switch (type) {
                case 0:
                    ellipse(x, y, size, size);
                    break;
                case 1:
                    rectMode(CENTER);
                    rect(x, y, size, size);
                    break;
                case 2:
                    beginShape();
                    for (int i = 0; i < 6; i++) { // Draw a hexagon
                        float angle = TWO_PI / 6 * i;
                        float vx = x + cos(angle) * size;
                        float vy = y + sin(angle) * size;
                        vertex(vx, vy);
                    }
                    endShape(CLOSE);
                    break;
            }
        }
    }

    public static void main(String[] args) {
        PApplet.main("ie.tudublin.SamVisuals");
    }
}

