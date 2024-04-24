package example;

import ie.tudublin.Visual;
import ie.tudublin.VisualException;
import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;
import processing.core.*;


public class jeff extends Visual {

    Minim m;
    AudioInput ai;
    AudioPlayer ap;
    AudioBuffer b;

    PVector lightPosition;
    float angle = 0;
    float bgcolor = 0;

    float[] lerpedBuffer;
    float y = 0;
    float smoothedY = 0;
    float smoothedAmplitude = 0;
    float lerpedAvg = 0;


    Particle[] particles;
    // Number of particles displayed
    int numParticles = 50; 

    // Particle class
    class Particle 
    {
        PVector position;
        PVector velocity;
        float lifespan;


        Particle(float x, float y, float vx, float vy) 
        {
            position = new PVector(x, y);
            velocity = new PVector(vx, vy);
            lifespan = 30000;
        }

        void update(float tot) 
        {
    
            // Move the particle
            position.add(PVector.mult(velocity,tot/8));

                // Check if the particle is out of the screen
            if (position.x < 0 || position.x > width || position.y < 0 || position.y > height) 
            {
                // Reset particle to exit from the centre cube
                position.x = width/2;
                position.y = height/2;
            }

            // Reduce its lifespan
            lifespan -=  1;
        }

        void display() 
        {
            // Draw the particle
            noStroke();
            fill(200,230, lifespan);
            ellipse(position.x, position.y, 10, 10);
        }
    }

    public void settings()
    {
        size(800, 800, P3D);
        println("CWD: " + System.getProperty("user.dir"));
        //fullScreen(P3D, SPAN);
    }
    
    public void keyPressed()
    {
        if (key == ' ')
        {
            getAudioPlayer().cue(45);
            getAudioPlayer().play();
        }
 
    }
    public void setup()
    {
        colorMode(HSB);
        noCursor();
        m = new Minim(this);
        ap = m.loadFile("The Chemical Brothers - We've Got To Try.mp3");
        ap.play();
        b = ap.mix;

        lightPosition = new PVector(width / 2, height / 2, 200);

        // Initialize the particle
        particles = new Particle[numParticles];
        for (int i = 0; i < numParticles; i++) 
        {
            particles[i] = new Particle(random(width), random(height), random(-1, 1), random(-1, 1));
        }
    }
    float rot = 0;

    //frequency bands
    float[] bands;

    public void draw()
    {
        //adds colour
        colorMode(HSB);
        background(0);
        stroke(bgcolor % 255, 255, 255);

        lights();
        background(0);

        stroke(255);
        lights();
        stroke(map(getSmoothedAmplitude(), 0, 1, 0, 255), 255, 255);
        
        //Finds amplitude of the song
        float amplitude = getSmoothedAmplitude(); 

        float tot = 0;

        // Set light position
        pointLight(255, 255, 255, lightPosition.x, lightPosition.y, lightPosition.z);
 
        float w = width / 2;
        float h = height / 2;
        float spot = 0;

        //calculates the total and average
        for(int i = 0 ; i < b.size() ; i ++)
        {
            tot += abs(b.get(i));
        }
        float avg = tot / b.size();

        // Smooth the amplitude value
        smoothedAmplitude = lerp(smoothedAmplitude, avg, 0.1f);

        // Update and display particles
        for (int j = 0; j < numParticles; j++) 
        {
            particles[j].update(tot);
            particles[j].display();
        }
        
        float cubeSize = map(smoothedAmplitude, 0, 1, 50, 400); // Adjusted base size

        //keeps it centered
        translate(w, h);

        //camera postioning
        for(int i = 0 ; i < b.size() ; i ++)
        {
            //calculates the hue based on the song
            float hue = map(i, 0, b.size(),0,256);


            //rotates camera when buffer is twice the average
            if(b.get(i) > (avg))
            {
                rotateY(frameCount * 0.0004f);
                rotateX(frameCount * 0.002f);

                // Adjust the translation based on amplitude
                float translation = map(amplitude, 0, 1, 0, 50); // Adjust the multiplier as needed
                spot += translation;
                stroke(bgcolor % 255, 255, 255);
                fill(200, 200, 200);
                box(cubeSize);
            }

            translate(spot, spot);
            // Draw the ellipse
            float ellipseX = w * 2;
            float ellipseY = h * 2;
            float ellipseWidth = b.get(i) * h/2;
            float ellipseHeight = h; 

            fill(50, 200, 200);
            stroke(hue,hue,hue);
            ellipse(ellipseX, ellipseY, ellipseWidth, ellipseHeight);

            // Calculate the bottom of the ellipse
            float bottomY = ellipseY + ellipseHeight / 2;

            // Calculate the new point that the line should extend to 
            float extendedBottomY = bottomY + (b.get(i) * h);

            // Draw a line from the bottom of the ellipse
            fill(hue, hue, hue);
            line(ellipseX, bottomY, ellipseX, extendedBottomY);

            // Draw a lines from both sides of the cube
            line(0,0,100,100);
            line(100,100,-100,-100);
        
        }           
    }
    float lerped = 0;
}