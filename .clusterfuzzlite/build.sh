#!/bin/bash
set -euo pipefail

# Fenrir runs this as /src/repo/build.sh (repo root). Older layouts keep it under
# .clusterfuzzlite/build.sh — locate the directory that contains gradlew.
find_repo_root() {
  local dir
  dir="$(cd "$(dirname "$0")" && pwd)"
  if [[ -f "$dir/gradlew" ]]; then
    echo "$dir"
    return 0
  fi
  if [[ -f "$dir/../gradlew" ]]; then
    cd "$dir/.." && pwd
    return 0
  fi
  if [[ -n "${SRC:-}" && -f "${SRC}/gradlew" ]]; then
    echo "$SRC"
    return 0
  fi
  echo "error: gradlew not found (started from $dir)" >&2
  return 1
}

cd "$(find_repo_root)"

export GRADLE_OPTS="-Dorg.gradle.daemon=false"

chmod +x ./gradlew
./gradlew :forgearchive-fuzz:fuzzJar --no-daemon --no-configuration-cache

FUZZ_JAR="forgearchive-fuzz/build/libs/forgearchive-fuzz.jar"
if [[ ! -f "$FUZZ_JAR" ]]; then
  FUZZ_JAR="$(find forgearchive-fuzz/build/libs -name '*.jar' ! -name '*-sources.jar' ! -name '*-javadoc.jar' | head -1)"
fi

if [[ ! -f "$FUZZ_JAR" ]]; then
  echo "error: fuzz jar not produced" >&2
  exit 1
fi

mkdir -p "$OUT"
cp "$FUZZ_JAR" "$OUT/forgearchive-fuzz.jar"

FUZZERS=(
  ArchiveReaderFuzzer ManifestFuzzer MetadataFuzzer IndexFuzzer
  JournalFuzzer TransactionFuzzer SnapshotFuzzer ChunkTableFuzzer
  ConfigFuzzer RpcFuzzer PacketFuzzer CompressionFuzzer
  VirtualFsFuzzer RecoveryFuzzer PatchFuzzer DiffFuzzer
  ObjectStoreFuzzer StreamReaderFuzzer QueryParserFuzzer
  PluginManifestFuzzer MerkleFuzzer DeltaFuzzer
)

JAZZER_BIN="${JAZZER:-jazzer}"

for fuzzer in "${FUZZERS[@]}"; do
  if command -v "$JAZZER_BIN" &>/dev/null; then
    "$JAZZER_BIN" --cp="$OUT/forgearchive-fuzz.jar" \
      --target="dev.forgearchive.fuzz.${fuzzer}" \
      --dry_run \
      -o "$OUT/${fuzzer}" 2>/dev/null || true
  fi
  if [[ ! -x "$OUT/${fuzzer}" ]]; then
    cat > "$OUT/${fuzzer}" <<EOF
#!/bin/bash
set -euo pipefail
JAZZER=\${JAZZER:-jazzer}
exec "\$JAZZER" --cp="$OUT/forgearchive-fuzz.jar" --target=dev.forgearchive.fuzz.${fuzzer} "\$@"
EOF
    chmod +x "$OUT/${fuzzer}"
  fi
done

echo "Built ${#FUZZERS[@]} fuzz targets into $OUT"
