locals {
  ecr_registry = "${data.aws_caller_identity.current.account_id}.dkr.ecr.${var.aws_region}.amazonaws.com"
}

resource "aws_instance" "api" {
  ami                         = data.aws_ami.al2023.id
  instance_type               = var.instance_type
  subnet_id                   = data.aws_subnets.default.ids[0]
  vpc_security_group_ids      = [aws_security_group.ec2.id]
  iam_instance_profile        = aws_iam_instance_profile.ec2.name
  associate_public_ip_address = true

  user_data = templatefile("${path.module}/templates/user_data.sh.tpl", {
    aws_region         = var.aws_region
    ecr_registry       = local.ecr_registry
    ecr_repository_url = aws_ecr_repository.api.repository_url
    db_endpoint        = aws_db_instance.postgres.address
    db_name            = var.db_name
    db_username        = var.db_username
    ssm_param_name     = aws_ssm_parameter.db_password.name
  })

  # Bump this to force a new user_data run (e.g. after changing the template).
  user_data_replace_on_change = true

  tags = {
    Name = "${var.project_name}-api"
  }
}
