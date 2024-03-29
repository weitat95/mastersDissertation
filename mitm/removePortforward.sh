#!/bin/sh

INTERFACE=wlx00c0ca8284d8

iptables -t nat -D PREROUTING -i $INTERFACE -p tcp --dport 80 -j REDIRECT --to-port 8080
iptables -t nat -D PREROUTING -i $INTERFACE -p tcp --dport 443 -j REDIRECT --to-port 8080
ip6tables -t nat -D PREROUTING -i $INTERFACE -p tcp --dport 80 -j REDIRECT --to-port 8080
ip6tables -t nat -D PREROUTING -i $INTERFACE -p tcp --dport 443 -j REDIRECT --to-port 8080
