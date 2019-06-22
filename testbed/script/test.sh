#!/bin/sh
FILE="/etc/network/interfaces"

if grep -xq "iface wlx00c0ca8284d8 inet static" "$FILE"; then
    echo "found"
else
    echo "not found"
fi
