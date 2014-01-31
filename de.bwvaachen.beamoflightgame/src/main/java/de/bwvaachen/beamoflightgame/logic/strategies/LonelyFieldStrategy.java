package de.bwvaachen.beamoflightgame.logic.strategies;

/*
Copyright (C) 2013 - 2014 by Andreas Pauls, Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import java.util.Map;

import de.bwvaachen.beamoflightgame.helper.BoardTraverser;
import de.bwvaachen.beamoflightgame.helper.IndexedMap;
import de.bwvaachen.beamoflightgame.helper.TraverseDirection;
import de.bwvaachen.beamoflightgame.logic.UnsolvablePuzzleException;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.ITileState;
import de.bwvaachen.beamoflightgame.model.LightTile;
import de.bwvaachen.beamoflightgame.model.LightTileState;
import de.bwvaachen.beamoflightgame.model.NumberTile;

public class LonelyFieldStrategy extends AbstractStrategy<LightTileState> {
        private BoardTraverser traverser;
        
        @Override
        protected void _init() {
                traverser = tile.getTraverser();
        }
        
        private boolean doesCross(ITileState a, ITileState b) {
            return !( (a==LightTileState.EMPTY) || (a.equals(b)) );
    }
        
        private void fillBoard(NumberTile neighbour, LightTileState direction) {
                TraverseDirection traverseDirection = direction.getTraverseDirection();
                
                traverser.reset();
                ITile<?> next = traverser.get();
                for (int i = neighbour.getRemainingLightRange(); i>0; --i) {
                        if(next == neighbour) break; //We already reached the neighbour: then break
                        traverser.shift(traverseDirection);
                        assert next instanceof LightTile;
                        ((LightTile) next).setState(direction.reverse(), true);
                        next = traverser.get();
                }
        }
        
        private NumberTile findNeighbour(LightTileState lts) {
            ITile<?> currentTile = null;

            traverser.reset();
            
            while (traverser.shift(lts.getTraverseDirection())) {
                    currentTile = traverser.get();
                    if(currentTile instanceof LightTile) //found a LightTile
                    {
                            //Does another LightTile cross the line?
                            //Then stop looking for the neighbour.
                            if (doesCross(currentTile.getTileState(), lts.reverse() ))
                                    return null;
                    }
                    else if(currentTile instanceof NumberTile) { //found an NumberTile
                             //This is our neighbour.
                            return (NumberTile) currentTile;
                    }
            }
            //We have reached the end of the board ...
            //So there is no neighbour.
         return null;
    }

    @Override
        public double getComplexity() {
                return 2.0;
        }
    
    
    private int getDistance(ITile<?> neighbour, LightTileState lts) {
                int distance = 0;
                traverser.reset();                
                traverser.moveTo(neighbour.getX(), neighbour.getY());
                while (traverser.shift(lts.reverse().getTraverseDirection())) {
                        if(traverser.get().getTileState() != lts ) { //ignore the same tile state!
                                distance++;
                        }
                        if(traverser.getX() == traverser.getStartX() && traverser.getY() == traverser.getStartY())
                        	break;
                }
                return distance;
        }


        @Override
        public boolean isApplicableForTile(ITile<?> t) {
                return (t instanceof LightTile) && (t.getTileState() == LightTileState.EMPTY);
        }
        
        @Override
        public boolean tryToSolve() throws UnsolvablePuzzleException {
         NumberTile neighbour = null;
         IndexedMap<LightTileState,NumberTile> neighbours
                 = new IndexedMap<LightTileState,NumberTile>();
        
         for(LightTileState lts : LightTileState.allDirections()) { 
        	 
                 neighbour = findNeighbour(lts);

                 if (neighbour != null) {
                         //determine the distance to the neighbour NumberTile.
                         int remainingLightRange = neighbour.getRemainingLightRange();
                         int distance = getDistance(neighbour,lts);
                                                 
                         System.out.println(String.format("LTS: %s Distance: %d Remaining: %d",lts,distance,remainingLightRange));                       
                         System.out.println(neighbour.getBoard().toString());
                         
                         if (remainingLightRange > 0 && distance <= remainingLightRange) {
                                 neighbours.put(lts,neighbour);
                         }         
                 }
                 
                 if (neighbours.size() > 1) break;
         }
        
         switch(neighbours.size()) {
         case 0: throw new UnsolvablePuzzleException(tile); //The tile is unreachable
         case 1: { //only one neighbour can reach this field
                 Map.Entry<LightTileState,NumberTile> entry = neighbours.getEntryByIndex(0);
                 fillBoard(entry.getValue(), entry.getKey());
                 return true;
                 }
         default: return false; //Ambiguous solution => try next step
         }

        }
}