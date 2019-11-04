/*
 * Copyright (C) 2009 Roman Masek
 *
 * This file is part of OpenSudoku.
 *
 * OpenSudoku is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OpenSudoku is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenSudoku.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.diwayou.game.sudoku.game;

/**
 * Some information about folder, used in FolderListActivity.
 *
 * @author romario
 */
public class FolderInfo {

    /**
     * Primary key of folder.
     */
    public long id;

    /**
     * Name of the folder.
     */
    public String name;

    /**
     * Total count of puzzles in the folder.
     */
    public int puzzleCount;

    /**
     * Count of solved puzzles in the folder.
     */
    public int solvedCount;

    /**
     * Count of puzzles in "playing" state in the folder.
     */
    public int playingCount;

    public FolderInfo() {

    }

    public FolderInfo(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getDetail() {
        return null;
    }

}
