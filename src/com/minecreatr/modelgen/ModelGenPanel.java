package com.minecreatr.modelgen;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;

/**
 * Panel for the gui
 */
public class ModelGenPanel extends JPanel {


    class TextClearFocusListener implements FocusListener {

        private ModelGenPanel panel;
        private JTextField field;
        private String originalText;
        private boolean num;

        public TextClearFocusListener(ModelGenPanel panel, JTextField field, boolean num){
            this.panel = panel;
            this.field = field;
            this.originalText = field.getText();
            this.num = num;
        }

        @Override
        public void focusGained(FocusEvent e){
            if (field.getText().equalsIgnoreCase(originalText)){
                field.setText("");
            }
        }

        @Override
        public void focusLost(FocusEvent e){
            if (field.getText().equals("") || (!isNumber(field.getText()) && this.num)){
                field.setText(originalText);
            }
            if (!isNumber(field.getText()) && this.num){
                panel.addMessage("Must be an Integer!", true);
            }
            else {
                panel.addMessage("", false);
            }
        }

    }

    private boolean isNumber(String v){
        try {
            Integer.parseInt(v);
            return true;
        } catch (Exception exception){
            return false;
        }
    }


    private JLabel messageLabel;

    public ModelGenPanel(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel label = new JLabel(Main.NAME);
        label.setAlignmentX(CENTER_ALIGNMENT);
        label.setAlignmentY(CENTER_ALIGNMENT);
        this.add(label);

        final JTextField numOfFrames = new JTextField("Number of frames in the video");
        numOfFrames.addFocusListener(new TextClearFocusListener(this, numOfFrames, true));
        numOfFrames.setAlignmentX(CENTER_ALIGNMENT);
        numOfFrames.setAlignmentY(CENTER_ALIGNMENT);
        this.add(numOfFrames);

        final JTextField framerate = new JTextField("Framerate");
        framerate.addFocusListener(new TextClearFocusListener(this, framerate, true));
        framerate.setAlignmentX(CENTER_ALIGNMENT);
        framerate.setAlignmentY(CENTER_ALIGNMENT);
        this.add(framerate);

        final JTextField fileName = new JTextField("FileName");
        fileName.addFocusListener(new TextClearFocusListener(this, fileName, false));
        fileName.setAlignmentX(CENTER_ALIGNMENT);
        fileName.setAlignmentY(CENTER_ALIGNMENT);
        this.add(fileName);

        messageLabel = new JLabel("");
        messageLabel.setAlignmentX(CENTER_ALIGNMENT);
        messageLabel.setAlignmentY(CENTER_ALIGNMENT);
        this.add(messageLabel);

        JButton submitButton = new JButton("Submit");
        submitButton.setAlignmentX(CENTER_ALIGNMENT);
        submitButton.setAlignmentY(CENTER_ALIGNMENT);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isNumber(numOfFrames.getText())){
                    addMessage("Number of frames must be an Integer!", true);
                }
                else if (!isNumber(framerate.getText())){
                    addMessage("Framerate must be an Integer!", true);
                }
                try {
                    float num = ModelGenerator.generateModelRP(new File(fileName.getText()+".zip"), Integer.parseInt(numOfFrames.getText()), Integer.parseInt(framerate.getText()));
                    JOptionPane.showMessageDialog(null, "Succesfully created zip" + fileName.getText() + ".zip, Commandblock number is " + num + " , it has been copied to your clipboard", "Number", JOptionPane.INFORMATION_MESSAGE);
                    StringSelection selection = new StringSelection(""+num);
                    Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clpbrd.setContents(selection, null);
                    addMessage("Number is " + num, false);
                } catch (Exception exception){
                    JOptionPane.showMessageDialog(null, exception.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        this.add(submitButton);

        this.setFocusable(true);
    }

    public void addMessage(String message, boolean error){
        messageLabel.setText(message);
        if (error) {
            messageLabel.setForeground(Color.RED);
        }
        else {
            messageLabel.setForeground(Color.BLACK);
        }
        revalidate();
        repaint();
    }
}
