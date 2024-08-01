#!/bin/bash

# Define file path
USER_FILE="src/data/users.txt"

# Create the directory if it doesn't exist
mkdir -p "$(dirname "$USER_FILE")"

# Define initial data
# Format: email<TAB>password
# Example data for admin
INITIAL_DATA="admin@example.com\tadminpassword"

# Write data to the file
echo -e "$INITIAL_DATA" > "$USER_FILE"

# Confirm success
echo "Initial data has been written to $USER_FILE"
