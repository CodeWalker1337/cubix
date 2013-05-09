package cubix;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.newdawn.slick.util.ResourceLoader;

public class SoundSystem
{

	private ArrayList<Clip> sounds = new ArrayList<Clip>();
	protected double volume = -10f;
	protected double maxVol = -100f;

	public void playSound(String url)
	{
		InputStream audioSrc = ResourceLoader.getResourceAsStream("res/sounds/" + url + ".wav");
		InputStream bufferedIn = new BufferedInputStream(audioSrc);
		try
		{
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
			Clip clip2 = AudioSystem.getClip();
			clip2.open(audioStream);
			sounds.add(clip2);
			FloatControl gainControl = (FloatControl) clip2.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue((float) volume);
			clip2.start();

		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setVolume(double volume)
	{
		this.volume = maxVol + volume;
		for (int i = 0; i < sounds.size(); i++)
		{
			try
			{
				FloatControl gainControl = (FloatControl) sounds.get(i).getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue((float) this.volume);
			} catch (IllegalArgumentException e)
			{
				e.printStackTrace();
				sounds.remove(i);
			}
		}
	}
}
