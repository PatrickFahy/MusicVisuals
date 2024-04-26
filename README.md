# Music Visualiser Project


# Description of the assignment
- Music Visualiser Project for Object Oriented Programming (OOP) 2024 Semester 2

- (Patrick, Ruben, Jeff, Cormac, Sam)

- multiple audio visualisations that react to the frequency of the music

- each visualisation includes multiple different elements that are reactive to the music

- Scenes are controlled using num keys 1-9



Name: Patrick Fahy

Student Number: C22394713

# How it works
- My First Scene is the Dog with the Large Text that reads "WE'VE GOT TO TRY" as this is the name of the song we are using [hyperlink](https://www.youtube.com/watch?v=mRfSM-lv55I)

- The reason I used the dog as a centerpiece of this scene is because the music video focuses on the story of a dog and how he learns to drive and travels through space.

- My Second Scene is the 3-D Rotating Sphere covered in a mesh grid, which has audio waveforms eminating from both poles of the sphere in both directions. 

- The reason I used this as my second scene is because it looks like the road the dog used to travel through space, and gives a lot of depth to the space around the globe, creating a psychadelic endless highway effect.

# What I am most proud of in the assignment
- I'm most proud of the creative aspect of this project, I was able to express my creativity through the visualisations. I had very specific original ideas which I wanted to carry out and I managed to execute them within the best of my ability.






# Headings
## Headings
#### Headings
##### Headings

This is code for the Dog:

```Java
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
        tot += v.abs(v.ap.mix.get(i));
    }

    float avg = tot / v.ap.mix.size();
    lerpedAvg = v.lerp(lerpedAvg, avg, 0.1f);
   
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
```


This is code for the Sphere:

```Java
public void BufferSphere()
    {    
        float y = 400;
        float bufferIndex = 0.0f; // Index for smooth looping within the buffer
        PVector lightPosition = new PVector(v.width / 2, v.height / 2, 200);
        float angle = 0; 
        //colorMode(HSB);
        v.background(0);
        v.stroke(v.bgcolor % 255, 255, 255);

        v.lights();
        //background(bgcolor % 255, 255, 255);
        v.background(0);

        // Move light position around the object
        float radius = 200;
    
        v.lightPosition.x = v.width / 2 + PApplet.cos(angle) * radius;
        v.lightPosition.y = v.height / 2 + PApplet.sin(angle) * radius;

        // Update angle for next frame
        angle += 0.01;
        v.bgcolor += 0.3;

        float tot = 0;
        for(int i = 0 ; i < v.ap.mix.size() *0.8; i ++)
        {
            tot += PApplet.abs(v.ap.mix.get(i));
        }

        float avg = tot / v.ap.mix.size();

        // Draw the object (sphere)
        v.translate(v.width / 2, v.height / 2);
        v.rotateY(v.frameCount * 0.01f);
        v.rotateX(v.frameCount * 0.01f);
        v.fill(200, 50, 50);
        v.sphereDetail(30);
        v.sphere(tot);
        
        // Set light position
        v.pointLight(255, 255, 255, v.lightPosition.x, v.lightPosition.y, v.lightPosition.z);

        float h = v.height / 2;
        //float w = width / (float) b.size(); // Width of each segment
        for(int i = 0 ; i < v.width*6; i ++)
        {
            bufferIndex += 0.6f; // Adjust value for line length  +  to get "Speedlines" effect
            float value = v.ap.mix.get((int) Math.floor(bufferIndex) % v.ap.mix.size()); //get buffer values

            float hue = PApplet.map(i, 0, v.ap.mix.size() , 0, 256);
            v.stroke(v.bgcolor % 255, 255, 255);
            v.noFill();
            v.line(i, h, i, h + value * h); // Top waveform line
            v.line(v.width - i, h, v.width - i, h + value * h); // Mirrored waveform line on the opposite side
            v.line(i, -h, i, -h + value * -h);// Draw line on the opposite pole
            v.line(v.width - i, -h, v.width - i, -h + value * -h); // Mirrored waveform line on the opposite side
        }
    }
```



These are screenshots of my second Scene:

![An image](images/sphereWave1.png)

![An image](images/sphereWave2.png)

![An image](images/sphereWave3.png)

![An image](images/sphereWave4.png)

![An image](images/sphereWave5.png)


These are screenshots of my Second Scene:

![An image](images/we'veGotToTryDog6.png)

![An image](images/we'veGotToTryDog5.png)

![An image](images/we'veGotToTryDog4.png)

![An image](images/we'veGotToTryDog3.png)

![An image](images/we'veGotToTryDog2.png)


This a link to the 'We've Got to Try' Music video on Youtube [hyperlink](https://www.youtube.com/watch?v=mRfSM-lv55I)


This is the youtube video:

![An image](images/MusicVideoScreenshot.png)



















# Music Visualiser Project

Name:

Student Number: 

# How it works

# What I am most proud of in the assignment

# Headings
## Headings
#### Headings

This is code:

```Java
public void render()
{
	ui.noFill();
	ui.stroke(255);
	ui.rect(x, y, width, height);
	ui.textAlign(PApplet.CENTER, PApplet.CENTER);
	ui.text(text, x + width * 0.5f, y + height * 0.5f);
}
```

This is an image using a relative URL:

![An image](images/p8.png)

This is a [hyperlink](http://bryanduggan.org)


This is a youtube video:

[![YouTube](http://img.youtube.com/vi/J2kHSSFA4NU/0.jpg)](https://www.youtube.com/watch?v=J2kHSSFA4NU)



Name : Cormac Holohan

Student Number C22363913

# How it works
- My first scene is a large amount of cubes that respond to the audio and grow/shrink size due to the audio level. The cubes are not fully randomized as I used matrices to run the 2d arrays 

- The cubes have a randomized colour and fly towards the screen creating abstract designs, creating an almost psychadelic effect.

- My second scene is flash of lightning, also based off of the audio level, if the audio level gets loud enough a flash of lightning comes across the screen, the background changes and two lightning shapes are shown, along with two yellow wave forms that respond to the audio level as well.

- There is also randomized rain to set the mood of the scene and give life to the blakced out scene for when there is no lightning.

# What I am most proud of in the assignment
- I am most proud of the teamwork aspect of this project and working well in a team, being able to help others fix bugs and have others help me fix issues that I would not have seen, being able to handle merge conflicts. This assignment helped me to understand how to work in a team when it comes to larger coding


Cube moving Visual

```Java
public void Comac_cubes_draw() {
        v.background(0);
        v.lights(); // Enable lighting
    
        // Calculate amplitude from audio buffer
        float amplitude = v.ab.level() * sizeMultiplier;
    
        // Translate to the center of the window
        v.translate(v.width / 2, v.height / 2, 0);
    
        // Update and draw cubes
        for (int i = 0; i < v.numCubes; i++) {
            v.cubeZPositions[i] -= v.cubeVelocities[i]; // Move cube towards the camera
    
            drawCube(amplitude, v.cubeXPositions[i], v.cubeYPositions[i], v.cubeZPositions[i], v.cubeColors[i]); // Draw cube at updated position
    
            // Reset cube position if it goes beyond the camera
            if (v.cubeZPositions[i] > 200) {
                v.cubeZPositions[i] = v.random(-500, -3000);
                v.cubeXPositions[i] = v.random((v.width *-1) / 2, v.width / 2);
                v.cubeYPositions[i] = v.random((v.height * -1) / 2, v.height / 2);
                v.cubeVelocities[i] = v.random(0.1f, 1f);
                v.cubeColors[i] = v.color(v.random(255), v.random(255), v.random(255)); // Random color
            }
        }
    }
    
    void drawCube(float size, float x, float y, float z, int cubeColor) {

        for (int i = 0; i < v.numCubes; i++) {
            v.cubeXPositions[i] = v.random((v.width *-1), v.width);
            v.cubeYPositions[i] = v.random((v.height * -1), v.height);
            v.cubeZPositions[i] = v.random(-800, -1500);
            v.cubeVelocities[i] = v.random(1, 5);
            v.cubeColors[i] = v.color(v.random(255), v.random(255), v.random(255)); // Random color
        }

        
        
        // Draw cube with size determined by audio amplitude
        v.pushMatrix(); // Save current transformation matrix
        v.translate(x, y, z); // Translate to position
        v.stroke(cubeColor); // Colour outline for the cubes
        v.fill(cubeColor); // Random fill color
        v.box(size); // Draw cube
        v.popMatrix(); // Restore previous transformation matrix
    }

```



Lightning Scene Code

```Java
public void Cormac_lightning_draw(){
        v.colorMode(PApplet.HSB, 360, 100, 100); // Set color mode to HSB

        v.background(0);
        float level = v.ab.level();

        // Clear the background
        if (changeBackground) {
            v.background(240, 48, 36); // Change background to white
        } else {
            v.background(0); // Keep the background black
        }

        if (level > 0.15) {
            changeBackground = true;
        }
        else{
            changeBackground = false;
        }

        if (changeBackground) {
            v.noStroke();
            v.fill(60, 100, 100); // Yellow color in HSB
            v.triangle(300,50,400,50,300,400);
            v.triangle(375,350,450, 350, 350, 700 );
            v.rect(363, 375,125,50 );
    
            v.triangle(700, 50, 800, 50, 700, 400); // Moved over 400 units
            v.triangle(775, 350, 850, 350, 750, 700); // Moved over 400 units
            v.rect(763, 375, 125, 50); // Moved over 400 units

            v.triangle(1100, 50, 1200, 50, 1100, 400); // Moved over 400 units again
            v.triangle(1175, 350, 1250, 350, 1150, 700); // Moved over 400 units again
            v.rect(1163, 375, 125, 50); // Moved over 400 units again



            // Draw waveform with a yellow line responsive to the audio
            v.stroke(60, 100, 100); // Yellow color in HSB
            v.noFill(); // No fill for the waveform
            v.beginShape();
            for (int i = 0; i < v.ab.size() - 1; i += 5) {
                float x = PApplet.map(i, 0, v.ab.size(), (v.width *-1)/2, v.width/2); // Offset by half the width
                float y = PApplet.map(v.ab.get(i), -1, 1, v.height, 0) * level; // Adjust y position based on audio level
               v.vertex(x + v.width/2, y + 100); // Offset x back to the center
            }
            v.endShape();
    
            v.stroke(60, 100, 100); // Yellow color in HSB for the second waveform
            v.beginShape();
            for (int i = 0; i < v.ab.size() - 1; i += 5) {
                float x = PApplet.map(i, 0, v.ab.size(), (v.width *-1)/2, v.width/2); // Offset by half the width
                float y = PApplet.map(v.ab.get(i), -1, 1, 0, v.height) * level; // Adjust y position based on audio level
                v.vertex(x + v.width/2, y + v.height/2); // Offset y by half the height and x back to the center
            }
            v.endShape();
    
        }

        
    
            // drawing of clouds
            v.stroke(192, 10, 90); // Gray color in HSB
            v.fill(192, 10, 90); // Gray color in HSB
            v.ellipse(0, 0, 250, 150);
            v.ellipse(400, 0, 250, 180);
            v.ellipse(250, 0, 400, 130);
            v.ellipse(600, 0, 350, 200);
            v.ellipse(800, 0, 260, 230);
            v.ellipse(1000, 0, 300, 200);
            v.ellipse(1200, 0, 250, 270);
            v.ellipse(1400, 0, 360, 225);
            v.ellipse(1600, 0, 280, 200);
            v.ellipse(1800, 0, 200, 175);

            // Draw blue lines falling like rain
        v.stroke(240, 100, 70); // Blue color in HSB
        v.strokeWeight(2); // Set the stroke weight
        for (int i = 0; i < v.width; i += 5) {
            float y = PApplet.map(v.random(0, 1), 0, 1, 0, v.height); // Randomize y-coordinate
            v.line(i, y, i + 8, y + 10); // Draw a vertical line
        }
    
            //background drawing
            v.fill(0); // Black color
            v.stroke(0); // Black color
            v.rect(0,800,v.width * 2,200);
    }
```

