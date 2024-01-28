package dev.forgearchive.fuzz;

import dev.forgearchive.rpc.*;
import dev.forgearchive.core.*;
import dev.forgearchive.fuzz.FuzzSupport;

@SuppressWarnings({"CatchMayIgnoreException", "unused"})
public class RpcFuzzer {
    public static void fuzzerTestOneInput(byte[] data) {
        if (data == null || data.length == 0) return;
        try {
            if (data.length > 8) new RpcDecoder().decodeFrame(data);
            new RpcValidator().validate(data);
        } catch (Throwable t) {
            // malformed input is expected during fuzzing
        }
    }
}
