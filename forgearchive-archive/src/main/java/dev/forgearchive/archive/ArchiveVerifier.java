package dev.forgearchive.archive;


import dev.forgearchive.archive.*;
import java.util.*;

public final class ArchiveVerifier {
    public List<String> verify(ArchiveReader reader) {
        List<String> errors = new ArrayList<>();
        for (FarEntry e : reader.entries()) {
            try {
                reader.readEntry(e);
            } catch (Exception ex) {
                errors.add(e.path() + ": " + ex.getMessage());
            }
        }
        return errors;
    }

}
