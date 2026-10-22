# Security Policy

The Tracelyt project treats security as a fundamental engineering requirement. We welcome responsible disclosures from researchers and community members.

## Supported Versions

As Tracelyt Ingestion is currently in its initial scaffolding phase (pre-1.0), security patches are provided only for the `main` branch.

| Version | Supported |
| :--- | :--- |
| `main` (0.1.0-SNAPSHOT) | Yes |
| Pre-release / Experimental tags | No |

## Reporting a Vulnerability

**Please do not report security vulnerabilities through public GitHub issues, discussions, or pull requests.**

To report a vulnerability:

1. **GitHub Private Vulnerability Reporting (Preferred)**:
   - If enabled on this repository, navigate to the **Security** tab of the repository on GitHub (`https://github.com/Tracelyt-Systems/tracelyt-ingestion/security`).
   - Click on **Advisories**, then select **Report a vulnerability** to submit a private advisory report directly to the repository maintainers.
2. **Alternative Disclosure**:
   - If private vulnerability reporting is not active on GitHub, contact an organization administrator through the GitHub organization profile or open an issue requesting a private security advisory channel without disclosing technical details of the vulnerability publicly.

## Information to Include

To help us investigate and remediate the issue quickly, please provide:

- A clear description of the vulnerability and its potential impact.
- Affected components, configurations, or dependencies.
- Step-by-step reproduction instructions or a minimal proof of concept.
- Any suggested fixes or mitigations, if known.

## Handling Committed Secrets

If a secret, private key, or credential was accidentally committed:
1. **Revoke and rotate** the secret immediately in the target service/infrastructure.
2. Contact maintainers to purge the credential from Git history.
3. Treat the credential as compromised from the moment of push.
