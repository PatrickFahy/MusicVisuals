package ie.tudublin;

import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;
import processing.core.PVector;

public class Assignment_Test extends poly {
    Minim minim;
    AudioPlayer ap;
    AudioInput ai;

    int mode = 0;

    float y = 0;
    float smoothedY = 0;
    float smoothedAmplitude = 0;

    Circle[] shapes = new Circle[18];
    Square theVoid = new Square();



    int screen_size = 600;
    int lastSpawnTime = 0; // Track the time since last circle spawn

    boolean scene1 = true; // Control variable for the initial scene
    boolean scene2 = false; // Control variable for the initial scene

    public Assignment_Test(ProjectVisual v){
        super(v);
    }

    @Override
    public void render()
    {
        RubenDraw();
    }


    public void settings() {
        v.size(screen_size, screen_size);
        //fullScreen(P3D, SPAN);
    }

    public void setup() {
        v.ab = ap.mix;
        v.colorMode(v.HSB);

        y = v.height / 2;
        smoothedY = y;


        // Initialize the shapes array with a specific size
        shapes[0] = new Circle();
        lastSpawnTime = v.millis(); // Initialize the last spawn time
    }

    float lerpedAvg = 0;

    public void RubenDraw() {
        v.background(0);

        // Check if it's time to spawn a new circle
        if (v.millis() - lastSpawnTime >= 500) { // Spawn every 4 seconds
            addCircle();
            lastSpawnTime = v.millis(); // Update last spawn time
        }


        // Calculate tempo based on amplitude
        float tempo = v.map(smoothedAmplitude, 0, 1, 0.1f, 5); // Map amplitude to tempo range

        lerpedAvg = v.lerp(lerpedAvg, (float)v.calculateAverageAmplitude(), 0.3f);

        if (v.millis() > 18000 && !theVoid.expanding) {
            theVoid.expanding = true; // Start expanding the square
            theVoid.startTime = v.millis(); // Record the start time
        }

        if (theVoid.expanding) {

            if (v.millis() - theVoid.startTime >= theVoid.duration) {
                scene1 = false; // Set flag to switch to the new scene
                scene2 = true;
            }
        }

        if (scene1) {
            drawScene1(tempo); // Draw the initial scene with circles and squares
        } 
        else if(scene2){
            drawScene2(tempo);
        }
        else {
            v.background(0); // Set background to black
        }
    }

    void drawScene1(float tempo) {

        drawHole();
        // Move and draw circles
        for (int i = 0; i < shapes.length; i++) {
            if (shapes[i] == null) {
                break;
            }
            shapes[i].move(tempo);
            shapes[i].draw();
        }
        if(theVoid.expanding){
            theVoid.expand();
        }
    }

    void drawScene2(float tempo){
        drawHole(v.width/3, v.height/4);
        drawHole(v.width/3*2, v.height/4);
        drawWaveForm(v.height/3*2);
    }

    // Method to draw circles
    void drawHole() {
        for (int i = 0; i < v.ab.size(); i += 10) {
            // Interpolate the hue value
            float hue = v.map(i, 0, v.ab.size(), 0, 256);
            v.stroke(hue, 255, 255);
            v.noFill();

            // Draw circles using lerpedAvg for size
            v.circle(v.width / 2, v.height / 2, v.ab.get(i) * lerpedAvg * screen_size / 2 * 6);
        }
    }

    void drawHole(float centerX, float centerY) {
        for (int i = 0; i < v.ab.size(); i += 10) {
            // Interpolate the hue value
            float hue = v.map(i, 0, v.ab.size(), 0, 256);
            v.stroke(hue, 255, 255);
            v.noFill();

            // Draw circles using lerpedAvg for size
            v.circle(centerX, centerY, v.ab.get(i) * lerpedAvg * screen_size / 2 * 6);
        }
    }

    void drawWaveForm(float pos){
        for(int i = 0; i < v.ab.size(); i++){
            float hue = v.map(i, 0, v.ab.size(), 0, 256);
            v.stroke(hue, 255, 255);
            v.line(i, pos, i, pos + v.ab.get(i) * pos * lerpedAvg);
        }
    }

    // Method to add a new circle to the next available slot in the array
    void addCircle() {
        for (int i = 0; i < shapes.length; i++) {
            if (shapes[i] == null) {
                shapes[i] = new Circle();
                break;
            }
        }
    }

    class Circle {
        float centerx;
        float centery;
        float radius = 100f;
        float speedX;
        float speedY;
        float hue; 

        Circle() {
            int side = (int) v.random(1, 4.9f); // left = 1, up = 2, right = 3, down = 4
            if (side == 1) {
                centerx = 0;
                centery = v.random(0, v.height);
            } else if (side == 2) {
                centerx = v.random(0, v.width);
                centery = 0;
            } else if (side == 3) {
                centerx = v.width;
                centery = v.random(0, v.height);
            } else {
                centerx = v.random(0, v.width);
                centery = v.height;
            }
            speedX = (float) (v.width / 2 - centerx) * 0.01f;
            speedY = (float) (v.height / 2 - centery) * 0.01f;
            hue = v.random(256); // Assign random hue value
        }

        void move(float tempo) {
            centerx += speedX * (tempo);
            centery += speedY * (tempo);
        }

        void draw() {
            // Calculate distance from center
            float distanceFromCenter = v.dist(centerx, centery, v.width / 2, v.height / 2);

            // Map the distance to a radius range (reverse the values)
            float mappedRadius = v.map(distanceFromCenter, 0, v.width / 2, 0, 100);

            // Draw the circle with the mapped radius
            v.fill(hue, 255, 255);
            v.ellipse(centerx, centery, mappedRadius * 2, mappedRadius * 2);

            // Remove the circle if it reaches the center
            if (distanceFromCenter <= 5) {
                removeCircle(this);
            }
        }
    }

    class Square {
        float startX;
        float startY;
        float endX;
        float endY;
        float duration = 2000; // 10 seconds duration for expansion
        float startTime;
        boolean expanding = false;

        Square() {
            startX = v.width / 2;
            startY = v.height / 2;
            endX = v.width / 2;
            endY = v.height / 2;
        }
        

        void expand() {
            float elapsedTime = v.millis() - startTime;

            float expansionFactor = v.map(elapsedTime, 0, duration, 0, 1);

            // Calculate the change in width and height from the center
            float changeX = (v.width / 2) * expansionFactor;
            float changeY = (v.height / 2) * expansionFactor;

            // Calculate the center position of the square
            float centerX = v.width / 2;
            float centerY = v.height / 2;

            // Adjust the start and end points equally from the center
            startX = centerX - changeX;
            startY = centerY - changeY;
            endX = centerX + changeX;
            endY = centerY + changeY;

            // Set the fill color to black
            v.fill(0);

            // Draw the square with the calculated center
            v.rect(startX, startY, endX - startX, endY - startY);
        }
    }

    // Method to remove a circle from the array
    void removeCircle(Circle circle) {
        for (int i = 0; i < shapes.length; i++) {
            if (shapes[i] == circle) {
                // Remove the circle
                shapes[i] = null;

                // Shift subsequent circles one position back
                for (int j = i; j < shapes.length - 1; j++) {
                    shapes[j] = shapes[j + 1];
                }
                shapes[shapes.length - 1] = null; // Clear the last element

                break; // Exit the loop once the circle is removed
            }
        }
    }

}