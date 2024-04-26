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


Name: Sam O'Connor

Student Number: C22310526

# How it works
- My Scene is the frantically moving box, sphere, and hexagon shapes in reraction to the base and amplitude movements of the songwe have chosen "WE'VE GOT TO TRY" [hyperlink](https://www.youtube.com/watch?v=mRfSM-lv55I)

- I have implemented these 3 different shapes by randomising the type of shape that is spawned from the 3 options by using a for loop and the random() method. My class VisualShapes represents each shape and in that class I use a switch case to determine the type and draw the shapes. For the hexagon I call to a createHexagon() method which uses vertex() function to create a central vertex and then a for loop to extend six more vertices at calculated angles essentially creating six inward pointing triangles to make the hexagon shape.

- I also randomised the colours for the shapes and flashing background with the HSB model and used P3D rendering mode to create a far more cool 3D style of view and movement. In order to get the shapes to move and react to the song playing, I created a bass variable which calculates the average amplitude of the song's bass frequency. Then in my update() method I multiply the bass by the randomised x, y and z coordinates of the shapes. The size of each shape also changes with the line (v.lerp(size, size + v.sin(v.frameCount * 0.1f) * bass * 5, 0.05f)) to create a pulsating effect on the shapes that syncs with the music. I added a z coordinate so the shapes would move along a z axis from a close point of view to the screen to a far point.

# What I am most proud of in the assignment
- The parts of my project I am most proud of is two of the different type of views that are presented by the shapes moving close and far away. When they all come in close to the bass high amplitude point, they overlap with each other creating an awesome and colourful almost paint splash like visual whilst pulsating. I did not add any stroke (outline) to the shapes so this would look better and I included transparency values on the shapes to also maximise this vision. The other part I'm proud of is when the shapes zoom out making the view on screen look like a psychedelic starry night sky. I maximised this view using a perspective() method to give a wider field of view.

# Headings
## Headings
#### Headings
##### Headings

This is my code:

```Java
public void settings() { // Sets the size of the window and the rendering mode (3D)
        v.size(v.height, v.width, v.P3D);
    }

    public void setup() {
        v.ab = ap.mix; // Gets the mixed buffer from the audio player
        v.fft = new FFT(ap.bufferSize(), ap.sampleRate()); // Analysing the song frequency
        v.colorMode(v.HSB); // Sets the color mode

        v.lerpedBuffer = new float[v.width]; // Array (window width size) to store lerped audio
        for (int i = 0; i < shapes1.length; i++) { // For loop randomises the shape type and position
            shapes1[i] = new VisualShape(v.random(maxWidth), v.random(maxHeight), (int) v.random(3));
        }
        v.perspective(v.PI/3, (float)v.width/v.height, 0.1f, 5000); // Adjust the field of view
    }

    public void Samdraw() {
        v.fft.forward(v.ab);
        float bass = v.fft.calcAvg(40, 80); // Averages the amplitude of frequencies between 40 and 80 Hz
        float treble = v.fft.calcAvg(5000, 10000); // "" for treble frequencies
        v.background(bass * 300 % 255, 255, treble * 300 % 255); // sets background colours

        for (int i = 0; i < shapes1.length; i++) { // Draws the shapes and updates each shapes state based on the song bass level
            if(shapes1[i] == null){
                break;
            }
            shapes1[i].update(bass);
            shapes1[i].display();
        }

        if (sceneActive && v.millis() > 1000) { // Makes each scene no longer than 1 seconds
            sceneActive = false;
            changeScene();
        }
    }

    // Method to regenerate the shapes when a particular condition is met
    void changeScene() {
        for (int i = 0; i < shapes1.length; i++) {
            shapes1[i] = new VisualShape(v.random(maxWidth), v.random(maxHeight), (int) v.random(3));
        }
    }

    // Creates the shapes
    class VisualShape {
        float x, y, z;  // Variables for the x and y coordinates of the shape's position
        float size;  // Variable for the size of the shape
        int type;  // Type of shape
        float colour;  // Shape colour
    
        VisualShape(float x, float y, int type) {
            this.x = v.constrain(x, 0, maxWidth); // Ensures that the x-coordinate doesn't exceed the max width
            this.y = v.constrain(y, 0, maxHeight); // Ensures that the y-coordinate doesn't exceed the max height
            this.size = v.random(10, 50); // Sets the shape size to a random value between 10 and 50
            this.type = type; // Sets the type of the shape
            this.colour = v.random(255); // Sets the colour of the shape
            this.z = v.random(-200, -50); // Added z-coordinate placing shapes further from the screen
        }
    
        // Method to change the shape's position and size based on the bass level of the music
        void update(float bass) {
            x += v.random(-1, 1) * bass; // Randomly adjusts the coordinates based on the bass (moves shapes)
            y += v.random(-1, 1) * bass;
            x = v.constrain(x, 0, maxWidth);
            y = v.constrain(y, 0, maxHeight);
            z += v.sin(v.frameCount * 0.1f) * bass * 10;
            size = v.lerp(size, size + v.sin(v.frameCount * 0.1f) * bass * 5, 0.05f); // Changes the size based on the bass and the frame count (pulsating effect)
        }
    
        // Method to display the shapes
        void display() {
            v.fill(colour, 255, 255, 200); // Sets the fill colour and a value of 200 for transparency.
            v.noStroke(); // Stops drawing an outline around the shapes
            v.pushMatrix(); // Saves the current drawing matrix
            v.translate(x, y, -size * 2); // Translates the matrix to the x and y coordinates (pushed further back)
            switch (type) { // Drawing the shapes based on the type
                case 0:
                    v.sphere(size); // Draws a sphere
                    break;
                case 1:
                    v.box(size, size, size); // Draws a box with dimensions
                    break;
                case 2:
                    createHexagon(size); // Draws a hexagon
                    break;
            }
            v.popMatrix(); // Restores the previously saved drawing matrix
        }
    
        // Method to create a hexagon shape
        void createHexagon(float size) {
            v.beginShape(v.TRIANGLE_FAN); // Drawing a hexagon defined by connecting triangles radiating from a central vertex.
            v.vertex(0, 0, 0); // Sets the central vertex
            for (int i = 0; i <= 6; i++) { // Loops to create the six vertices of the hexagon and one to close the shape
                float angle = v.TWO_PI / 6 * i; // Calculates the angle for each vertex
                v.vertex(size * v.cos(angle), size * v.sin(angle), size); // Sets each vertex at an angle and at a distance based on size
            }
            v.endShape(v.CLOSE); // Closes the shape
        }
    }
```


These are screenshots of my visuals:

![An image](images/Sam'sShapes1.png)

![An image](images/Sam'sShapes2.png)

![An image](images/Sam'sShapes3.png)

![An image](images/Sam'sShapes4.png)

![An image](images/Sam'sShapes5.png)

![An image](images/Sam'sShapes6.png)

![An image](images/Sam'sShapes7.png)

![An image](images/Sam'sShapes8.png)


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
