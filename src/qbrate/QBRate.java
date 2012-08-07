/*
 * Program QB Rating
 * Programmer: Dominque Terry
 * Description: Calculates a quaterback's rating.
 */
package qbrate;


import javax.swing.*;
import java.lang.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.text.*;
import java.awt.event.*;

public class QBRate {

    private JFrame myFrame;
    private JLabel PassAttLabel;
    private JLabel PassComLabel;
    private JLabel YardsLabel;
    private JLabel touchLabel;
    private JLabel INTlabel;
    private JLabel Total;
    private JLabel Total2;
    private JButton calcButton;
    private JButton exitButton;
    private JButton clear;
    private JTextField myText1;
    private JTextField myText2;
    private JTextField myText3;
    private JTextField myText4;
    private JTextField myText5;
    private JTextField myText6;
    private ActionListener chandler;
    private ActionListener ehandler;
    private String passAtt;
    private String passCom;
    private String YardsGain;
    private String touch;
    private String Int;
    private float Attempts;
    private float Completed;
    private float Gained;
    private float TD;
    private float INT;

    public QBRate() 
    {
        myFrame = new JFrame("QB Rating Calculator");
        PassAttLabel = new JLabel("Enter pass attempts");
        PassComLabel = new JLabel("Enter passes completed");
        YardsLabel = new JLabel("Enter yards gained");
        touchLabel = new JLabel("Enter number of touchdowns");
        INTlabel = new JLabel("Enter number of interceptions");
        Total = new JLabel("QB Rating");
        Total2 = new JLabel();
        calcButton = new JButton("Calculate Rating");
        exitButton = new JButton("Exit");
        clear = new JButton("Clear");
        myText1 = new JTextField(10);
        myText2 = new JTextField(10);
        myText3 = new JTextField(10);
        myText4 = new JTextField(10);
        myText5 = new JTextField(10);

        Container c = myFrame.getContentPane();
        c.setLayout(new GridLayout(10, 10));
        myFrame.getContentPane().setBackground(Color.YELLOW);
        c.add(PassAttLabel).setForeground(Color.red);
        c.add(myText1);
        c.add(PassComLabel).setForeground(Color.red);
        c.add(myText2);
        c.add(YardsLabel).setForeground(Color.red);
        c.add(myText3);
        c.add(touchLabel).setForeground(Color.red);
        c.add(myText4);
        c.add(INTlabel).setForeground(Color.red);
        c.add(myText5);
        c.add(Total).setForeground(Color.red);
        c.add(Total2);
        c.add(calcButton).setForeground(Color.red);
        c.add(exitButton).setForeground(Color.red);
        c.add(clear).setForeground(Color.red);
        calcButton.setMnemonic('c');
        exitButton.setMnemonic('e');
        myFrame.setSize(800, 600);
        myFrame.addWindowFocusListener(new WindowAdapter() 
        {

            public void windowClosing(WindowEvent e) 
            {
                System.exit(0);
            }
        });
        calcButton.addActionListener(new calculateButtonHandler());
        calcButton.addActionListener(chandler);
        clear.addActionListener(new calculateButtonHandler());
        clear.addActionListener(chandler);
        ExitButtonHandler ehandler = new ExitButtonHandler();
        exitButton.addActionListener(ehandler);
        FocusHandler fhandler = new FocusHandler();
        myText1.addFocusListener(fhandler);
        myText2.addFocusListener(fhandler);
        myText3.addFocusListener(fhandler);
        myText4.addFocusListener(fhandler);
        myText5.addFocusListener(fhandler);
        myFrame.show();
    }

    float CompletionRating() 
    {
        // Calculates the completion Ratio
        return ((Completed / Attempts * 100) - 30) * .05f;
    }

    float YardsRating() 
    {
        //Calculates the yards Ratio
        return ((Gained / Attempts) - 3) * .25f;
    }

    float touchdownRating() 
    {
        //calculates touchdown Ratio
        return (TD / Attempts * 100) * .2f;
    }

    float interceptionRating() 
    {
        //calculates interception ratio
        return 2.375f - ((INT / Attempts * 100) * .25f);
    }

    float qb_rating() 
    {
        //calulates the completion rato, yards ratio, touchdown ratio, and interception ratio
        return ((CompletionRating() + YardsRating() + touchdownRating() + interceptionRating()) / 6)*100;
    }

    class calculateButtonHandler implements ActionListener 
    {

        public calculateButtonHandler chandler;

        public void actionPerformed(ActionEvent e) 
        {
            try 
            {
                DecimalFormat num = new DecimalFormat(",###.##");
                passAtt = myText1.getText();
                passCom = myText2.getText();
                YardsGain = myText3.getText();
                touch = myText4.getText();
                Int = myText5.getText();
                Attempts = Float.parseFloat(passAtt);
                Completed = Float.parseFloat(passCom);
                Gained = Float.parseFloat(YardsGain);
                TD = Float.parseFloat(touch);
                INT = Float.parseFloat(Int);
                if (e.getSource() == calcButton) 
                {
                    Total2.setText(num.format(qb_rating()));
                }
                if (e.getSource() == clear) 
                {
                    myText1.setText("");
                    myText2.setText("");
                    myText3.setText("");
                    myText4.setText("");
                    myText5.setText("");
                    Total2.setText("");
                }
                try 
                {
                    String value = Total2.getText();
                    File file = new File("QBRateStats");
                    FileWriter fstream = new FileWriter(file, true);
                    BufferedWriter out = new BufferedWriter(fstream);
                    out.write("\nTotal Completions = " + CompletionRating() + "\nTotal Yards = " + YardsRating() + "\nTotal Touchdowns = "
                            + touchdownRating() + "\nTotal Interceptions = " + interceptionRating() + "\nQuarterback rating is:" + qb_rating());
                    out.newLine();
                    out.close();
                } catch (Exception exc) 
                {
                }
            } catch (NumberFormatException ex) 
            {
                JOptionPane.showMessageDialog(null, "Please enter data in each field");
            }
        }
    }

    class ExitButtonHandler implements ActionListener 
    {

        public ExitButtonHandler ehandler;

        public void actionPerformed(ActionEvent e) 
        {
            System.exit(0);
        }
    }
   
    class FocusHandler implements FocusListener {

        public void focusGained(FocusEvent e) 
        {
            if (e.getSource() == myText1 || e.getSource() == myText2 || e.getSource() == myText3 || e.getSource() == myText4 || e.getSource() == myText5) 
            {
                Total2.setText("");
            } else if (e.getSource() == Total2) 
            {
                Total2.setNextFocusableComponent(calcButton);
                calcButton.grabFocus();
            }
        }


        public void focusLost(FocusEvent e) 
        {
        }
    }
    public static void main(String[] args) 
    {
        QBRate qb = new QBRate();
        
    }
}

