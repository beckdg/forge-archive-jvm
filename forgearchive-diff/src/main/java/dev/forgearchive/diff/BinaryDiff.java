package dev.forgearchive.diff;


public final class BinaryDiff {
    public byte[] diff(byte[] oldData, byte[] newData) {
        int m = oldData.length;
        int n = newData.length;
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (oldData[i - 1] == newData[j - 1]) dp[i][j] = dp[i - 1][j - 1] + 1;
                else dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
        dev.forgearchive.core.BinaryWriter w = new dev.forgearchive.core.BinaryWriter();
        w.writeInt(m);
        w.writeInt(n);
        w.writeInt(dp[m][n]);
        return w.toByteArray();
    }

}
