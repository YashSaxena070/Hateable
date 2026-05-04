# Hateable

Hateable is a microservices-based application implemented in this repository. It provides a modular backend of Java services and a modern TypeScript/React frontend. This README explains what Hateable does, how it works, the technologies used, the microservices architecture, and quick-start commands to run the project locally.

**What it does:**
- Provides a set of cooperating microservices to power the Hateable application (user/account management, discovery, configuration, intelligence/analysis, workspace management, and an API gateway).
- Exposes a responsive web UI (`project-companion`) for users to interact with the application.

**How it works (high level):**
- A user interacts with the frontend served by `project-companion` (Vite + React + TypeScript).
- The frontend calls an API gateway which routes requests to the appropriate backend microservice.
- Backend microservices register with the discovery service and load configuration from the config service.
- Services use shared libraries from `common-lib` and communicate over HTTP (and optionally other internal protocols). The intelligence service encapsulates domain-specific logic and analysis.

**Microservices architecture and components:**
- `api-gateway` — single entry point for frontend and external clients; routes and handles cross-cutting concerns.
- `discovery-service` — service registry (enables dynamic discovery of services).
- `config-service` — centralized configuration for services.
- `account-service` — user and account management (registration, authentication, profiles).
- `intelligence-service` — domain logic, analysis, or ML-related features.
- `workspace-service` — per-user workspace and data management.
- `common-lib` — shared code and utilities used across services.

Folders in this repository of interest:
- `Distributed-Currix/` — backend microservices and related modules.
- `project-companion/` — frontend application (Vite + React + TypeScript).

**Tech stack:**
- Backend: Java (Maven) microservices. The repo includes Maven wrappers (`mvnw`, `mvnw.cmd`).
- Frontend: Node.js, Vite, React, TypeScript.
- Infrastructure: Docker, Kubernetes manifests under `k8s/`, and `services.docker-compose.yml` for local containers.
- Misc: Shared libraries, build scripts, and automation helpers (examples: `sync-to-minio.ps1`).

**Quick start — Backend (Windows, using Maven wrapper):**
Open a PowerShell terminal and run:

```powershell
cd Distributed-Currix
.\mvnw.cmd -DskipTests package
```

To start services locally using Docker Compose (if you prefer containers):

```powershell
docker compose -f services.docker-compose.yml up --build
```

**Quick start — Frontend:**

```bash
cd project-companion
npm install
npm run dev
```

**Notes & where to look next:**
- Backend service code and their `pom.xml` files are inside `Distributed-Currix/` and subfolders.
- Frontend source and configuration live inside `project-companion/src/` and `project-companion/vite.config.ts`.
- Kubernetes manifests for deployment are in `k8s/` and `Distributed-Currix/k8s/` (when present).

If you'd like, I can:
- Add detailed, runnable `README.md` files inside `Distributed-Currix/` and `project-companion/` with service-by-service run instructions and environment examples.
- Produce a simple architecture diagram (ASCII or Mermaid) and add it to this README.

---
If you want me to add the per-subproject READMEs now, tell me which level of detail you prefer (quick-start only, or full dev+debug steps). 
