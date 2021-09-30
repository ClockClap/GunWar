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

package com.github.clockclap.gunwar.util.data.codecs;

import com.github.clockclap.gunwar.GwAPI;
import com.github.clockclap.gunwar.util.data.Codec;
import com.github.clockclap.gunwar.util.data.Decoder;
import com.github.clockclap.gunwar.util.data.Encoder;
import com.github.clockclap.gunwar.util.data.Serializer;

import java.io.*;
import java.util.*;

@GwAPI(since = 2)
public final class MapCodec implements Codec<HashMap<Object, Object>> {

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Encoder<MapCodec> encoder() {
        final MapCodec codec = this;
        return new Encoder<MapCodec>() {
            @Override
            public MapCodec getCodec() {
                return codec;
            }

            @Override
            public byte[] encode(byte[] o) {
                byte[] b = new byte[o.length * 2];
                for(int i = 0, j = 0, k = 1; i < o.length; i++, k += 2, j += 2) {
                    int c = ((int) o[i]) & 0xFF;
                    int c1;
                    int c2;
                    if(c > 127) {
                        c1 = 127;
                        c2 = c - 127;
                    } else {
                        c1 = c;
                        c2 = 0;
                    }
                    b[j] = (byte) c2;
                    b[k] = (byte) c1;
                }
                return b;
            }
        };
    }

    @Override
    public Decoder<MapCodec> decoder() {
        final MapCodec codec = this;
        return new Decoder<MapCodec>() {
            @Override
            public MapCodec getCodec() {
                return codec;
            }

            @Override
            public byte[] decode(byte[] o) {
                byte[] b = new byte[o.length / 2];
                for(int i = 1, ii = 0; i < o.length; i += 2, ii++) {
                    int j = i - 1;
                    char c2 = (char) b[j];
                    char c1 = (char) b[i];
                    char c = (char)(c1 + c2);
                    b[ii] = (byte) c;
                }
                return b;
            }
        };
    }

    @Override
    public Serializer<?> serializer() {
        final MapCodec codec = this;
        return new Serializer<Object>() {
            @Override
            public Object getCodec() {
                return codec;
            }

            @Override
            public byte[] serialize(Object o) throws IOException {
                if(o instanceof Map) {
                    @SuppressWarnings({ "unchecked" })
                    Map<Object, Object> map = (Map<Object, Object>) o;
                    byte[] r = new byte[16];
                    int i = 0;
                    for(Map.Entry<Object, Object> entry : map.entrySet()) {
                        byte[] k;
                        byte[] v;
                        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
                             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
                            oos.writeObject(entry.getKey());
                            k = bos.toByteArray();
                        }
                        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
                             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
                            oos.writeObject(entry.getValue());
                            v = bos.toByteArray();
                        }
                        Encoder<?> encoder = encoder();
                        k = encoder.encode(k);
                        v = encoder.encode(v);
                        char e = 128;
                        char l = 129;

                        for (byte value : k) {
                            if(i >= r.length * 0.75) r = Arrays.copyOf(r, r.length + 16);
                            r[i] = value;
                            i++;
                        }
                        if(i >= r.length * 0.75) r = Arrays.copyOf(r, r.length + 16);
                        r[i] = (byte) e;
                        i++;
                        for (byte value : v) {
                            if(i >= r.length * 0.75) r = Arrays.copyOf(r, r.length + 16);
                            r[i] = value;
                            i++;
                        }
                        if(i >= r.length * 0.75) r = Arrays.copyOf(r, r.length + 16);
                        r[i] = (byte) l;
                    }
                    return r;
                }
                return new byte[0];
            }

            @Override
            public Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
                Map<Object, Object> m = new HashMap<>();
                byte[] b = new byte[bytes.length];
                int j = 0;
                for(int i = 0; i < bytes.length; i++) {
                    if(bytes[i] == (byte)129) {
                        byte[] k = new byte[b.length];
                        byte[] v = new byte[b.length];
                        int jj = 0;
                        for(int ii = 0; ii < b.length; ii++) {
                            if(b[ii] == (byte)128) {
                                if(jj != 0) break;
                                jj = i + 1;
                            } else if(jj == 0) {
                                k[ii] = b[i];
                            } else {
                                v[ii - jj] = b[i];
                            }
                        }
                        Decoder<?> decoder = decoder();
                        k = decoder.decode(k);
                        v = decoder.decode(v);
                        Object ok;
                        Object ov;
                        try(ByteArrayInputStream bis = new ByteArrayInputStream(k);
                            ObjectInputStream ois = new ObjectInputStream(bis)) {
                            ok = ois.readObject();
                        }
                        try(ByteArrayInputStream bis = new ByteArrayInputStream(v);
                            ObjectInputStream ois = new ObjectInputStream(bis)) {
                            ov = ois.readObject();
                        }
                        m.put(ok, ov);
                        b = new byte[bytes.length];
                        j = i + 1;
                    } else {
                        b[i - j] = bytes[i];
                    }
                }
                return m;
            }
        };
    }
}
