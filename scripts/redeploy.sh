#!/bin/bash
# Re-runs deploy.sh on the EC2 instance via SSM (no SSH needed) to pull the
# latest image from ECR and restart the container.
set -euo pipefail

cd "$(dirname "$0")/.."

AWS_REGION=$(terraform -chdir=infra/ec2 output -raw aws_region)
INSTANCE_ID=$(terraform -chdir=infra/ec2 output -raw ec2_instance_id)

COMMAND_ID=$(aws ssm send-command \
  --region "$AWS_REGION" \
  --instance-ids "$INSTANCE_ID" \
  --document-name "AWS-RunShellScript" \
  --parameters 'commands=["/opt/app/deploy.sh"]' \
  --query "Command.CommandId" --output text)

echo "Sent command $COMMAND_ID, waiting for it to finish..."
aws ssm wait command-executed \
  --region "$AWS_REGION" \
  --command-id "$COMMAND_ID" \
  --instance-id "$INSTANCE_ID" || true

aws ssm get-command-invocation \
  --region "$AWS_REGION" \
  --command-id "$COMMAND_ID" \
  --instance-id "$INSTANCE_ID" \
  --query "{Status:Status,StdOut:StandardOutputContent,StdErr:StandardErrorContent}" \
  --output table
