#!/usr/bin/env bash
#Exit immediately if a command exits with a non-zero status.
set -e
EXIT_STATUS=0

# Builds and Publishes a SNAPSHOT
function build_snapshot() {
  echo -e "Building and publishing a snapshot out of branch [$TRAVIS_BRANCH]"
  ./gradlew -PartifactoryRepoKey=libs-snapshot-local -DbuildInfo.build.number=${TRAVIS_COMMIT::7} artifactoryPublish --stacktrace || EXIT_STATUS=$?
}

# Builds a Pull Request
function build_pullrequest() {
  echo -e "Building pull request #$TRAVIS_PULL_REQUEST of branch [$TRAVIS_BRANCH]. Won't publish anything to Artifactory."
  ./gradlew publishToMavenLocal rat || EXIT_STATUS=$?
}

# For other branches we need to add branch name as prefix
function build_otherbranch() {
  echo -e "Building a snapshot out of branch [$TRAVIS_BRANCH] and publishing it with prefix '${TRAVIS_BRANCH}-SNAPSHOT'"
  ./gradlew -PartifactoryRepoKey=libs-snapshot-local -DbuildInfo.build.number=${TRAVIS_COMMIT::7} -PexternalVersion=${TRAVIS_BRANCH}-SNAPSHOT artifactoryPublish --stacktrace || EXIT_STATUS=$?
}

# Builds and Publishes a Tag
function build_tag() {
  echo -e "Building tag [$TRAVIS_TAG] and publishing it as a release"
  ./gradlew -PartifactoryRepoKey=libs-release-local -PexternalVersion=$TRAVIS_TAG artifactoryPublish --stacktrace || EXIT_STATUS=$?

}

echo -e "TRAVIS_BRANCH=$TRAVIS_BRANCH"
echo -e "TRAVIS_TAG=$TRAVIS_TAG"
echo -e "TRAVIS_COMMIT=${TRAVIS_COMMIT::7}"
echo -e "TRAVIS_PULL_REQUEST=$TRAVIS_PULL_REQUEST"

# Build Logic
if [ "$TRAVIS_PULL_REQUEST" != "false" ]; then
  build_pullrequest
elif [ "$TRAVIS_PULL_REQUEST" == "false" ] && [ "$TRAVIS_BRANCH" != "$BUILD_SNAPSHOTS_BRANCH" ] && [ "$TRAVIS_TAG" == "" ]  ; then
  build_otherbranch
elif [ "$TRAVIS_PULL_REQUEST" == "false" ] && [ "$TRAVIS_BRANCH" == "$BUILD_SNAPSHOTS_BRANCH" ] && [ "$TRAVIS_TAG" == "" ] ; then
  build_snapshot
elif [ "$TRAVIS_PULL_REQUEST" == "false" ] && [ "$TRAVIS_TAG" != "" ]; then
  build_tag
else
  echo -e "WARN: Unexpected env variable values => Branch [$TRAVIS_BRANCH], Tag [$TRAVIS_TAG], Pull Request [#$TRAVIS_PULL_REQUEST]"
  ./gradlew clean build
fi

exit ${EXIT_STATUS}
