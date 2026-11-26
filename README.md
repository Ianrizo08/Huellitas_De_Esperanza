# Huellitas de Esperanza

Misma estructura y configuración de Gradle que Practica2 (Room + Hilt + Compose).

## Base de datos (Práctica 2 integrada)
Se implementan las dos primeras tablas del modelo ER: **USUARIO** y **PERRO**.

- `data/database/entity/`: `UsuarioEntity` (tabla `usuario`, correo único) y `PerroEntity` (tabla `perro`). Los nombres de columna son los del modelo ER.
- `data/database/dao/`: `UsuarioDao` y `PerroDao` (insertar, actualizar, eliminar, consultas con `Flow`).
- `data/database/AppDatabase.kt`: `huellitas.db`, versión 1, con las dos entidades.
- `data/database/SemillaDatos.kt`: datos de prueba al crear la base (4 perros y 1 administrador demo).
- `data/security/PasswordHasher.kt`: la contraseña se guarda como hash PBKDF2 con sal (`contrasena_hash`).
- `data/mapper/`, `data/repository/`, `domain/model`, `domain/repository`: misma arquitectura que Practica2.
- `data/di/`: módulos Hilt (`DataBaseModule`, `RepositoryModule`).
- `ui/PerroViewModel.kt` y `ui/UsuarioViewModel.kt`.

## Pantallas
- `ui/PrimeraPantalla.kt` (Inicio): muestra cuántos perros están disponibles (consulta a Room).
- `ui/SegundaPantalla.kt` (Catálogo): lista de la tabla `perro` con búsqueda por nombre o personalidad y filtros.
- `ui/PerfilPerroPantalla.kt` (Perfil): todos los datos del perrito seleccionado.
- `ui/NuevoPerroPantalla.kt` (Agregar perrito): formulario que inserta en la tabla `perro`.
- `ui/ApoyarPantalla.kt` (Apoyar): formas de ayudar al albergue.
- `ui/CuentaPantalla.kt` (Cuenta): inicio de sesión, registro y datos del usuario.
- `data/sesion/SesionManager.kt`: mantiene la sesión iniciada, también al reabrir la app.

## Usuario demo (solo pruebas)
`admin@huellitas.org` / `Admin123!` — eliminar antes de publicar la app.
