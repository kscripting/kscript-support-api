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

# How to publish a new version?

```bash
#  cd /c/brandl_data/projects/misc/kscript-support-api
 
./gradlew install

./gradlew publishToMavenLocal

#./gradlew publishToSonatype closeSonatypeStagingRepository
./gradlew publishToSonatype closeAndReleaseSonatypeStagingRepository
```

Links
-----


http://stackoverflow.com/questions/38021360/kotlin-object-vs-companion-object-vs-package-scoped-methods

