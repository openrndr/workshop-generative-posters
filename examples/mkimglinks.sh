#!/usr/bin/env bash


# run this to generate markdown links from the contents of ./images
find images -type f -name *.png -o -name *.jpg  | xargs -I % bash -c "echo '![%](%)<br>'"