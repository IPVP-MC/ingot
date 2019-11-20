# ingot [![Build Status](https://travis-ci.org/IPVP-MC/ingot.svg?branch=master)](https://travis-ci.org/IPVP-MC/ingot)

Hotbar item management library.

## Using ingot

ingot is integrated into plugins through the use of Maven.

#### Requirements
* [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Maven 3](http://maven.apache.org/download.html)
* [Git](https://git-scm.com/downloads)

Then use the following command to install ingot to your local maven repository
```
git clone https://github.com/IPVP-MC/ingot.git
cd ingot/
mvn clean install
```

You will now be able to add ingot as a repository in your pom.xml files with the following
```xml
<dependency>
    <groupId>org.ipvp</groupId>
    <artifactId>ingot</artifactId>
    <version>1.3-SNAPSHOT</version>
    <scope>compile</scope>
</dependency>
```

## License
ingot is open source and is available under the [MIT license](LICENSE.txt).