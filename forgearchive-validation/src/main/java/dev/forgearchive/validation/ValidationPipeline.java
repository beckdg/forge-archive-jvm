package dev.forgearchive.validation;


import dev.forgearchive.archive.*;
import java.util.*;

public final class ValidationPipeline {
    private final List<java.util.function.Function<ArchiveReader, List<String>>> stages = new ArrayList<>();

    public ValidationPipeline addStage(java.util.function.Function<ArchiveReader, List<String>> stage) {
        stages.add(stage);
        return this;
    }

    public List<String> run(ArchiveReader reader) {
        List<String> all = new ArrayList<>();
        for (var stage : stages) all.addAll(stage.apply(reader));
        return all;
    }

}
