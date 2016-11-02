FastLdr
=======

Alternative data loader with Oracle sqlldr compatible interface

Required driver must be available on classpath - ojdbc7.jar, h2.jar, or other

Run:
java -cp fastldr.jar:dbdriver.jar org.agileworks.fastldr.App <parameters>

Compilation:

ojdbc14.jar must be downloaded from oracle website and installed manualy to local maven repository. It can be downloaded eg. from oracle maven repository

mvn install:install-file -Dfile=<path-to-file-ojdbc14.jar> -DgroupId=com.oracle -DartifactId=ojdbc7 -Dversion=12.1.0.2 -Dpackaging=jar

Connecting to other database than Oracle:
[TODO]

Credits:
========
Martin Bednář <xxbedy@gmail.com>
Vítek Tajzich <v.tajzich@gmail.com>
Pavel Lobodinský <pavel.lobo@gmail.com>
Michal Misecius <m.misecius@gmail.com>