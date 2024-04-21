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

    public void draw()
    {
        background(0);
        float rotationSpeed = map(smoothedAmplitude, 0.0f, 1.0f, 0.01f, 0.01f); // Map amplitude to rotation speed

        y = height / 2; // Reset y position to center

        // Set the color based on amplitude
        float hue = map(frameCount % 255, 0.0f, 255.0f, 0.0f, 360.0f);
        float saturation = 255;
        float brightness = 255;
        fill(hue, saturation, brightness);
        stroke(hue, saturation, brightness);

        // Draw 3D sphere at the center of the screen
        lights();
        translate(width / 2, height / 2);
        rotateY(frameCount * rotationSpeed);
        rotateX(frameCount * rotationSpeed);    
        sphere(100);


        // Draw waveform lines based on amplitude
        float waveRadius = 200;
        float angleStep = TWO_PI / ab.size();

        for (int i = 0; i < ab.size(); i++) {
            float x = sin(angleStep * i) * waveRadius;
            float y = map(ab.get(i), -2, 2, -100 , 100); // Map amplitude to height around the sphere
            float z = cos(angleStep * i) * waveRadius;
            // Translate the waveform point to start from the surface of the sphere
            float scaleFactor = 1.2f; // Scale factor to extend the lines slightly beyond the sphere's surface
            float newX = x * scaleFactor;
            float newY = y * scaleFactor;
            float newZ = z * scaleFactor;
            // Draw the line from the sphere's center to the translated point
            line(0, 0, 0, newX, newY, newZ);
        }
    }

    // public void draw()
    // {
    //     float average = 0;
    //     float sum = 0;
    //     off += 1;
    //     // Calculate sum and average of the samples
    //     // Also lerp each element of buffer;
    //     for(int i = 0 ; i < ab.size() ; i ++)
    //     {
    //         sum += abs(ab.get(i));
    //         lerpedBuffer[i] = lerp(lerpedBuffer[i], ab.get(i), 0.05f);
    //     }
    //     average= sum / (float) ab.size();

    //     smoothedAmplitude = lerp(smoothedAmplitude, average, 0.1f);
        
    //     //float cx = width / 2;
    //     //float cy = height / 2;

    //     background(0);
    //         float rotationSpeed = map(smoothedAmplitude, 0.0f, 1.0f, 0.01f, 0.01f); // Map amplitude to rotation speed

    //         y = height / 2; // Reset y position to center

    //         //float xTranslate = random(-width/2, width/2);
    //         //float yTranslate = random(-height/2, height/2);


    //         // Set the color based on amplitude
    //         float hue = map(frameCount % 255, 0.0f, 255.0f, 0.0f, 360.0f);
    //         float saturation = 255;
    //         float brightness = 255;
    //         fill(hue, saturation, brightness);
    //         stroke(hue, saturation, brightness);

    //         // Draw 3D sphere at the center of the screen
    //         lights();
    //         translate(width / 2, height / 2);
    //         rotateY(frameCount * rotationSpeed);
    //         rotateX(frameCount * rotationSpeed);    
    //         sphere(100);


    //         // Draw waveform lines based on amplitude
    //         float waveRadius = 200;
    //         float angleStep = TWO_PI / ab.size();

    //         for (int i = 0; i < ab.size(); i++) {
    //             float x = sin(angleStep * i) * waveRadius;
    //             float y = map(ab.get(i), -2, 2, -100 , 100); // Map amplitude to height around the sphere
    //             float z = cos(angleStep * i) * waveRadius;
    //             // Translate the waveform point to start from the surface of the sphere
    //             float scaleFactor = 1.1f; // Scale factor to extend the lines slightly beyond the sphere's surface
    //             float newX = x * scaleFactor;
    //             float newY = y * scaleFactor;
    //             float newZ = z * scaleFactor;
    //             // Draw the line from the sphere's center to the translated point
    //             line(0, 0, 0, newX, newY, newZ);
    //         }
    // }

}