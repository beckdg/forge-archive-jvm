package dev.forgearchive.cli;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import java.util.List;
import java.util.concurrent.Callable;

@Command(name = "forgearchive", mixinStandardHelpOptions = true, version = "1.0")
public final class ForgeArchiveCli implements Callable<Integer> {
    public static void main(String[] args) {
        ForgeArchiveCli app = new ForgeArchiveCli();
        CommandLine cmd = new CommandLine(app);
        cmd.addSubcommand("create", new CreateCmd());
        cmd.addSubcommand("extract", new ExtractCmd());
        cmd.addSubcommand("verify", new VerifyCmd());
        cmd.addSubcommand("repair", new RepairCmd());
        cmd.addSubcommand("diff", new DiffCmd());
        cmd.addSubcommand("patch", new PatchCmd());
        cmd.addSubcommand("snapshot", new SnapshotCmd());
        cmd.addSubcommand("mount", new MountCmd());
        cmd.addSubcommand("inspect", new InspectCmd());
        cmd.addSubcommand("validate", new ValidateCmd());
        cmd.addSubcommand("query", new QueryCmd());
        cmd.addSubcommand("benchmark", new BenchmarkCmd());
        cmd.addSubcommand("stats", new StatsCmd());
        cmd.addSubcommand("recover", new RecoverCmd());
        cmd.addSubcommand("index", new IndexCmd());
        cmd.addSubcommand("sync", new SyncCmd());
        cmd.addSubcommand("merge", new MergeCmd());
        cmd.addSubcommand("split", new SplitCmd());
        cmd.addSubcommand("convert", new ConvertCmd());
        int exit = cmd.execute(args);
        System.exit(exit);
    }

    @Override
    public Integer call() {
        CommandLine.usage(this, System.out);
        return 0;
    }

    @Command(name = "create", description = "create archive operation")
    static final class CreateCmd implements Runnable {
        @Parameters(paramLabel = "ARGS", description = "arguments") List<String> args;
        @Override public void run() { System.out.println("create: " + args); }
    }

    @Command(name = "extract", description = "extract archive operation")
    static final class ExtractCmd implements Runnable {
        @Parameters(paramLabel = "ARGS", description = "arguments") List<String> args;
        @Override public void run() { System.out.println("extract: " + args); }
    }

    @Command(name = "verify", description = "verify archive operation")
    static final class VerifyCmd implements Runnable {
        @Parameters(paramLabel = "ARGS", description = "arguments") List<String> args;
        @Override public void run() { System.out.println("verify: " + args); }
    }

    @Command(name = "repair", description = "repair archive operation")
    static final class RepairCmd implements Runnable {
        @Parameters(paramLabel = "ARGS", description = "arguments") List<String> args;
        @Override public void run() { System.out.println("repair: " + args); }
    }

    @Command(name = "diff", description = "diff archive operation")
    static final class DiffCmd implements Runnable {
        @Parameters(paramLabel = "ARGS", description = "arguments") List<String> args;
        @Override public void run() { System.out.println("diff: " + args); }
    }

    @Command(name = "patch", description = "patch archive operation")
    static final class PatchCmd implements Runnable {
        @Parameters(paramLabel = "ARGS", description = "arguments") List<String> args;
        @Override public void run() { System.out.println("patch: " + args); }
    }

    @Command(name = "snapshot", description = "snapshot archive operation")
    static final class SnapshotCmd implements Runnable {
        @Parameters(paramLabel = "ARGS", description = "arguments") List<String> args;
        @Override public void run() { System.out.println("snapshot: " + args); }
    }

    @Command(name = "mount", description = "mount archive operation")
    static final class MountCmd implements Runnable {
        @Parameters(paramLabel = "ARGS", description = "arguments") List<String> args;
        @Override public void run() { System.out.println("mount: " + args); }
    }

    @Command(name = "inspect", description = "inspect archive operation")
    static final class InspectCmd implements Runnable {
        @Parameters(paramLabel = "ARGS", description = "arguments") List<String> args;
        @Override public void run() { System.out.println("inspect: " + args); }
    }

    @Command(name = "validate", description = "validate archive operation")
    static final class ValidateCmd implements Runnable {
        @Parameters(paramLabel = "ARGS", description = "arguments") List<String> args;
        @Override public void run() { System.out.println("validate: " + args); }
    }

    @Command(name = "query", description = "query archive operation")
    static final class QueryCmd implements Runnable {
        @Parameters(paramLabel = "ARGS", description = "arguments") List<String> args;
        @Override public void run() { System.out.println("query: " + args); }
    }

    @Command(name = "benchmark", description = "benchmark archive operation")
    static final class BenchmarkCmd implements Runnable {
        @Parameters(paramLabel = "ARGS", description = "arguments") List<String> args;
        @Override public void run() { System.out.println("benchmark: " + args); }
    }

    @Command(name = "stats", description = "stats archive operation")
    static final class StatsCmd implements Runnable {
        @Parameters(paramLabel = "ARGS", description = "arguments") List<String> args;
        @Override public void run() { System.out.println("stats: " + args); }
    }

    @Command(name = "recover", description = "recover archive operation")
    static final class RecoverCmd implements Runnable {
        @Parameters(paramLabel = "ARGS", description = "arguments") List<String> args;
        @Override public void run() { System.out.println("recover: " + args); }
    }

    @Command(name = "index", description = "index archive operation")
    static final class IndexCmd implements Runnable {
        @Parameters(paramLabel = "ARGS", description = "arguments") List<String> args;
        @Override public void run() { System.out.println("index: " + args); }
    }

    @Command(name = "sync", description = "sync archive operation")
    static final class SyncCmd implements Runnable {
        @Parameters(paramLabel = "ARGS", description = "arguments") List<String> args;
        @Override public void run() { System.out.println("sync: " + args); }
    }

    @Command(name = "merge", description = "merge archive operation")
    static final class MergeCmd implements Runnable {
        @Parameters(paramLabel = "ARGS", description = "arguments") List<String> args;
        @Override public void run() { System.out.println("merge: " + args); }
    }

    @Command(name = "split", description = "split archive operation")
    static final class SplitCmd implements Runnable {
        @Parameters(paramLabel = "ARGS", description = "arguments") List<String> args;
        @Override public void run() { System.out.println("split: " + args); }
    }

    @Command(name = "convert", description = "convert archive operation")
    static final class ConvertCmd implements Runnable {
        @Parameters(paramLabel = "ARGS", description = "arguments") List<String> args;
        @Override public void run() { System.out.println("convert: " + args); }
    }

}
