# flutter_frontend

A new Flutter project.

## Getting Started

This project is a starting point for a Flutter application.

A few resources to get you started if this is your first Flutter project:

- [Lab: Write your first Flutter app](https://docs.flutter.dev/get-started/codelab)
- [Cookbook: Useful Flutter samples](https://docs.flutter.dev/cookbook)

For help getting started with Flutter development, view the
[online documentation](https://docs.flutter.dev/), which offers tutorials,
samples, guidance on mobile development, and a full API reference.

## Problems

1. In case of changing github secrets for the frontend. Docker has to be forced to rebuild the container, so the secrets are available. Otherwise the cache will skip this step.
```bash
docker-compose --env-file coin_library.env build --no-cache frontend
```
Also do not forget to add it as a build arg in the frontend.Dockerfile