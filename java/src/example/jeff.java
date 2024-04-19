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
    //float angle = 0;
    float bgcolor = 0;


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
    float y = 400;
    float lerpedAvg = 0;

    public void draw()
    {
        colorMode(HSB);
        background(0);
        stroke(bgcolor % 255, 255, 255);

        lights();
        background(0);
  
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
 

        float h = height / 2;

        //Width of each segment
        float w = width / (float) b.size(); 
        for(int i = 0 ; i < b.size() ; i ++)
        {
            float hue = map(i, 0, b.size() , 0, 256);
            stroke(bgcolor % 255, 255, 255);
            noFill();
        }

        // Drawing code goes here
        line(200, 200, h/2, w/2);
        circle(tot, h, w);
        
    }
    float lerped = 0;
    float angle = 0;
}