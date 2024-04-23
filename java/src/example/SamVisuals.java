package example;

import ddf.minim.AudioBuffer;
import ddf.minim.analysis.FFT;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;

public class SamVisuals extends PApplet {
    Minim minim; // Variable declared for audio operations like loading files
    AudioPlayer ap; // Controls playback of audiofile
    AudioBuffer ab; // Synchronizes the graphics with the music
    FFT fft; // Manages the frequency spectrum of music

    float[] lerpedBuffer; // Lerping array
    float smoothedAmplitude = 0; // Variable creating smoother visual transitions
    VisualShape[] shapes; // Array that controls the shapes behavior
    int screen_size = 800; // Defines the dimensions of the visulas display window
    int maxWidth = 700; // Maximum width for shape positions
    int maxHeight = 700; // Maximum height for shape positions
    boolean sceneActive = true; // Boolean that controls the visuals activity

    public void settings() { // Sets the size of the window and the rendering mode (3D)
        size(screen_size, screen_size, P3D);
    }

    public void setup() {
        minim = new Minim(this); // Initializes a new Minim audio library with the current code as context
        ap = minim.loadFile("java\\data\\The Chemical Brothers - We've Got To Try.mp3", 1024); // Function to add the song
        ap.play(); // Plays the music
        ab = ap.mix; // Gets the mixed buffer from the audio player
        fft = new FFT(ap.bufferSize(), ap.sampleRate()); // Analysing the song frequency
        colorMode(HSB); // Sets the color mode

        lerpedBuffer = new float[width]; // Array (window width size) to store lerped audio
        shapes = new VisualShape[50]; // sketch will manage 50 shapes
        for (int i = 0; i < shapes.length; i++) { // For loop randomizes the shape type and position
            shapes[i] = new VisualShape(random(maxWidth), random(maxHeight), (int) random(3));
        }
    }

    public void draw() {
        fft.forward(ab);
        float bass = fft.calcAvg(40, 80); // Averages the amplitude of frequencies between 40 and 80 Hz
        float treble = fft.calcAvg(5000, 10000); // "" for treble frequencies
        background(bass * 300 % 255, 255, treble * 300 % 255); // sets background colours

        float sum = 0;
        for (int i = 0; i < 799; i++) { // Max audio size is 800
            sum += abs(ab.get(i)); // Sets the value of each audio sample to sum (positive value)
            lerpedBuffer[i] = lerp(lerpedBuffer[i], ab.get(i), 0.1f); // lerp function calculates between the previous buffer value and the current one
        }
        float average = sum / ab.size(); // Average amplitude
        smoothedAmplitude = lerp(smoothedAmplitude, average, 0.2f); // Smooths the average amplitude to control the visual response to changes in sound level

        for (VisualShape shape : shapes) { // Draws the shapes and updates each shapes state based on the song bass level
            shape.update(bass);
            shape.display();
        }

        if (sceneActive && millis() > 20000) { // Makes each scene no longer than 20 seconds
            sceneActive = false;
            changeScene();
        }
    }

    // Method to regenerate the shapes when a particular condition is met
    void changeScene() {
        for (int i = 0; i < shapes.length; i++) {
            shapes[i] = new VisualShape(random(maxWidth), random(maxHeight), (int) random(3));
        }
    }

    // Creates the shapes
    class VisualShape {
        float x, y;  // Variables for the x and y coordinates of the shape's position
        float size;  // Variable for the size of the shape
        int type;  // Type of shape
        float hue;  // Shape colour
    
        VisualShape(float x, float y, int type) {
            this.x = constrain(x, 0, maxWidth); // Ensures that the x-coordinate doesn't exceed the max width
            this.y = constrain(y, 0, maxHeight); // Ensures that the y-coordinate doesn't exceed the max height
            this.size = random(10, 50); // Sets the shape size to a random value between 10 and 50
            this.type = type; // Sets the type of the shape
            this.hue = random(255); // Sets the colour hue of the shape
        }
    
        // Method to change the shape's position and size based on the bass level of the music
        void update(float bass) {
            x += random(-1, 1) * bass; // Randomly adjusts the coordinates based on the bass (moves shapes)
            y += random(-1, 1) * bass;
            x = constrain(x, 0, maxWidth);
            y = constrain(y, 0, maxHeight); 
            size = lerp(size, size + sin(frameCount * 0.1f) * bass * 5, 0.05f); // Changes the size based on the bass and the frame count (pulsating effect)
        }
    
        // Method to display the shapes
        void display() {
            fill(hue, 255, 255, 200); // Sets the fill color and an alpha value of 200 for transparency.
            noStroke(); // Stops drawing an outline around the shapes
            pushMatrix(); // Saves the current drawing matrix
            translate(x, y); // Translates the matrix to the x and y coordinates
            switch (type) { // Drawing the shapes based on the type
                case 0:
                    sphere(size); // Draws a sphere
                    break;
                case 1:
                    box(size, size, size); // Draws a box with dimensions
                    break;
                case 2:
                    createHexagon(size); // Draws a hexagon
                    break;
            }
            popMatrix(); // Restores the previously saved drawing matrix
        }
    
        // Method to create a hexagon shape
        void createHexagon(float size) {
            beginShape(TRIANGLE_FAN); // Drawing a hexagon defined by connecting triangles radiating from a central vertex.
            vertex(0, 0, 0); // Sets the central vertex
            for (int i = 0; i <= 6; i++) { // Loops to create the six vertices of the hexagon and one to close the shape
                float angle = TWO_PI / 6 * i; // Calculates the angle for each vertex
                vertex(size * cos(angle), size * sin(angle), size); // Sets each vertex at an angle and at a distance based on size
            }
            endShape(CLOSE); // Closes the shape
        }
    }
    

    // Connects to main
    public static void main(String[] args) {
        PApplet.main("example.SamVisuals");
    }
}