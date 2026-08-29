#!/bin/bash
# Builds the API image and pushes it to the ECR repo created by Terraform.
set -euo pipefail

cd "$(dirname "$0")/.."

AWS_REGION=$(terraform -chdir=infra/ec2 output -raw aws_region)
ECR_REPO_URL=$(terraform -chdir=infra/ec2 output -raw ecr_repository_url)
ECR_REGISTRY="${ECR_REPO_URL%%/*}"

aws ecr get-login-password --region "$AWS_REGION" \
  | docker login --username AWS --password-stdin "$ECR_REGISTRY"

docker build --platform linux/amd64 -t "$ECR_REPO_URL:latest" .
docker push "$ECR_REPO_URL:latest"

echo "Pushed $ECR_REPO_URL:latest"
echo "Run scripts/redeploy.sh to pull it onto the running instance."
