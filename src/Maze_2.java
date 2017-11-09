/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rainara
 */

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Maze_2 extends JPanel implements Runnable{
    
    int[][]mazeM=null;
    private int boxSize=2;
    
    Maze_2(int cubeSize){
        mazeM=SolvingMaze.getInstance().getMap();
       this.boxSize=30;
    }
    
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        for(int top=0; top<mazeM.length; top++){
            for(int left =0;left<mazeM[0].length;left++){
                g.setColor(getColor(mazeM[top][left]));
                g.fill3DRect(boxSize*left, boxSize*top, boxSize, boxSize, true);
            }}}
    private Color getColor(int mapValue){
        Color color=null;
        switch(mapValue){
            case 0:
                color=Color.black;
                break;
            case 1:
                color=Color.white;
                break;
            case 2:
                color=Color.pink;
                break;
            case 3:
                color=Color.red;
                break;
            case 4:
                color=Color.magenta;
                break;
            case 5:
                color=Color.green;
                break;
            default:
                color=Color.blue;
        }
        return color;
    }
    
    @Override
    public void run(){
        while(true){
            mazeM=SolvingMaze.getInstance().getMap();
            repaint();
            try{
                Thread.sleep(2);
            }catch(Exception ex){
                
            }}}}

