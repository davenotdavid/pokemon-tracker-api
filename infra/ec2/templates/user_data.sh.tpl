#!/bin/bash
set -euxo pipefail

dnf update -y
dnf install -y amazon-ssm-agent docker
systemctl enable --now amazon-ssm-agent
systemctl enable --now docker

mkdir -p /usr/local/lib/docker/cli-plugins
curl -SL "https://github.com/docker/compose/releases/latest/download/docker-compose-linux-$(uname -m)" \
  -o /usr/local/lib/docker/cli-plugins/docker-compose
chmod +x /usr/local/lib/docker/cli-plugins/docker-compose

mkdir -p /opt/app

cat > /opt/app/deploy.sh <<'DEPLOY'
#!/bin/bash
set -euxo pipefail

DB_PASSWORD=$(aws ssm get-parameter --name "${ssm_param_name}" --with-decryption \
  --query Parameter.Value --output text --region "${aws_region}")

aws ecr get-login-password --region "${aws_region}" \
  | docker login --username AWS --password-stdin "${ecr_registry}"

cat > /opt/app/docker-compose.yml <<EOF
services:
  api:
    image: ${ecr_repository_url}:latest
    restart: always
    ports:
      - "80:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://${db_endpoint}/${db_name}
      SPRING_DATASOURCE_USERNAME: ${db_username}
      SPRING_DATASOURCE_PASSWORD: $${DB_PASSWORD}
EOF

cd /opt/app
docker compose pull
docker compose up -d
DEPLOY

chmod +x /opt/app/deploy.sh
/opt/app/deploy.sh
