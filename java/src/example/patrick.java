/**
 * C22394713: Patrick Fahy - scene 2 
 * 
 * Using the loadshape command to import my own 3-d model of a dog which I can then
 * manipulate the same as a primitive 3-d object in processing, this is done using
 * an OBJ file and the rest of the elements are completed using string manipulation
 * and sound buffer manipulation. 
 */



 package example;

import ie.tudublin.Visual;
import processing.core.PFont;
import processing.core.PShape;
import ddf.minim.*;
import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import ddf.minim.analysis.*;
import ddf.minim.effects.*;
import ddf.minim.signals.*;
import ddf.minim.spi.*;
import ddf.minim.ugens.*;




public class patrick extends Visual {

    Minim m;
    AudioInput ai;
    AudioPlayer ap;

    AudioBuffer b;

    BeatDetect beat;

    float[] lerpedBuffer;




    PShape ball;
    float bgcolor = 0;


    float theta;

    String myText = "WE'VE GOT TO TRY";
    PFont myFont;



 public void settings() {
    //size(800, 800, "processing.opengl.PGraphics3D");
    fullScreen(P3D, SPAN);
  }


  public void keyPressed()
    {
        if (key == ' ')
        {
            getAudioPlayer().cue(0);
            getAudioPlayer().play();
            
        }
 
    }

   
 public void setup() {
    colorMode(HSB);
    noCursor();
    m = new Minim(this);
    ap = m.loadFile("The Chemical Brothers - We've Got To Try.mp3"); //java\data\The Chemical Brothers - We've Got To Try.mp3
    ap.play();

    beat = new BeatDetect();
    beat.setSensitivity(400);


    //size(800, 800, P3D);
    myFont = createFont("Arial", 32);
    textFont(myFont);



    ball = loadShape("dog.obj");

    b = ap.mix;



 }
 float y = 400;
 float lerped = 0;
 float lerpedAvg = 0;
 float bufferIndex = 0.0f; // Index for smooth looping within the buffer
 long lastBeatTime = 0; // Stores the time of the last beat detection

 
 public void draw() {
    beat.detect(b);
    // Check if a second has passed since the last beat
    if (millis() - lastBeatTime >= 10) {
        if (beat.isOnset()) {
        background(0); // Change background on beat (once per second)
        }
        lastBeatTime = millis(); // Update last beat time
    }
  
    //background(0);
    lights();
    lerped = lerp(lerped, y, 0.1f);
    
    float tot = 0;

    for(int i = 0 ; i < b.size() *0.8; i ++)
    {
        tot += abs(b.get(i));
    }

    float avg = tot / b.size();

    lerpedAvg = lerp(lerpedAvg, avg, 0.1f);
    
   
    pushMatrix();
    translate(width/2, height/1.5f);
    rotate((-PI));
    rotateY(theta/2);
    scale(2.0f);
    shape(ball);
    popMatrix();

   //fill(0, 255, 0); // Set object to green (flat color)
   //tint(255, 0, 0); // Set object to red
   
   fill(bgcolor % 255, 255, 255, 100); // Adjust transparency as needed
   //stroke(bgcolor % 255, 255, 255);

   pushMatrix();


   float textWidth = textWidth(myText);

    float textHeight = textAscent();
    float spacingX = textWidth + 5; // Adjust spacing as needed (5 is for gap between characters)
    float spacingY = textHeight + 5; // Adjust spacing as needed (5 is for gap between lines)
    translate(width/2, height/1.5f);
    rotateY(theta/2); 


    for (int y = 0; y < height; y += spacingY) {
        for (int x = 0; x < width; x += spacingX) {
            text(myText, x, y);
            text(myText, -x, -y);

        }
    }

    for (int y = 0; y < height; y += spacingY) {
        for (int x = 0; x > 0; x -= spacingX) {
            text(myText, x, y);
            text(myText, -x, -y);

        }
    }




    popMatrix();
    theta += 0.01f + tot/1800;
    bgcolor += 0.3 + tot/2;

   
 }

}