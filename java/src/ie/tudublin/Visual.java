package ie.tudublin;

import processing.core.*;
import ddf.minim.*;
import ddf.minim.analysis.*;



public abstract class Visual extends PApplet
{


	private int frameSize = 512;
	private int sampleRate = 44100;

	private float[] bands;
	private float[] smoothedBands;
	float[] lerpedBuffer = new float[width];

	private Minim minim;
	private AudioInput ai;
	AudioPlayer ap;
	AudioBuffer ab;
	FFT fft;
	BeatDetect beat;
	BeatListener bl;

	private float amplitude  = 0;
	private float smothedAmplitude = 0;

	// jeff Variables ---------------


// Patrick Variables ---------------
	PShape ball;
    float bgcolor = 0;
    float theta;
    String myText = "WE'VE GOT TO TRY";
    PFont myFont;
	//PVector lightPosition = new PVector(width / 2, height / 2, 200);
	long lastBeatTime = 0; // Stores the time of the last beat detection	
	

	public BeatDetect getBeat() {
		return beat;
	}

	public void setBeat(BeatDetect beat) {
		this.beat = beat;
	}
// End Patrick Variables ---------------

// Ruben Vars:
	Assignment_Test.Circle[] shapes = new Assignment_Test.Circle[18];
	Assignment_Test.Square theVoid; // Create an instance of Square

//End Ruben Vars

// Sam Variables
	SamVisuals.VisualShape[] shapes1 = new SamVisuals.VisualShape[50];
	


// Cormac Variables

	// Cormac_cubes Variables
	int numCubes = 500;

	float[] cubeXPositions = new float[numCubes]; // X positions of cubes
    float[] cubeYPositions = new float[numCubes]; // Y positions of cubes
    float[] cubeZPositions = new float[numCubes]; // Z positions of cubes
    float[] cubeVelocities = new float[numCubes]; // Velocities of cubes
    int[] cubeColors = new int[numCubes];; // Colors of cubes

//End Cormac Variables


	public void startMinim() 
	{
		minim = new Minim(this);

		fft = new FFT(frameSize, sampleRate);

		bands = new float[(int) log2(frameSize)];
  		smoothedBands = new float[bands.length];

	}

	float log2(float f) {
		return log(f) / log(2.0f);
	}

	protected void calculateFFT() throws VisualException
	{
		fft.window(FFT.HAMMING);
		if (ab != null)
		{
			fft.forward(ab);
		}
		else
		{
			throw new VisualException("You must call startListening or loadAudio before calling fft");
		}
	}

	//calculates the average
	public float calculateAverageAmplitude()
	{
		float total = 0;
		for(int i = 0 ; i < ab.size() ; i ++)
        {
			total += abs(ab.get(i));
		}
		amplitude = total / ab.size();
		return amplitude;
	}
	
	//calculates the total
	public float calculateTotal()
	{
		float total = 0;
		for(int i = 0 ; i < ab.size() ; i ++)
        {
			total += abs(ab.get(i));
		}
		return total;
	}


	protected void calculateFrequencyBands() {
		for (int i = 0; i < bands.length; i++) {
			int start = (int) pow(2, i) - 1;
			int w = (int) pow(2, i);
			int end = start + w;
			float average = 0;
			for (int j = start; j < end; j++) {
				average += fft.getBand(j) * (j + 1);
			}
			average /= (float) w;
			bands[i] = average * 5.0f;
			smoothedBands[i] = lerp(smoothedBands[i], bands[i], 0.05f);
		}
	}

	public void startListening()
	{
		//ap.loop();
		ap.play();
	}

	public void loadAudio(String filename)
	{
		ap = minim.loadFile(filename, frameSize);
		ab = ap.mix;
	}

	public void settings(){
		size(800, 800,P3D);
	}

	public int getFrameSize() {
		return frameSize;
	}

	public void setFrameSize(int frameSize) {
		this.frameSize = frameSize;
	}

	public int getSampleRate() {
		return sampleRate;
	}

	public void setSampleRate(int sampleRate) {
		this.sampleRate = sampleRate;
	}

	public float[] getBands() {
		return bands;
	}

	public float[] getSmoothedBands() {
		return smoothedBands;
	}

	public Minim getMinim() {
		return minim;
	}

	public AudioInput getAudioInput() {
		return ai;
	}


	public AudioBuffer getAudioBuffer() {
		return ab;
	}

	public float getAmplitude() {
		return amplitude;
	}

	public float getSmoothedAmplitude() {
		smothedAmplitude = lerp(smothedAmplitude, calculateAverageAmplitude(), 0.1f);
		return smothedAmplitude;
	}

	public AudioPlayer getAudioPlayer() {
		return ap;
	}

	public FFT getFFT() {
		return fft;
	}
}

class BeatListener implements AudioListener
{
  private BeatDetect beat;
  private AudioPlayer source;
  
  BeatListener(BeatDetect beat, AudioPlayer source)
  {
    this.source = source;
    this.source.addListener(this);
    this.beat = beat;
  }
  
  public void samples(float[] samps)
  {
    beat.detect(source.mix);
  }
  
  public void samples(float[] sampsL, float[] sampsR)
  {
    beat.detect(source.mix);
  }
}