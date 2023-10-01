# Security Model

- Content integrity via SHA-256 per entry
- Optional AES-GCM encryption via forgearchive-crypto
- HMAC-SHA256 for authentication
- Path traversal prevented by PathNormalizer
- Fuzz testing via Jazzer for all parsers
