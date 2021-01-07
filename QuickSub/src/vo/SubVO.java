// 
// Decompiled by Procyon v0.5.36
// 

package vo;

import java.io.Serializable;

public class SubVO implements Serializable
{
    private static final long serialVersionUID = -6177374479363967366L;
    private int subNum;
    private TimeLineVO timeLine;
    private String line1;
    private String line2;
    private String line3;
    private String line4;
    
    public SubVO() {
    }
    
    public SubVO(final int subNum, final TimeLineVO timeLine, final String line1, final String line2, final String line3, final String line4) {
        this.subNum = subNum;
        this.timeLine = timeLine.getTimeLine();
        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
        this.line4 = line4;
    }
    
    public SubVO(final int subNum, final TimeLineVO timeLine, final String line1, final String line2, final String line3) {
        this(subNum, timeLine, line1, line2, line3, "");
    }
    
    public SubVO(final int subNum, final TimeLineVO timeLine, final String line1, final String line2) {
        this(subNum, timeLine, line1, line2, "");
    }
    
    public SubVO(final int subNum, final TimeLineVO timeLine, final String line1) {
        this(subNum, timeLine, line1, "");
    }
    
    public SubVO(final SubVO subVo) {
        this(subVo.getSubNum(), subVo.getTimeLine(), subVo.getLine1(), subVo.getLine2(), subVo.getLine3(), subVo.getLine4());
    }
    
    public int getSubNum() {
        return this.subNum;
    }
    
    public TimeLineVO getTimeLine() {
        return this.timeLine.getTimeLine();
    }
    
    public String getLine1() {
        return this.line1;
    }
    
    public String getLine2() {
        return this.line2;
    }
    
    public String getLine3() {
        return this.line3;
    }
    
    public String getLine4() {
        return this.line4;
    }
    
    public void setSubNum(final int subNum) {
        this.subNum = subNum;
    }
    
    public void setTimeLine(final TimeLineVO timeLine) {
        this.timeLine = timeLine.getTimeLine();
    }
    
    public void setLine1(final String line1) {
        this.line1 = line1;
    }
    
    public void setLine2(final String line2) {
        this.line2 = line2;
    }
    
    public void setLine3(final String line3) {
        this.line3 = line3;
    }
    
    public void setLine4(final String line4) {
        this.line4 = line4;
    }
}
