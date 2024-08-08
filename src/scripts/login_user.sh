#!/bin/bash

USER_FILE="src/data/user-store.txt"
EMAIL="$1"
HASHED_PASSWORD="$2"

while IFS=$'\t' read -r storedEmail storedHashedPassword uuid first_name last_name dob has_hiv hiv_diagnosis_date on_art art_start_date country_iso life_remaining stored_role; do
    if [ "$storedEmail" == "$EMAIL" ]; then
        if [ "$storedHashedPassword" == "$HASHED_PASSWORD" ]; then
                echo "Authentication successful"
                echo "$storedEmail"
                echo "$first_name"
                echo "$last_name"
                echo "$dob"
                echo "$has_hiv"
                echo "$hiv_diagnosis_date"
                echo "$on_art"
                echo "$art_start_date"
                echo "$country_iso"
                echo "$life_remaining"
                echo "$storedHashedPassword"
                echo "$uuid"
                echo "$stored_role"
            exit 0
        else
            echo "Password does not match"
            exit 1
        fi
    fi
done < "$USER_FILE"

echo "Email not found"
exit 1