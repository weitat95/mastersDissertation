#!/bin/sh

WINTERFACE=wlp3s0
ALPHAINTERFACE=wlx00c0ca8284d8
CONFIGPATH=../configs/wpa_supplicant.conf
#CONFIGPATH=../configs/wpa_supplicant_wei.conf

sudo rm /var/run/wpa_supplicant/$WINTERFACE
echo "%%%%Restart client interface"
sudo ifdown $WINTERFACE; sudo ifup $WINTERFACE
echo "%%%%Start wpa_supplicant"
sudo gnome-terminal --window-with-profile=hold -- su -c "wpa_supplicant -i $WINTERFACE -d -c ${CONFIGPATH}"
sleep 5
echo "%%%%Get address for client"
sudo dhclient $WINTERFACE
