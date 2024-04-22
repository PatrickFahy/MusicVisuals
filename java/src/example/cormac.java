package example;

import ie.tudublin.Visual;
import ie.tudublin.VisualException;
import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;
import processing.core.*;

public class cormac extends Visual{
    Minim minim;
    AudioPlayer ap;
    AudioInput ai;
    AudioBuffer ab;

    float[] lerpedBuffer;
    float y = 0;
    float smoothedY = 0;
    float smoothedAmplitude = 0;


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
        minim = new Minim(this);
        ap = minim.loadFile("The Chemical Brothers - We've Got To Try.mp3");
        ap.play();
        
        ab = ap.mix;
        colorMode(HSB);
        
        y = height /2;
        smoothedY =y;

        lerpedBuffer = new float[width];
    }

    float off = 0;

    

    public void draw() {
        background(0);
        calculateAverageAmplitude();
        
        // Scale the cube size based on the smoothed amplitude
        float cubeSize = map(smoothedAmplitude, 0, 1, 70, 300); // Adjusted base size
        
        // Map amplitude to hue for color change
        float hue = map(smoothedAmplitude, 0, 1, 0, 255);
        
        // Set fill color based on the mapped hue
        fill(hue, 255, 255);
        
        // Draw the cube in the center of the screen
        translate(width / 2, height / 2, 0);
        rotateX(frameCount * 0.01f);
        rotateY(frameCount * 0.01f);
        box(cubeSize);
    }
    

    public void calculateAverageAmplitude() {
        float sum = 0;
        int numSamples = 1000; // Number of samples to average over
        
        // Calculate the sum of the amplitudes
        for (int i = 0; i < numSamples; i++) {
            sum += Math.abs(ab.get(i));
        }
        
        // Calculate the average amplitude
        float average = sum / numSamples;
        
        // Smooth the amplitude value
        smoothedAmplitude = lerp(smoothedAmplitude, average, 0.1f);
    }
    
    

    }