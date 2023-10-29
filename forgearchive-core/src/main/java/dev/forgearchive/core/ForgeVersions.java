package dev.forgearchive.core;

/**
 * ForgeArchive format and API version constants.
 */
public final class ForgeVersions {
    public static final int FAR_MAGIC = 0x46415221; // "FAR!"
    public static final int FAR_MAJOR = 1;
    public static final int FAR_MINOR = 0;
    public static final int FAR_PATCH = 0;

    public static final int MANIFEST_MAGIC = 0x4D414E46; // "MANF"
    public static final int JOURNAL_MAGIC = 0x4A524E4C; // "JRNL"
    public static final int INDEX_MAGIC = 0x49445821; // "IDX!"
    public static final int SNAPSHOT_MAGIC = 0x534E4150; // "SNAP"
    public static final int RPC_MAGIC = 0x52504321; // "RPC!"

    public static final int PROTOCOL_VERSION = 1;

    private ForgeVersions() {}
}
