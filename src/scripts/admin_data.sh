#!/bin/bash

# Define file path
USER_FILE="src/data/user-store.txt"
#
## Create the directory if it doesn't exist
#mkdir -p "$(dirname "$USER_FILE")"
#
## Collect arguments
#USER_FILE="$1"

NUM_PARAMS=$#

 # Conditional logic based on the number of parameters
 if [ "$NUM_PARAMS" -eq 2 ]; then
    # Set Initial Patient email and uuid if we have only two parameters
    PATIENT_EMAIL="$1"
    PATIENT_UUID="$2"
   INITIAL_DATA="$PATIENT_EMAIL\t\t$PATIENT_UUID\t\t\t\t\t\t\t\t"
  echo "Saved email and uuid for the patient"
  echo -e "$INITIAL_DATA" >> "$USER_FILE"
  exit 1
 fi

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

DATA="$EMAIL\t$PASSWORD\t$UUID\t$FIRST_NAME\t$LAST_NAME\t$DOB\t$HAS_HIV\t$HIV_DIAGNOSIS_DATE\t$ON_ART\t$ART_START_DATE\t$COUNTRY_ISO"

# Temp file
TEMP_FILE=$(mktemp)

awk -v email="$EMAIL" -v new_data="$DATA" 'BEGIN{FS=OFS="\t"} $1 == email {$0 = new_data} {print}' "$USER_FILE" > "$TEMP_FILE"

mv "$TEMP_FILE" "$USER_FILE"


# Write data to the file
#echo -e "$DATA" >> "$USER_FILE"

# Confirm success
echo "Data has been written to $USER_FILE"

