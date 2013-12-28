package com.xeeapps.AmPlayer;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.*;
import com.xeeapps.mappers.Mapper;

/**
 * Created with IntelliJ IDEA.
 * User: khafaga
 * Date: 04/09/12
 * Time: 04:26 م
 * To change this template use File | Settings | File Templates.
 */
public class ArtistsActivity extends ListActivity {
    private ListView artistsView;
    private SimpleCursorAdapter artistsAdapter;
    private Cursor artistsCursor;
    private ArrayAdapter<String> originalAdapter;
   private String[] proj={MediaStore.Audio.Artists.ARTIST,MediaStore.Audio.Artists._ID};
 //   private String[] proj={MediaStore.Audio.Genres.ARTIST,MediaStore.Audio.Genres.Members._ID};
    private int[] views={R.id.artistsrow};
    private Bundle bundle;
    private String artistsSelection;
    private String[] artistsArgs;
    private Uri artistsUri;
    private Integer genreId;
    private EditText searchInput;
    private  GUIMaker maker;
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.artists);
        //**************************************************fake*********************************************************
//        Artist[] artists = new Artist[2];
//        artists[0] = new Artist("Paul",1);
//        artists[1] =  new Artist("Paul",2);
//        final ListView myview  = getListView();
//        ArrayAdapter<Artist> artistArrayAdapter = new ArrayAdapter<Artist>(this,R.layout.artists_row,R.id.artistsrow,artists);
//        setListAdapter(artistArrayAdapter);
//
//
//        myview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//               Artist artist = (Artist) myview.getItemAtPosition(i);
//                Log.i("current Artist: ",artist.getId()+"");
//            }
//        });
        //**************************************************************************************************************

         maker = new GUIMaker();
        artistsUri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;

        bundle = getIntent().getExtras();
        if(bundle!=null){
        	artistsSelection = bundle.getString("artistsSelection");
        	artistsArgs = bundle.getStringArray("artistsArgs");
        	genreId = (Integer) bundle.get("genreId");
        	if(genreId!=null){
            	artistsUri= MediaStore.Audio.Genres.Members.getContentUri("external", genreId);
        	}
        }
        searchInput = (EditText) findViewById(R.id.artistSearchInput);
//        searchInput.setActivated(false);

       maker.setupGUI(this,artistsUri,proj,artistsSelection, artistsArgs, null,R.layout.artists_row,R.id.artistsrow,searchInput);


        artistsView = getListView();
        artistsCursor = maker.getCursor();

        originalAdapter = (ArrayAdapter<String>) getListAdapter();
        artistsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

                 //  artistsView.getItemAtPosition(arg2) ;
                Mapper artist  = (Mapper) artistsView.getItemAtPosition(arg2);
				 artistsCursor.moveToPosition(artist.getId());
                Intent mainIntent =  new Intent(getApplicationContext(),MainActivity.class);
				 mainIntent.putExtra("tabsList",new String[]{"Albums","Songs"});
				 mainIntent.putExtra("albumsSelection", "artist=?");
				 mainIntent.putExtra("albumsArgs", new String[]{artistsCursor.getString(0)});
				 mainIntent.putExtra("songsSelection", "artist=?");
				 mainIntent.putExtra("songsArgs", new String[]{artistsCursor.getString(0)});
	             startActivity(mainIntent);

			}
		});
    }
}