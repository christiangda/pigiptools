-- PigIpTools sample script

-- Register PigIpTools library (https://github.com/christiangda/pigiptools)
REGISTER $LIBRARY_PATH/pigiptools-tests.jar

-- Put aliases to the functions in the library
DEFINE IsValidIP com.github.christiangda.pig.ip.IsValidIP();
DEFINE IsValidIPV4 com.github.christiangda.pig.ip.IsValidIPV4();
DEFINE IsValidIPV6 com.github.christiangda.pig.ip.IsValidIPV6();

DEFINE CountryName com.github.christiangda.pig.geoip.GetCountryName('$IPV4_GEOIPDB_COUNTRY_PATH','$IPV6_GEOIPDB_COUNTRY_PATH');
DEFINE CountryCode com.github.christiangda.pig.geoip.GetCountryCode('$IPV4_GEOIPDB_COUNTRY_PATH','$IPV6_GEOIPDB_COUNTRY_PATH');
DEFINE CityName com.github.christiangda.pig.geoip.GetCityName('$IPV4_GEOIPDB_CITY_PATH','$IPV6_GEOIPDB_CITY_PATH');

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