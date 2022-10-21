#!/bin/sh

#kubectl apply -f kubernetes/kafka-namespace.yml
kubectl apply -f kubernetes/kafka-zookeeper.yml
kubectl apply -f kubernetes/kafka-broker.yml

