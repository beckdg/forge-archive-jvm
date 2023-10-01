# ForgeArchive Architecture

ForgeArchive is a modular archive system organized in layers:

1. **Core** - binary primitives, hashing, merkle trees
2. **Buffer/IO** - zero-copy slices, ring buffers, seekable input
3. **Compression/Crypto** - LZ4, Zstd, Deflate, AES-GCM, HMAC
4. **Format** - FAR archives, manifests, metadata, journals, indexes
5. **Algorithms** - chunking, dedup, diff/patch, caches
6. **Services** - pack/unpack, sync, query, recovery
7. **API/CLI** - public facade and command-line tools

Data flows: source files -> chunker -> compressor -> FAR writer -> storage.
