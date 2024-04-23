/**
 * Load and Display an OBJ Shape. 
 * 
 * The loadShape() command is used to read simple SVG (Scalable Vector Graphics)
 * files and OBJ (Object) files into a Processing sketch. This example loads an
 * OBJ file of a rocket and displays it to the screen. 
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
import ddf.minim.analysis.FFT;

public class patrick1 extends Visual {

    Minim m;
    AudioInput ai;
    AudioPlayer ap;

    AudioBuffer b;

    float[] lerpedBuffer;




    PShape ball;
    float bgcolor = 0;

    float ry;
    float theta;

    String myText = "WE'VE GOT TO TRY";
    PFont myFont;



 public void settings() {
    size(800, 800, "processing.opengl.PGraphics3D");
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
 
 public void draw() {
   background(0);
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
   rotate(-PI);
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
    theta += 0.01f + tot/1250;
    bgcolor += 0.3 + tot;

   
 }

}