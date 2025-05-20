CURRENT_DIR=$(cd -P -- "$(dirname -- "$0")" && pwd -P)
ROOT_DIR="$CURRENT_DIR/../"
IMG_NAME=test_container:SNAPSHOT
echo "$ROOT_DIR"

echo [Application] creating jar...
(cd "$ROOT_DIR" && exec ./mvnw package)

echo [Application] build docker image..
(cd "$ROOT_DIR" && exec docker build -t "$IMG_NAME" .)

echo [Application] start docker compose
(cd "$CURRENT_DIR"/image-compose && IMG_NAME="$IMG_NAME" docker-compose up -d)