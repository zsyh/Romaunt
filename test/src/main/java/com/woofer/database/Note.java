package com.woofer.database;

/**
 * Created by YOURFATHERME on 2016/5/17.
 */

import android.text.format.Time;
public class Note {
    private int ID;
    private String dateTimeStr;
    private String noteData;
    private String imgUrl;
    /*日期字串*/
    private String dateStr;
    /*时间字串*/
    private String timeStr;
    private Time time = new Time();
    private String theme;
    private String lable;
    private int publicstatus;
    private int uploadflag;


    /**
     * 得到note的标题
     *
     * @return theme
    */

    public void setUploadflag(int uploadflag){
        this.uploadflag = uploadflag;
    }
    public int getUploadflag(){
        return uploadflag;
    }
    public void setPublicstatus(int status){
        this.publicstatus = status;
    }
    public int getPublicstatus(){
        return publicstatus;
    }


    public void setLable(String lable){
        this.lable = lable;
    }
    public  String getLable(){
        return lable;
    }
    public String getTheme(){
        return theme;
    }
    public void setTheme(String theme){
        this.theme=theme;
    }

    /**
    * 得到该记录id
    *
    * @return ID
    */
    public int getID(){
        return  ID;
    }
    /**
     * 设置记录id
     *
     * @param ID
     */
    public void setID(int ID){
        this.ID=ID;
    }
    /**
     *获得数据
     *
     * @return noteData
     */
    public String getNoteData(){
        return noteData;
    }
    /**
     * 设置日记数据
     *
     * @param noteData
     */
    public void setNoteData(String noteData){
        this.noteData = noteData;
    }
    /**
     * 获取图片的地址
     *
     * @return imgUrl
     */
    public String getImgUrl(){
        return imgUrl;
    }
    /**
     * 设置图片地址
     *
     * @param imgUrl
     */
    public void setImgUrl(String imgUrl){
        this.imgUrl=imgUrl;
    }
    /**
     *获取日期时间
     *
     * @return dateTimeStr
     */
    public String getDateTimeStr(){
        return dateTimeStr;
    }
    /**
     *设置日期时间
     *
     * @param dateTimeStr
     */
    public void setDateTimeStr(String dateTimeStr){
        this.dateTimeStr=dateTimeStr;
        initDateAndTime();
    }
    /**
     *初始化时间
     * ——————————
     * !!!!!!修改！！！！！
     * ——————————
     */
    public void initDateAndTime(){
        /*split 将一个字符串分割成字符串数组*/
        try {
            String[] dateTimeStrs = dateTimeStr.split(" ");
            String[] dates = dateTimeStrs[0].split("-");
            String[] times = dateTimeStrs[1].split(":");
            this.time.year = Integer.parseInt(dates[0]);
            this.time.month = Integer.parseInt(dates[1]);
            this.time.monthDay = Integer.parseInt(dates[2]);
            this.time.hour = Integer.parseInt(times[0]);
            this.time.minute = Integer.parseInt(times[1]);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        /*
        *为什么用try语句就可以访问this.time.month
        * */
        if(this.time.month<10){
            /*月份字串双位数表示*/
            this.dateStr = "0"+time.month+"/";
        }else {
            this.dateStr = time.month+"/";
        }
        if(this.time.monthDay<10){
            this.dateStr = this.dateStr+"0"+this.time.monthDay;
        }else{
            this.dateStr=this.dateStr+this.time.monthDay;
        }
        if (this.time.hour<10){
            this.timeStr = "0"+this.time.hour+":";
        }else{
            this.timeStr=this.time.hour+":";
        }
        if(this.time.minute<10){
            this.timeStr=this.timeStr+"0"+this.time.minute;
        }else{
            this.timeStr=this.timeStr+this.time.minute;
        }
    }

    public  String getDateStr(){
        return dateStr;
    }
    public  String getTimeStr(){
        return timeStr;
    }
    public Time getTime(){
        return time;
    }
}