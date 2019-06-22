#!/bin/sh

WINTERFACE=wlp3s0
ALPHAINTERFACE=wlx00c0ca8284d8
CONFIGPATH=../configs

ifdown $ALPHAINTERFACE; ifup $ALPHAINTERFACE
gnome-terminal --window-with-profile=hold -- hostapd ${CONFIGPATH}/hostapd.conf

sleep 5
service isc-dhcp-server restart

