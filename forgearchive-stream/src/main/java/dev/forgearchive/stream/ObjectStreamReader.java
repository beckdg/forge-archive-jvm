package dev.forgearchive.stream;


import dev.forgearchive.core.*;
import java.util.*;

public final class ObjectStreamReader {
    public List<byte[]> readObjects(byte[] stream) throws Exception {
        BinaryReader r = BinaryReader.wrap(stream);
        int count = r.readVarInt();
        List<byte[]> objects = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int len = r.readVarInt();
            objects.add(r.readBytes(len));
        }
        return objects;
    }

}
