// 
// Decompiled by Procyon v0.5.36
// 

package test;

import helpers.Utils;
import java.util.ArrayList;

public class Test
{
    public static void main(final String[] args) {
        final String s = "{50}{200}:\u05d4\u05ea\u05e8\u05d2\u05d5\u05dd \u05e0\u05e7\u05e8\u05e2 \u05e2\"\u05d9|MoviezFreak";
        System.out.println(s);
        int i = s.indexOf("}") + 1;
        i = i + s.substring(i).indexOf("}") + 1;
        System.out.println(s.substring(i));
        System.out.println(i);
        String line = "{50}{200}:\u05d4\u05ea\u05e8\u05d2\u05d5\u05dd \u05e0\u05e7\u05e8\u05e2 \u05e2\"\u05d9|MoviezFreak";
        final String[] pack = new String[6];
        final ArrayList<String> parts = new ArrayList<String>();
        String start = null;
        String end = null;
        boolean isPack = false;
        try {
            i = line.indexOf("{");
            if (i >= 0) {
                line = line.substring(i + 1);
                i = line.indexOf("}");
                if (i >= 0) {
                    start = line.substring(0, i);
                    i = line.indexOf("{");
                    if (i >= 0) {
                        line = line.substring(i + 1);
                        i = line.indexOf("}");
                        if (i >= 0) {
                            end = line.substring(0, i);
                            i = line.indexOf("}");
                            line = line.substring(i + 1);
                            if (line.length() > 0) {
                                isPack = true;
                                final double startTime = Double.parseDouble(start) * 1000.0 / 25.0;
                                final double endTime = Double.parseDouble(end) * 1000.0 / 25.0;
                                parts.add(String.valueOf(Utils.miliSecondsToTimeLine(startTime)) + " --> " + Utils.miliSecondsToTimeLine(endTime));
                                while (line.indexOf("|") >= 0) {
                                    parts.add(line.substring(0, line.indexOf("|")));
                                    line = line.substring(line.indexOf("|") + 1);
                                }
                                parts.add(line);
                            }
                        }
                    }
                }
            }
            if (isPack) {
                for (int j = 0; j < parts.size() && j < 6; ++j) {
                    pack[j] = parts.get(j);
                }
                System.out.println("{" + pack[0] + "}" + pack[1] + " | " + pack[2] + " | " + pack[3] + " | " + pack[4] + " | " + pack[5]);
            }
            else {
                System.out.println("not qa ack");
            }
        }
        catch (Exception ex) {}
    }
}
