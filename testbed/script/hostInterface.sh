#!/bin/sh



#WINTERFACE=wlp3s0
#ALPHAINTERFACE=wlx00c0ca8284d8
#CONFIGPATH=../configs

WINTERFACE=$1
ALPHAINTERFACE=$2
CONFIGPATH=$3


echo "%%%%Restart client interface"
sudo ifdown $ALPHAINTERFACE; sudo ifup $ALPHAINTERFACE
echo "%%%%Start hostapd"
sudo gnome-terminal --window-with-profile=hold -- hostapd ${CONFIGPATH}/hostapd.conf
sleep 5
echo "%%%%Restart Service - isc-dhcp-server"
sudo service isc-dhcp-server restart
