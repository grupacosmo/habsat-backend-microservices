#!/usr/bin/env bash
set -x
awslocal --no-sign-request s3 mb s3://"${AWS_BUCKET_NAME}"
