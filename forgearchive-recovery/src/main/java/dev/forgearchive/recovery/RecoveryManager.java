package dev.forgearchive.recovery;


import dev.forgearchive.journal.*;
import dev.forgearchive.transaction.*;
import java.io.*;
import java.util.*;

public final class RecoveryManager {
    public void recover(File journal, File stateDir) throws Exception {
        JournalReader reader = new JournalReader();
        List<JournalRecord> records = reader.replay(journal);
        TransactionManager mgr = new TransactionManager(new File(stateDir, "recovery.journal"));
        for (JournalRecord r : records) mgr.apply(r);
    }

}
