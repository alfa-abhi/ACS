package com.google.engedu.puzzle8;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

import static java.lang.Math.abs;


public class PuzzleBoard {

    private static final int NUM_TILES = 3;
    private static final int[][] NEIGHBOUR_COORDS = {
            { -1, 0 },
            { 1, 0 },
            { 0, -1 },
            { 0, 1 }
    };
    private ArrayList<PuzzleTile> tiles;
    private PuzzleBoard prevoiusPuzzleBoard=null;
    private int steps=0;
    private final PuzzleBoard aNull = null;


    PuzzleBoard(Bitmap bitmap, int parentWidth) {
        tiles=new ArrayList<>();
        int size=parentWidth/NUM_TILES;
        Bitmap scaleBitmap= Bitmap.createScaledBitmap(bitmap,parentWidth,parentWidth,true);
        for(int i=0;i<NUM_TILES*NUM_TILES-1;i++)
        {  int x=i%NUM_TILES;
           int y=i/NUM_TILES;
            Bitmap tileBitmap=Bitmap.createBitmap(scaleBitmap,x*size,y*size,size,size) ;
            PuzzleTile tile=new PuzzleTile(tileBitmap,i);
            tiles.add(tile);

        }
        tiles.add(null);



    }

    PuzzleBoard(PuzzleBoard otherBoard) {
        tiles = (ArrayList<PuzzleTile>) otherBoard.tiles.clone();
        prevoiusPuzzleBoard=otherBoard;
        steps=getSteps()+1;
    }

    public void reset() {

    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        return tiles.equals(((PuzzleBoard) o).tiles);
    }

    public void draw(Canvas canvas) {
        if (tiles == null) {
            return;
        }
        for (int i = 0; i < NUM_TILES * NUM_TILES; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile != null) {
                tile.draw(canvas, i % NUM_TILES, i / NUM_TILES);
            }
        }
    }

    public boolean click(float x, float y) {
        for (int i = 0; i < NUM_TILES * NUM_TILES; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile != null) {
                if (tile.isClicked(x, y, i % NUM_TILES, i / NUM_TILES)) {
                    return tryMoving(i % NUM_TILES, i / NUM_TILES);
                }
            }
        }
        return false;
    }

    private boolean tryMoving(int tileX, int tileY) {
        for (int[] delta : NEIGHBOUR_COORDS) {
            int nullX = tileX + delta[0];
            int nullY = tileY + delta[1];
            if (nullX >= 0 && nullX < NUM_TILES && nullY >= 0 && nullY < NUM_TILES &&
                    tiles.get(XYtoIndex(nullX, nullY)) == null) {
                swapTiles(XYtoIndex(nullX, nullY), XYtoIndex(tileX, tileY));
                return true;
            }

        }
        return false;
    }

    public boolean resolved() {
        for (int i = 0; i < NUM_TILES * NUM_TILES - 1; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile == null || tile.getNumber() != i)
                return false;
        }
        return true;
    }

    private int XYtoIndex(int x, int y) {
        return x + y * NUM_TILES;
    }

    protected void swapTiles(int i, int j) {
        PuzzleTile temp = tiles.get(i);
        tiles.set(i, tiles.get(j));
        tiles.set(j, temp);
    }

    public ArrayList<PuzzleBoard> neighbours() {
        PuzzleBoard neighbourBoard;
        ArrayList<PuzzleBoard> neighbours=new ArrayList<>();
        int emptyTileX=0;
        int emptyTileY=0;
        for(int i=0;i<NUM_TILES*NUM_TILES;i++)
            if(tiles.get(i)== null)
            {   emptyTileX=i%NUM_TILES;
                emptyTileY=i/NUM_TILES;
                break;
            }

        for(int[] delta: NEIGHBOUR_COORDS){
            int neighbourX=emptyTileX +delta[0];
            int neighbourY=emptyTileY +delta[1];
            if(neighbourX>=0 &&  neighbourX <NUM_TILES && neighbourY>=0 && neighbourY< NUM_TILES) {
                neighbourBoard = new PuzzleBoard(this);
                neighbourBoard.swapTiles(XYtoIndex(emptyTileX, emptyTileY), XYtoIndex(neighbourX, neighbourY));
                neighbours.add(neighbourBoard);
            }
        }

        return neighbours;
    }

    public int priority() {
         int distance=0;
         for (int i=0;i<NUM_TILES*NUM_TILES;i++) {

             PuzzleTile tile = tiles.get(i);
             if (tile != null) {

                 int previousTileX = i % NUM_TILES;
                 int previousTileY = i / NUM_TILES;
                 int currentTileX = tile.getNumber() % NUM_TILES;
                 int currentTileY = tile.getNumber() / NUM_TILES;

                 distance = abs(previousTileX - currentTileX) + abs(previousTileY - currentTileY);
             }
         }

        return  distance + getSteps();
    }

    public int getSteps() {
        return steps;
    }

    public PuzzleBoard getPreviousPuzzleBoard(){
        return prevoiusPuzzleBoard;
    }
}
