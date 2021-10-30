/*
 * Copyright (c) 2021, ClockClap. All rights reserved.
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

package com.github.clockclap.gunwar.util.data.codecs;

import com.github.clockclap.gunwar.util.data.Codec;
import com.github.clockclap.gunwar.util.data.Decoder;
import com.github.clockclap.gunwar.util.data.Encoder;
import com.github.clockclap.gunwar.util.data.Serializer;

import java.io.*;
import java.util.Arrays;

public final class DefaultCodec implements Codec {

    static byte m = (byte) 0x00;

    @Override
    public Class<Object> getSubject() {
        return Object.class;
    }

    @Override
    public String getName() {
        return "DefaultCodec";
    }

    @Override
    public Encoder<DefaultCodec> encoder() {
        final DefaultCodec codec = this;
        return new Encoder<DefaultCodec>() {
            @Override
            public DefaultCodec getCodec() {
                return codec;
            }

            @Override
            public byte[] encode(byte[] o) {
                return o;
            }
        };
    }

    @Override
    public Decoder<DefaultCodec> decoder() {
        final DefaultCodec codec = this;
        return new Decoder<DefaultCodec>() {
            @Override
            public DefaultCodec getCodec() {
                return codec;
            }

            @Override
            public byte[] decode(byte[] o) {
                return o;
            }
        };
    }

    @Override
    public Serializer<?> serializer() {
        final DefaultCodec codec = this;
        return new Serializer<DefaultCodec>() {
            @Override
            public DefaultCodec getCodec() {
                return codec;
            }

            @Override
            public byte[] serialize(Object o) throws IOException {
                byte[] b = Serializer.newBytesForSerialize(m);
                byte[] c;
                try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
                     ObjectOutputStream oos = new ObjectOutputStream(bos)) {
                    oos.writeObject(o);
                    c = bos.toByteArray();
                }
                int i = 4;
                for (byte value : c) {
                    if(i >= b.length * 0.75) b = Arrays.copyOf(b, b.length + 16);
                    b[i] = value;
                    i++;
                }
                return b;
            }

            @Override
            public Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
                if(Serializer.getCodecIdByBytes(bytes) != m) throw new IllegalArgumentException("different codec");
                byte[] in = Serializer.removeCodecId(bytes);
                try (ByteArrayInputStream bis = new ByteArrayInputStream(in);
                     ObjectInputStream ois = new ObjectInputStream(bis)) {
                    return ois.readObject();
                }
            }
        };
    }

}
