#!/bin/sh

WINTERFACE=wlp3s0
ALPHAINTERFACE=wlx00c0ca8284d8
CONFIGPATH=../configs

sudo systemctl start systemd-resolved
sudo systemctl stop systemd-resolved
sudo systemctl stop dnsmasq
sudo systemctl start dnsmasq

echo "%%%%Stop service - network-manager"
sudo service network-manager stop
echo "%%%%Restart service - dnsmasq"
sudo service dnsmasq restart
sudo ./configureAPinterface.sh
sudo rm /var/run/hostapd/$ALPHAINTERFACE

# Client Interface WPASUPPLICANT
sudo ./clientInterface.sh $WINTERFACE $ALPHAINTERFACE $CONFIGPATH

#sudo rm /var/run/wpa_supplicant/$WINTERFACE
#echo "%%%%Restart client interface"
#sudo ifdown $WINTERFACE; sudo ifup $WINTERFACE
#echo "%%%%Start wpa_supplicant"
#sudo gnome-terminal --window-with-profile=hold -- su -c "wpa_supplicant -i $WINTERFACE -d -c ${CONFIGPATH}/wpa_supplicant.conf"
#sleep 5
#echo "%%%%Get address for client"
#sudo dhclient $WINTERFACE

# Host Interface HOSTAPD
sudo ./hostInterface.sh $WINTERFACE $ALPHAINTERFACE $CONFIGPATH
#echo "%%%%Restart client interface"
#sudo ifdown $ALPHAINTERFACE; sudo ifup $ALPHAINTERFACE
#echo "%%%%Start hostapd"
#sudo gnome-terminal --window-with-profile=hold -- hostapd ${CONFIGPATH}/hostapd.conf
#sleep 5
#echo "%%%%Restart Service - isc-dhcp-server"
#sudo service isc-dhcp-server restart

echo "%%%%Port Forwarding"
sudo gnome-terminal -- su -c "
echo 1 > /proc/sys/net/ipv4/ip_forward;
iptables -A FORWARD -i $ALPHAINTERFACE -o $WINTERFACE -j ACCEPT;
iptables -A FORWARD -i $WINTERFACE -o $ALPHAINTERFACE -m state --state ESTABLISHED,RELATED -j ACCEPT;
iptables -t nat -A POSTROUTING -o $WINTERFACE -j MASQUERADE"



