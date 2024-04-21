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

    //camera settings Not used yet
    float cameraX = 0;
    float cameraY = 0;
    float cameraZ = 0;



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

        rot += getAmplitude() / 8.0f; // increase rotation based on song
        
        rotateY(rot);

        bands = getSmoothedBands(); // Frequency bands

        // Move light position around the object
        // float radius = 200;
        // lightPosition.x = width / 2  + cos(angle) * radius;
        // lightPosition.y = height / 2 + sin(angle) * radius;
  
        // Update angle for next frame
        angle += 0.01;
        bgcolor += 0.3;
        lerped = lerp(lerped, y, 0.1f);

        //calculates the total
        float tot = 0;

        for(int i = 0 ; i < b.size() ; i ++)
        {
            tot += abs(b.get(i));
        }

        float avg = tot / b.size();

        lerpedAvg = lerp(lerpedAvg, avg, 0.1f);

        // Set light position
        pointLight(255, 255, 255, lightPosition.x, lightPosition.y, lightPosition.z);
 
        float w = width / 2;
        float h = height / 2;
        float spot = 0;


        for(int i = 0 ; i < b.size() ; i ++)
        {
            float hue = map(i, 0, b.size() , 0, 256);
            stroke(bgcolor % 255, 255, 255);
            noFill();

            //line(i, h , i , h + b.get(i) * h); //waveform
            //line(i, h , i ,b.get(i) * h + h);
            // fill(0, 200, 200);
            // Draw box with size based on amplitude
            fill(200, 200, 200);
            box(90);
            ellipse(w, h/3,b.get(i)*h, h);
            //rect(w,w,b.get(i) * h, h); // Draw a rectangle

            //rotates camera when buffer is twice the average
            if(b.get(i) > (avg))
            {
                rotateY(frameCount * 0.001f);
                rotateX(frameCount * 0.001f);
                spot += 15;
                translate(spot,spot);
            }
        line(w, i , h, h + b.get(i) * h); //waveform

        // Draw a small sphere at the bottom of the line
        float sphereX = w; // X-coordinate of the sphere
        float sphereY = h + b.get(i) * h; // Y-coordinate
        float sphereDiameter = 15; // Diameter of the sphere
        ellipse(sphereX, sphereY, sphereDiameter, sphereDiameter); // Draw the sphere
        }
        // Drawing code goes here  
        
    }
    float lerped = 0;
}