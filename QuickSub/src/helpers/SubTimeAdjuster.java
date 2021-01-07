// 
// Decompiled by Procyon v0.5.36
// 

package helpers;

import vo.TimeLineVO;
import java.awt.event.ActionEvent;
import main.MasachRashi;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JLabel;
import vo.SubVO;
import java.util.Vector;
import java.awt.event.ActionListener;
import templates.MyPanel;

public class SubTimeAdjuster extends MyPanel implements ActionListener
{
    private static final long serialVersionUID = 1L;
    private Vector<SubVO> subs;
    private JLabel originalLine;
    private JSensitiveIntField hh;
    private JSensitiveIntField mm;
    private JSensitiveIntField ss;
    private JSensitiveIntField ms;
    private JButton hUp;
    private JButton hDown;
    private JButton mUp;
    private JButton mDown;
    private JButton sUp;
    private JButton sDown;
    private JButton msUp;
    private JButton msDown;
    private JSubComboBox comboBox;
    private GridBagConstraints comboBoxConstraints;
    private GridBagConstraints originalConstraints;
    private GridBagConstraints hhConstraints;
    private GridBagConstraints b1Constraints;
    private GridBagConstraints mmConstraints;
    private GridBagConstraints b2Constraints;
    private GridBagConstraints ssConstraints;
    private GridBagConstraints b3Constraints;
    private GridBagConstraints msConstraints;
    
    public SubTimeAdjuster(final String koteret) {
        this.originalLine = new JLabel("00:00:00.000");
        this.comboBox = null;
        (this.comboBox = new JSubComboBox()).addActionListener(this);
        this.originalLine.setFont(new Font("Arial", 0, 18));
        this.hUp = new JButton("^");
        this.hDown = new JButton("v");
        this.mUp = new JButton("^");
        this.mDown = new JButton("v");
        this.sUp = new JButton("^");
        this.sDown = new JButton("v");
        this.msUp = new JButton("^");
        this.msDown = new JButton("v");
        this.hUp.addActionListener(this);
        this.hDown.addActionListener(this);
        this.mUp.addActionListener(this);
        this.mDown.addActionListener(this);
        this.sUp.addActionListener(this);
        this.sDown.addActionListener(this);
        this.msUp.addActionListener(this);
        this.msDown.addActionListener(this);
        this.hUp.setFont(new Font("Arial", 0, 12));
        this.hDown.setFont(new Font("Arial", 1, 8));
        this.mUp.setFont(new Font("Arial", 0, 12));
        this.mDown.setFont(new Font("Arial", 1, 8));
        this.sUp.setFont(new Font("Arial", 0, 12));
        this.sDown.setFont(new Font("Arial", 1, 8));
        this.msUp.setFont(new Font("Arial", 0, 12));
        this.msDown.setFont(new Font("Arial", 1, 8));
        this.hh = new JSensitiveIntField(2);
        this.mm = new JSensitiveIntField(2);
        this.ss = new JSensitiveIntField(2);
        this.ms = new JSensitiveIntField(3);
        this.hh.setFont(new Font("Arial", 0, 12));
        this.mm.setFont(new Font("Arial", 0, 12));
        this.ss.setFont(new Font("Arial", 0, 12));
        this.ms.setFont(new Font("Arial", 0, 12));
        this.hh.setRangeFrom(0);
        this.mm.setRangeFrom(0);
        this.mm.setRangeTo(59);
        this.ss.setRangeFrom(0);
        this.ss.setRangeTo(59);
        this.ms.setRangeFrom(0);
        this.ms.setRangeTo(999);
        final JPanel destHH = new JPanel(new GridLayout(2, 1));
        final JPanel destMM = new JPanel(new GridLayout(2, 1));
        final JPanel destSS = new JPanel(new GridLayout(2, 1));
        final JPanel destMS = new JPanel(new GridLayout(2, 1));
        JPanel p = new JPanel(new GridLayout(1, 2));
        p.add(this.hUp);
        p.add(this.hDown);
        destHH.add(p);
        destHH.add(this.hh);
        p = new JPanel(new GridLayout(1, 2));
        p.add(this.mUp);
        p.add(this.mDown);
        destMM.add(p);
        destMM.add(this.mm);
        p = new JPanel(new GridLayout(1, 2));
        p.add(this.sUp);
        p.add(this.sDown);
        destSS.add(p);
        destSS.add(this.ss);
        p = new JPanel(new GridLayout(1, 2));
        p.add(this.msUp);
        p.add(this.msDown);
        destMS.add(p);
        destMS.add(this.ms);
        this.setLayout(new GridBagLayout());
        this.calculatePanelsConstraints();
        this.add(this.comboBox, this.comboBoxConstraints);
        this.add(this.originalLine, this.originalConstraints);
        this.add(destHH, this.hhConstraints);
        this.add(new JLabel(":"), this.b1Constraints);
        this.add(destMM, this.mmConstraints);
        this.add(new JLabel(":"), this.b2Constraints);
        this.add(destSS, this.ssConstraints);
        this.add(new JLabel(","), this.b3Constraints);
        this.add(destMS, this.msConstraints);
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true), " " + koteret + " ", 1, 2, MasachRashi.FONT));
    }
    
    private void calculatePanelsConstraints() {
        this.comboBoxConstraints = new GridBagConstraints();
        this.comboBoxConstraints.gridx = 0;
        this.comboBoxConstraints.gridy = 0;
        this.comboBoxConstraints.gridwidth = 8;
        this.comboBoxConstraints.ipadx = 250;
        this.comboBoxConstraints.ipady = 20;
        this.comboBoxConstraints.fill = 2;
        this.comboBoxConstraints.anchor = 19;
        this.originalConstraints = new GridBagConstraints();
        this.originalConstraints.gridx = 0;
        this.originalConstraints.gridy = 1;
        this.originalConstraints.ipadx = 50;
        this.originalConstraints.ipady = 10;
        this.originalConstraints.anchor = 19;
        this.hhConstraints = new GridBagConstraints();
        this.hhConstraints.gridx = 1;
        this.hhConstraints.gridy = 1;
        this.b1Constraints = new GridBagConstraints();
        this.b1Constraints.gridx = 2;
        this.b1Constraints.gridy = 1;
        this.mmConstraints = new GridBagConstraints();
        this.mmConstraints.gridx = 3;
        this.mmConstraints.gridy = 1;
        this.b2Constraints = new GridBagConstraints();
        this.b2Constraints.gridx = 4;
        this.b2Constraints.gridy = 1;
        this.ssConstraints = new GridBagConstraints();
        this.ssConstraints.gridx = 5;
        this.ssConstraints.gridy = 1;
        this.b3Constraints = new GridBagConstraints();
        this.b3Constraints.gridx = 6;
        this.b3Constraints.gridy = 1;
        this.msConstraints = new GridBagConstraints();
        this.msConstraints.gridx = 7;
        this.msConstraints.gridy = 1;
        this.msConstraints.anchor = 17;
    }
    
    public void setSubVector(final Vector<SubVO> subs) {
        this.subs = new Vector<SubVO>(subs.size());
        for (int i = 0; i < subs.size(); ++i) {
            this.subs.add(i, new SubVO(subs.elementAt(i)));
        }
        this.comboBox.setAllData(this.subs);
    }
    
    public void setSelectedIndex(final int index) {
        this.comboBox.setSelectedIndex(index);
    }
    
    public SubVO getSelectedSub() {
        return this.comboBox.getSelectedSub();
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) {
        if (e.getSource() == this.comboBox) {
            final TimeLineVO timeLine = this.subs.elementAt(this.comboBox.getSelectedIndex()).getTimeLine();
            try {
                this.hh.setText(Utils.num2(timeLine.getStartHH()));
                this.mm.setText(Utils.num2(timeLine.getStartMM()));
                this.ss.setText(Utils.num2(timeLine.getStartSS()));
                this.ms.setText(Utils.num3(timeLine.getStartMS()));
                this.originalLine.setText(timeLine.startToString());
                this.repaint();
            }
            catch (Exception ex) {}
        }
        if (e.getSource() == this.hUp) {
            try {
                int n = Integer.parseInt(this.hh.getText());
                ++n;
                this.hh.setText(Utils.num2(n));
            }
            catch (Exception ex2) {}
        }
        if (e.getSource() == this.hDown) {
            try {
                int n = Integer.parseInt(this.hh.getText());
                if (--n < 0) {
                    n = 0;
                }
                this.hh.setText(Utils.num2(n));
            }
            catch (Exception ex3) {}
        }
        if (e.getSource() == this.mUp) {
            try {
                int n = Integer.parseInt(this.mm.getText());
                if (++n > 59) {
                    n = 0;
                }
                this.mm.setText(Utils.num2(n));
            }
            catch (Exception ex4) {}
        }
        if (e.getSource() == this.mDown) {
            try {
                int n = Integer.parseInt(this.mm.getText());
                if (--n < 0) {
                    n = 59;
                }
                this.mm.setText(Utils.num2(n));
            }
            catch (Exception ex5) {}
        }
        if (e.getSource() == this.sUp) {
            try {
                int n = Integer.parseInt(this.ss.getText());
                if (++n > 59) {
                    n = 0;
                }
                this.ss.setText(Utils.num2(n));
            }
            catch (Exception ex6) {}
        }
        if (e.getSource() == this.sDown) {
            try {
                int n = Integer.parseInt(this.ss.getText());
                if (--n < 0) {
                    n = 59;
                }
                this.ss.setText(Utils.num2(n));
            }
            catch (Exception ex7) {}
        }
        if (e.getSource() == this.msUp) {
            try {
                int n = Integer.parseInt(this.ms.getText());
                if (++n > 999) {
                    n = 0;
                }
                this.ms.setText(Utils.num3(n));
            }
            catch (Exception ex8) {}
        }
        if (e.getSource() == this.msDown) {
            try {
                int n = Integer.parseInt(this.ms.getText());
                if (--n < 0) {
                    n = 999;
                }
                this.ms.setText(Utils.num3(n));
            }
            catch (Exception ex9) {}
        }
    }
    
    public String getAdjustedStart() {
        int newHH = 0;
        int newMM = 0;
        int newSS = 0;
        int newMS = 0;
        try {
            newHH = Integer.parseInt(this.hh.getText());
            newMM = Integer.parseInt(this.mm.getText());
            newSS = Integer.parseInt(this.ss.getText());
            newMS = Integer.parseInt(this.ms.getText());
        }
        catch (Exception ex) {}
        return String.valueOf(Utils.num2(newHH)) + ":" + Utils.num2(newMM) + ":" + Utils.num2(newSS) + "," + Utils.num3(newMS);
    }
}
