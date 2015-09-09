#!/usr/bin/env bash
set -e

gradle -b core/build.gradle uploadArchives
gradle -b sample/build.gradle umengBuild