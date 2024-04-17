package example;

import ie.tudublin.Visual;
import ie.tudublin.VisualException;

import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;
import processing.core.*;



public class RotatingBands extends Visual {


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
            getAudioPlayer().cue(0);
            getAudioPlayer().play();
            
        }
 
    }

    public void setup()
    {
        colorMode(HSB);
        noCursor();
        m = new Minim(this);
        ap = m.loadFile("heroplanet.mp3"); 
        ap.play();
        
        //setFrameSize(256);

        //startMinim();
        //loadAudio("heroplanet.mp3");
        //getAudioPlayer().play();
        b = ap.mix;
        lightPosition = new PVector(width / 2, height / 2, 200);
        //startListening();
        
    }

    //float radius = 200;

    //float smoothedBoxSize = 0;

    //float rot = 0;

    float y = 400;

    float lerpedAvg = 0;

    public void draw()
    {
        
        colorMode(HSB);
        background(0);
        stroke(bgcolor % 255, 255, 255);




        lights();
        //background(bgcolor % 255, 255, 255);
        background(0);

        // Move light position around the object
        float radius = 200;
        lightPosition.x = width / 2 + cos(angle) * radius;
        lightPosition.y = height / 2 + sin(angle) * radius;

        // Update angle for next frame
        angle += 0.01;
        bgcolor += 0.3;
        lerped = lerp(lerped, y, 0.1f);


        float tot = 0;
        for(int i = 0 ; i < b.size() ; i ++)
        {
            tot += abs(b.get(i));
        }

        float avg = tot / b.size();

        lerpedAvg = lerp(lerpedAvg, avg, 0.1f);

        // Draw the object (sphere)
        translate(width / 2, height / 2);
        rotateY(frameCount * 0.01f);
        rotateX(frameCount * 0.01f);
        fill(200, 50, 50);
        sphereDetail(40);
        sphere(tot);
        

        // Set light position
        pointLight(255, 255, 255, lightPosition.x, lightPosition.y, lightPosition.z);







        //float f = lerp(10, 20, 0.2);

        //float a = lerp(a, b, 0.1f);

        

        float h = height / 2;
        float w = width / (float) b.size(); // Width of each segment
        for(int i = 0 ; i < b.size() ; i ++)
        {
            float hue = map(i, 0, b.size() , 0, 256);
            stroke(bgcolor % 255, 255, 255);
            noFill();
            line(i, h , i , h + b.get(i) * h);                  //waveform
            line(width - i, h , width - i , h + b.get(i) * h); // Draw mirrored line on the opposite side


            line(i, -h , i , -h + b.get(i) * -h); // Draw line on the opposite pole
            line(width - i, -h , width - i , -h + b.get(i) * -h);

            //line(width + 10 + (i * w), h , -10, h + b.get(i) * h); 
           // line(-1- (i * w), h , width + 10, h + b.get(i) * h); 







            // Draw line from left to center
            //line(i * w, h , width / 2 , h + b.get(i) * h); 

            // Draw line from right to center
            //line(width - (i * w), h , width / 2 , h + b.get(i) * h);


            // Draw line from left to center
            //line(i, h , width / 2 , h + b.get(i) * h);

            // Draw line from right to center
            //line(width - i, h , width / 2 , h + b.get(i) * h);
        }

        
        

        /*

        float tot = 0;
        for(int i = 0 ; i < b.size() ; i ++)
        {
            tot += abs(b.get(i));
        }

        float avg = tot / b.size();

        lerpedAvg = lerp(lerpedAvg, avg, 0.1f);
        
        stroke(250, 255, 255);
        circle(h, h, avg * h * 5);

        stroke(200, 255, 255);
        circle(h * 0.5f, h, lerpedAvg * h * 5);

        circle(h, y, 50);
        y += random(-10, 10);

        lerped = lerp(lerped, y, 0.1f);

        stroke(100, 255, 255);
        circle(h + 200, lerped, 50);
        */



        // Drawing code goes here
        
    }
    float lerped = 0;

    float angle = 0;

    }