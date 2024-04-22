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
    int numParticles = 100; 

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
            lifespan = 3000;
        }

        void update(float amplitude) 
        {
            float speed = map(amplitude, 0, 1, -2, 2);
            // Move the particle
            position.add(PVector.mult(velocity,speed));

            // Reduce its lifespan
            lifespan -= 1;
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

        // increase rotation based on song
        rot += getAmplitude() / 20.0f; 
        
        // Frequency bands
        bands = getSmoothedBands();

        //Finds amplitude of the song
        float amplitude = getSmoothedAmplitude(); 

  
        // Update angle for next frame
        // angle += 0.01;
        // bgcolor += 0.3;
        // lerped = lerp(lerped, y, 0.1f);

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
        lerpedAvg = lerp(lerpedAvg, avg, 0.1f);

        // Update and display particles
        for (int j = 0; j < numParticles; j++) 
        {
            particles[j].update(amplitude);
            particles[j].display();
        }

        //camera postioning
        for(int i = 0 ; i < b.size() ; i ++)
        {
            //rotates camera when buffer is twice the average
            if(b.get(i) > (avg))
            {
                translate(width /2 ,height /2);
                rotateY(frameCount * 0.003f);
                rotateX(frameCount *0.003f);

                // Adjust the translation based on amplitude
                float translationFactor = map(amplitude, 0, 1, 0, 50); // Adjust the multiplier as needed
                spot += translationFactor;
                translate(spot, spot);


                // spot += 10;
                // translate(spot,spot);
            }

            //Drawing code goes here
            stroke(bgcolor % 255, 255, 255);

            // Draw boxes
            fill(200, 200, 200);
            box(60);

            fill(50, 200, 200);

            // Draw the ellipse
            float ellipseX = w * 2;
            float ellipseY = h * 2;
            float ellipseWidth = b.get(i) * h;
            float ellipseHeight = h; 

            ellipse(ellipseX, ellipseY, ellipseWidth, ellipseHeight);

            // Calculate the bottom of the ellipse
            float bottomY = ellipseY + ellipseHeight / 2;

            // Calculate the new point that the line should extend to 
            float extendedBottomY = bottomY + (b.get(i) * h);

            // Draw a line from the bottom of the ellipse
            line(ellipseX, bottomY, ellipseX, extendedBottomY);
        

            // Draw a small circle at the bottom of the line
            float circleDiameter = 30;
            stroke(100, 255, 255);
            circle(ellipseX, extendedBottomY, circleDiameter);
        }           
        
    }
    float lerped = 0;
}