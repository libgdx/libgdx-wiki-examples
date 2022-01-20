#!/usr/bin/env bash

set -e

OUTPUT_DIR="ghpages"
mkdir -p "$OUTPUT_DIR"

while IFS= read -r -d '' -u 9
do 
    # only find repos which contain a html module
    if [[ "$REPLY" == */html/build.gradle ]]; then
        # Remove html folder and gradle filename so we figure out the projects folder
        FOLDER="${REPLY%"/html/build.gradle"}"
        # Get the actual gradle file
        GRADLE_FILE="$FOLDER/build.gradle"

        if test -f "$GRADLE_FILE"; then
            echo "Building $GRADLE_FILE"
            (cd "$FOLDER" && pwd ; chmod u+x gradlew && ./gradlew html:dist)

            if [[ $? -eq 0 ]]; then
                DIST_FOLDER="$FOLDER/html/build/dist/."
                OUTPUT_FOLDER="$OUTPUT_DIR/$FOLDER"
                echo "Copying $DIST_FOLDER to $OUTPUT_FOLDER"
                mkdir -p "$OUTPUT_FOLDER"
                cp -r "$DIST_FOLDER" "$OUTPUT_FOLDER"
            else
                echo "Couldn't build $GRADLE_FILE"
            fi
        else
            echo "$GRADLE_FILE doesnt exist"
        fi
    fi
done 9< <( find . -type f -name "build.gradle" -exec printf '%s\0' {} + )