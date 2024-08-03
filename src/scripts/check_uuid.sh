#!/bin/bash

USER_FILE="src/data/user-store.txt"
PROVIDED_UUID="$1"
#PROVIDED_UUID="a20c8e45-b48a-4de4-a21c-042498f6f4a0"

while IFS=$'\t' read -r storedEmail storedUUID
    do
        if [ "$storedUUID" == "$PROVIDED_UUID" ]; then
            echo "UUID validation successful"
            exit 0
        fi
    done < "$USER_FILE"

    echo "UUID not found"
    exit 1
