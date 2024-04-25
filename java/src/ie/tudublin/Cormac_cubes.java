package ie.tudublin;

import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;

public class Cormac_cubes extends poly {
    Minim minim;
    AudioPlayer ap;
    AudioInput ai;
    AudioBuffer ab;
    float rotationSpeed = 0.0001f; // Speed of rotation
    float sizeMultiplier = 175; // Increase the size multiplier for a larger base size

    public Cormac_cubes(ProjectVisual v){
        super(v);
    }

    @Override
    public void render()
    {
        Comac_cubes_draw();
    }


    public void Comac_cubes_draw() {
        v.background(0);
        v.lights(); // Enable lighting
    
        // Calculate amplitude from audio buffer
        float amplitude = v.ab.level() * sizeMultiplier;
    
        // Translate to the center of the window
        v.translate(v.width / 2, v.height / 2, 0);
    
        // Update and draw cubes
        for (int i = 0; i < v.numCubes; i++) {
            v.cubeZPositions[i] -= v.cubeVelocities[i]; // Move cube towards the camera
    
            drawCube(amplitude, v.cubeXPositions[i], v.cubeYPositions[i], v.cubeZPositions[i], v.cubeColors[i]); // Draw cube at updated position
    
            // Reset cube position if it goes beyond the camera
            if (v.cubeZPositions[i] > 200) {
                v.cubeZPositions[i] = v.random(-500, -3000);
                v.cubeXPositions[i] = v.random((v.width *-1) / 2, v.width / 2);
                v.cubeYPositions[i] = v.random((v.height * -1) / 2, v.height / 2);
                v.cubeVelocities[i] = v.random(0.1f, 1f);
                v.cubeColors[i] = v.color(v.random(255), v.random(255), v.random(255)); // Random color
            }
        }
    }
    
    void drawCube(float size, float x, float y, float z, int cubeColor) {

        for (int i = 0; i < v.numCubes; i++) {
            v.cubeXPositions[i] = v.random((v.width *-1), v.width);
            v.cubeYPositions[i] = v.random((v.height * -1), v.height);
            v.cubeZPositions[i] = v.random(-800, -1500);
            v.cubeVelocities[i] = v.random(1, 5);
            v.cubeColors[i] = v.color(v.random(255), v.random(255), v.random(255)); // Random color
        }

        
        
        // Draw cube with size determined by audio amplitude
        v.pushMatrix(); // Save current transformation matrix
        v.translate(x, y, z); // Translate to position
        v.stroke(cubeColor); // Colour outline for the cubes
        v.fill(cubeColor); // Random fill color
        v.box(size); // Draw cube
        v.popMatrix(); // Restore previous transformation matrix
    }
}
