#!/bin/bash

# Services
services=(
    "https://raw.githubusercontent.com/alexdragnea/e-commerce-microservices/main/infra/svc/discovery-service.yaml"
    "https://raw.githubusercontent.com/alexdragnea/e-commerce-microservices/main/infra/svc/api-gateway-service.yaml"
    "https://raw.githubusercontent.com/alexdragnea/e-commerce-microservices/main/infra/svc/monitoring-service.yaml"
    "https://raw.githubusercontent.com/alexdragnea/e-commerce-microservices/main/infra/svc/user-service.yaml"
    "https://raw.githubusercontent.com/alexdragnea/e-commerce-microservices/main/infra/svc/order-service.yaml"
    "https://raw.githubusercontent.com/alexdragnea/e-commerce-microservices/main/infra/svc/inventory-service.yaml"
    "https://raw.githubusercontent.com/alexdragnea/e-commerce-microservices/main/infra/svc/notification-service.yaml"
)

for service in "${services[@]}"; do
    kubectl apply -f "$service"
    sleep 1
done

kubectl apply -f https://raw.githubusercontent.com/alexdragnea/e-commerce-microservices/main/infra/dep/discovery-service-dep.yaml
sleep 60

# Deployments
deployments=(
    "https://raw.githubusercontent.com/alexdragnea/e-commerce-microservices/main/infra/dep/api-gateway-dep.yaml"
    "https://raw.githubusercontent.com/alexdragnea/e-commerce-microservices/main/infra/dep/monitoring-service-dep.yaml"
    "https://raw.githubusercontent.com/alexdragnea/e-commerce-microservices/main/infra/dep/user-service-dep.yaml"
    "https://raw.githubusercontent.com/alexdragnea/e-commerce-microservices/main/infra/dep/order-service-dep.yaml"
    "https://raw.githubusercontent.com/alexdragnea/e-commerce-microservices/main/infra/dep/inventory-service-dep.yaml"
    "https://raw.githubusercontent.com/alexdragnea/e-commerce-microservices/main/infra/dep/notification-service-dep.yaml"
)

sleep 5

for deployment in "${deployments[@]}"; do
    kubectl apply -f "$deployment"
    sleep 1
done

# Horizontal Pod Autoscaler (HPA)
hpa_files=(
    "https://raw.githubusercontent.com/alexdragnea/e-commerce-microservices/main/infra/hpa/api-gateway-hpa.yml"
    "https://raw.githubusercontent.com/alexdragnea/e-commerce-microservices/main/infra/hpa/discovery-service-hpa.yml"
    "https://raw.githubusercontent.com/alexdragnea/e-commerce-microservices/main/infra/hpa/inventory-service-hpa.yml"
    "https://raw.githubusercontent.com/alexdragnea/e-commerce-microservices/main/infra/hpa/notification-service-hpa.yml"
    "https://raw.githubusercontent.com/alexdragnea/e-commerce-microservices/main/infra/hpa/monitoring-service-hpa.yml"
    "https://raw.githubusercontent.com/alexdragnea/e-commerce-microservices/main/infra/hpa/order-service-hpa.yml"
    "https://raw.githubusercontent.com/alexdragnea/e-commerce-microservices/main/infra/hpa/user-service-hpa.yml"
)

for hpa_file in "${hpa_files[@]}"; do
    kubectl apply -f "$hpa_file"
    sleep 1
done