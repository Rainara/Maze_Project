/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rainara
 */

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {
    
    private static int recSize=30;
    private static boolean solvedOrNot = true;
    
    public static void main(String[]args){
        final JButton jB=new JButton("BEGIN GAME");
        final Thread threadM= new Thread(new Runnable(){
            public void run(){
                while (true)
                    if(solvedOrNot){
                        if(SolvingMaze.getInstance().solveMaze(
                                SolvingMaze.getInstance().getInitialDirection(),SolvingMaze.getInstance().getStartPoint()))
                            JOptionPane.showMessageDialog(null, "You won the Game!!");
                        else
                            JOptionPane.showMessageDialog(null,"You Loose!");
                        
                        jB.setText("Restart");
                        solvedOrNot = false;
                    }else{
                        try{
                            Thread.sleep(100);
                            
                        }catch(InterruptedException e){
                            JOptionPane.showMessageDialog(null, e);
                        }}}});
        jB.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae){
                if(jB.getText()=="BEGIN GAME"){
                    threadM.start();
                    jB.setText("STOP");
                }else if(jB.getText()=="STOP"){
                    threadM.suspend();
                    jB.setText("RESUME");
                }else if(jB.getText()=="RESUME"){
                    threadM.resume();
                    jB.setText("STOP");
                }else if(jB.getText()=="RETRY"){
                    SolvingMaze.getInstance().restart();
                    solvedOrNot = true;
                    jB.setText("STOP");
                }}});
        JFrame jFrame =new JFrame("Maze Game");
        jFrame.setLayout(new BorderLayout());
        Maze_2 mzpanel=new Maze_2(recSize);
        mzpanel.setLayout(null);
        jFrame.add(mzpanel,BorderLayout.CENTER);
        jFrame.add(jB,BorderLayout.SOUTH);
        int[][]mazeM=SolvingMaze.getInstance().getMap();
        jFrame.setSize(mazeM[0].length* recSize + 10,mazeM.length * recSize+60);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        mzpanel.run();
    }
}

