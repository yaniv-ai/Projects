// 
// Decompiled by Procyon v0.5.36
// 

package vo;

import helpers.Utils;
import java.io.Serializable;

public class TimeLineVO implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int startHH;
    private int startMM;
    private int startSS;
    private int startMS;
    private int endHH;
    private int endMM;
    private int endSS;
    private int endMS;
    
    public TimeLineVO(final int startHH, final int startMM, final int startSS, final int startMS, final int endHH, final int endMM, final int endSS, final int endMS) {
        this.startHH = startHH;
        this.startMM = startMM;
        this.startSS = startSS;
        this.startMS = startMS;
        this.endHH = endHH;
        this.endMM = endMM;
        this.endSS = endSS;
        this.endMS = endMS;
    }
    
    public int getStartHH() {
        return this.startHH;
    }
    
    public int getStartMM() {
        return this.startMM;
    }
    
    public int getStartSS() {
        return this.startSS;
    }
    
    public int getStartMS() {
        return this.startMS;
    }
    
    public int getEndHH() {
        return this.endHH;
    }
    
    public int getEndMM() {
        return this.endMM;
    }
    
    public int getEndSS() {
        return this.endSS;
    }
    
    public int getEndMS() {
        return this.endMS;
    }
    
    public void setStartHH(final int startHH) {
        this.startHH = startHH;
    }
    
    public void setStartMM(final int startMM) {
        this.startMM = startMM;
    }
    
    public void setStartSS(final int startSS) {
        this.startSS = startSS;
    }
    
    public void setStartMS(final int startMS) {
        this.startMS = startMS;
    }
    
    public void setEndHH(final int endHH) {
        this.endHH = endHH;
    }
    
    public void setEndMM(final int endMM) {
        this.endMM = endMM;
    }
    
    public void setEndSS(final int endSS) {
        this.endSS = endSS;
    }
    
    public void setEndMS(final int endMS) {
        this.endMS = endMS;
    }
    
    public TimeLineVO getTimeLine() {
        return new TimeLineVO(this.startHH, this.startMM, this.startSS, this.startMS, this.endHH, this.endMM, this.endSS, this.endMS);
    }
    
    public String startToString() {
        return String.valueOf(Utils.num2(this.startHH)) + ":" + Utils.num2(this.startMM) + ":" + Utils.num2(this.startSS) + "," + Utils.num3(this.startMS);
    }
    
    public String endToString() {
        return String.valueOf(Utils.num2(this.endHH)) + ":" + Utils.num2(this.endMM) + ":" + Utils.num2(this.endSS) + "," + Utils.num3(this.endMS);
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.startToString()) + " --> " + this.endToString();
    }
}
