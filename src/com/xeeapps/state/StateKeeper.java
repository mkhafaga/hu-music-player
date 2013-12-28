package com.xeeapps.state;

/**
 * Created with IntelliJ IDEA.
 * User: Khafaga
 * Date: 27/12/13
 * Time: 04:25 ص
 * To change this template use File | Settings | File Templates.
 */
public class StateKeeper {
    private static final StateKeeper stateKeeper= new StateKeeper();
    private static final String RUNNING_SONG = "running";
    //public static String CURRENT_SONG=null;
    // public static String CURRENT_REPEAT_STATE="repeatNone";
    // public static String CURRENT_SHUFFLE_STATE = "shuffleOff";
    // public static String ALBUM_ART_PATH  = "";
    //  public static  Object[] SONG_DETAILS_LIST =null;
    // public static SongDetails CURRENT_SONG_DETAILS = null;
    //   public static Integer CURRENT_SONG_INDEX = null;
    public static String REPEAT_ALL="repeatAll";
    public static String REPEAT_CURRENT="repeatCurrent";
    public static String REPEAT_NONE="repeatNone";
    public static String SHUFFLE_ON="shuffleOn";
    public static String SHUFFLE_OFF="shuffleOff";
    public static String PAUSED_SONG="paused";
    public static String STOPPED_SONG="stopped";
    private StateKeeper(){}
    public static StateKeeper getInstance(){
        return  stateKeeper;
    }
}
