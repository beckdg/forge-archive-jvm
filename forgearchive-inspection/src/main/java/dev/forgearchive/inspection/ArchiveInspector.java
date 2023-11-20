package dev.forgearchive.inspection;


import dev.forgearchive.archive.*;
import dev.forgearchive.validation.*;
import java.util.*;

public final class ArchiveInspector {
    public Map<String, Object> inspect(ArchiveReader reader) {
        Map<String, Object> report = new LinkedHashMap<>();
        report.put("entryCount", reader.entries().size());
        report.put("created", reader.header().created());
        report.put("errors", new ArchiveVerifier().verify(reader));
        ValidationPipeline pipe = new ValidationPipeline()
            .addStage(r -> new ArchiveVerifier().verify(r));
        report.put("validation", pipe.run(reader));
        return report;
    }

}
