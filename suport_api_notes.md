Developer Info
--------------


```bash
#cd /Users/brandl/projects/kotlin/kscript-support-api
## to install to local maven repo
gradle install

## to run the tests 
gradle test

```

Release Checklist
-----------------

1. Update version in
* `build.gradle`
* `kscript and`
* `kscript-support-api/README`
* `kscript/README`


2. Do the central release
```bash
#  cd /c/brandl_data/projects/misc/kscript-support-api
 
./gradlew install

./gradlew publishToMavenLocal

#./gradlew publishToSonatype closeSonatypeStagingRepository
./gradlew publishToSonatype closeAndReleaseSonatypeStagingRepository
```

3. Do the github source release

```bash

# make sure that are no pending chanes
#(git diff --exit-code && git tag v${kscript_version})  || echo "could not tag current branch"
git diff --exit-code  || echo "There are uncommitted changes"


git tag "1.2.5"

git push origin
git push origin --tags
```


Links
-----


http://stackoverflow.com/questions/38021360/kotlin-object-vs-companion-object-vs-package-scoped-methods

