/*
 * Copyright (c) 2021. ClockClap. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package com.github.clockclap.gunwar.util.data;

import com.github.clockclap.gunwar.GwAPI;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@GwAPI(since = 2)
public interface Serializer<C> {

    C getCodec();

    byte[] serialize(Object o) throws IOException;

    Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException;

    default void serialize(Object o, OutputStream output) throws IOException {
        byte[] b = serialize(o);
        output.write(b);
    }

    default Object deserialize(InputStream input) throws IOException, ClassNotFoundException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buf = new byte['?'];
            int length;
            while ((length = input.read(buf)) > 0) {
                bos.write(buf, 0, length);
            }
            byte[] b = bos.toByteArray();
            return deserialize(b);
        }
    }

}
