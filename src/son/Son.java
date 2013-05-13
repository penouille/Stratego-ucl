package son;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import Pion.Pion;

/**
 * 
 * @author Meyers Quentin
 *
 *C'est cette class qui s'occupe de jouer du son.
 *
 */
public class Son
{
	public boolean sonOn;
	
	private URL son_canon;
	private URL son_champ_bataille;
	private URL cri_wilhelm;
	private URL son_song;
	private URL son_bombe;
	private URL son_victoire;
	private URL son_desamorcage;
	
	private Clip music;
	private Clip sonor;
	
	private boolean game;
	
	public Son()
	{
		sonOn = true;
		game = false;
		
		//initialisation de URL
		son_canon = this.getClass().getResource("/canon.wav");
		son_champ_bataille = this.getClass().getResource("/champ_bataille.wav");
		cri_wilhelm = this.getClass().getResource("/cri_wilhelm.wav");
		son_song = this.getClass().getResource("/song.wav");
		son_bombe = this.getClass().getResource("/bombe.wav");
		son_victoire = this.getClass().getResource("/victoire.wav");
		son_desamorcage = this.getClass().getResource("/desamorcage.wav");
		
		startSonMenu();
	}
	
	public void stopSon()
	{
		music.stop();
		sonOn = false;
	}
	public void startSon()
	{
		if(game)
		{
			music.stop();
			try
			{
				music = AudioSystem.getClip();
				music.open(AudioSystem.getAudioInputStream (son_song));
				music.loop(Clip.LOOP_CONTINUOUSLY);
			}
			catch (LineUnavailableException exception) { }
			catch (IOException exception) {  }
			catch (UnsupportedAudioFileException exception) {  }
		}
		else
		{
			try
			{
				music = AudioSystem.getClip();
				music.open(AudioSystem.getAudioInputStream (son_champ_bataille));
				music.loop(Clip.LOOP_CONTINUOUSLY);
			}
			catch (LineUnavailableException exception) { }
			catch (IOException exception) {  }
			catch (UnsupportedAudioFileException exception) {  }
		}
		sonOn = true;
	}
	
	
	private void startSonMenu() 
	{
		if(sonOn)
		{
			try
			{
				music = AudioSystem.getClip();
				music.open(AudioSystem.getAudioInputStream (son_champ_bataille));
				music.loop(Clip.LOOP_CONTINUOUSLY);
			}
			catch (LineUnavailableException exception) { }
			catch (IOException exception) {  }
			catch (UnsupportedAudioFileException exception) {  }
		}
	}
	
	public void playCanon()
	{
		if(sonOn)
		{
			try
			{
				sonor = AudioSystem.getClip();
				sonor.open(AudioSystem.getAudioInputStream (son_canon));
				sonor.start();
			}
			catch (LineUnavailableException exception) { }
			catch (IOException exception) {  }
			catch (UnsupportedAudioFileException exception) {  }
		}
	}
	
	public void changeSong(boolean game)
	{
		this.game = game;
		if(sonOn)
		{
			if(game)
			{
				music.stop();
				try
				{
					music = AudioSystem.getClip();
					music.open(AudioSystem.getAudioInputStream (son_song));
					music.loop(Clip.LOOP_CONTINUOUSLY);
				}
				catch (LineUnavailableException exception) { }
				catch (IOException exception) {  }
				catch (UnsupportedAudioFileException exception) {  }
			}
			else
			{
				music.stop();
				try
				{
					music = AudioSystem.getClip();
					music.open(AudioSystem.getAudioInputStream (son_champ_bataille));
					music.loop(Clip.LOOP_CONTINUOUSLY);
				}
				catch (LineUnavailableException exception) { }
				catch (IOException exception) {  }
				catch (UnsupportedAudioFileException exception) {  }
			}
		}
	}


	public void playDeath(Pion pion1, Pion pion2, boolean bothdead)
	{
		if(sonOn)
		{
			if(pion2.getName().equals("bombe"))
			{
				try
				{
					sonor = AudioSystem.getClip();
					sonor.open(AudioSystem.getAudioInputStream (son_bombe));
					sonor.start();
				}
				catch (LineUnavailableException exception) { }
				catch (IOException exception) {  }
				catch (UnsupportedAudioFileException exception) {  }
			}
			else if(pion1.getName().equals("bombe"))
			{
				try
				{
					sonor = AudioSystem.getClip();
					sonor.open(AudioSystem.getAudioInputStream (son_desamorcage));
					sonor.start();
				}
				catch (LineUnavailableException exception) { }
				catch (IOException exception) {  }
				catch (UnsupportedAudioFileException exception) {  }
			}
			else if(pion1.getName().equals("drapeau"))
			{
				music.stop();
				try
				{
					sonor = AudioSystem.getClip();
					sonor.open(AudioSystem.getAudioInputStream (son_victoire));
					sonor.start();
				}
				catch (LineUnavailableException exception) { }
				catch (IOException exception) {  }
				catch (UnsupportedAudioFileException exception) {  }
			}
			else if(pion1.getName().equals("marechal"))
			{
				try
				{
					sonor = AudioSystem.getClip();
					sonor.open(AudioSystem.getAudioInputStream (cri_wilhelm));
					sonor.start();
				}
				catch (LineUnavailableException exception) { }
				catch (IOException exception) {  }
				catch (UnsupportedAudioFileException exception) {  }
			}
			else
			{
				//TODO son d'un pion normal qui meurt
				try
				{
					sonor = AudioSystem.getClip();
					sonor.open(AudioSystem.getAudioInputStream (cri_wilhelm));
					sonor.start();
				}
				catch (LineUnavailableException exception) { }
				catch (IOException exception) {  }
				catch (UnsupportedAudioFileException exception) {  }
			}
		}
	}
}
