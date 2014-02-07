package com.xeeapps.service;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Khafaga
 * Date: 17/12/13
 * Time: 09:04 م
 * To change this template use File | Settings | File Templates.
 */
public class SongDetails implements Serializable {
   private int id;
    private String songData;
    private String artistName;
    private String songTitle;

    public SongDetails(){}
    public SongDetails(int id,String songData,String songTitle, String artistName){
        this.id =  id;
        this.songData = songData;
        this.artistName = artistName;
        this.songTitle = songTitle;
    }

    public String getSongData() {
        return songData;
    }

    public void setSongData(String songData) {
        this.songData = songData;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }


    @Override
    public boolean equals(Object o) {
      SongDetails comming = (SongDetails) o;
       return  comming.getSongTitle().equals(this.getSongTitle())&&comming.getArtistName().equals(this.artistName);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
