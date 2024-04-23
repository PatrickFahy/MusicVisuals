package example;

import ie.tudublin.Visual;
import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;

public class cormac2 extends Visual {
    Minim minim;
    AudioPlayer ap;
    AudioInput ai;
    AudioBuffer ab;
    float rotationSpeed = 0.0001f; // Speed of rotation
    float sizeMultiplier = 200; // Increase the size multiplier for a larger base size

    int numCubes = 500; // Number of cubes (particles)
    float[] cubeXPositions; // X positions of cubes
    float[] cubeYPositions; // Y positions of cubes
    float[] cubeZPositions; // Z positions of cubes
    float[] cubeVelocities; // Velocities of cubes
    int[] cubeColors; // Colors of cubes

    public void settings() {
        size(800, 800, P3D); // Use P3D renderer for 3D
    }

    public void setup() {
        minim = new Minim(this);
        ap = minim.loadFile("The Chemical Brothers - We've Got To Try.mp3");
        ap.play();
        ab = ap.mix;

        cubeXPositions = new float[numCubes]; // Initialize arrays
        cubeYPositions = new float[numCubes];
        cubeZPositions = new float[numCubes];
        cubeVelocities = new float[numCubes];
        cubeColors = new int[numCubes];

        // Randomize initial positions, velocities, and colors
        for (int i = 0; i < numCubes; i++) {
            cubeXPositions[i] = random(-width / 2, width / 2);
            cubeYPositions[i] = random(-height / 2, height / 2);
            cubeZPositions[i] = random(-500, -1000);
            cubeVelocities[i] = random(1, 5);
            cubeColors[i] = color(random(255), random(255), random(255)); // Random color
        }
    }

    public void draw() {
        background(0);
        lights(); // Enable lighting

        // Calculate amplitude from audio buffer
        float amplitude = ab.level() * sizeMultiplier;

        // Translate to the center of the window
        translate(width / 2, height / 2, 0);

        // Update and draw cubes
        for (int i = 0; i < numCubes; i++) {
            cubeZPositions[i] += cubeVelocities[i]; // Move cube towards the camera
            drawCube(amplitude, cubeXPositions[i], cubeYPositions[i], cubeZPositions[i], cubeColors[i]); // Draw cube at updated position

            // Reset cube position if it goes beyond the camera
            if (cubeZPositions[i] > 0) {
                cubeZPositions[i] = random(-500, -1000);
                cubeXPositions[i] = random(-width / 2, width / 2);
                cubeYPositions[i] = random(-height / 2, height / 2);
                cubeVelocities[i] = random(1, 5);
                cubeColors[i] = color(random(255), random(255), random(255)); // Random color
            }
        }
    }

    void drawCube(float size, float x, float y, float z, int cubeColor) {
        // Draw cube with size determined by audio amplitude
        pushMatrix(); // Save current transformation matrix
        translate(x, y, z); // Translate to position
        stroke(cubeColor); // White outline
        fill(cubeColor); // Random fill color
        box(size); // Draw cube
        popMatrix(); // Restore previous transformation matrix
    }
}
