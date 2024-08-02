#!/bin/bash

# Define file path
USER_FILE="src/data/user-store.txt"
#
## Create the directory if it doesn't exist
#mkdir -p "$(dirname "$USER_FILE")"
#
## Collect arguments
#USER_FILE="$1"

EMAIL="$1"
PASSWORD="$2"
UUID="$3"
FIRST_NAME="$4"
LAST_NAME="$5"
DOB="$6"
HAS_HIV="$7"
HIV_DIAGNOSIS_DATE="$8"
ON_ART="$9"
ART_START_DATE="${10}"
COUNTRY_ISO="${11}"

# Hash the password using SHA-256
#HASHED_PASSWORD=$(echo -n "$PASSWORD" | sha256sum | awk '{print $1}')

# Format data
INITIAL_DATA="$EMAIL\t$PASSWORD\t$UUID\t$FIRST_NAME\t$LAST_NAME\t$DOB\t$HAS_HIV\t$HIV_DIAGNOSIS_DATE\t$ON_ART\t$ART_START_DATE\t$COUNTRY_ISO"

# Write data to the file
echo -e "$INITIAL_DATA" > "$USER_FILE"

# Confirm success
echo "Initial admin data has been written to $USER_FILE"

