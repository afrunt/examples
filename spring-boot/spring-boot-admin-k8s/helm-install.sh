#!/usr/bin/env bash
helm install --wait --set global.repository=afrunt --name spring-boot-admin-k8s ./spring-boot-admin-k8s-chart
