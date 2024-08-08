#!/bin/bash

USER_FILE="src/data/user-store.txt"

while IFS=$'\t' read -r storedEmail storedHashedPassword uuid first_name last_name dob has_hiv hiv_diagnosis_date on_art art_start_date country_iso life_remaining stored_role; do

    echo "$storedEmail\t$uuid\t$first_name\t$last_name\t$dob\t$has_hiv\t$hiv_diagnosis_date\t$on_art\t$art_start_date\t$country_iso\t$life_remaining\t$stored_role"
done < "$USER_FILE"
