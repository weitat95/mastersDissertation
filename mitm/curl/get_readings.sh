#!/bin/sh

#USERID=63363
#AUTHTOKEN=06e40a343d9a183c92515bfc0fd5bb4d22f4e891

# Damien Owen
USERID=63342
AUTHTOKEN=e9568109372290ee92f0cecc02d0db595a64eb55

#USERID=63378
#AUTHTOKEN=a149e309c2796e11b8a467eabed7850c1aa22aee

curl -k \
    -H 'Authorization:token '"${AUTHTOKEN}" \
    -H 'Host:api.activ8rlives.com' 'https://51.179.221.21/api/0.1/events/?owner='"${USERID}"'&data_types=Diastolic-Blood-Pressure&order_by=-start_date&count=1'
echo ""
