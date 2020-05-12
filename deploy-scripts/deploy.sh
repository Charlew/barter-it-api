#!/bin/bash

set -e

if [[ -z "$HEROKU_API_NAME" ]]; then
  echo "Missing HEROKU_API_NAME env variable" >&2
fi

if [[ -z "$HEROKU_API_KEY" ]]; then
  echo "Missing HEROKU_API_KEY env variable" >&2
fi

echo "Deploying $HEROKU_API_NAME..."

docker build -t barter-it/api .
docker tag barter-it/api registry.heroku.com/"$HEROKU_API_NAME"/web
docker push registry.heroku.com/"$HEROKU_API_NAME"/web
heroku container:login
heroku container:release web --app "$HEROKU_API_NAME"

echo "Finished!"