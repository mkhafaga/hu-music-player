package com.xeeapps.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import com.xeeapps.service.SongDetails;

/**
 * Created with IntelliJ IDEA.
 * User: Khafaga
 * Date: 05/01/14
 * Time: 11:48 ص
 * To change this template use File | Settings | File Templates.
 */
public class PlaylistUtils {
    public static void addToPlaylist(ContentResolver resolver, SongDetails songDetails, int playlistId) {
        String[] cols = new String[]{
                "count(*)"
        };
        Uri uri = MediaStore.Audio.Playlists.Members.getContentUri("external", playlistId);
        Cursor cur = resolver.query(uri, cols, null, null, null);
        cur.moveToFirst();
        final int base = cur.getInt(0);
        cur.close();
        ContentValues values = new ContentValues();

        values.put(MediaStore.Audio.Playlists.Members.PLAY_ORDER, base);
        values.put(MediaStore.Audio.Playlists.Members.AUDIO_ID, songDetails.getId());
        Log.i("URI:", resolver.insert(uri, values) + "");


    }

    public static int createPlaylist(ContentResolver resolver, String pName) {
        Uri uri = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;
        ContentValues values = new ContentValues();
        values.put(MediaStore.Audio.Playlists.NAME, pName);
        Uri newPlaylistUri = resolver.insert(uri, values);
        Log.i("uri",newPlaylistUri+"");
                        return    new Integer(newPlaylistUri.toString().replace("content://media/external/audio/playlists/",""));

     //   Console.debug(TAG, "newPlaylistUri:" + newPlaylistUri, Console.Liviu);
    }

}
