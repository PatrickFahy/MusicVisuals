package ie.tudublin;

import ddf.minim.analysis.BeatDetect;


public class ProjectVisual extends Visual {

   
    poly play;

    public void settings(){
        fullScreen(P3D);
	}

	public void setup(){
        colorMode(HSB,360,100,100);
		startMinim();
        rectMode(CENTER); 
		loadAudio("The Chemical Brothers - We've Got To Try.mp3");
        beat = new BeatDetect(ap.bufferSize(),ap.sampleRate());
        ball = loadShape("dog.obj");
        noiseSeed(0l);

        beat = new BeatDetect(getAudioPlayer().bufferSize(), getAudioPlayer().sampleRate());
        beat.setSensitivity(10);
        bl = new BeatListener(beat, getAudioPlayer());
        play = new patrick2(this);
		startListening();
        colorMode(HSB, 360, 100, 100);
	}
    

    
    public void keyPressed() {
    
        if (key == ' ') 
        {
            if(getAudioPlayer().isPlaying()){
                getAudioPlayer().pause(); //pauses the song
            }
            else{
                getAudioPlayer().loop(); //starts the song playing again from the point it left off
            }
        }
        
        if (key == '1')
        {
            play = new patrick2(this);
        }
        
        if (key == '2')
        {
            play = new c22394713(this);
        }

        if ( key == '3')
        {
            play = new Assignment_Test(this);
        }

        if (key =='4')
        {
            //play = new Spiral(this);
        }

        if(key == '5'){
            //play = new Cubesquared2(this);
        }

        if(key == '6'){
            //play = new SinWaves(this);
        }
        /*if(key == '7'){
            play = new WaveyVisual(this);
        } */

        if(key =='r' || key =='R'){ //allows for the song to be  started again from the beginning

            getAudioPlayer().cue(0);
            startListening();
        }
        
    }    


	public void draw(){

        stroke(255,255,255);
    
        background(0);
        try
        {
            // Call this if you want to use FFT data
            calculateFFT(); 
        }
        catch(VisualException e)
        {
            e.printStackTrace();
        }
        // Call this is you want to use frequency bands
        calculateFrequencyBands(); 

        // Call this is you want to get the average amplitude

        calculateAverageAmplitude();        
       
      
        //will pulse an object with music volume
        calculateAverageAmplitude();    

        play.render(); //renders the currently loaded visual
        
    }   
    
    
}
    