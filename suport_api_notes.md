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


1. Push and create github release tag

2. 
```bash
# cd /Users/brandl/projects/kotlin/kscript-support-api

gradle test
gradle install
```

4. Create new version on jcenter

```bash
gradle bintrayUpload
```

<!-- * Upload jar, sources.jar and pom for new version from `~/.m2/repository/com/github/holgerbrandl/kscript` to: -->
<!-- > https://bintray.com/holgerbrandl/mpicbg-scicomp/kscript -->

1. Check for release status on
https://jcenter.bintray.com/com/github/holgerbrandl/kscript

2. Bump versions for new release cycle


Links
-----


http://stackoverflow.com/questions/38021360/kotlin-object-vs-companion-object-vs-package-scoped-methods

