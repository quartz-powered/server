/**
 * This file is a component of Quartz Powered, this license makes sure any work
 * associated with Quartz Powered, must follow the conditions of the license included.
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Quartz Powered
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.quartzpowered.protocol.data;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum GameState {
    INVALID_BED(0),
    END_RAINING(1),
    BEGIN_RAINING(2),
    CHANGE_GAMEMODE(3),
    ENTER_CREDITS(4),
    DEMO_MESSAGE(5),
    ARROW_HITING_PLAYER(6),
    FADE_VALUE(7),
    FADE_TIME(8),
    PLAY_MOB_APPEARANCE(10);

    @Getter
    private final int id;

    private GameState(int id) {
        this.id = id;
    }

    private static final Map<Integer, GameState> idMap = new HashMap<>();

    static {
        for (GameState gameState : values()) {
            idMap.put(gameState.id, gameState);
        }
    }

    public static GameState fromId(int id) {
        return idMap.get(id);
    }
}
