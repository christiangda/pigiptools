-- PigIpTools sample script

-- Register PigIpTools library (https://github.com/christiangda/pigiptools)
REGISTER $LIBRARY_PATH/pigiptools-tests.jar

-- Put aliases to the functions in the library
DEFINE Base64Decode com.github.christiangda.pig.url.Base64Decode();
DEFINE Base64Encode com.github.christiangda.pig.url.Base64Encode();
DEFINE DecodeUTF8 com.github.christiangda.pig.url.Decode();
DEFINE EncodeUTF8 com.github.christiangda.pig.url.Encode();
DEFINE DecodeLatin1 com.github.christiangda.pig.url.Decode('ISO-8859-1');
DEFINE EncodeLatin1 com.github.christiangda.pig.url.Encode('ISO-8859-1');
DEFINE IsDecodable com.github.christiangda.pig.url.IsDecodable();
DEFINE IsEncodable com.github.christiangda.pig.url.IsEncodable();


-- Load the sample data
data = LOAD 'input' AS (uri:chararray);

-- Use the library functions to enrich the data
uris = FOREACH data GENERATE
    uri                 AS uri,
    Base64Decode(uri)   AS base_64_decode,
    Base64Encode(uri)   AS base_64_encode,
    DecodeUTF8(uri)     AS decode_utf_8,
    EncodeUTF8(uri)     AS encode_utf_8,
    DecodeLatin1(uri)   AS decode_latin_1,
    EncodeLatin1(uri)   AS encode_latin_1,
    IsDecodable(uri)    AS is_encodable,
    IsEncodable(uri)    AS is_decodable;

-- Store our enrichest data
STORE urls INTO 'output' USING PigStorage();