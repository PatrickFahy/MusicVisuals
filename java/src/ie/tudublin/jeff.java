package ie.tudublin;

import ie.tudublin.Visual;
import ie.tudublin.VisualException;
import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.*;


public class jeff extends poly {


   public jeff(ProjectVisual v)
   {
       super(v);
   }

    @Override
    public void render()
    {
        //v.background(0);
        v.noStroke();
        //v.lights();        
        //function calls 
        cube();
    }

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
            if (position.x < 0 || position.x > v.width || position.y < 0 || position.y > v.height) 
            {
                // Reset particle to exit from the centre cube
                position.x = v.width/2;
                position.y = v.height/2;
            }

            // Reduce its lifespan
            lifespan -=  1;
        }

        void display() 
        {
            // Draw the particle
            v.noStroke();
            v.fill(200,230, lifespan);
            v.ellipse(position.x, position.y, 10, 10);
        }
    }

    // public void settings()
    // {
    //     v.size(800, 800);
    //     v.println("CWD: " + System.getProperty("user.dir"));
    //     //fullScreen(P3D, SPAN);
    // }
    
    // public void setup()
    // {
    //     // colorMode(HSB);
    //     v.noCursor();
    //     m = new Minim(this);
    //     ap = m.loadFile("The Chemical Brothers - We've Got To Try.mp3");
    //     ap.play();
    //     b = ap.mix;

    //     lightPosition = new PVector(v.width / 2,v.height / 2, 200);

    //     // Initialize the particle
    //     particles = new Particle[numParticles];
    //     for (int i = 0; i < numParticles; i++) 
    //     {
    //         particles[i] = new Particle(v.random(v.width), v.random(v.height), v.random(-1, 1), v.random(-1, 1));
    //     }
    // }
    float rot = 0;

    //frequency bands
    float[] bands;

    public void cube()
    {
        v.background(0);
        v.stroke(bgcolor % 255, 255, 255);

        v.lights();
        v.background(0);

        v.stroke(255);
        v.lights();
        v.stroke(v.map(v.getSmoothedAmplitude(), 0, 1, 0, 255), 255, 255);
        
        //Finds amplitude of the song
        float amplitude = v.getSmoothedAmplitude(); 

        float tot = 0;

        // Set light position
        v.pointLight(255, 255, 255, lightPosition.x, lightPosition.y, lightPosition.z);
 
        float w = v.width / 2;
        float h = v.height / 2;
        float spot = 0;

        //calculates the total and average
        for(int i = 0 ; i < v.ap.mix.size() ; i ++)
        {
            tot += v.abs(v.ap.mix.get(i));
        }
        float avg = tot / v.ap.mix.size();

        // Smooth the amplitude value
        smoothedAmplitude = v.lerp(smoothedAmplitude, avg, 0.1f);

        // Update and display particles
        for (int j = 0; j < numParticles; j++) 
        {
            particles[j].update(tot);
            particles[j].display();
        }
        
        float cubeSize = v.map(smoothedAmplitude, 0, 1, 50, 400); // Adjusted base size

        //keeps it centered
        v.translate(w, h);

        //camera postioning
        for(int i = 0 ; i < v.ap.mix.size() ; i ++)
        {
            //calculates the hue based on the song
            float hue = v.map(i, 0, v.ap.mix.size(),0,256);


            //rotates camera when buffer is twice the average
            if(v.ap.mix.get(i) > (avg))
            {
                v.rotateY(v.frameCount * 0.0004f);
                v.rotateX(v.frameCount * 0.002f);

                // Adjust the translation based on amplitude
                float translation = v.map(amplitude, 0, 1, 0, 50); // Adjust the multiplier as needed
                spot += translation;
                v.stroke(bgcolor % 255, 255, 255);
                v.fill(200, 200, 200);
                v.box(cubeSize);
            }

            v.translate(spot, spot);
            // Draw the ellipse
            float ellipseX = w * 2;
            float ellipseY = h * 2;
            float ellipseWidth = v.ap.mix.get(i) * h/2;
            float ellipseHeight = h; 

            v.fill(50, 200, 200);
            v.stroke(hue,hue,hue);
            v.ellipse(ellipseX, ellipseY, ellipseWidth, ellipseHeight);

            // Calculate the bottom of the ellipse
            float bottomY = ellipseY + ellipseHeight / 2;

            // Calculate the new point that the line should extend to 
            float extendedBottomY = bottomY + (v.ap.mix.get(i) * h);

            // Draw a line from the bottom of the ellipse
            v.fill(hue, hue, hue);
            v.line(ellipseX, bottomY, ellipseX, extendedBottomY);

            // Draw a lines from both sides of the cube
            v.line(0,0,100,100);
            v.line(100,100,-100,-100);
        
        }           
    }
    float lerped = 0;
}