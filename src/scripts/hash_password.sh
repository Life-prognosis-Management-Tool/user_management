#!/bin/bash

PASSWORD=$1
HASHED_PASSWORD=$(echo -n "$PASSWORD" | openssl dgst -sha256 | awk '{print $2}')
echo "$HASHED_PASSWORD"
