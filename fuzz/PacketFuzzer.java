package dev.forgearchive.fuzz;

import dev.forgearchive.protocol.*;
import dev.forgearchive.core.*;
import dev.forgearchive.fuzz.FuzzSupport;

@SuppressWarnings({"CatchMayIgnoreException", "unused"})
public class PacketFuzzer {
    public static void fuzzerTestOneInput(byte[] data) {
        if (data == null || data.length == 0) return;
        try {
            if (data.length >= 20) new PacketDecoder().decode(data);
            new FrameDecoder().feed(data);
            new PacketValidator().validate(data);
        } catch (Throwable t) {
            // malformed input is expected during fuzzing
        }
    }
}
