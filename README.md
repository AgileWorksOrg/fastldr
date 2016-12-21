FastLdr
=======

Alternative data loader with Oracle sqlldr compatible interface

### Compilation:

`ojdbc7.jar` must be downloaded from oracle website and installed manualy to local maven repository. It can be downloaded eg. from oracle maven repository
To install the file into your local repository, use following command `mvn install:install-file -DgroupId=com.oracle -DartifactId=ojdbc7 -Dversion=12.1.0.1 -DgeneratePom=true -Dfile=ojdbc7.jar -Dpackaging=jar`

Execute `mvn package` in the project root. The file `fastldr-1.0.0-SNAPSHOT-jar-with-dependencies.jar` is generated in **fastldr/target/** directory.

### Run:
`java -jar fastldr-1.0.0-SNAPSHOT-jar-with-dependencies.jar <parameters>`

Note:

The **USERID** paremeter is parsed as [EZCONNECT](http://www.orafaq.com/wiki/EZCONNECT) string - _username/password@[//]host[:port][/service_name]_ 

The connection string is then generated as **jdbc:oracle:thin:@//host:port/service_name** (eg. using service name instead of SID)

 Connecting to other database than Oracle:
 -
 
[TODO]

Credits:
========
Martin Bednář <xxbedy@gmail.com>
Vítek Tajzich <v.tajzich@gmail.com>
Pavel Lobodinský <pavel.lobo@gmail.com>
Michal Misecius <m.misecius@gmail.com>