#!/bin/sh

TARGET='https://1t20591l6g.execute-api.us-west-2.amazonaws.com/test_stage/test'

PARAM1=[1,2,3]
PARAM2="bbb"

# Construct JSON payload
JSON_PAYLOAD="{\"param1\":${PARAM1},\"param2\":\"${PARAM2}\"}"

curl -X POST -H "Content-Type: application/json" -d "${JSON_PAYLOAD}" "${TARGET}"
