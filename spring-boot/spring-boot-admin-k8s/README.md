## Requirements
* JDK12
* Maven
* Docker
* kubectl
* local cluster (docker-for-desktop or minikube)
* Helm

## Build
Build the project and images
```bash
mvn clean package docker:build
```
Build and push images to your own repo
```bash
mvn clean package docker:build docker:push -Ddocker.hub.id=your_docker_hub_id
```

## Deploy
Deploy with Helm
```bash
helm install --wait --name spring-boot-admin-k8s ./spring-boot-admin-k8s-chart
```
or just run 
```bash
./helm-install.sh
```
Deploy your own images
```bash
helm install --wait --set global.repository=your_docker_hub_id  --name spring-boot-admin-k8s ./spring-boot-admin-k8s-chart
```

Delete
```bash
helm del --purge spring-boot-admin-k8s
```
or simply run 
```bash
./helm-delete.sh
```
