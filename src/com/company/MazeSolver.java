/**  MAZE SOLVER
 *   Author: Andrew Pauls
 *   Date: March 27, 2022
 *   School Assignment - Brock University Faculty of Computer Science
 */


/**
 * A simple program that utilizes a recursive method to navigate a possible path through a maze from point A to point B
 * utilizes a library written by Brock University Computer Science faculty for reading the input file ( BasicIO library )
 */

package com.company;
import BasicIO.*;

import static java.lang.Math.random;

public class MazeSolver {

    private ASCIIDataFile input;
    char[][] maze;
    int columns;
    int rows;
    int startingC;
    int startingR;
    int endingC;
    int endingR;

    public MazeSolver () {

        input   = new ASCIIDataFile("maze2.txt");
        rows    = input.readInt();
        columns = input.readInt();
        maze    = new char[rows][columns];

        fillInMaze();                               // read text file maze into 2x2 char array 'maze'
        getStartingCoordinates();                   // generate random valid starting position in maze
        getEndingCoordinates();                     // find the 'E' on the map
        System.out.println("Starting row: " + startingR + ", column "  + startingC );
        solveMaze(startingR, startingC);            // solves maze
    }


    // recursive algorithm to navigate from a starting position to an ending position in a 2x2 char array
    // parameters:          r = row index       c = column index
    private boolean solveMaze( int r, int c) {
        // cursor positioned on the Exit
        if ( maze[r][c] == 'E' ) {          // check if we've landed on the 'E'
            success();
            return true;}

        // cursor not positioned on an empty space
        else if (maze[r][c] != ' ') { return false; }

        // cursor blocked in
        else if (maze[r - 1][c] != ' ' && maze[r + 1][c] != ' ' && maze[r][c - 1] != ' ' && maze[r][c + 1] != ' ') {
            maze[r][c] = '.';
            return false;}

        // cursor on empty space. check all four directions.
        else {
            maze[r][c] = '>' ;  solveMaze(r, c + 1);
            maze[r][c] = '<' ;  solveMaze(r, c - 1);
            maze[r][c] = '^' ;  solveMaze(r - 1, c);
            maze[r][c] = 'v' ;  solveMaze(r + 1, c);}
        maze[r][c] = ' ' ;
        return false;
    }

    // Prints success message, halts quest, displays path, exit message
    private void success() {
        maze[startingR][startingC] = 'S';           // redraw the 'S'
        displayMaze();
        System.out.println("~~~~ MAZE EXITED ~~~~   " );
    }

    // display contents of maze array to System.out
    private void displayMaze() {
        for ( int i = 0 ; i < rows; i++ ) {
            for ( int j = 0 ; j < columns ; j++ ) {
                System.out.write(maze[i][j]);
            }
            System.out.println();
        }
    }

    // reads data into 2x2 char array
    private void fillInMaze () {
        for ( int i = 0 ; i < rows ; i++ ) {
            String theLine = input.readLine();
            char[] theLineArray = theLine.toCharArray();
            for (int j = 0; j < columns; j++) {
                maze[i][j] = theLineArray[j];
            }
        }
    }

    // generates a random row number (x coordinate) and column number (y coordinate) on an empty space in 'maze' array
    private void getStartingCoordinates() {
        startingC = (int)(columns*random());
        startingR = (int)(rows*random());
        while ( maze[startingR][startingC] == '#' ) {
            startingC = (int)(columns*random());
            startingR = (int)(rows*random());
        }
    }

    // combs through maze array and sets two global variables to the (r,c) coordinates of character 'E'
    private void getEndingCoordinates() {
        for ( int i = 0 ; i < rows ; i++ ) {
            for ( int j = 0 ; j < columns ; j++ ) {
                char theCurrent = maze[i][j];
                if (theCurrent == 'E') { endingR = i; endingC = j; }
            }
        }
    }

    public static void main (String [] args) { MazeSolver ms = new MazeSolver();}
}  // MazeSolver