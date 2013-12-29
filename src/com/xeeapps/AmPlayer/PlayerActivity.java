package com.xeeapps.AmPlayer;

import android.app.Activity;
import android.content.*;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import com.xeeapps.service.SongDetails;
import com.xeeapps.utils.GetLyricsTask;
import com.xeeapps.utils.TimeFormatter;

/**
 * Created with IntelliJ IDEA. User: khafaga Date: 11/09/12 Time: 05:17 م To
 * change this template use File | Settings | File Templates.
 */
public class PlayerActivity extends Activity implements
        SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    private MusicPlayerService service;
    private ImageButton playPauseButton, nextButton, previousButton,
            stopButton, repeatButton, shuffleButton;
    private int currentSongPosition;
  //  private String currentRepeatState = Globals.REPEAT_NONE;
  //  private String currentShuffleState = Globals.SHUFFLE_OFF;
    private Handler handler = new Handler();
    private SeekBar progressBar;
    //	private Cursor songsCursor;
    //sprivate MediaPlayer player = MediaPlayerFactory.getMediaPlayer();
    private TextView leftDurationLbl, playedDurationLbl;
    private WebView lyricsView;
    private TextView titleView;
    private String lyricsWords="";
    private Intent serviceIntent;
    //    private int shuffleCounter;
    private Bundle bundle;
    private Object[] songDetailsList;
    private int currentSongIndex;
 //   private String songState = Globals.RUNNING_SONG;
    private SongDetails currentSongDetails;
    private String albumArtPath;
    //private Document doc;
    private  BroadcastReceiver accomplishedBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            Toast.makeText(context, "Intent Detected.", Toast.LENGTH_LONG).show();
            playPauseButton.setImageResource(R.drawable.playerplay);
            titleView.setText(service.getCurrentSongDetails().getSongTitle());
        }
    }       ;
//    public class MyReceiver extends BroadcastReceiver {
//
//        public MyReceiver(){
//            super();
//        }
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            Toast.makeText(context, "Intent Detected.", Toast.LENGTH_LONG).show();
//        }
//
//    }

    private Runnable uiUpdater = new Runnable() {

        public void run() {
//            if (service.getCurrentPosition() == 0) {
//                playPauseButton.setImageResource(R.drawable.playerplay);
//              //  updateLyricsView();
//            }

            Log.i("updating GUI", "done");
            //    if(service.getStatus().equals("running")){
            progressBar.setMax(service.getDuration());
            progressBar.setProgress(service.getCurrentPosition());
            leftDurationLbl.setText(TimeFormatter.toHoursAndMinutesAndSeconds(service.getDuration() - service.getCurrentPosition()));
            playedDurationLbl.setText(TimeFormatter.toHoursAndMinutesAndSeconds(service.getCurrentPosition()));
            //   }


            handler.postDelayed(this, 100);
        }
    };
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MusicPlayerService.MusicPlayerBinder binder = (MusicPlayerService.MusicPlayerBinder) iBinder;
            service = binder.getService();
            updateRepeatButton();
            updateShuffleButton();
            updateProgressBar();
            updateLyricsView();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    };

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            albumArtPath  /*= Globals.ALBUM_ART_PATH */ = bundle.getString("albumArtPath");

            songDetailsList /*= Globals.SONG_DETAILS_LIST*/ = (Object[]) bundle.getSerializable("songDetailsList");
            setCurrentSongDetails((SongDetails) bundle.get("currentSongDetails"));
            currentSongIndex /*= Globals.CURRENT_SONG_INDEX */ = bundle.getInt("currentSongIndex");
        }
        setContentView(R.layout.player);
        serviceIntent = new Intent(this, MusicPlayerService.class);
        serviceIntent.putExtra("song", getCurrentSongDetails().getSongData());
        serviceIntent.putExtra("currentSongDetails", getCurrentSongDetails());
        serviceIntent.putExtra("currentSongIndex", currentSongIndex);
        serviceIntent.putExtra("songDetailsList", songDetailsList);
        startService(serviceIntent);
        bindService(serviceIntent, mConnection, Context.BIND_NOT_FOREGROUND);
//        currentRepeatState =  Globals.CURRENT_REPEAT_STATE;
//        currentShuffleState = Globals.CURRENT_SHUFFLE_STATE;

        //initialize gui.
        playPauseButton = (ImageButton) findViewById(R.id.playPauseButton);
        shuffleButton = (ImageButton) findViewById(R.id.shuffleButton);
        repeatButton = (ImageButton) findViewById(R.id.repeatButton);

        lyricsView = (WebView) findViewById(R.id.webView);
        Drawable background = Drawable.createFromPath(albumArtPath);
        lyricsView.setBackgroundColor(0);
        lyricsView.setBackgroundDrawable(background);
        titleView = (TextView) findViewById(R.id.titleView);
        nextButton = (ImageButton) findViewById(R.id.nextButton);
        previousButton = (ImageButton) findViewById(R.id.previousButton);
        progressBar = (SeekBar) findViewById(R.id.progressBar);
        progressBar.setOnSeekBarChangeListener(this);
        leftDurationLbl = (TextView) findViewById(R.id.leftDurationLbl);
        playedDurationLbl = (TextView) findViewById(R.id.playedDurationLbl);


        //     updateProgressBar();

        nextButton.setOnClickListener(this);
        previousButton.setOnClickListener(this);
        playPauseButton.setOnClickListener(this);
        shuffleButton.setOnClickListener(this);
        repeatButton.setOnClickListener(this);
        IntentFilter intentFilter = new IntentFilter("accomplished") ;
        registerReceiver(accomplishedBroadcastReceiver,intentFilter);
    }

    private void updateShuffleButton() {
        if (service.getShuffleState().equals(Globals.SHUFFLE_ON)) {
            shuffleButton.setImageResource(R.drawable.shuffleon);
        } else {
            shuffleButton.setImageResource(R.drawable.shuffleoff);
        }
    }

    private void updateRepeatButton() {
        if (service.getRepeatState().equals(Globals.REPEAT_ALL)) {
            repeatButton.setImageResource(R.drawable.repeatall);
        } else if (service.getRepeatState().equals(Globals.REPEAT_CURRENT)) {
            repeatButton.setImageResource(R.drawable.repeatcurrent);
        } else {
            repeatButton.setImageResource(R.drawable.repeatnone);
        }
    }

    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {
        // TODO Auto-generated method stub

    }

    public void onStartTrackingTouch(SeekBar seekBar) {
        handler.removeCallbacks(uiUpdater);

    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        handler.removeCallbacks(uiUpdater);
        currentSongPosition = progressBar.getProgress();
        service.seekTo(currentSongPosition);
        //player.seekTo(currentSongPosition);
        updateProgressBar();

    }

    public void updateProgressBar() {
        handler.postDelayed(uiUpdater, 100);

    }

    //    public void playSong(String songToPlay) {
//		try {
////			if(!player.isPlaying()){
////            lyricsView.loadDataWithBaseURL(null,lyricsWords,"lyricsWords/html", "UTF-8",null);
//
//            if(player.isPlaying()){
//                if(Globals.CURRENT_SONG==null||Globals.CURRENT_SONG.equals(currentSongDetails.getSongTitle())){
//                    Globals.CURRENT_SONG=currentSongDetails.getSongTitle();
//                 //   Globals.CURRNT_SONGDETAILS = currentSongDetails;
//                    progressBar.setMax(player.getDuration());
//                    titleView.setText(currentSongDetails.getSongTitle());
//            updateProgressBar();
//                    return;
//                }
//            }
//          //  if(!Globals.CURRENT_SONG.equals(currentSongDetails.getSongTitle())){
//
//               ComponentName service =    startService(new Intent(this, MusicPlayerService.class));
//
//                player.reset();
//                player.setDataSource(songToPlay);
//                player.prepare();
//                player.start();
//            player.setWakeMode(this, PowerManager.PARTIAL_WAKE_LOCK);
//                progressBar.setProgress(0);
//                progressBar.setMax(player.getDuration());
//                titleView.setText(currentSongDetails.getSongTitle());
//            Globals.CURRENT_SONG=currentSongDetails.getSongTitle();
//        //    Globals.CURRNT_SONGDETAILS = currentSongDetails;
//                //  updateLyricsView();
////            lyricsView.loadData(lyricsWords,"lyricsWords/html", "UTF-8");
//                updateProgressBar();
//           // }
//
//
////			}
//
//
//		} catch (IOException e) {
//			// e.printStackTrace(); // To change body of catch statement use
//			// File | Settings | File Templates.
//			Log.e(e.getMessage(), e.toString());
//		}
//
//	}
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
    }

    public boolean updateLyricsView() {

        lyricsWords = "Some Error";


//            Log.i("song - artist", songName + " - " + artistName);
//            Log.i("link", "http://hulyrics.com/LyricsService/webresources/service/getLyricBySongCriteria?title=" + songName + "&artistName=" + artistName);
//            Log.i("artist:");
           new GetLyricsTask(lyricsView).execute(service.getCurrentSongDetails());
         //   lyricsWords = new GetLyricsTask().execute(songName, artistName).get();

        //    lyricsView.loadDataWithBaseURL(null, lyricsWords, "text/html", "UTF-8", null);
        //    Log.i("words", lyricsWords);
//            Log.i("the lyric:",lyricsWords);

        return true;
    }

    @Override
    public void onClick(View view) {

        ImageButton button = (ImageButton) view;
        if (button.getId() == R.id.playPauseButton) {

            if (service.getSongState().equals(Globals.PAUSED_SONG)) {

                playPauseButton
                        .setImageResource(R.drawable.playerpause);
                service.resume(service.getCurrentPosition());
             //   songState = Globals.RUNNING_SONG;
            } else if (service.getSongState().equals(Globals.RUNNING_SONG)) {
                playPauseButton
                        .setImageResource(R.drawable.playerplay);

                currentSongPosition = service.getCurrentPosition();
//                unbindService(mConnection);
//                stopService(serviceIntent);
                service.pause();
            //    songState = Globals.PAUSED_SONG;
            } else {
                playPauseButton
                        .setImageResource(R.drawable.playerpause);
//                serviceIntent = new Intent(this, MusicPlayerService.class);
//                currentSongDetails = (SongDetails) songDetailsList[0];
//                serviceIntent.putExtra("song", currentSongDetails.getSongData());
//                serviceIntent.putExtra("currentSongDetails", currentSongDetails);
//                serviceIntent.putExtra("currentSongIndex", 0);
//                serviceIntent.putExtra("songDetailsList", songDetailsList);
//                startService(serviceIntent);
//                bindService(serviceIntent, mConnection, Context.BIND_NOT_FOREGROUND);
               service.playSong();
           //     songState = Globals.RUNNING_SONG;


            }


        } else if (button.getId() == R.id.previousButton) {
            service.back();
            playPauseButton.setImageResource(R.drawable.playerpause);

            service.setSongState(Globals.RUNNING_SONG);
            titleView.setText(service.getCurrentSongDetails().getSongTitle());
            updateLyricsView();
        } else if (button.getId() == R.id.nextButton) {
            service.next();
            playPauseButton.setImageResource(R.drawable.playerpause);
          //  songState = Globals.RUNNING_SONG;
            service.setSongState(Globals.RUNNING_SONG);
            titleView.setText(service.getCurrentSongDetails().getSongTitle());
                 updateLyricsView();

        }  else if(button.getId()==R.id.repeatButton){
            if(service.getRepeatState().equals(Globals.REPEAT_NONE)){
                service.setRepeatState(Globals.REPEAT_CURRENT);
                service.setShuffleState(Globals.SHUFFLE_OFF);
                shuffleButton.setImageResource(R.drawable.shuffleoff);
                repeatButton.setImageResource(R.drawable.repeatcurrent);
                service.setLooping(true);

            } else if(service.getRepeatState().equals(Globals.REPEAT_CURRENT)){
                service.setLooping(false);
                service.setRepeatState( Globals.REPEAT_ALL);
                repeatButton.setImageResource(R.drawable.repeatall);
            } else{
                service.setLooping(false);
               service.setRepeatState(Globals.REPEAT_NONE);
                repeatButton.setImageResource(R.drawable.repeatnone);
                service.setShuffleState(Globals.SHUFFLE_OFF);
                shuffleButton.setImageResource(R.drawable.shuffleoff);
            }
        }else{
            if(service.getShuffleState().equals(Globals.SHUFFLE_OFF)){
                service.setShuffleState(Globals.SHUFFLE_ON) ;
                shuffleButton.setImageResource(R.drawable.shuffleon);
                repeatButton.setImageResource(R.drawable.repeatall);
                service.setRepeatState(Globals.REPEAT_ALL);
            }else{
                service.setShuffleState(Globals.SHUFFLE_OFF);
                shuffleButton.setImageResource(R.drawable.shuffleoff);
            }
        }
    }

    public SongDetails getCurrentSongDetails() {
        return currentSongDetails;
    }

    public void setCurrentSongDetails(SongDetails currentSongDetails) {
        this.currentSongDetails = currentSongDetails;
    }
}