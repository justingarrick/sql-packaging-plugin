sql-packaging-plugin
=

A maven packaging plugin that enables ```<packaging>sql</packaging>``` for .sql artifacts.

Why? Shouldn't .sql files go in SCM?
=
Yes, a .sql file would normally be stored in SCM.  However, in some cases, it's nice to treat them as versioned binaries.  For example, you might have a build which programattically aggregates a number of smaller .sql files (fix-1.sql, fix-2.sql, fix-3.sql) into a single .sql file (productpatch-2.0.0.sql).  In this case, the aggregate is never edited, but is a build artifact.  As such, it is handy to treat it as a versioned binary so that you can add it as a Maven ```<dependency>```, and, for example, package it into an installer using the maven-assembly-plugin.

Building the Plugin
=
Clone this repository and install the plugin to your local Maven repository:
```
$ cd sql-packaging-plugin
$ mvn clean install
```

Using the Plugin
=
In the Maven project you wish to package as a .sql file, edit the pom.xml file to enable extensions for the plugin and declare the packaging type to be sql:
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" 
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>my.group.id</groupId>
  <artifactId>myartifact</artifactId>
  <packaging>sql</packaging>
  <version>2.0.0</version>
  
  <build>
    <extensions>
      <extension>
        <groupId>com.github.justingarrick</groupId>
        <artifactId>sql-packaging-plugin</artifactId>
        <version>1.0.0</version>
      </extension>
    </extensions>
  </build>
  
</project>
```

When the plugin is executed (by default, in the [package](http://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html) phase), the plugin looks for a file in ```/target``` (or ```${project.build.directory}```) with a name of the format ```artifactId-version.sql```.  Using the example pom above, the plugin would expect a file named ```myartifact-2.0.0.sql```.  Whether this file is copied from someplace else, e.g. ```src/main/resources```, or created via the execution of some other plugin is up to you.  
