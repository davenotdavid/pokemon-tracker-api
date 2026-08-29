variable "aws_region" {
  description = "AWS region to deploy into"
  type        = string
  default     = "us-east-1"
}

variable "project_name" {
  description = "Short name used to prefix/tag all resources"
  type        = string
  default     = "pokemon-tracker"
}

variable "instance_type" {
  description = "EC2 instance type running the API container"
  type        = string
  default     = "t3.micro"
}

variable "db_instance_class" {
  description = "RDS instance class (db.t3.micro / db.t4g.micro are free-tier eligible)"
  type        = string
  default     = "db.t3.micro"
}

variable "db_name" {
  description = "Postgres database name"
  type        = string
  default     = "appdb"
}

variable "db_username" {
  description = "Postgres master username"
  type        = string
  default     = "app_user"
}

variable "db_allocated_storage" {
  description = "RDS storage in GB (20GB is within the free tier)"
  type        = number
  default     = 20
}
