package ie.tudublin;

import ie.tudublin.Visual;
import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;

public class Cormac_cubes extends poly {
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

    public Cormac_cubes(ProjectVisual v){
        super(v);
    }

    @Override
    public void render()
    {
        Comac_cubes_draw();
    }

    public void settings() {
        v.size(v.width, v.height); // Use P3D renderer for 3D
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
            cubeXPositions[i] = v.random((v.width *-1)/ 2, v.width / 2);
            cubeYPositions[i] = v.random((v.height * -1)/ 2, v.height / 2);
            cubeZPositions[i] = v.random(-500, -1000);
            cubeVelocities[i] = v.random(1, 5);
            cubeColors[i] = v.color(v.random(255), v.random(255), v.random(255)); // Random color
        }
    }

    public void Comac_cubes_draw() {
        v.background(0);
        v.lights(); // Enable lighting

        // Calculate amplitude from audio buffer
        float amplitude = ab.level() * sizeMultiplier;

        // Translate to the center of the window
        v.translate(v.width / 2, v.height / 2, 0);

        // Update and draw cubes
        for (int i = 0; i < numCubes; i++) {
            cubeZPositions[i] += cubeVelocities[i]; // Move cube towards the camera
            drawCube(amplitude, cubeXPositions[i], cubeYPositions[i], cubeZPositions[i], cubeColors[i]); // Draw cube at updated position

            // Reset cube position if it goes beyond the camera
            if (cubeZPositions[i] > 0) {
                cubeZPositions[i] = v.random(-500, -1000);
                cubeXPositions[i] = v.random((v.width *-1) / 2, v.width / 2);
                cubeYPositions[i] = v.random((v.height * -1) / 2, v.height / 2);
                cubeVelocities[i] = v.random(1, 5);
                cubeColors[i] = v.color(v.random(255), v.random(255), v.random(255)); // Random color
            }
        }
    }

    void drawCube(float size, float x, float y, float z, int cubeColor) {
        // Draw cube with size determined by audio amplitude
        v.pushMatrix(); // Save current transformation matrix
        v.translate(x, y, z); // Translate to position
        v.stroke(cubeColor); // White outline
        v.fill(cubeColor); // Random fill color
        v.box(size); // Draw cube
        v.popMatrix(); // Restore previous transformation matrix
    }
}
