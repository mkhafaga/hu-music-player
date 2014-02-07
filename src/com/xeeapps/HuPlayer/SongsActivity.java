package com.xeeapps.HuPlayer;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import com.xeeapps.mappers.Mapper;
import com.xeeapps.service.SongDetails;

/**
 * Created with IntelliJ IDEA. User: khafaga Date: 05/09/12 Time: 06:31 م To
 * change this template use File | Settings | File Templates.
 */
public class SongsActivity extends ListActivity {
	private ListView songsView;

	private Cursor songsCursor;
	private String songsSelection;
	private String[] songsArgs;
	private Bundle bundle;
	private Integer genreId=null;
    private Integer playlistId=null;
    private String albumArtPath;
    SongDetails[] songDetailsList;
    private GUIMaker maker;
    private EditText searchInput;
	private String[]

           // proj = {MediaStore.Audio.Playlists.Members.DISPLAY_NAME,MediaStore.Audio.Playlists.Members.DATA,MediaStore.Audio.Playlists.Members._ID};

    proj = { MediaStore.Audio.Media.TITLE,//EDISPLAY_NAME,
			MediaStore.Audio.Media.DATA, MediaStore.Audio.Media._ID,MediaStore.Audio.Media.ARTIST,MediaStore.Audio.Media.ALBUM_ID};

	private Uri songsUri;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
        setContentView(R.layout.songs);
		 maker = new GUIMaker();
		bundle = getIntent().getExtras();
		songsUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		if (bundle != null) {
			songsSelection = bundle.getString("songsSelection");
			songsArgs = bundle.getStringArray("songsArgs");
            albumArtPath =  bundle.getString("albumArtPath");
            playlistId = (Integer) bundle.get("playlistId");
			genreId = (Integer) bundle.get("genreId");
			if (genreId != null) {
				songsUri = MediaStore.Audio.Genres.Members.getContentUri(
						"external", genreId);
				Log.i("Hereeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee", ""
						+ genreId);
				// songsUri =
				// MediaStore.Audio.//Genres.Members.getContentUri("external",
				// genreId);
			}
            Log.i("playlistId", ""
                    + playlistId);
            if(playlistId!=null){
//                MediaStore.Audio.Media.DISPLAY_NAME,
//                        MediaStore.Audio.Media.DATA, MediaStore.Audio.Media._ID,MediaStore.Audio.Media.ARTIST};
               // proj = new String[] {MediaStore.Audio.Playlists.Members.DISPLAY_NAME,MediaStore.Audio.Playlists.Members.DATA,MediaStore.Audio.Playlists.Members._ID,MediaStore.Audio.Playlists.Members.ARTIST};
                songsUri = MediaStore.Audio.Playlists.Members.getContentUri("external",playlistId);
             //   Log.i("tacks cursor size: ",PlaylistUtils.getTrackListFromPlaylist(getContentResolver(),playlistId).getInt(0)+"");
                String[] MEDIA_COLUMNS = new String[] {
                        "count(*)"
                };
                Cursor cur2 =getContentResolver().query(songsUri,
                        MEDIA_COLUMNS, null, null, null);
                cur2.moveToFirst();


                Log.i("tacks cursor size: " ,cur2.getInt(0)+" - "+playlistId);

            }

		}
		// maker.setupGUI(this,R.layout.songs,R.id.songsList,MediaStore.Audio.Genres.Members.getContentUri("external",
		// genreId),proj,null,null,null,R.layout.songs_row,new
		// String[]{MediaStore.Audio.Media.DISPLAY_NAME},views);
        searchInput = (EditText) findViewById(R.id.songSearchInput);
//        searchInput.setActivated(false);
		maker.setupGUI(this, songsUri, proj, songsSelection, songsArgs, null,
				R.layout.songs_row,R.id.songsrow,searchInput);

		songsCursor = maker.getCursor();
        songsCursor.moveToFirst();
       songDetailsList = new SongDetails[songsCursor.getCount()];
       for (int i=0;i<songsCursor.getCount();i++){
           songsCursor.moveToPosition(i);
           Log.i("1-",songsCursor.getString(1)) ;
           Log.i("0-",songsCursor.getString(0)) ;
           Log.i("3-",songsCursor.getString(3)) ;
           songDetailsList[i] = new SongDetails(songsCursor.getInt(2),songsCursor.getString(1),songsCursor.getString(0),songsCursor.getString(3));
//           songDetailsList[i].setSongData(songsCursor.getString(1)) ;
//           songDetailsList[i].setArtistName(songsCursor.getString(3));
       }
		songsView = getListView();
        songsView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i2, int i3) {
             Log.i("i - i2 - i3",i+" - "+i2+" - "+i3);
            }
        });
		songsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> adapterView, View view,
					int i, long l) {
                     Mapper song = (Mapper) songsView.getItemAtPosition(i);
				songsCursor.moveToPosition(song.getId());

//                int childrenCount  =  maker.getRowsValues().size();
//                for(int j=0;j<childrenCount;j++){
//                    LinearLayout layout = (LinearLayout) songsView.getChildAt(j);
//                    ImageView imageView  = (ImageView) layout.findViewById(R.id.marked);
//                    if(i==j){
//                        imageView.setImageResource(R.drawable.marked);
//                        Log.i("mark:",i+"");
//                    }else{
//                        imageView.setImageBitmap(null);
//                    }


//                }

//				Globals.CURRENT_SONG = songsCursor.getString(1);
//				Globals.SONG_STATE = Globals.RUNNING_SONG;
//				Globals.CURRENT_CURSOR = songsCursor;
			//	Log.i("view class", view.getClass() + "");
				Intent playerIntent = new Intent(getApplicationContext(),
						PlayerActivity.class);


              //  if(albumArtPath==null){
                    Cursor albumCursor =  managedQuery(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,new String[]{MediaStore.Audio.Albums.ALBUM_ART,MediaStore.Audio.Albums._ID},"_id=?",new String[]{songsCursor.getString(4)},null) ;
                    albumCursor.moveToFirst();
                   albumArtPath =  albumCursor.getString(0) ;
                 //   albumCursor.close();
                    // songsCursor.getPosition()
          //      }
              SongDetails currentSongDetails = new SongDetails(songsCursor.getInt(2),songsCursor.getString(1),songsCursor.getString(0),songsCursor.getString(3));
                if(Globals.CURRENT_SONGDETAILS!=null&&currentSongDetails.equals(Globals.CURRENT_SONGDETAILS)){
                //    Intent playerIntent = new Intent(this,PlayerActivity.class) ;

                        playerIntent.setAction("recover");
//            playerIntent.putExtra("albumArtPath", Globals.ALBUM_ART_PATH) ;    // songsCursor.getPosition()
//            playerIntent.putExtra("currentSongDetails", Globals.CURRENT_SONG_DETAILS);
//            playerIntent.putExtra("currentSongIndex",Globals.CURRENT_SONG_INDEX);
//            playerIntent.putExtra("songDetailsList", Globals.SONG_DETAILS_LIST);
                        startActivity(playerIntent);
                } else{
                    playerIntent.setAction("start");
                    playerIntent.putExtra("albumArtPath",albumArtPath) ;
                    playerIntent.putExtra("currentSongDetails", currentSongDetails);
                    playerIntent.putExtra("currentSongIndex",songsCursor.getPosition());
                    playerIntent.putExtra("songDetailsList", songDetailsList);

                    startActivity(playerIntent);
                }


			}
		});
	}
}