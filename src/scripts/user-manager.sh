#! /bin/bash


# Define file paths
USER_FILE="data/users.txt"

# Get email and password from arguments
EMAIL="$1"
PASSWORD="$2"

# Check if the file exists
if [ ! -f "$USER_FILE" ]; then
    echo "User file does not exist."
    exit 1
fi

# Check if email and password match
if grep -q "$EMAIL,$PASSWORD" "$USER_FILE"; then
    echo "Authentication successful."
    exit 0
else
    echo "Authentication failed."
    exit 1
fi


