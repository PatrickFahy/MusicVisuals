package example;

import ie.tudublin.Visual;
import ddf.minim.*;
import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;

public class patrick extends Visual {
    
    FFT fft;
    Minim m;
    AudioInput ai;
    AudioPlayer ap;
    
    AudioBuffer b;
  
    int numParticles = 200; // Number of particles
    Particle[] particles;
  
    public void settings() {
      size(800, 800, P3D);
    }
  
    public void setup() {
        colorMode(HSB);
        noCursor();
        m = new Minim(this);
        ap = m.loadFile("The Chemical Brothers - We've Got To Try.mp3"); //java\data\The Chemical Brothers - We've Got To Try.mp3

        ai = m.getLineIn(Minim.STEREO); // Assuming line input (adjust for microphone if needed)
        fft = new FFT(ai.bufferSize(), 2); // Assuming stereo input (adjust for mono)
        
        ap.play();
        b = ap.mix;
  
        particles = new Particle[numParticles];
        for (int i = 0; i < numParticles; i++) {
            particles[i] = new Particle(width / 2, height / 2);
        }
    }
  
    public void draw() {
      background(0);
      //fft.AnalyzeSound();
  
      for (int i = 0; i < numParticles; i++) {
        particles[i].update(fft);
        particles[i].display();
      }
    }
  
    // Inner class for particle object
    class Particle {
  
      float x, y;
      float velX, velY;
      float radius;
  
      public Particle(float x, float centerY) {
        this.x = x;
        this.y = random(height);
        velX = 0;
        velY = 0;
        radius = random(2, 5);
      }
  
      public void update(FFT fft) {
        // Update velocity based on average amplitude across all bands
        float avgAmplitude = 0;
        for (int i = 0; i < b.size(); i++) {
          avgAmplitude += abs(b.get(i));
        }
        avgAmplitude /= b.size();
  
        velX = map(avgAmplitude, 0, 1, -2, 2); // Adjust mapping values for desired movement range
  
        // Update position based on velocity
        x += velX;
        y += velY;
  
        // Bounce off edges
        if (x < 0 + radius) {
          x = 0 + radius;
          velX *= -1;
        } else if (x > width - radius) {
          x = width - radius;
          velX *= -1;
        }
        if (y < 0 + radius) {
          y = 0 + radius;
          velY *= -1;
        } else if (y > height - radius) {
          y = height - radius;
          velY *= -1;
        }
      }
  
      public void display() {
        fill(255);
        ellipse(x, y, radius, radius);
      }
    }
}