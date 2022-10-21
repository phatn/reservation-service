#!/bin/sh

kubectl delete -f kubernetes/kafka-broker.yml
kubectl delete -f kubernetes/kafka-zookeeper.yml
#kubectl delete -f kubernetes/kafka-namespace.yml