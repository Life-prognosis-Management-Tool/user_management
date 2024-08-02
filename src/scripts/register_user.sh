#!/bin/bash

USER_FILE="src/data/user-store.txt"


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
ROLE="${12}"

INITIAL_DATA="$EMAIL\t$PASSWORD\t$UUID\t$FIRST_NAME\t$LAST_NAME\t$DOB\t$HAS_HIV\t$HIV_DIAGNOSIS_DATE\t$ON_ART\t$ART_START_DATE\t$COUNTRY_ISO\t$ROLE"

echo -e "$INITIAL_DATA" >> "$USER_FILE"

echo "SUCCESS! Your information is saved. Please enter 1 to Login"