/**
 * C22394713: Patrick Fahy - scene 2 
 * 
 * Using the loadshape command to import my own 3-d model of a dog which I can then
 * manipulate the same as a primitive 3-d object in processing, this is done using
 * an OBJ file and the rest of the elements are completed using string manipulation
 * and sound buffer manipulation. 
 */
 package ie.tudublin;

 import processing.core.PApplet;

public class patrick2 extends poly {


    public patrick2(ProjectVisual v)
    {
        super(v);
    }

    @Override
    public void render()
    {
        v.background(0);
        v.noStroke();
        //v.lights();        
        //function calls 
        gotToTry();
    }

    float[] lerpedBuffer;

 float y = 400;
 float lerped = 0;
 float lerpedAvg = 0;
 float bufferIndex = 0.0f; // Index for smooth looping within the buffer
 //long lastBeatTime = 0; // Stores the time of the last beat detection
 //long lastColorChange = 0; // Stores the time of the last bgcolor update


 public void gotToTry() {
    v.beat.detect(v.ap.mix);
    // Check if a second has passed since the last beat
    if (v.millis() - v.lastBeatTime >= 10) {
        if (v.beat.isOnset()) {
            v.background(0); // Change background on beat (once per second)
        }
        v.lastBeatTime = v.millis(); // Update last beat time
    }
    //background(0);
    v.lights();
    float tot = 0;

    for(int i = 0 ; i < v.ap.mix.size() *0.8; i ++)
    {
        tot += PApplet.abs(v.ap.mix.get(i));
    }

    float avg = tot / v.ap.mix.size();
    lerpedAvg = PApplet.lerp(lerpedAvg, avg, 0.1f);
   
    v.pushMatrix();
    v.translate(v.width/2, v.height/1.5f);
    v.rotate(-v.PI);
    v.rotateY(v.theta/2);
    v.scale(2.0f);
    v.shape(v.ball);
    v.popMatrix();
   
   v.fill(v.bgcolor % 255, 255, 255, 100); // Adjust transparency as needed
   String myText = "WE'VE GOT TO TRY";
   v.pushMatrix();

   float textWidth = v.textWidth(v.myText);
    float textHeight = v.textAscent();
    float spacingX = textWidth + 5; // Adjust spacing as needed (5 is for gap between characters)
    float spacingY = textHeight + 5; // Adjust spacing as needed (5 is for gap between lines)
    v.translate(v.width/2, v.height/1.5f);
    v.rotateY(v.theta/2); 

    for (int y = 0; y < v.height; y += spacingY) {
        for (int x = 0; x < v.width; x += spacingX) {
            v.text(v.myText, x, y);
            v.text(v.myText, -x, -y);

        }
    }
    v.popMatrix();
    v.theta += 0.01f + tot/1625;
    v.bgcolor += 0.3 + tot/2;
 }

}