// 
// Decompiled by Procyon v0.5.36
// 

package helpers;

import java.util.Iterator;
import java.util.ArrayList;
import java.io.Serializable;

public class SubtitlePack implements Serializable
{
    private static final long serialVersionUID = 1L;
    private Integer lineNum;
    private String timeLine;
    private ArrayList<String> subtitles;
    
    public SubtitlePack() {
        this.lineNum = null;
        this.timeLine = null;
        this.subtitles = null;
        this.lineNum = 0;
        this.timeLine = "00:00:00.000 --> 00:00:00.000";
        this.subtitles = new ArrayList<String>();
    }
    
    public Integer getLineNum() {
        return this.lineNum;
    }
    
    public void setLineNum(final Integer lineNum) {
        this.lineNum = lineNum;
    }
    
    public String getTimeLine() {
        return this.timeLine;
    }
    
    public void setTimeLine(final String timeLine) {
        this.timeLine = timeLine;
    }
    
    public ArrayList<String> getSubtitles() {
        return this.subtitles;
    }
    
    public String[] getSubtitlesArray() {
        if (this.subtitles == null) {
            return null;
        }
        final String[] s = new String[this.subtitles.size()];
        final Iterator<String> i = this.subtitles.iterator();
        int j = 0;
        while (i.hasNext()) {
            s[j] = i.next();
            ++j;
        }
        return s;
    }
    
    public void setSubtitles(final ArrayList<String> subtitles) {
        this.subtitles = subtitles;
    }
    
    public String addSubtitle(final String subtitle) {
        this.subtitles.add(subtitle);
        return subtitle;
    }
    
    public static void main(final String[] a) {
        final SubtitlePack s = new SubtitlePack();
        s.getSubtitlesArray();
        System.out.println("");
    }
}
