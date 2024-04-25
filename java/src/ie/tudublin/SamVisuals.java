package ie.tudublin;

import ddf.minim.AudioBuffer;
import ddf.minim.analysis.FFT;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;

public class SamVisuals extends poly {
    Minim minim; // Audio operations
    AudioPlayer ap; // Controls playback of audiofile
    // AudioBuffer ab; Synchronizes the graphics with the music
    // FFT fft; Manages the frequency spectrum of music

    //float[] lerpedBuffer;
    float smoothedAmplitude = 0; // Variable creating smoother visual transitions
    static VisualShape[] shapes1 = new SamVisuals.VisualShape[50]; // Array that controls the shapes behavior
    int maxWidth = v.width; // Maximum width for shape positions
    int maxHeight = v.height; // Maximum height for shape positions
    boolean sceneActive = true; // Boolean that controls the visuals activity

    public SamVisuals(ProjectVisual v) {
        super(v);
    }

    @Override
    public void render()
    {
        Samdraw();
    }

    public void settings() { // Sets the size of the window and the rendering mode (3D)
        v.size(v.height, v.width, v.P3D);
    }

    public void setup() {
        v.ab = ap.mix; // Gets the mixed buffer from the audio player
        v.fft = new FFT(ap.bufferSize(), ap.sampleRate()); // Analysing the song frequency
        v.colorMode(v.HSB); // Sets the color mode

        v.lerpedBuffer = new float[v.width]; // Array (window width size) to store lerped audio
        //shapes1 = new VisualShape[50]; // Sketch will manage 50 shapes
        for (int i = 0; i < shapes1.length; i++) { // For loop randomizes the shape type and position
            shapes1[i] = new VisualShape(v.random(maxWidth), v.random(maxHeight), (int) v.random(3));
        }
        v.perspective(v.PI/3, (float)v.width/v.height, 0.1f, 5000); // Adjust the field of view
    }

    public void Samdraw() {
        v.fft.forward(v.ab);
        float bass = v.fft.calcAvg(40, 80); // Averages the amplitude of frequencies between 40 and 80 Hz
        float treble = v.fft.calcAvg(5000, 10000); // "" for treble frequencies
        v.background(bass * 300 % 255, 255, treble * 300 % 255); // sets background colours

        // float sum = 0;
        // for (int i = 0; i < 99; i++) { // Max audio size is 800
        //     sum += v.abs(v.ab.get(i)); // Sets the value of each audio sample to sum (positive value)
        //      v.lerpedBuffer[i] = v.lerp(v.lerpedBuffer[i], v.ab.get(i), 0.1f); // Lerp function calculates between the previous buffer value and the current one
        // }
        // float average = sum / v.ab.size(); // Average amplitude
        // smoothedAmplitude = v.lerp(smoothedAmplitude, average, 0.2f); // Smooths the average amplitude to control the visual response to changes in sound level

        for (int i = 0; i < shapes1.length; i++) { // Draws the shapes and updates each shapes state based on the song bass level
            if(shapes1[i] == null){
                break;
            }
            shapes1[i].update(bass);
            shapes1[i].display();
        }

        if (sceneActive && v.millis() > 1000) { // Makes each scene no longer than 1 seconds
            sceneActive = false;
            changeScene();
        }
    }

    // Method to regenerate the shapes when a particular condition is met
    void changeScene() {
        for (int i = 0; i < shapes1.length; i++) {
            shapes1[i] = new VisualShape(v.random(maxWidth), v.random(maxHeight), (int) v.random(3));
        }
    }

    // Creates the shapes
    class VisualShape {
        float x, y, z;  // Variables for the x and y coordinates of the shape's position
        float size;  // Variable for the size of the shape
        int type;  // Type of shape
        float colour;  // Shape colour
    
        VisualShape(float x, float y, int type) {
            this.x = v.constrain(x, 0, maxWidth); // Ensures that the x-coordinate doesn't exceed the max width
            this.y = v.constrain(y, 0, maxHeight); // Ensures that the y-coordinate doesn't exceed the max height
            this.size = v.random(10, 50); // Sets the shape size to a random value between 10 and 50
            this.type = type; // Sets the type of the shape
            this.colour = v.random(255); // Sets the colour of the shape
            this.z = v.random(-200, -50); // Added z-coordinate placing shapes further from the screen
        }
    
        // Method to change the shape's position and size based on the bass level of the music
        void update(float bass) {
            x += v.random(-1, 1) * bass; // Randomly adjusts the coordinates based on the bass (moves shapes)
            y += v.random(-1, 1) * bass;
            x = v.constrain(x, 0, maxWidth);
            y = v.constrain(y, 0, maxHeight);
            z += v.sin(v.frameCount * 0.1f) * bass * 10;
            size = v.lerp(size, size + v.sin(v.frameCount * 0.1f) * bass * 5, 0.05f); // Changes the size based on the bass and the frame count (pulsating effect)
        }
    
        // Method to display the shapes
        void display() {
            v.fill(colour, 255, 255, 200); // Sets the fill colour and a value of 200 for transparency.
            v.noStroke(); // Stops drawing an outline around the shapes
            v.pushMatrix(); // Saves the current drawing matrix
            v.translate(x, y, -size * 2); // Translates the matrix to the x and y coordinates (pushed further back)
            switch (type) { // Drawing the shapes based on the type
                case 0:
                    v.sphere(size); // Draws a sphere
                    break;
                case 1:
                    v.box(size, size, size); // Draws a box with dimensions
                    break;
                case 2:
                    createHexagon(size); // Draws a hexagon
                    break;
            }
            v.popMatrix(); // Restores the previously saved drawing matrix
        }
    
        // Method to create a hexagon shape
        void createHexagon(float size) {
            v.beginShape(v.TRIANGLE_FAN); // Drawing a hexagon defined by connecting triangles radiating from a central vertex.
            v.vertex(0, 0, 0); // Sets the central vertex
            for (int i = 0; i <= 6; i++) { // Loops to create the six vertices of the hexagon and one to close the shape
                float angle = v.TWO_PI / 6 * i; // Calculates the angle for each vertex
                v.vertex(size * v.cos(angle), size * v.sin(angle), size); // Sets each vertex at an angle and at a distance based on size
            }
            v.endShape(v.CLOSE); // Closes the shape
        }
    }
    

    // Connects to main
    public static void main(String[] args) {
        PApplet.main("example.SamVisuals");
    }
}