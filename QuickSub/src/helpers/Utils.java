// 
// Decompiled by Procyon v0.5.36
// 

package helpers;

import vo.TimeLineVO;
import java.util.ArrayList;
import java.net.URL;
import javax.swing.ImageIcon;
import java.io.File;

public class Utils
{
    public static final String sub = "SUB";
    public static final String srt = "SRT";
    public static final String txt = "TXT";
    public static final String NUMBER = "0123456789";
    
    public static String getExtension(final File f) {
        String ext = null;
        final String s = f.getName();
        final int i = s.lastIndexOf(46);
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }
    
    protected static ImageIcon createImageIcon(final String path) {
        final URL imgURL = Utils.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        }
        System.err.println("Couldn't find file: " + path);
        return null;
    }
    
    public static boolean isTimeLine(final String s) {
        return s != null && s.length() == 29 && (s.charAt(2) == ':' && s.charAt(5) == ':' && s.charAt(19) == ':' && s.charAt(22) == ':');
    }
    
    public static boolean isTime(final String s) {
        if (s == null || s.length() != 12) {
            return false;
        }
        if (s.charAt(2) != ':' || s.charAt(5) != ':' || s.charAt(8) != ',') {
            return false;
        }
        final String hour = s.substring(0, 2);
        final String min = s.substring(3, 5);
        final String sec = s.substring(6, 8);
        final String milisec = s.substring(9, 12);
        return isNumeric(hour) && isNumeric(min) && isNumeric(sec) && isNumeric(milisec) && isNumeric(hour) && Integer.parseInt(min) <= 59 && Integer.parseInt(min) >= 0 && Integer.parseInt(sec) <= 59 && Integer.parseInt(min) >= 0;
    }
    
    public static boolean isBlanks(final String s) {
        final char[] line = s.toCharArray();
        for (int i = 0; i < line.length; ++i) {
            if (line[i] != ' ') {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isNumeric(final String s) {
        if (s == null) {
            return false;
        }
        for (int i = 0; i < s.length(); ++i) {
            final char c = s.charAt(i);
            if ("0123456789".indexOf(c) < 0) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean notNumeric(final String s) {
        return !isNumeric(s);
    }
    
    public static String ajustTime(final String timeLine, final double a, final double b) throws Exception {
        final String dugma = "00:00:00,000 --> 00:00:00,000";
        Double startTime = 0.0;
        Double endTime = 0.0;
        if (timeLine == null) {
            throw new Exception("Null has been sent");
        }
        if (timeLine.length() != dugma.length()) {
            throw new Exception("Time line has not the right length");
        }
        if (timeLine.charAt(2) != ':' || timeLine.charAt(5) != ':' || timeLine.charAt(19) != ':' || timeLine.charAt(22) != ':' || timeLine.charAt(8) != ',' || timeLine.charAt(25) != ',' || timeLine.charAt(12) != ' ' || timeLine.charAt(16) != ' ' || timeLine.charAt(13) != '-' || timeLine.charAt(14) != '-' || timeLine.charAt(15) != '>') {
            throw new Exception("Time line has not the right charachters");
        }
        startTime = (double)timeLineToMiliSeconds(timeLine.substring(0, 12));
        endTime = (double)timeLineToMiliSeconds(timeLine.substring(17, 29));
        startTime = startTime * a + b;
        endTime = endTime * a + b;
        return String.valueOf(miliSecondsToTimeLine(startTime)) + " --> " + miliSecondsToTimeLine(endTime);
    }
    
    public static int timeLineToMiliSeconds(final String timeLine) throws Exception {
        final String dugma = "00:00:00,000";
        int miliseconds = 0;
        if (timeLine == null) {
            throw new Exception("Null has been sent");
        }
        if (timeLine.length() != dugma.length()) {
            throw new Exception("Time line has not the right length");
        }
        if (timeLine.charAt(2) != ':' || timeLine.charAt(5) != ':' || timeLine.charAt(8) != ',') {
            throw new Exception("Time line has not the right charachters");
        }
        final String hour = timeLine.substring(0, 2);
        final String min = timeLine.substring(3, 5);
        final String sec = timeLine.substring(6, 8);
        final String milisec = timeLine.substring(9, 12);
        if (!isNumeric(hour) || !isNumeric(min) || !isNumeric(sec) || !isNumeric(milisec) || !isNumeric(hour)) {
            throw new Exception("Time line is invalid");
        }
        miliseconds = Integer.parseInt(timeLine.substring(0, 2)) * 3600000 + Integer.parseInt(timeLine.substring(3, 5)) * 60000 + Integer.parseInt(timeLine.substring(6, 8)) * 1000 + Integer.parseInt(timeLine.substring(9, 12)) * 1;
        return miliseconds;
    }
    
    public static String miliSecondsToTimeLine(final double miliSeconds) throws Exception {
        final String[] timeLine = new String[4];
        final int timeH = (int)(miliSeconds / 3600000.0);
        final int timeM = (int)((miliSeconds - timeH * 3600000) / 60000.0);
        final int timeS = (int)((miliSeconds - timeH * 3600000 - timeM * 60000) / 1000.0);
        final int timeMS = (int)(miliSeconds - timeH * 3600000 - timeM * 60000 - timeS * 1000);
        Integer current = timeH;
        if (current == 0) {
            timeLine[0] = "00:";
        }
        if (current > 0 && current < 10) {
            timeLine[0] = "0" + current.toString() + ":";
        }
        if (current >= 10) {
            timeLine[0] = String.valueOf(current.toString()) + ":";
        }
        current = timeM;
        if (current == 0) {
            timeLine[1] = "00:";
        }
        if (current > 0 && current < 10) {
            timeLine[1] = "0" + current.toString() + ":";
        }
        if (current >= 10) {
            timeLine[1] = String.valueOf(current.toString()) + ":";
        }
        current = timeS;
        if (current == 0) {
            timeLine[2] = "00,";
        }
        if (current > 0 && current < 10) {
            timeLine[2] = "0" + current.toString() + ",";
        }
        if (current >= 10) {
            timeLine[2] = String.valueOf(current.toString()) + ",";
        }
        current = timeMS;
        if (current == 0) {
            timeLine[3] = "000";
        }
        if (current > 0 && current < 10) {
            timeLine[3] = "00" + current.toString();
        }
        if (current >= 10 && current < 100) {
            timeLine[3] = "0" + current.toString();
        }
        if (current >= 100) {
            timeLine[3] = current.toString();
        }
        return String.valueOf(timeLine[0]) + timeLine[1] + timeLine[2] + timeLine[3];
    }
    
    public static SubtitlePack stringToSubtitlePack(String line, final Integer lineNum) throws Exception {
        final ArrayList<String> parts = new ArrayList<String>();
        SubtitlePack pack = null;
        int i = 0;
        try {
            while (line != null && line.length() != 0) {
                if (i == 0) {
                    parts.add(line.substring(0, 1));
                    line = line.substring(1);
                }
                if (i == 1) {
                    parts.add(line.substring(0, line.indexOf(125)));
                    line = line.substring(line.indexOf(125));
                }
                if (i == 2) {
                    parts.add(line.substring(0, 2));
                    line = line.substring(2);
                }
                if (i == 3) {
                    parts.add(line.substring(0, line.indexOf(125)));
                    line = line.substring(line.indexOf(125));
                }
                if (i == 4) {
                    parts.add(line.substring(0, 1));
                    line = line.substring(1);
                }
                if (i > 4) {
                    while (line.length() != 0 && line.charAt(0) == '|') {
                        line = line.substring(1);
                    }
                    final int last = line.indexOf(124);
                    if (last < 0) {
                        parts.add(line);
                        line = "";
                    }
                    else {
                        parts.add(line.substring(0, last));
                        line = line.substring(last + 1);
                    }
                }
                ++i;
            }
            final double startTime = Double.parseDouble(parts.get(1)) * 1000.0 / 25.0;
            final double endTime = Double.parseDouble(parts.get(3)) * 1000.0 / 25.0;
            pack = new SubtitlePack();
            pack.setLineNum(lineNum);
            pack.setTimeLine(String.valueOf(miliSecondsToTimeLine(startTime)) + " --> " + miliSecondsToTimeLine(endTime));
            for (int j = 5; j < parts.size(); ++j) {
                pack.addSubtitle(parts.get(j));
            }
        }
        catch (Exception e) {
            throw new Exception(String.valueOf(e.getMessage()) + "\n" + "packing subtitle not successfull.\nLine number: " + lineNum.toString() + "\nfor line: " + line);
        }
        return pack;
    }
    
    public static boolean isSign(final String value) {
        if (value == null) {
            return false;
        }
        for (int i = 0; i < value.length(); ++i) {
            final char c = value.charAt(i);
            if ("\\|`~!@#$%^&*()-_=+[{]}'\"|/?.>,<:;".indexOf(c) < 0) {
                return false;
            }
        }
        return true;
    }
    
    public static TimeLineVO stringToTimeLine(final String timeline) {
        TimeLineVO timeLineVO = null;
        if (isTimeLine(timeline)) {
            try {
                final int startHH = Integer.parseInt(timeline.substring(0, 2));
                final int startMM = Integer.parseInt(timeline.substring(3, 5));
                final int startSS = Integer.parseInt(timeline.substring(6, 8));
                final int startMS = Integer.parseInt(timeline.substring(9, 12));
                final int endHH = Integer.parseInt(timeline.substring(17, 19));
                final int endMM = Integer.parseInt(timeline.substring(20, 22));
                final int endSS = Integer.parseInt(timeline.substring(23, 25));
                final int endMS = Integer.parseInt(timeline.substring(26, 29));
                timeLineVO = new TimeLineVO(startHH, startMM, startSS, startMS, endHH, endMM, endSS, endMS);
            }
            catch (Exception ex) {}
        }
        return timeLineVO;
    }
    
    public static String timeLineToString(final TimeLineVO timeLineVO) {
        if (timeLineVO != null) {
            final String startHH = intToString(timeLineVO.getStartHH(), 2);
            final String startMM = intToString(timeLineVO.getStartMM(), 2);
            final String startSS = intToString(timeLineVO.getStartSS(), 2);
            final String startMS = intToString(timeLineVO.getStartMS(), 3);
            final String endHH = intToString(timeLineVO.getEndHH(), 2);
            final String endMM = intToString(timeLineVO.getEndMM(), 2);
            final String endSS = intToString(timeLineVO.getEndSS(), 2);
            final String endMS = intToString(timeLineVO.getEndMS(), 3);
            return String.valueOf(startHH) + ":" + startMM + ":" + startSS + "." + startMS + " --> " + endHH + ":" + endMM + ":" + endSS + "." + endMS;
        }
        return null;
    }
    
    private static String intToString(final int num, final int size) {
        String s = Integer.toString(num);
        if (s.length() > size) {
            s = s.substring(s.length() - size, s.length());
        }
        for (int i = s.length(); i < size; ++i) {
            s = "0" + s;
        }
        return s;
    }
    
    public static String num2(final int n) {
        String num;
        try {
            num = Integer.toString(n);
        }
        catch (Exception ex) {
            num = "00";
        }
        if (num.length() == 0) {
            num = "00";
        }
        if (num.length() == 1) {
            num = "0" + num;
        }
        return num;
    }
    
    public static String num3(final int n) {
        String num;
        try {
            num = Integer.toString(n);
        }
        catch (Exception ex) {
            num = "000";
        }
        if (num.length() == 0) {
            num = "000";
        }
        if (num.length() == 1) {
            num = "00" + num;
        }
        if (num.length() == 2) {
            num = "0" + num;
        }
        return num;
    }
}
