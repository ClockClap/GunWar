/*
 * Copyright (c) 2021, ClockClap. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.clockclap.gunwar.util;

import com.github.clockclap.gunwar.GwAPI;

@GwAPI
public class PermissionInfo {

    private final String required;
    private final String current;
    private final boolean passed;

    public PermissionInfo(String required, String current, boolean passed) {
        this.required = required;
        this.current = current;
        this.passed = passed;
    }

    public String getRequired() {
        return required;
    }

    public String getCurrent() {
        return current;
    }

    public boolean isPassed() {
        return passed;
    }
}
