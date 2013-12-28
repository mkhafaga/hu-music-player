package com.xeeapps.AmPlayer;


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
 * Date: 05/09/12
 * Time: 04:50 م
 * To change this template use File | Settings | File Templates.
 */
public class AlbumsActivity extends ListActivity {
    private ListView albumsView;
    private SimpleCursorAdapter albumsAdapter;
    private Cursor albumsCursor;
    private String[] proj = {MediaStore.Audio.Albums.ALBUM, MediaStore.Audio.Albums._ID,MediaStore.Audio.Albums.ALBUM_ART};
    private int[] views = {R.id.albumsrow};
    private String albumsSelection;
    private String[] albumsArgs;
    private Uri albumsUri;
    private EditText searchInput;
    private Bundle bundle;
    private Integer genreId;
    private GUIMaker maker;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.albums);
         maker = new GUIMaker();
        albumsUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        bundle = getIntent().getExtras();
        if (bundle != null) {
            albumsSelection = bundle.getString("albumsSelection");
            albumsArgs = bundle.getStringArray("albumsArgs");
            genreId = (Integer) bundle.get("genreId");
//            if (genreId != null) {
//                proj = new String[]{MediaStore.Audio.Genres.Members.ALBUM, MediaStore.Audio.Genres.Members._ID,MediaStore.Audio.Genres.Members.ARTIST};
//                albumsUri = MediaStore.Audio.Genres.Members.getContentUri("external", genreId);
//            }
        }

        searchInput = (EditText) findViewById(R.id.albumSearchInput);
//        searchInput.setActivated(false);
        maker.setupGUI(this, albumsUri, proj, albumsSelection, albumsArgs, null, R.layout.albums_row,R.id.albumsrow, searchInput);

        albumsView = getListView();
        albumsCursor = maker.getCursor();
        albumsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
               // albumsCursor.moveToPosition(maker.getRowsValues().indexOf(albumsView.getItemAtPosition(arg2)));
                Mapper album = (Mapper) albumsView.getItemAtPosition(arg2);
                albumsCursor.moveToPosition(album.getId());

//                Log.i("zzz", "value selected :"+albumsView.getItemAtPosition(arg2) + " - " + "index: "+maker.getRowsValues().indexOf(albumsView.getItemAtPosition(arg2) + " - album:" + albumsCursor.getString(0)));
                              Log.i("zzz", "value selected :" + albumsView.getItemAtPosition(arg2) + " - " + "album:" + albumsCursor.getString(0) + " - " + maker.getRowsValues().indexOf(albumsView.getItemAtPosition(arg2)) + "list count: " + albumsView.getCount() + " - cursor count: " + albumsCursor.getCount());

                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                mainIntent.putExtra("tabsList", new String[]{"Songs"});
                mainIntent.putExtra("songsSelection", "album_id=? AND album=?");
                mainIntent.putExtra("songsArgs", new String[]{albumsCursor.getString(1),albumsCursor.getString(0)});
                mainIntent.putExtra("albumArtPath",albumsCursor.getString(2));
                startActivity(mainIntent);

            }
        });

    }
}