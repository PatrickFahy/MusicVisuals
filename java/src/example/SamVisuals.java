package example;

import ddf.minim.AudioBuffer;
import ddf.minim.analysis.FFT;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;

public class SamVisuals extends PApplet {
    Minim minim;
    AudioPlayer ap;
    AudioBuffer ab;
    FFT fft;

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
        ap = minim.loadFile("java\\data\\The Chemical Brothers - We've Got To Try.mp3", 1024);
        ap.play();
        ab = ap.mix;
        fft = new FFT(ap.bufferSize(), ap.sampleRate());
        colorMode(HSB);

        lerpedBuffer = new float[width];
        shapes = new VisualShape[50];
        for (int i = 0; i < shapes.length; i++) {
            shapes[i] = new VisualShape(random(width), random(height), (int) random(3));
        }
    }

    public void draw() {
        fft.forward(ab);
        float bass = fft.calcAvg(40, 80);
        float treble = fft.calcAvg(5000, 10000);
        background(bass * 900 % 255, 255, treble * 900 % 255);

        float sum = 0;
        for (int i = 0; i < 799; i++) {
            sum += abs(ab.get(i));
            lerpedBuffer[i] = lerp(lerpedBuffer[i], ab.get(i), 0.1f);
        }
        float average = sum / ab.size();
        smoothedAmplitude = lerp(smoothedAmplitude, average, 0.2f);

        for (VisualShape shape : shapes) {
            shape.update(bass);
            shape.display();
        }

        if (sceneActive && millis() > 20000) {
            sceneActive = false;
            changeScene();
        }
    }

    void changeScene() {
        for (int i = 0; i < shapes.length; i++) {
            shapes[i] = new VisualShape(random(width), random(height), (int) random(3));
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

        void update(float bass) {
            x += random(-1, 1) * bass;
            y += random(-1, 1) * bass;
            size = lerp(size, size + sin(frameCount * 0.1f) * bass * 5, 0.05f);
        }

        void display() {
            fill(hue, 255, 255, 200);
            noStroke();
            pushMatrix();
            translate(x, y);
            switch (type) {
                case 0:
                    sphere(size); // 3D sphere instead of ellipse
                    break;
                case 1:
                    box(size, size, size); // 3D box instead of rect
                    break;
                case 2:
                    createHexagon(size); // 3D hexagon
                    break;
            }
            popMatrix();
        }

        void createHexagon(float size) {
            beginShape(TRIANGLE_FAN);
            vertex(0, 0, 0); // center vertex
            for (int i = 0; i <= 6; i++) { // Creating a circular fan
                float angle = TWO_PI / 6 * i;
                vertex(size * cos(angle), size * sin(angle), size); // Adding Z dimension for 3D effect
            }
            endShape(CLOSE);
        }
    }

    public static void main(String[] args) {
        PApplet.main("example.SamVisuals");
    }
}


