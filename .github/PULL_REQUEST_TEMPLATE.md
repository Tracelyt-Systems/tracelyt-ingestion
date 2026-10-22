## Description

Briefly describe the purpose of this change and the problem it addresses.

Fixes #(issue)

## Type of Change

- [ ] Bug fix (non-breaking change fixing an issue)
- [ ] New capability / Architecture task (non-breaking change)
- [ ] Breaking change (change causing existing behavior to not work as expected)
- [ ] Refactoring / Technical debt
- [ ] Documentation update
- [ ] Build / CI / Security tooling update

## Key Changes

- Summary of changes made...

## Verification Steps

- [ ] Build and unit tests passed locally (`./mvnw test`)
- [ ] Checkstyle passed locally (`./mvnw checkstyle:check`)
- [ ] SpotBugs static analysis passed locally (`./mvnw spotbugs:check`)
- [ ] Full build verified (`./mvnw clean verify`)

## Checklist

- [ ] My code adheres to the project coding standards ([.checkstyle.xml](.checkstyle.xml)).
- [ ] I have performed a self-review of my code.
- [ ] I have updated documentation in `docs/` where appropriate.
- [ ] My changes introduce no new compiler warnings, Checkstyle errors, or SpotBugs findings.
- [ ] Tests covering new functionality or fixes are included and passing.
- [ ] No secrets, credentials, or private identifiers are included in code or commits.
