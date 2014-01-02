package com.xeeapps.HuPlayer;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import com.xeeapps.mappers.Mapper;

/**
 * Created with IntelliJ IDEA.
 * User: khafaga
 * Date: 04/09/12
 * Time: 06:18 م
 * To change this template use File | Settings | File Templates.
 */
public class PlaylistsActivity extends ListActivity {
    private ListView playlistsView;
    private SimpleCursorAdapter playlistsAdapter;
    private Cursor playlistsCursor;
    private String[] proj={MediaStore.Audio.Playlists.NAME,MediaStore.Audio.Playlists.Members._ID};
    private int[] views={R.id.playlistsrow};
    private Uri playlistsUri;
    private Bundle bundle;
    private EditText searchInput;
    private GUIMaker maker;
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlists);
         maker = new GUIMaker();
      bundle = getIntent().getExtras();
        if(bundle!=null){

        }
        playlistsUri = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;
//        if (bundle != null) {
//            songsSelection = bundle.getString("songsSelection");
//            songsArgs = bundle.getStringArray("songsArgs");
//            genreId = (Integer) bundle.get("genreId");
//            if (genreId != null) {
//                songsUri = MediaStore.Audio.Genres.Members.getContentUri(
//                        "external", genreId);
//                Log.i("Hereeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee", ""
//                        + genreId);
//                // songsUri =
//                // MediaStore.Audio.//Genres.Members.getContentUri("external",
//                // genreId);
//            }
//
//        }
        // maker.setupGUI(this,R.layout.songs,R.id.songsList,MediaStore.Audio.Genres.Members.getContentUri("external",
        // genreId),proj,null,null,null,R.layout.songs_row,new
        // String[]{MediaStore.Audio.Media.DISPLAY_NAME},views);
  searchInput = (EditText) findViewById(R.id.playlistSearchInput);
        maker.setupGUI(this, playlistsUri, proj, null, null, null, R.layout.playlists_row, R.id.playlistsrow,searchInput);
//
      playlistsCursor = maker.getCursor();

        playlistsView = getListView();
        Log.i("Does it come here?", "Yes");
        playlistsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Mapper playlist = (Mapper) playlistsView.getItemAtPosition(arg2);
                playlistsCursor.moveToPosition(playlist.getId());
                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                mainIntent.putExtra("tabsList", new String[]{"Songs"});
//               mainIntent.putExtra("songsSelection", MediaStore.Audio.Playlists._ID+"=?");
                mainIntent.putExtra("playlistId",playlistsCursor.getInt(1));
               // mainIntent.putExtra("songsArgs", new String[]{""+playlistsCursor.getInt(1)});
                startActivity(mainIntent);

            }
        });
    }
}