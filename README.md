# PigIpTools [![Build Status](https://travis-ci.org/christiangda/pigiptools.png)](https://travis-ci.org/christiangda/pigiptools)

## About
This is a group of Apache Pig Java UDFs utilities to help us get more productive ours Pig Latin Scripts, using [GeoIP database files](http://dev.maxmind.com/geoip/legacy/downloadable/), and other [Java Ip tools](http://docs.oracle.com/javase/7/docs/api/java/net/InetAddress.html)

## License
[The GNU General Public License v3.0](http://www.gnu.org/licenses/gpl-3.0.en.html).

## Quickstart
This is basically an example of how to use all the functions of the library
```PigLatin
-- Register PigIpTools library (https://github.com/christiangda/pigiptools)
REGISTER /usr/local/pig/lib/pigiptools-1.0.0.jar

-- Put aliases to the functions in the library
DEFINE IsValidIP com.github.christiangda.pig.ip.IsValidIP();
DEFINE IsValidIPV4 com.github.christiangda.pig.ip.IsValidIPV4();
DEFINE IsValidIPV6 com.github.christiangda.pig.ip.IsValidIPV6();

DEFINE CountryName com.github.christiangda.pig.geoip.GetCountryName('/usr/share/GeoIP/GeoIP.dat','/usr/share/GeoIP/GeoIPv6.dat');
DEFINE CountryCode com.github.christiangda.pig.geoip.GetCountryCode('/usr/share/GeoIP/GeoIP.dat','/usr/share/GeoIP/GeoIPv6.dat');
DEFINE CityName com.github.christiangda.pig.geoip.GetCityName('/usr/share/GeoIP/GeoLiteCity.dat','/usr/share/GeoIP/GeoLiteCityv6.dat');

-- Load the sample data
data = LOAD 'input' AS (ip:chararray);

-- Use the library functions to enrich the data
ips = FOREACH data GENERATE
    ip              AS ipaddress,
    IsValidIP(ip)   AS is_valid_ip,
    IsValidIPV4(ip) AS is_valid_ipv4,
    IsValidIPV6(ip) AS is_valid_ipv6,
    CountryName(ip) AS country_name,
    CountryCode(ip) AS country_code,
    CityName(ip)    AS city_name;

-- Store our enrichest data
STORE ips INTO 'output' USING PigStorage();
```

## Maven repository (Download)
PigIpTools release artifacts are published to the [Sonatype OSS](https://oss.sonatype.org/) [releases repository](https://oss.sonatype.org/service/local/staging/deploy/maven2) and promoted from there to [Maven Central](http://search.maven.org/).
From time to time we may also deploy snapshot releases to the Sonatype OSS [snapshots repository](https://oss.sonatype.org/content/repositories/snapshots).

## Build yourself
if you want to build it yourself, remember to have the following tools
- Apache Maven 3.3.3 o higher (recommended)
- Java 6, 7 and 8 (OpenJDK  or Oracle version)
- Internet Connection! (of course)

The procedure is the following
```bash
git clone https://github.com/christiangda/pigiptools.git pigiptools
cd pigiptools/

# Recommended
mvn clean test package

# No testing
mvn -DskipTests clean package

# Your pigiptools-x.y.z.jar is in:
ls target/
```

## Version compatibility
- Hadoop 2.x
- Pig 0.9+
- AWS EMR (pig-0.12.0, Hadoop-2.4.0 and S3)

## How To Contribute
Bug fixes, features, and documentation improvements are welcome! Please fork the project and send us a pull request on github.

## Contributors
Major contributors are listed below. Lots of others have helped too, thanks to all of them!
See git logs for credits.
- Christian González ([@christiangda](https://twitter.com/christiangda))
