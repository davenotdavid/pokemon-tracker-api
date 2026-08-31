output "api_url" {
  description = "Base URL of the deployed API (stable across redeploys via the Elastic IP)"
  value       = "http://${aws_eip.api.public_ip}"
}

output "ec2_instance_id" {
  description = "Instance ID, useful for `aws ssm start-session` / `aws ssm send-command`"
  value       = aws_instance.api.id
}

output "ecr_repository_url" {
  description = "Push images here with scripts/build_and_push.sh"
  value       = aws_ecr_repository.api.repository_url
}

output "rds_endpoint" {
  description = "Postgres endpoint (only reachable from the EC2 security group)"
  value       = aws_db_instance.postgres.address
}

output "aws_region" {
  value = var.aws_region
}
