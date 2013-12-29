package com.xeeapps.AmPlayer;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import com.xeeapps.service.SongDetails;

import java.util.Random;


/**
 * Created with IntelliJ IDEA.
 * User: Khafaga
 * Date: 21/12/13
 * Time: 11:50 م
 * To change this template use File | Settings | File Templates.
 */
public class MusicPlayerService extends Service implements MediaPlayer.OnCompletionListener {
    private MediaPlayer player = MediaPlayerFactory.getMediaPlayer();
    private final Binder binder =  new MusicPlayerBinder();
    private Object[] songDetailsList;
    private int currentSongIndex;
   // private String currentRepeatState =Globals.REPEAT_NONE;
    private String songState=Globals.RUNNING_SONG;
    private String shuffleState =  Globals.SHUFFLE_OFF;
    private String repeatState = Globals.REPEAT_NONE;




    private SongDetails currentSongDetails;


    public void setLooping(boolean looping){
        player.setLooping(looping);
    }

    public int getCurrentPosition() {


        return  player.getCurrentPosition();
    }



    public int getDuration() {

        return player.getDuration();
    }

    public String getSongState() {
        return songState;
    }

    public void setSongState(String songState) {
        this.songState = songState;
    }

    public SongDetails getCurrentSongDetails() {
        return currentSongDetails;
    }

    public void setCurrentSongDetails(SongDetails currentSongDetails) {
        this.currentSongDetails = currentSongDetails;
    }

    public String getRepeatState() {
        return repeatState;
    }

    public void setRepeatState(String repeatState) {
        this.repeatState = repeatState;
    }

    public String getShuffleState() {
        return shuffleState;
    }

    public void setShuffleState(String shuffleState) {
        this.shuffleState = shuffleState;
    }

//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }


    public class MusicPlayerBinder extends Binder{
        public MusicPlayerService getService(){
            return MusicPlayerService.this;
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        status =  "running";
        player.setOnCompletionListener(this);
        Bundle bundle = intent.getExtras();
        if (bundle!=null){



            songDetailsList = (Object[]) bundle.getSerializable("songDetailsList");
            setCurrentSongDetails((SongDetails) bundle.get("currentSongDetails"));
            currentSongIndex =   bundle.getInt("currentSongIndex");


                    playSong();

        }

        Notification notification = new Notification(R.drawable.appicon,"",
                System.currentTimeMillis());
        Intent notificationIntent = new Intent(this, PlayerActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notification.setLatestEventInfo(this, "",
              "", pendingIntent);

        startForeground(123, notification);

        return 0;
    }

    @Override
    public IBinder onBind(Intent intent) {
//        Bundle bundle = intent.getExtras();
//        if (bundle!=null){
//            String song  =  bundle.getString("song");
//            playSong(song);
//        }
       // startForeground();

        return binder;  //To change body of implemented methods use File | Settings | File Templates.
    }



    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.i("does it come here???", "yes");
       // if (songDetailsList==null) songDetailsList = Globals.SONG_DETAILS_LIST;
       // currentSongIndex =  Globals.CURRENT_SONG_INDEX;
        if(repeatState.equals(Globals.REPEAT_NONE)){


            if(currentSongIndex <songDetailsList.length-1){
                currentSongIndex++;
//                       Globals.CURRENT_SONG_INDEX++;
                setCurrentSongDetails((SongDetails) songDetailsList[currentSongIndex]);
                setSongState(Globals.RUNNING_SONG);

                        playSong();

                //playPauseButton.setImageResource(R.drawable.playerpause);

               // updateLyricsView();
            }else{
                currentSongIndex =0;

                setCurrentSongDetails((SongDetails) songDetailsList[0]);
                setSongState(Globals.STOPPED_SONG);
                //titleView.setText(currentSongDetails.getSongTitle());
                player.seekTo(0);
                player.stop();
             //   player.reset();
                Intent accomplishedIntent = new Intent();
                accomplishedIntent.setAction("accomplished");
                sendBroadcast(accomplishedIntent);
                setCurrentSongDetails((SongDetails) songDetailsList[0]);

             //   stopSelf();
//                updateProgressBar();
//                updateLyricsView();
            }

        }else{
            if(repeatState.equals(Globals.REPEAT_ALL)){
                if(getShuffleState().equals(Globals.SHUFFLE_OFF)){
                    if(currentSongIndex <songDetailsList.length-1){
                        currentSongIndex++;
                    }else{
                        currentSongIndex =0;
                    }
                }else{
                    Random random = new Random();
                    currentSongIndex = random.nextInt(songDetailsList.length);
                }



                setCurrentSongDetails((SongDetails) songDetailsList[currentSongIndex]);
                setSongState(Globals.RUNNING_SONG);

                        playSong();

             //   playPauseButton.setImageResource(R.drawable.playerpause);

              //  updateLyricsView();
            }
        }
//        Globals.CURRENT_SONG_INDEX =  currentSongIndex;
//        Globals.CURRENT_SONG_DETAILS =  currentSongDetails;
//        Globals.CURRENT_SONG =  currentSongDetails.getSongTitle();
    }


    public void playSong() {
//        if(player.isPlaying()){
//            player.stop();
//        }
//        playerThread =
//

                try {

//                    Uri.Builder uriBuilder =  new Uri.Builder();
//                    uriBuilder =  uriBuilder.path(songToPlay);
//
//                    player =  MediaPlayer.create(getApplicationContext(),uriBuilder.build());
                    Log.i("method:","play");
                    player.reset();
                  //  Toast.makeText(getApplicationContext(), currentSongDetails.getSongTitle(), Toast.LENGTH_LONG).show();
                    player.setDataSource(currentSongDetails.getSongData());
                    player.prepare();
                    player.start();
                      setSongState(Globals.RUNNING_SONG);


                } catch (Exception e) {
                    // e.printStackTrace(); // To change body of catch statement use
                    // File | Settings | File Templates.
                    e.printStackTrace();
                    /// Log.e(e.getMessage(), e.toString());
                }

//        playerThread.start();


            }




//    public int getCurrentPosition(){
//      return   player.getCurrentPosition();
//    }

  public void seekTo(int position){
      player.seekTo(position);
  }

//    @Override
//    public void onDestroy() {
////        status =  "stopped";
////        player.stop();
////        player.release();
//    }

           public void pause(){

//               Log.i("method:","pause");
//               new Thread(){
//                   @Override
//                   public void run() {
               songState = Globals.PAUSED_SONG;
                       player.pause();
                 //  }
            //   }.start();

           }

    public void resume(final int position){
        Log.i("method:","resume");
//        new Thread(){
//            @Override
           // public void run() {
        songState = Globals.RUNNING_SONG;
                player.seekTo(position);
                 player.start();
          //  }
       // }.start();
    }

    public void back(){
        player.stop();
       // playerThread.interrupt();
        //	songsCursor = Globals.CURRENT_CURSOR;
        if (currentSongIndex >0) {
            //Globals.CURRENT_SONG = songsCursor.getString(1);
            currentSongIndex--;

        }else{
            currentSongIndex = songDetailsList.length-1;

        }
        setCurrentSongDetails((SongDetails) songDetailsList[currentSongIndex]);
//                titleView.setText(currentSongDetails.getSongTitle());

                playSong();



        setSongState(Globals.RUNNING_SONG);


//        Globals.CURRENT_SONG_INDEX =  currentSongIndex;
//        Globals.CURRENT_SONG_DETAILS =  currentSongDetails;
//        Globals.CURRENT_SONG =  currentSongDetails.getSongTitle();

    }

    public void next(){

       //playerThread.interrupt();
        // player.stop();

//        new Thread(){
//            @Override
//            public void run() {
                if (currentSongIndex < songDetailsList.length-1) {
                    currentSongIndex++;
                }else{
                    currentSongIndex =0;

                }
                setCurrentSongDetails((SongDetails) songDetailsList[currentSongIndex]);
        setSongState(Globals.RUNNING_SONG);
        Log.i("method:","next "+ getCurrentSongDetails().getSongData());

                playSong();




//                Globals.CURRENT_SONG_INDEX =  currentSongIndex;
//                Globals.CURRENT_SONG_DETAILS =  currentSongDetails;
//                Globals.CURRENT_SONG =  currentSongDetails.getSongTitle();
}

//        }.start();


//        }

}
