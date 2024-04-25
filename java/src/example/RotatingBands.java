/**
 * C22394713: Patrick Fahy - scene 1 
 * 
 * Using the lights() and lighting commands to load a 3-d model primitive of a sphere with a mesh
 * in processing, and then using the Audio/Sound buffer to create waveforms which rotate about the poles
 * of the sphere and I also used Audio/Sound buffer manipulation to control the size/lerping of the
 * sphere.
 * 
 */


 package example;

 import ddf.minim.AudioBuffer;
 import ddf.minim.AudioInput;
 import ddf.minim.AudioPlayer;
 import ddf.minim.Minim;
 import ie.tudublin.Visual;
 import processing.core.PApplet;
 import processing.core.*;
 
 
 
 public class RotatingBands extends Visual {
 
 
     Minim m;
     AudioInput ai;
     AudioPlayer ap;
     
     AudioBuffer b;
 
     float[] lerpedBuffer;
 
 
     PVector lightPosition;
     //float angle = 0;
     float bgcolor = 0;
 
     public void settings()
     {
         //size(800, 800, P3D);
         println("CWD: " + System.getProperty("user.dir"));
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
 
     public void setup()
     {
         colorMode(HSB);
         noCursor();
         m = new Minim(this);
         ap = m.loadFile("The Chemical Brothers - We've Got To Try.mp3"); //java\data\The Chemical Brothers - We've Got To Try.mp3
         ap.play();
         
         //setFrameSize(256);
 
         //startMinim();
         //loadAudio("heroplanet.mp3");
         //getAudioPlayer().play();
         b = ap.mix;
         lightPosition = new PVector(width / 2, height / 2, 200);
         //startListening();
 
         
     }
 
     float y = 400;
     float lerpedAvg = 0;
     float bufferIndex = 0.0f; // Index for smooth looping within the buffer
 
 
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
         for(int i = 0 ; i < b.size() *0.8; i ++)
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
         sphereDetail(30);
         sphere(tot);
         
         // Set light position
         pointLight(255, 255, 255, lightPosition.x, lightPosition.y, lightPosition.z);
 
         float h = height / 2;
         //float w = width / (float) b.size(); // Width of each segment
         for(int i = 0 ; i < width*6; i ++)
         {
             bufferIndex += 0.6f; // Adjust value for line length  +  to get "Speedlines" effect
             float value = b.get((int) Math.floor(bufferIndex) % b.size()); //get buffer values
 
             float hue = map(i, 0, b.size() , 0, 256);
             stroke(bgcolor % 255, 255, 255);
             noFill();
             //line(i, h , i , h + b.get(i) * h);                  //waveform
             //line(width - i, h , width - i , h + b.get(i) * h); // Draw mirrored line on the opposite side
             line(i, h, i, h + value * h); // Top waveform line
             line(width - i, h, width - i, h + value * h); // Mirrored waveform line on the opposite side
 
             //line(i, -h , i , -h + b.get(i) * -h); // Draw line on the opposite pole
             //line(width - i, -h , width - i , -h + b.get(i) * -h); 
             line(i, -h, i, -h + value * -h);
             line(width - i, -h, width - i, -h + value * -h);
 
 
         }
 
     }
     float lerped = 0;
 
     float angle = 0;
 
     }