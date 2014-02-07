package com.xeeapps.HuPlayer;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends TabActivity {
	/**
	 * Called when the activity is first created.
	 */
	private String[] tabsList;
	private String albumsSelection, songsSelection, artistsSelection;
	private String[] albumsArgs, songsArgs, artistsArgs;
	private Integer genreId = null;
    private Integer playlistId = null;
    private String albumArtPath;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainoptionsmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.bringPlayer){

            Intent playerIntent = new Intent(this,PlayerActivity.class) ;
          if(!Globals.NEVER_PLAYED){
              playerIntent.setAction("recover");
//            playerIntent.putExtra("albumArtPath", Globals.ALBUM_ART_PATH) ;    // songsCursor.getPosition()
//            playerIntent.putExtra("currentSongDetails", Globals.CURRENT_SONG_DETAILS);
//            playerIntent.putExtra("currentSongIndex",Globals.CURRENT_SONG_INDEX);
//            playerIntent.putExtra("songDetailsList", Globals.SONG_DETAILS_LIST);
              startActivity(playerIntent);
          }else{
              Toast.makeText(this,"You should play a song first",Toast.LENGTH_SHORT).show();

          }

            return true;
        }else if(item.getItemId()==R.id.about){
            TextView myMsg = new TextView(this);
            myMsg.setText("This is a lightweight Music Player with lyrics.\nHu Player ©");
            myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
            new AlertDialog.Builder(this)
                    .setTitle("About")
                    .setView(myMsg)
//                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            // continue with delete
//                        }
//                    })
//                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            // do nothing
//                        }
//                    }
//                    )
                    .show();
            return  true;
        }else return  super.onOptionsItemSelected(item);
    }

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		TabHost tabHost = getTabHost();
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			tabsList = (String[]) extras.get("tabsList");
			albumsSelection = extras.getString("albumsSelection");
			songsSelection = extras.getString("songsSelection");
			artistsSelection = extras.getString("artistsSelection");
			genreId = (Integer) extras.get("genreId");
            playlistId = (Integer) extras.get("playlistId");
			albumsArgs = extras.getStringArray("albumsArgs");
			songsArgs = extras.getStringArray("songsArgs");
			artistsArgs = extras.getStringArray("artistsArgs");
            albumArtPath = extras.getString("albumArtPath");
			for (int i = 0; i < tabsList.length; i++) {
				if (tabsList[i].equals("Artists")) {
					TabHost.TabSpec spec1 = tabHost.newTabSpec("Artists");
					spec1.setIndicator("Artists",
							getResources().getDrawable(R.drawable.artist));
					Intent artistsIntent = new Intent(this,
							ArtistsActivity.class);
					artistsIntent.putExtra("genreId", genreId);
					artistsIntent.putExtra("artistsSelection", artistsSelection);
					artistsIntent.putExtra("artistsArgs", artistsArgs);
					spec1.setContent(artistsIntent);
					tabHost.addTab(spec1);

				} else if (tabsList[i].equals("Playlists")) {
					TabHost.TabSpec spec2 = tabHost.newTabSpec("Playlists");
					spec2.setIndicator("Playlists",
							getResources().getDrawable(R.drawable.playlist));
					Intent playlistsIntent = new Intent(this,
							PlaylistsActivity.class);
                    playlistsIntent.putExtra("genreId", genreId);
                    playlistsIntent.putExtra("albumsSelection", albumsSelection);
                    playlistsIntent.putExtra("albumsArgs", albumsArgs);
					spec2.setContent(playlistsIntent);
					tabHost.addTab(spec2);
				} else if (tabsList[i].equals("Albums")) {
					TabHost.TabSpec spec3 = tabHost.newTabSpec("Albums");
					spec3.setIndicator("Albums",
							getResources().getDrawable(R.drawable.album));
					Intent albumsIntent = new Intent(this, AlbumsActivity.class);
					albumsIntent.putExtra("genreId", genreId);
					albumsIntent.putExtra("albumsSelection", albumsSelection);
					albumsIntent.putExtra("albumsArgs", albumsArgs);
					spec3.setContent(albumsIntent);
					tabHost.addTab(spec3);
				} else if (tabsList[i].equals("Songs")) {
					TabHost.TabSpec spec4 = tabHost.newTabSpec("Songs");
					spec4.setIndicator("Songs",
							getResources().getDrawable(R.drawable.song));
					Intent songsIntent = new Intent(this, SongsActivity.class);

					songsIntent.putExtra("songsSelection", songsSelection);
						songsIntent.putExtra("genreId", genreId);
                    songsIntent.putExtra("playlistId",playlistId);
					songsIntent.putExtra("songsArgs", songsArgs);
                    songsIntent.putExtra("albumArtPath",albumArtPath);
					spec4.setContent(songsIntent);
					tabHost.addTab(spec4);

				} else  /*(tabsList[i].equals("Genres"))*/ {
					TabHost.TabSpec spec5 = tabHost.newTabSpec("Genres");
					spec5.setIndicator("Genres",
							getResources().getDrawable(R.drawable.genre));
					Intent genresIntent = new Intent(this, GenresActivity.class);
					spec5.setContent(genresIntent);
					tabHost.addTab(spec5);
				}

			}

		} else {
			TabHost.TabSpec spec1 = tabHost.newTabSpec("Artists");
//			spec1.setIndicator("Artists",
//					getResources().getDrawable(R.drawable.artist));
            spec1.setIndicator("Artists");
			Intent artistsIntent = new Intent(this, ArtistsActivity.class);
			spec1.setContent(artistsIntent);
			tabHost.addTab(spec1);
//			//
			TabHost.TabSpec spec2 = tabHost.newTabSpec("Playlists");
//			spec2.setIndicator("Playlists",
//					getResources().getDrawable(R.drawable.playlist));
            spec2.setIndicator("Playlists");
			Intent playlistsIntent = new Intent(this, PlaylistsActivity.class);
			spec2.setContent(playlistsIntent);
			tabHost.addTab(spec2);
			//
			TabHost.TabSpec spec3 = tabHost.newTabSpec("Albums");
//			spec3.setIndicator("Albums",
//					getResources().getDrawable(R.drawable.album));
            spec3.setIndicator("Albums");
			Intent albumsIntent = new Intent(this, AlbumsActivity.class);
			spec3.setContent(albumsIntent);
			tabHost.addTab(spec3);
			//
			TabHost.TabSpec spec4 = tabHost.newTabSpec("Songs");
//			spec4.setIndicator("Songs",
//					getResources().getDrawable(R.drawable.song));
            spec4.setIndicator("Songs") ;
			Intent songsIntent = new Intent(this, SongsActivity.class);
			spec4.setContent(songsIntent);
			tabHost.addTab(spec4);
			//
//			TabHost.TabSpec spec5 = tabHost.newTabSpec("Genres");
//			spec5.setIndicator("Genres",
//					getResources().getDrawable(R.drawable.genre));
//			Intent genresIntent = new Intent(this, GenresActivity.class);
//			spec5.setContent(genresIntent);
//			tabHost.addTab(spec5);
			//
//			TabHost.TabSpec spec6 = tabHost.newTabSpec("Composers");
//			spec6.setIndicator("Composers",
//					getResources().getDrawable(R.drawable.composer));
//			Intent composersIntent = new Intent(this, ComposersActivity.class);
//			spec6.setContent(composersIntent);
//			tabHost.addTab(spec6);

		}

	}

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {

        if (keyCode == KeyEvent.KEYCODE_BACK)
        {


            moveTaskToBack(true);
            return true; // return
        }

        return false;
    }
}
