#!/usr/bin/env bash

openssl aes-256-cbc \
    -K "${encrypted_79917fed2cf8_key}" \
    -iv "${encrypted_79917fed2cf8_iv}" \
    -in ./travis/codesigning.asc.enc \
    -out ./travis/codesigning.asc \
    -d

gpg --fast-import travis/codesigning.asc