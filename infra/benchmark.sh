#!/bin/bash

url="http://localhost:8888/v1/user/login"
json_body='{"email": "alexdg722@gmail.com", "password": "pass"}'

for ((i = 1; i <= 1000; i++)); do
    curl -X POST -H "Content-Type: application/json" -d "$json_body" "$url"
    echo "Request $i sent"
done
