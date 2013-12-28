package com.xeeapps.utils;

public class TimeFormatter {
	public static String toHoursAndMinutesAndSeconds(int milliseconds){
		String formattedTime ="";
		int hours = milliseconds/3600000;
		int minutes = (milliseconds%3600000)/60000;
		int seconds =  (milliseconds - ((hours*3600000)+(minutes*60000)))/1000;
		if(hours==0){
			formattedTime =  minutes+":"+seconds;
		}else if (minutes==0){
			formattedTime = ""+seconds;
		}else{
			formattedTime = hours+":"+minutes+":"+seconds;
		}
        String[] formattedSlices = formattedTime.split(":");
        formattedTime="";
        for(int i=0;i<formattedSlices.length;i++){
            if (formattedSlices[i].length()<2){
                formattedSlices[i]="0"+formattedSlices[i];
            }
            if(i==formattedSlices.length-1)
                formattedTime+=formattedSlices[i];
            else
             formattedTime+=formattedSlices[i]+":";
        }
		
		return formattedTime;
	}

}
