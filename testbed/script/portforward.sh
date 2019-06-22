#!/bin/sh
WINTERFACE=wlp3s0
ALPHAINTERFACE=wlx00c0ca8284d8
su -c "echo 1 > /proc/sys/net/ipv4/ip_forward; iptables -A FORWARD -i $ALPHAINTERFACE -o $WINTERFACE -j ACCEPT; iptables -A FORWARD -i $WINTERFACE -o $ALPHAINTERFACE -m state --state ESTABLISHED,RELATED -j ACCEPT; iptables -t nat -A POSTROUTING -o $WINTERFACE -j MASQUERADE"
