{
    background(0);

    break;
}
    case 2:
    {
        background(0);
        float hue = map(frameCount % 255, 0.0f, 255.0f, 0.0f, 360.0f);
        float saturation = 255;
        float brightness = 255;
    fill(hue, saturation, brightness);
    stroke(hue, saturation, brightness);
        float smoothing = (float) 0.5; // Adjust the smoothing factor as needed
        float previousSample = 0;
        for (int i = 0; i < ab.size(); i++) {
            float currentSample = ab.get(i) * 100; // Adjust the multiplier as needed
            float smoothedSample = lerp(previousSample, currentSample, smoothing);
            line(i - 1, cy + previousSample, i, cy + smoothedSample);
            previousSample = smoothedSample;
        }
        break;
    }
case 3:
{
    background(0);
    stroke(255);
    for (int i = 0; i < ab.size() - 1; i++) {
        float freq = map(i, 0, ab.size(), 20, 20000); // Map index to frequency range
        float y1 = map(ab.get(i), -1, 1, height, 0); // Map amplitude to screen height
        float y2 = map(ab.get(i + 1), -1, 1, height, 0);
        line(i, y1, i + 1, y2);
    }
}
break;

case 4:
{
    background(0);
    float rotationSpeed = map(smoothedAmplitude, 0.0f, 1.0f, 0.01f, 0.01f); // Map amplitude to rotation speed

    y = height / 2; // Reset y position to center

    float xTranslate = random(-width/2, width/2);
    float yTranslate = random(-height/2, height/2);


    // Set the color based on amplitude
    float hue = map(frameCount % 255, 0.0f, 255.0f, 0.0f, 360.0f);
    float saturation = 255;
    float brightness = 255;
    fill(hue, saturation, brightness);
    stroke(hue, saturation, brightness);

    // Draw 3D sphere at the center of the screen
    lights();
    translate(width / 2, height / 2);
    rotateY(frameCount * rotationSpeed);
    rotateX(frameCount * rotationSpeed);    
    sphere(100);


    // Draw waveform lines based on amplitude
    float waveRadius = 200;
    float angleStep = TWO_PI / ab.size();

    for (int i = 0; i < ab.size(); i++) {
        float x = sin(angleStep * i) * waveRadius;
        float y = map(ab.get(i), -2, 2, -100 , 100); // Map amplitude to height around the sphere
        float z = cos(angleStep * i) * waveRadius;
        // Translate the waveform point to start from the surface of the sphere
        float scaleFactor = 1.1f; // Scale factor to extend the lines slightly beyond the sphere's surface
        float newX = x * scaleFactor;
        float newY = y * scaleFactor;
        float newZ = z * scaleFactor;
        // Draw the line from the sphere's center to the translated point
        line(0, 0, 0, newX, newY, newZ);
    }
    

    break;
}