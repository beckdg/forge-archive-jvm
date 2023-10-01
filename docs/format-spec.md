# FAR Format Specification

## Header (64 bytes)
- Magic: 0x46415231 (FAR1)
- Version: uint32
- Flags: uint32
- Created: int64 epoch millis
- Manifest hash: 32 bytes SHA-256
- CRC32C: uint64

## Entries
Variable-length records with path, offsets, sizes, content hash, codec id.
