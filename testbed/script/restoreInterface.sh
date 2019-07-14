#!/bin/sh
# Copied this to /etc/init.d/
# Should run in startup
# sudo update-rc.d restoreInterface.sh defaults

INTERFACEPATH=/etc/network/interfaces

sudo iwconfig wlp3s0 txpower 10dPm

if grep -xq "iface wlx00c0ca8284d8 inet static" "$INTERFACEPATH"; then
        
    sudo sed -i 's/auto wlx00c08284d8/#auto wlx00c08284d8/g' $INTERFACEPATH
    sudo sed -i 's/iface wlx00c0ca8284d8 inet static/#iface wlx00c0ca8284d8 inet static/g' $INTERFACEPATH
    sudo sed -i 's/    address 172.25.1.1/#    address 172.25.1.1/g' $INTERFACEPATH
    sudo sed -i 's/    netmask 255.255.255.0/#    netmask 255.255.255.0/g' $INTERFACEPATH
    sudo sed -i 's/    network 172.25.1.0/#    network 172.25.1.0/g' $INTERFACEPATH
    sudo sed -i 's/auto wlp3s0/#auto wlp3s0/g' $INTERFACEPATH
    sudo sed -i 's/iface wlp3s0 inet dhcp/#iface wlp3s0 inet dhcp/g' $INTERFACEPATH
fi
sudo service network-manager restart

sudo systemctl start systemd-resolved
sudo systemctl stop systemd-resolved
sudo systemctl stop dnsmasq
sudo systemctl start dnsmasq
