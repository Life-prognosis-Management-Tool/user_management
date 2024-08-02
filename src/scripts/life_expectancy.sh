#!/bin/bash

#CODE="RWA"
CODE="$1"
INPUT_FILE="src/data/life-expectancy.csv"

LIFE_EXPECTANCY=$(awk -F, -v code="$CODE" '$6 == code {print $7}' "$INPUT_FILE")

# Check if the life expectancy was found
if [ -z "$LIFE_EXPECTANCY" ]; then
    echo "No data found for country code: $CODE"
    exit 1
fi

# Output the life expectancy value
echo "$LIFE_EXPECTANCY"
