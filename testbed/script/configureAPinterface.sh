#!/bin/sh

INTERFACEPATH=/etc/network/interfaces


if grep -xq "#iface wlx00c0ca8284d8 inet static" "$INTERFACEPATH"; then
    sudo sed -i 's/#auto wlx00c08284d8/auto wlx00c08284d8/g' $INTERFACEPATH
    sudo sed -i 's/#iface wlx00c0ca8284d8 inet static/iface wlx00c0ca8284d8 inet static/g' $INTERFACEPATH
    sudo sed -i 's/#    address 172.25.1.1/    address 172.25.1.1/g' $INTERFACEPATH
    sudo sed -i 's/#    netmask 255.255.255.0/    netmask 255.255.255.0/g' $INTERFACEPATH
    sudo sed -i 's/#    network 172.25.1.0/    network 172.25.1.0/g' $INTERFACEPATH
    sudo sed -i 's/#auto wlp3s0/auto wlp3s0/g' $INTERFACEPATH
    sudo sed -i 's/#iface wlp3s0 inet dhcp/iface wlp3s0 inet dhcp/g' $INTERFACEPATH
fi
