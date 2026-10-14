# Control de Asistencia

Aplicación de escritorio desarrollada en Java para registrar la entrada y salida de empleados y consultar su historial de fichajes.

## Funcionalidades

- Registrar empleados con identificador, nombre y departamento.
- Registrar entradas y salidas de empleados.
- Evitar que un empleado registre una nueva entrada si ya tiene un fichaje abierto.
- Consultar el historial de fichajes de cada empleado.
- Calcular la duración de cada fichaje y el total de horas del día.
- Guardar y recuperar los datos localmente mediante serialización de objetos Java.

## Tecnologías

- Java
- Swing para la interfaz gráfica
- `java.time` para fechas, horas y duraciones
- Serialización de objetos para persistencia local

## Estructura del proyecto

```text
control-asistencia/
├── src/
│   ├── AppAsistencia.java
│   ├── Empleado.java
│   ├── Fichaje.java
│   ├── GestorAsistencia.java
│   └── InterfazAsistencia.java
├── README.md
└── .gitignore
```

## Requisitos

- JDK 11 o superior.

## Compilación y ejecución

Desde la carpeta raíz del proyecto, crea una carpeta de salida, compila los archivos fuente y ejecuta la clase principal.

En Windows (PowerShell o CMD):

```powershell
mkdir out
javac -d out src\AppAsistencia.java src\Empleado.java src\Fichaje.java src\GestorAsistencia.java src\InterfazAsistencia.java
java -cp out AppAsistencia
```

En macOS o Linux:

```bash
mkdir -p out
javac -d out src/AppAsistencia.java src/Empleado.java src/Fichaje.java src/GestorAsistencia.java src/InterfazAsistencia.java
java -cp out AppAsistencia
```

Al iniciar por primera vez, no habrá empleados si no existe un archivo de datos previo. Utiliza **Nuevo Empleado** para registrar el primero.

## Persistencia de datos

La aplicación guarda empleados y fichajes en el archivo local `asistencia.dat`, creado en el directorio de trabajo desde el que se ejecuta el programa. Este archivo se genera al utilizar la aplicación y está excluido del repositorio mediante `.gitignore`.

## Alcance

Es una aplicación de escritorio con almacenamiento local en archivos; no utiliza una base de datos ni un backend remoto.
