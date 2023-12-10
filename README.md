# E-Commerce Microservice - Cloud Computing

### Members of the Decanii's team

- Dragnea Alexandru Marian
- Neagu Fabian
- Leca Cristian

Asistent laborator: Andrei Nicolae Damian

### Project requirements

- [x] existența și integrarea microserviciului propriu de autentificare și autorizare (0.8p) → **user-service**

- [x] existența și integrarea microserviciului propriu de „business logic” (0.8p) → **order-service**

- [x] existența și integrarea microserviciului de baze de date (0.4p) → **inventory-service**

- [x] existența și integrarea unui utilitar de gestiune a bazelor de date, precum Adminer,
  phpMyAdmin, DBeaver, etc. (0.4p) → **PgAdmin4**

- [x] existența și integrarea unui utilitar grafic de gestiune a clusterului, precum Portainer, Rancher,
  Yacht, etc. (0.4p) → **Portainer**

- [x] utilizarea Docker și rularea într-un cluster Kubernetes (în minikube, microk8s, kind, etc.) cu
  minim un control plane și doi workeri (0.6p)

- [x] utilizarea de Terraform pentru crearea infrastructurii de Kubernetes (0.6p) → **Terraform script for PgAdmin4 provision.**

De asemenea, puteți primi bonus pentru următoarele elemente adiționale:

- [x] existența și integrarea unei componente de logging și/sau monitorizare a aplicației, plus vizualizare a acestora, precum Prometheus, cAdvisor, Loki, Grafana, etc. (0.4p) → **Prometheus + Grafana**

- [ ] deployment-ul aplicației în cadrul unui provider de cloud, precum AWS, Azure, GCE, etc.
  (0.4p).


## Project Overview

In this project, following tools and technologies were used:
- <strong>Minikube cluster</strong> with two working nodes and one control plane.
- <strong>Deployments & Services & HPA and PDB</strong> files for each service, as well as scripts for demo.
- <strong>Docker & Docker-Compose</strong>.
- <strong>GitHub Actions</strong> for building and pushing docker images automatically at any code change in the repo.
- <strong>7 Microservices based on Spring Boot & Spring Security</strong>.
- <strong>PostgresSQL</strong> instance provisioned with <strong>Helm</strong>.
- <strong>PgAdmin4 instance</strong> provisioned with <strong>Terraform</strong> as a load balancer for external access to the Postgres instance outside the cluster.
- <strong>PortainerUI</strong> for cluster management.
- <strong>Prometheus Server & Grafana</strong>.


The project impl is based on Spring Boot & Spring Security with the following services and their purposes:

- <strong>User-Service</strong>: microservice that does user registration & login with Spring Security, it handles authentication & authorization.
- <strong>Order-Service</strong>: microservice that has endpoints for making orders.
- <strong>Notification-Service</strong>: microservices that uses JavaMailSender to sends emails regarding the status of the order.
- <strong>Monitoring-Service</strong>: Spring Boot Admin dashboard for microservice monitoring.
- <strong>Inventory-Service</strong>: microservices that adds & updates product stocks.
- <strong>Discovery-Service</strong>: Eureka Service in which all services are registered (part of Spring Cloud family), it helps in implementing spring cloud gateway load balancing.
- <strong>API-Gateway</strong>: Entry point in the arhitecture, with the help of Discovery-Service it serves as the entry point in the architecture. Routes the requests to the specific microservice. 


### Project structure

```
(root directory)
    - api-gateway
        pom.xml
    - discovery-service
        pom.xml
...
    - .githuhb/workflows/build-and-push.yml
    - infra
        /dep - k8s deployment files
        /svc - k8s service files
        /hpa - k8s hpa files
        /postgres - postgres instance helm provisioning
        /main.tf - terraform main file for pgadmin4 provisioning
        /apply_manifest.sh - bash script for applying k8s dep & svc & hpa files
        /delete_dep.sh - bash script for deleting k8s dep & svc & hpa files
        /benchmark.sh - bash script for HPA demo
        
    - docker-compose.yml
```

### K8s Setup

- <strong>Minikube</strong>: 2 working nodes & 1 control plane using profiles:
```
minikube start --nodes 3 -p cc-cluster 
```
- <strong>Terraform PgAdmin4</strong> Provisioning:
```
cd infra
terraform init
terraform validate & apply

Email: admin@admin.com
Password: admin
```
- <strong>Helm Postgres</strong> Provisioning:
```
cd infra/postgres
helm install postgres bitnami/postgresql --values values.yml
```

- <strong>Minikube Dashboard & Metrics & Tunnel</strong>
```
minikube dashboard
minikube addons enable metrics-server 
minikube tunnel
```

- <strong>Deploying microservices on k8s cluster</strong>
```
cd infra
chmod +x apply_manifest.sh -> ./apply_manifest.sh - for deploying the microservices with their svc and hpa
chmod +x delete delete_manifesth.sh -> ./delete_manifest.sh - for deleting dep & svc & hpa 
```

- <strong>Portainer UI</strong>
```
helm repo add portainer https://portainer.github.io/k8s/
helm repo update
helm install --create-namespace -n portainer portainer portainer/portainer
```
Portainer as LoadBalancer for external access
```
helm install --create-namespace -n portainer portainer portainer/portainer \  
--set service.type=LoadBalancer
```

- <strong>Prometheus & Grafana</strong>
```
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo update
helm install prometheus prometheus-community/kube-prometheus-stack --namespace=prometheus --create-namespace --wait
```
Add Port Forward
```
Prometheus node Exporter
kubectl port-forward service/prometheus-prometheus-node-exporter 9100   --namespace=prometheus
```
```
Prometheus UI
kubectl port-forward service/prometheus-operated  9090 --namespace=prometheus
```
```
Prometheus Grafana
kubectl port-forward deployment/prometheus-grafana 3000 --namespace=prometheus
```

Access Grafana UI
```
http://127.0.0.1:3000

Username: admin
Password: prom-operator
```


- <strong>Deploy Microservices on K8s Cluster</strong>
```
cd infra
./apply_manifest.sh -> it will do kubectl aply for each configuration ymls
./delete_manifest.sh -> it will delete all deployments & svc & hpa 
```


### Microservices Deployment & Service and HPA configurations

```
# Deployment config for user-service

apiVersion: apps/v1 
kind: Deployment # Specifies the type of Kubernetes object
metadata:
  name: user-service # Name of the Deployment
spec:
  replicas: 2 # Number of desired replicas (instances) of the application
  strategy:
    type: RollingUpdate # Deployment strategy: RollingUpdate for gradual updates
  selector:
    matchLabels:
      app: user-service # Label selector for pods managed by this Deployment
  template:
    metadata:
      labels:
        app: user-service # Labels for pods created by this template
    spec:
      containers:
      - name: user-service # Container name
        image: alexdragnea/cc-user-service:latest # Docker image for the container
        resources:
          limits:
            memory: 768Mi # Maximum memory the container can use
            cpu: 512m # Maximum CPU the container can use
          requests:
            cpu: 256m # Initial CPU request
            memory: 512Mi # Initial memory request
        ports:
        - containerPort: 8090 # Port the container exposes
        livenessProbe:
          httpGet:
            path: /actuator/health/liveness # Path for the liveness probe
            port: 8090 # Port for the liveness probe
          initialDelaySeconds: 100 # Delay before the liveness probe starts
          periodSeconds: 5 # How often the liveness probe is performed
        readinessProbe:
          httpGet:
            path: /actuator/health/readiness # Path for the readiness probe
            port: 8090 # Port for the readiness probe
          initialDelaySeconds: 100 # Delay before the readiness probe starts
          periodSeconds: 5 # How often the readiness probe is performed
```
For Liveness and Readiness Probe spring boot actuator was used. 
LivenessProbe - used to determine if the container is alive or not (if it s not alive, the container is restarted by kubernetes). Helps in ensuring that the app is running properly.
ReadinessProbe - used to determine if the container is ready to serve traffic (If it fails, the container is marked as not ready). Helps preventing traffic if the pod is not ready.

```
# Service config for user-service

apiVersion: v1 
kind: Service # Specifies the type of Kubernetes object

metadata:
  name: user-service # Name of the Service

spec:
  selector:
    app: user-service # Label selector for pods to be targeted by the Service
  ports:
    - protocol: TCP
      port: 8090 # Port on which the Service will listen
      targetPort: 8090 # Port to which traffic will be forwarded inside the pods
```

```
# HorizontalPodAutoscaler config for user-service application

apiVersion: autoscaling/v1 # API version for HorizontalPodAutoscaler
kind: HorizontalPodAutoscaler # Specifies the type of Kubernetes object

metadata:
  name: hpa-user-service # Name of the HorizontalPodAutoscaler

spec:
  scaleTargetRef:
    apiVersion: apps/v1
    kind: Deployment
    name: user-service # Deployment to which the HorizontalPodAutoscaler will scale
  minReplicas: 2 # Minimum number of replicas (pods)
  maxReplicas: 3 # Maximum number of replicas (pods)
  targetCPUUtilizationPercentage: 70 # Target CPU utilization percentage for autoscaling
```

To test the HPA, ./benchmark.sh script can be run to demo the HPA for user-service.

```
apiVersion: policy/v1
kind: PodDisruptionBudget

# Metadata for the PodDisruptionBudget
metadata:
  name: user-service-pdb

# Describe the desired state of the PodDisruptionBudget
spec:
  # Set the minimum number of available pods during voluntary disruptions to 1
  minAvailable: 1

  # Specify a label selector to identify the pods controlled by this PodDisruptionBudget
  selector:
    matchLabels:
      app: user-service
```

PDB can be seen with ```kubectl get pdb```. To test this, ```kubectl delete pod <pod-name>``` can be used to delete user-service pods.


### GitHub Actions explained

The workflow from above is used to use Maven to package spring boot apps as .jar files, to build the docker image for each service and to deploy them to docker hub. Dockerhub credentials are stored as secrets.
```
name: Build & Deploy

on:
  push:
    branches: [main]
  workflow_dispatch:

jobs:
  build_and_push:
    name: Build & Push to DockerHub
    runs-on: ubuntu-latest
    strategy:
      matrix:
        service: [discovery-service, api-gateway, user-service, order-service, monitoring-service, notification-service, inventory-service]  

    steps:
      - name: Checkout Repository
        uses: actions/checkout@v3

      - name: Set up JDK
        uses: actions/setup-java@v1
        with:
          java-version: '17'

      - name: Cache Maven packages
        uses: actions/cache@v1
        with:
          path: ~/.m2
          key: ${{ runner.os }}-m2-${{ hashFiles('${{ matrix.service }}/pom.xml') }}
          restore-keys: ${{ runner.os }}-m2

      - name: Build with Maven
        run: mvn -B package -DskipTests --file ${{ matrix.service }}/pom.xml

      - name: Login to DockerHub
        uses: docker/login-action@v2
        with:
          username: ${{ secrets.DOCKERHUB_USERNAME }}
          password: ${{ secrets.DOCKERHUB_PASSWORD }}

      - name: Docker Build and Push to DockerHub
        run: |
          cd ${{ matrix.service }}
          docker build -t alexdragnea/cc-${{ matrix.service }}:latest .
          docker push alexdragnea/cc-${{ matrix.service }}:latest

```