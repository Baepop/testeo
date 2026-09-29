# MarTech Pesca — versión 0.2

## Incluye
- Android/Kotlin
- Mapa interactivo
- GPS real del teléfono
- Posición, velocidad y rumbo
- Zoom y localización
- Consulta de coordenadas
- Guardado de waypoints
- Menú de capas
- Temperatura / clorofila / corrientes / viento / batimetría como módulos
- Base para rutas y modo offline

## Crear APK
1. Instala Android Studio.
2. Abre la carpeta `MarTechPesca_Android`.
3. Instala Android SDK Platform 35.
4. Conecta el teléfono con Depuración USB.
5. Ejecuta Run para probar.
6. Para generar APK: Build > Build Bundle(s) / APK(s) > Build APK(s).
7. El debug APK normalmente queda en:
   `app/build/outputs/apk/debug/app-debug.apk`

## Importante
Las capas oceanográficas todavía son puntos de integración: no muestran valores inventados.
La siguiente etapa debe conectar proveedores autorizados para SST, clorofila,
corrientes, viento, oleaje y batimetría.

La interfaz toma como referencia funcional la categoría de herramientas públicas
de BigBlue, pero no copia su código, marca ni recursos propietarios.

## Compilar con GitHub Actions
1. Sube este proyecto a un repositorio de GitHub (incluida la carpeta .github).
2. Pestaña Actions -> "Build APK" (se ejecuta al hacer push, o con Run workflow).
3. Descarga el APK desde Artifacts al terminar.
