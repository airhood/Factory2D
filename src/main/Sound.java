package main;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	
	public int soundAmount = 1;
	
	Clip[] playingClips = new Clip[128];
	ArrayList<Integer> releasedAudioPlayer = new ArrayList<Integer>();
	
	Clip[] clips = new Clip[soundAmount];
	URL[] soundURL = new URL[soundAmount];
	String[] soundFileName = new String[soundAmount];
	
	public Sound() {
		 for (int i = 0; i < 128; i++) {
			 releasedAudioPlayer.add(i);
		 }
		 
		 LoadSoundFile();
	}
	
	public void LoadSoundFile() {
		try {
			InputStream inputStream = getClass().getResourceAsStream("/sound/soundMeta.meta");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			
			for (int i = 0; i < soundAmount; i++) {
				String line = bufferedReader.readLine();
				soundURL[i] = getClass().getResource("/sound/" + line);
			}
			
			for (int i = 0; i < soundAmount; i++) {
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL[i]);
				clips[i] = AudioSystem.getClip();
				clips[i].open(audioInputStream);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int play(int i) {
		int playID = releasedAudioPlayer.get(0);
		releasedAudioPlayer.remove(0);
		
		playingClips[playID] = clips[i];
		playingClips[playID].start();
		
		return playID;
	}
	
	public void loop(int playID) {
		playingClips[playID].loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop(int playID) {
		playingClips[playID] = null;
		releasedAudioPlayer.add(playID);
	}
}
