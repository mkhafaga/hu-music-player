package com.xeeapps.HuPlayer;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
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
 * Time: 07:15 م
 * To change this template use File | Settings | File Templates.
 */
public class GenresActivity extends ListActivity {
    private ListView genresView;
    private SimpleCursorAdapter genresadapter;
    private Cursor genresCursor;
    private String genresSelection;
    private String[] genresArgs;
    private Bundle bundle;
    private EditText searchInput;
    private String[] proj={"distinct "+MediaStore.Audio.Genres.NAME,MediaStore.Audio.Genres._ID};
    private int[] views={R.id.genresrow};
    private  GUIMaker maker;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.genres);
         maker = new GUIMaker();
        bundle = getIntent().getExtras();
        if(bundle!=null){
        	genresSelection = bundle.getString("genresSelection");
        	genresArgs = bundle.getStringArray("genresArgs");
        	
        }
        searchInput = (EditText) findViewById(R.id.genreSearchInput);
//        searchInput.setActivated(false);
        maker.setupGUI(this,MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI,proj,genresSelection,genresArgs,null,R.layout.genres_row,R.id.genresrow,searchInput);

        genresCursor = maker.getCursor();
        genresView = getListView();
        genresView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
                Mapper mapper = (Mapper) genresView.getItemAtPosition(arg2);
                genresCursor.moveToPosition(mapper.getId());
				String args[] = new String[]{genresCursor.getString(0)};
//				Globals.GENRE_ID =  genresCursor.getInt(1);
				Intent mainIntent =  new Intent(getApplicationContext(),MainActivity.class);
				 mainIntent.putExtra("tabsList",new String[]{"Songs"});
				 mainIntent.putExtra("genreId",genresCursor.getInt(1) );
//				 mainIntent.putExtra("artistsSelection", "genre=?");
//				 mainIntent.putExtra("artistsArgs", args);
//				 mainIntent.putExtra("albumsSelection", "genre=?");
//				 mainIntent.putExtra("albumsSelection", args);
//				 mainIntent.putExtra("songsSelection", "genre=?");
//				 mainIntent.putExtra("songsArgs", args);
	             startActivity(mainIntent);
				
			}
		});
    }

  

}