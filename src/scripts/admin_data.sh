#!/bin/bash

echo "Reached HERE"
# Define file path
USER_FILE="src/data/users.txt"
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
HASHED_PASSWORD=$(echo -n "$PASSWORD" | sha256sum | awk '{print $1}')

# Format data
INITIAL_DATA="$EMAIL\t$HASHED_PASSWORD\t$UUID\t$FIRST_NAME\t$LAST_NAME\t$DOB\t$HAS_HIV\t$HIV_DIAGNOSIS_DATE\t$ON_ART\t$ART_START_DATE\t$COUNTRY_ISO"

# Write data to the file
echo -e "$INITIAL_DATA" > "$USER_FILE"

# Confirm success
echo "Initial admin data has been written to $USER_FILE"


##!/bin/bash
#
## Define file path
#USER_FILE="src/data/users.txt"
#
### Create the directory if it doesn't exist
##mkdir -p "$(dirname "$USER_FILE")"
##
### Define initial data
##EMAIL="admin@example.com"
##PASSWORD="adminpassword"  # Plain password
##UUID="123e4567-e89b-12d3-a456-426614174000"
##FIRST_NAME="John"
##LAST_NAME="Doe"
##DOB="1980-01-01"
##HAS_HIV=true
##HIV_DIAGNOSIS_DATE="2000-01-01"
##ON_ART=true
##ART_START_DATE="2001-01-01"
##COUNTRY_ISO="US"
##
### Hash the password using SHA-256
###HASHED_PASSWORD=$(echo -n "$PASSWORD" | sha256sum | awk '{print $1}')
##
### Format: email<TAB>hashed_password<TAB>UUID<TAB>first_name<TAB>last_name<TAB>DOB<TAB>has_hiv<TAB>hiv_diagnosis_date<TAB>on_art<TAB>art_start_date<TAB>country_iso
##INITIAL_DATA="$EMAIL\t$PASSWORD\t$UUID\t$FIRST_NAME\t$LAST_NAME\t$DOB\t$HAS_HIV\t$HIV_DIAGNOSIS_DATE\t$ON_ART\t$ART_START_DATE\t$COUNTRY_ISO"
##
### Write data to the file
##echo -e "$INITIAL_DATA" > "$USER_FILE"
##
### Confirm success
##echo "Initial data has been written to $USER_FILE"
