package com.minecreatr.modelgen;

import javax.swing.*;
import java.io.File;

public class Main {

    public static final String NAME = "Datenegassie's Video Creator";


    public static void main(String[] args){
        JFrame frame = new JFrame(NAME);
        frame.setContentPane(new ModelGenPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setVisible(true);
    }

    public static File getDefaultFile(){
        return new File("VideoPlayer.zip");
    }
}
