package com.xeeapps.AmPlayer;

import android.media.MediaPlayer;

public final class MediaPlayerFactory {
	private static final MediaPlayer player = new MediaPlayer();
	public static final MediaPlayer getMediaPlayer(){

		return player;
	}
}
	
