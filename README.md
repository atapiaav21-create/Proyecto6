# Alke Wallet - Módulo 6

Proyecto Android desarrollado para la evaluación del Módulo 6 del bootcamp. La aplicación corresponde a una billetera virtual básica que permite registrar usuarios, iniciar sesión, almacenar información localmente y gestionar transacciones.

## Tecnologías utilizadas

- Android
- Kotlin
- Gradle
- Room
- Retrofit
- Gson Converter
- Picasso
- Coroutines
- StateFlow
- Arquitectura MVVM

## Funcionalidades

- Registro de usuario.
- Inicio de sesión mediante correo y contraseña.
- Almacenamiento local de usuarios.
- Almacenamiento local de transacciones.
- Consulta del historial de transacciones.
- Registro de envíos y solicitudes de dinero.
- Fecha, monto, tipo y descripción de cada transacción.
- Consulta de información desde una API REST mediante Retrofit.
- Visualización de imagen de perfil mediante Picasso.
- Validación básica de datos ingresados.
- Manejo básico de errores de acceso, almacenamiento y conexión.

## Arquitectura

El proyecto utiliza una estructura basada en MVVM:

- **View:** `MainActivity` y archivos XML de interfaz.
- **ViewModel:** `WalletViewModel`, encargado de gestionar el estado y las operaciones de la interfaz.
- **Repository:** `WalletRepository`, encargado de centralizar el acceso a datos locales y remotos.
- **Data:** contiene las entidades, DAO, base de datos local y componentes de conexión con la API.

## Persistencia local con Room

Room se utiliza para almacenar información de manera local.

Las entidades principales son:

- `Usuario`: almacena los datos básicos del usuario.
- `Transaccion`: almacena el historial de movimientos.

Los DAO utilizados son:

- `UsuarioDao`
- `TransaccionDao`

La base de datos se encuentra en `AppDatabase`.

Las contraseñas no se almacenan directamente en texto plano. Antes de guardarlas se aplica una función de hash SHA-256 mediante `SecurityUtils`.

## Consumo de API REST

La comunicación con una API REST se realiza mediante Retrofit.

La aplicación utiliza:

- `RetrofitClient`: configuración de Retrofit.
- `WalletApiService`: definición de los endpoints.
- `ApiUser`: modelo de datos recibido desde la API.

La API utilizada para la demostración es:

`https://jsonplaceholder.typicode.com/`

Se utiliza el endpoint de usuarios para demostrar el consumo de información remota y el manejo básico de errores de conexión.

## Picasso

Picasso se utiliza para cargar la imagen de perfil desde una URL remota.

También se configuraron imágenes de reemplazo para los casos en que la imagen no pueda cargarse correctamente.

## Manejo de errores

Se incorporaron validaciones y manejo básico de excepciones para situaciones como:

- Campos obligatorios vacíos.
- Montos inválidos.
- Credenciales incorrectas.
- Errores al guardar o consultar información local.
- Problemas de conexión con la API.

Los mensajes se gestionan desde el `WalletViewModel` mediante `StateFlow`.

## Estructura principal

```text
Proyecto6
├── app
│   └── src
│       └── main
│           ├── java/com/example/proyecto6
│           │   ├── data
│           │   │   ├── local
│           │   │   ├── remote
│           │   │   └── repository
│           │   ├── viewmodel
│           │   └── MainActivity.kt
│           └── res
│               ├── layout
│               └── values
├── gradle
├── build.gradle
├── gradle.properties
├── settings.gradle
└── README.md
```

## Clases principales

### `MainActivity`

Controla la interfaz principal y las acciones del usuario, como iniciar sesión, registrar usuarios, realizar transacciones y acceder al perfil.

### `WalletViewModel`

Gestiona el estado de la aplicación y comunica la interfaz con el repositorio utilizando `StateFlow` y corrutinas.

### `WalletRepository`

Centraliza las operaciones de acceso a datos locales mediante Room y las operaciones de consulta a la API mediante Retrofit.

### `AppDatabase`

Define la base de datos local utilizada por la aplicación.

### `UsuarioDao` y `TransaccionDao`

Contienen las operaciones de consulta e inserción de usuarios y transacciones.

### `RetrofitClient` y `WalletApiService`

Permiten configurar y consumir la API REST.

### `SecurityUtils`

Contiene la función utilizada para generar el hash de las contraseñas antes de almacenarlas.

## Compilación

El proyecto fue configurado y comprobado mediante Gradle.

Comando utilizado:

```text
./gradlew assembleDebug
```

La compilación Debug finalizó correctamente.

## Consideraciones

Este proyecto tiene fines académicos y busca demostrar la integración de persistencia local, consumo de servicios REST y una estructura basada en MVVM.

Para una aplicación financiera real sería necesario implementar mecanismos adicionales de seguridad, autenticación y protección de datos.