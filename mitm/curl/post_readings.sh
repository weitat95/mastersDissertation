#!/bin/sh

#USERID=63363
#AUTHTOKEN=06e40a343d9a183c92515bfc0fd5bb4d22f4e891


USERID=63342
AUTHTOKEN=e9568109372290ee92f0cecc02d0db595a64eb55



#USERID=63378
#AUTHTOKEN=a149e309c2796e11b8a467eabed7850c1aa22aee

#curl -k -H 'Content-Type:application/json; charset=utf-8' \
#    -H "Authorization:token ${AUTHTOKEN}" \
#    -H 'Accept-Encoding:identity' \
#    -H 'Content-Length:272' \
#    -H 'User-Agent:Dalvik/2.1.0 (Linux; U; Android 5.1.1; MotoG3 Build/LPI23.72-66)' \
#    -H 'Host:api.activ8rlives.com' \
#    -H 'Connection:Keep-Alive' \
#    -X POST 'https://51.179.221.21/api/0.1/events/' \
#    --data-binary '[{"guid":"5ac68840-8a76-482d-9b0a-4db60323eafe","start_date":"2019-07-18T11:57:48.096448Z","end_date":"2019-07-18T11:57:48.096448Z","source":"bloodpressure","data_types":["Systolic-Blood-Pressure","Diastolic-Blood-Pressure","Pulse"],"data":[19.0,19.0,19.0],"owner"'":${USERID}}]"
curl -k \
    -H 'Content-Type:application/json; charset=utf-8' \
    -H "Authorization:token ${AUTHTOKEN}" \
    -H 'Host:api.activ8rlives.com' \
    -X POST 'https://51.179.221.21/api/0.1/events/' \
    --data-binary '[{"guid":"5ac68840-8a76-482d-9b0a-4db60323eafe","start_date":"2019-07-18T11:57:48.096448Z","end_date":"2019-07-18T11:57:48.096448Z","source":"bloodpressure","data_types":["Systolic-Blood-Pressure","Diastolic-Blood-Pressure","Pulse"],"data":[19.0,19.0,19.0],"owner"'":${USERID}}]"
echo ""
