# JarSerialver
Clone of serialver utility which works with JARs

Using this utility you can get SerialVersionUIDs for all classes in JAR file.

##Usage:

```bash
java -jar jarserialver.jar --file <your-lib.jar> --classpath <classpath with your jar and dependencies>
```

additionally you can ignore anonymous classes with --ignore-anonymous option and limit classes by package with --package option:

```bash
java -jar jarserialver.jar --file <your-lib.jar> --classpath <classpath with your jar and dependencies> -a --package <your package>
```

To generate a long classpath from a directory use this snippet:

```bash
--classpath $(echo lib/*.jar | tr ' ' ':')
```

## Build

To build this project use SBT:

```bash
sbt assembly
```
