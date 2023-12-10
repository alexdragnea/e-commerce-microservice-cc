provider "kubernetes" {
  config_path = "~/.kube/config"  
}

resource "kubernetes_service" "pgadmin-service" {
  metadata {
    name = "pgadmin-service"
    labels = {
      app = "pgadmin"
    }
  }

  spec {
    selector = {
      app = "pgadmin"
    }

    port {
      port        = 80
      target_port = 80
    }

    type = "LoadBalancer"
  }
}

# Define a Deployment for pgAdmin
resource "kubernetes_deployment" "pgadmin" {
  metadata {
    name = "pgadmin"
  }

  spec {
    replicas = 1

    selector {
      match_labels = {
        app = "pgadmin"
      }
    }

    template {
      metadata {
        labels = {
          app = "pgadmin"
        }
      }

      spec {
        container {
          name  = "pgadmin"
          image = "dpage/pgadmin4:latest"

          env {
            name  = "PGADMIN_DEFAULT_EMAIL"
            value = "admin@admin.com"
          }

          env {
            name  = "PGADMIN_DEFAULT_PASSWORD"
            value = "admin"
          }

          env {
            name  = "PGADMIN_LISTEN_PORT"
            value = "80"
          }

          env {
            name  = "PGADMIN_LISTEN_ADDRESS"
            value = "0.0.0.0"
          }

          env {
            name  = "PGADMIN_SERVER_PORT"
            value = "5432"
          }
        }
      }
    }
  }
}
