REGISTER ./dist/.jar

--DEFINE GetCountryName com.slashdevops.pig.geoip.GetCountryName(ipv4db,ipv6db);

data = LOAD 'input' AS (ip:chararray);

ips = FOREACH data GENERATE CountryName(ip);

STORE ips INTO 'output' USING PigStorage();
