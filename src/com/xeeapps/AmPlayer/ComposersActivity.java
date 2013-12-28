package com.xeeapps.AmPlayer;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ListView;

/**
 * Created with IntelliJ IDEA.
 * User: khafaga
 * Date: 08/09/12
 * Time: 02:51 ص
 * To change this template use File | Settings | File Templates.
 */
public class ComposersActivity extends ListActivity {
    private ListView composersView;
    private Cursor composersCursor;
    private String[] proj={MediaStore.Audio.Media.COMPOSER,MediaStore.Audio.Media._ID};
    private String composersSelection;
    private String[] composersArgs;
    private Bundle bundle;
    public void onCreate(Bundle savedInstanceState) {
//    	 super.onCreate(savedInstanceState);
//        GUIMaker maker = new GUIMaker();
//        bundle = getIntent().getExtras();
//        if(bundle!=null){
//
//        }
//        maker.setupGUI(this,MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,proj,"composer!=? AND composer!=?" , new String[]{"null",""},null,R.layout.composers_row);
//        composersCursor = maker.getCursor();
//        composersView = getListView();
//        composersView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				composersCursor.moveToPosition(arg2);
//				String args[] = new String[]{composersCursor.getString(0),"null",""};
//				Intent mainIntent =  new Intent(getApplicationContext(),MainActivity.class);
//				 mainIntent.putExtra("tabsList",new String[]{"Artists","Albums","Songs"});
//				 mainIntent.putExtra("artistsSelection", "composer=? AND composer!=? AND composer!=?");// AND composer!=? AND composer!=?"
//				 mainIntent.putExtra("artistsArgs", args);
//				 mainIntent.putExtra("albumsSelection", "composer=? AND composer!=? AND composer!=?");
//				 mainIntent.putExtra("albumsSelection", args);
//				 mainIntent.putExtra("songsSelection", "composer=? AND composer!=? AND composer!=?");
//				 mainIntent.putExtra("songsArgs", args);
//				 startActivity(mainIntent);
//
//			}
//		});
    }
}