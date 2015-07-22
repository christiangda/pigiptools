REGISTER $LIBRARY_PATH/pigiptools-tests.jar

DEFINE CountryName com.github.christiangda.pig.geoip.GetCountryName('$IPV4_GEODB_PATH','$IPV6_GEODB_PATH');

data = LOAD 'input' AS (ip:chararray);

ips = FOREACH data GENERATE CountryName(ip);

STORE ips INTO 'output' USING PigStorage();
