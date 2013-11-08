package de.bwvaachen.winzen.lichtspiel.model;

/**
* Copyright (C) 2013 Marius Spix, Bastian Winzen, Andreas Pauls, Christian Frühholz, Georg Braun
*
* This file is part of the lichtspiel project.
*
* lichtspiel is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 2 of the License, or
* (at your option) any later version.
*
* lichtspiel is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with lichtspiel. If not, see <http://www.gnu.org/licenses/>.
*/

/**
* @file
* Light tile
*/

public class Light extends Tile {
        private int scope;
        
        public boolean isLights() {
                return true;
        }
        
        public int getScope() {
                return scope;
        }
        
        void setScope(int n) {
                scope = Math.abs(n);
        }
}