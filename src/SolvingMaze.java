/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rainara
 */

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class SolvingMaze {
        private static SolvingMaze solvingMaze= null;
        private int[][]mazeM=null;
        private Point startP=null;
        private int sleepT=700;
        
        private static final int wall=0;
        private static final int empty=1;
        private static final int pass=2;
        private static final int wrong=3;
        private static final int startp=4;
        private static final int goal=5;
        
        private static final int north=0;
        private static final int south=180;
        private static final int east= 270;
        private static final int west=90;
        
        private static final int right=90;
        private static final int straight=0;
        private static final int left=270;
        
        
        
        private SolvingMaze(){
            mazeM=readFromFile("maze1.txt");
            startP=initialStartPoint();
            
        }
        public static SolvingMaze getInstance(){
            if(solvingMaze==null){
                solvingMaze=new SolvingMaze();
               
            } return solvingMaze;
        }
        
        private int[][]readFromFile(String fileName){
            File fileMap=new File(fileName);
            BufferedReader bufferedReader=null;
            
            try{
                bufferedReader=new BufferedReader(new FileReader(fileMap));
                String tempString=null;
                String[]tempLineStr=null;
                int[]tempLineInt=null;
                ArrayList<int[]> arrayList=new ArrayList<int[]>();
                while((tempString=bufferedReader.readLine())!=null){
                    tempLineStr=tempString.split(" ");
                    tempLineInt=new int[tempLineStr.length];
                    for(int i=0;i< tempLineInt.length;i++)
                        tempLineInt[i]=Integer.parseInt(tempLineStr[i]);
                        arrayList.add(tempLineInt);
                    
                }
                mazeM=new int[arrayList.size()][tempLineInt.length];
                for(int i=0;i<arrayList.size();i++)
                    mazeM[i]=arrayList.get(i);
                
            }catch(Exception ex){
                System.out.println(ex);
                mazeM=new int[1][1];
                mazeM[0][0]=SolvingMaze.wall;
            }
            return mazeM;
        }
        private Point initialStartPoint(){
            for(int i=0;i<mazeM.length;i++){
                if(mazeM[i][0]==SolvingMaze.startp)
                    return new Point(i,0);
                if(mazeM[i][mazeM[0].length-1]==SolvingMaze.startp)
                    return new Point(i,mazeM[0].length-1);
            }
            
            for(int i=0;i<mazeM[0].length;i++){
                if(mazeM[0][i]==SolvingMaze.startp)
                    return new Point(0,i);
                if(mazeM[mazeM.length - 1][i]==SolvingMaze.startp)
                    return new Point(mazeM.length-1,i);
            }
            return null;
        }
        
        public int getInitialDirection(){
            Point startP=initialStartPoint();
            if(startP.x==mazeM.length)
                return SolvingMaze.south;
            else if(startP.x==0)
                return SolvingMaze.north;
            else if(startP.y==mazeM[0].length)
                return SolvingMaze.east;
            else if(startP.y==0)
                return SolvingMaze.west;
               
             return 0;
        }
        
        private boolean checkGoal(Point currentPoint){
            if(mazeM[currentPoint.x+1][currentPoint.y]==SolvingMaze.goal
                    || mazeM[currentPoint.x-1][currentPoint.y]==SolvingMaze.goal
                     || mazeM[currentPoint.x][currentPoint.y+1]==SolvingMaze.goal
                     || mazeM[currentPoint.x][currentPoint.y-1]==SolvingMaze.goal)
                return true;
            return false;
        }
        private boolean checkPoint(int x, int y){
            if(mazeM[x][y]==SolvingMaze.empty)
                return true;
            return false;
        }
        private boolean checkNextMove(int direction,int turning,Point currentPoint){
            int tempDirection=direction+turning;
            tempDirection %= 360;
            Point tempPoint=currentPoint;
            
            switch(tempDirection){
                case SolvingMaze.north:
                    return checkPoint(tempPoint.x-1,tempPoint.y);
                case SolvingMaze.east:
                    return checkPoint(tempPoint.x,tempPoint.y+1);
                case SolvingMaze.south:
                    return checkPoint(tempPoint.x+1,tempPoint.y);
                case SolvingMaze.west:
                    return checkPoint(tempPoint.x,tempPoint.y-1);
            }return false;
        }
        private Point setNextPoint(int direction,Point currentPoint){
            Point newPoint=new Point(currentPoint);
            switch(direction){
                case SolvingMaze.north:
                    newPoint.x -= 1;
                    break;
                case SolvingMaze.south:
                    newPoint.x += 1;
                    break;
                case SolvingMaze.east:
                    newPoint.y += 1;
                    break;
                case SolvingMaze.west:
                    newPoint.y -= 1;
                    break;
            }
            return newPoint;
        }
        
        public boolean solveMaze(int direction, Point currentPoint){
            if((startP != currentPoint) && checkGoal(currentPoint)){
                return true;
            }
            //Right
            if(checkNextMove(direction,SolvingMaze.right,currentPoint)){
                int nextDirection=(direction+90)% 360;
                Point nextCurrentPoint=setNextPoint(nextDirection,currentPoint);
                mazeM[nextCurrentPoint.x][nextCurrentPoint.y]=SolvingMaze.pass;
                try{
                    Thread.sleep(sleepT);
                    
                }catch(InterruptedException ie){
                    System.out.println(ie);
                }
                if(solveMaze(nextDirection,nextCurrentPoint))
                    return true;
            }
            //Straight
            if(checkNextMove(direction,SolvingMaze.straight,currentPoint)){
                int nextDirection=(direction+0)%360;
                 Point nextCurrentPoint=setNextPoint(nextDirection,currentPoint);
                mazeM[nextCurrentPoint.x][nextCurrentPoint.y]=SolvingMaze.pass;
                try{
                    Thread.sleep(sleepT);
                    
                }catch(InterruptedException ie){
                    System.out.println(ie);
                }
                if(solveMaze(nextDirection,nextCurrentPoint))
                    return true;
            }
            //left
             if(checkNextMove(direction,SolvingMaze.left,currentPoint)){
                int nextDirection=(direction+270)%360;
                 Point nextCurrentPoint=setNextPoint(nextDirection,currentPoint);
                mazeM[nextCurrentPoint.x][nextCurrentPoint.y]=SolvingMaze.pass;
                try{
                    Thread.sleep(sleepT);
                    
                }catch(InterruptedException ie){
                    System.out.println(ie);
                }
                if(solveMaze(nextDirection,nextCurrentPoint))
                    return true;
            }
             mazeM[currentPoint.x][currentPoint.y]=SolvingMaze.wrong;
             try{
                 Thread.sleep(sleepT);
            }catch(InterruptedException ie){
                ie.printStackTrace();
            }
             return false;
             
        }
        public int [][]getMap(){
            return mazeM;
        }
        public Point getStartPoint(){
            return startP;
        }
        public void restart(){
            mazeM=readFromFile("maze1.txt");
        }
}

