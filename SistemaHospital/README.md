# Sistema de Gestión Hospitalaria

Proyecto de Programación Orientada a Objetos en Java. Simula un
hospital por consola: pacientes, médicos, enfermeros, salas y citas.

## Cómo abrirlo y correrlo en VS Code

1. **Instala la extensión "Extension Pack for Java"** (de Microsoft)
   desde el ícono de extensiones en VS Code, si no la tienes.
2. **Abre la carpeta `SistemaHospital`** completa en VS Code
   (`Archivo > Abrir carpeta...`), no solo un archivo.
3. Abre `src/com/hospital/app/App.java`.
4. Haz clic en **"Run"** (aparece arriba del método `main`) o presiona
   `F5`. VS Code compila y ejecuta automáticamente.
5. La consola interactiva (`TERMINAL` / panel de "Debug Console" o
   "Java" abajo) te mostrará el menú. Escribe el número de la opción
   y presiona Enter.

### Alternativa por terminal (sin extensión)

Si prefieres compilar y correr a mano, abre una terminal en la
carpeta `SistemaHospital` y ejecuta:

```bash
# Compilar todo el código fuente hacia la carpeta bin/
javac -d bin -encoding UTF-8 $(find src -name "*.java")

# Ejecutar el programa
java -cp bin com.hospital.app.App
```

En Windows (PowerShell), reemplaza la primera línea por:

```powershell
javac -d bin -encoding UTF-8 (Get-ChildItem -Recurse -Filter *.java -Path src).FullName
java -cp bin com.hospital.app.App
```

## Estructura del proyecto

```
SistemaHospital/
└── src/com/hospital/
    ├── modelo/        ← las clases del dominio (Personal, Paciente, Hospital...)
    ├── excepciones/    ← las excepciones personalizadas
    └── app/            ← App.java, el menú principal
```

## Sobre las excepciones personalizadas

El enunciado pedía 3 excepciones (`CamaNoDisponibleException`,
`PacienteNoEncontradoException`, `CitaInvalidaException`). Se
agregaron **5 excepciones adicionales** para cubrir más escenarios de
error sin que el programa se detenga:

| Excepción | Cuándo se lanza |
|---|---|
| `CamaNoDisponibleException` | La sala ya está llena |
| `PacienteNoEncontradoException` | El código de paciente no existe |
| `CitaInvalidaException` | Fecha o motivo de una cita vienen vacíos |
| `MedicoNoEncontradoException` *(extra)* | El ID de médico no existe |
| `SalaNoEncontradaException` *(extra)* | El nombre de sala no existe |
| `CodigoDuplicadoException` *(extra)* | Se intenta registrar un código de paciente repetido |
| `EntradaInvalidaException` *(extra)* | El usuario escribe texto donde se esperaba un número (en el menú o en el tipo de paciente) |
| `PacienteYaAsignadoException` *(extra)* | Se intenta asignar a una sala un paciente que ya está en alguna sala |

Todas son **checked exceptions** (extienden `Exception`, no
`RuntimeException`), y todas se capturan con `try/catch` dentro de
`App.java`, así que ningún error del usuario tumba el programa: solo
se imprime "Error: ..." y el menú vuelve a aparecer.

Además, ver `GUIA-EXPLICACION.md` para una explicación fragmento por
fragmento del código, pensada para poder explicárselo al profesor.

## Correcciones aplicadas tras pruebas manuales

Durante las pruebas del programa se encontraron y corrigieron estos
detalles:

1. **Tipo de paciente sin validar:** al registrar un paciente, cualquier
   valor distinto de `1` se trataba como "Hospitalizado" (por ejemplo
   escribir `9`). Ahora se valida explícitamente `1` o `2`; cualquier
   otro valor lanza `EntradaInvalidaException`.
2. **Comparación de códigos inconsistente:** los nombres de sala se
   comparaban ignorando mayúsculas (`equalsIgnoreCase`), pero los
   códigos de paciente no. Ahora todo el sistema compara códigos con
   `equalsIgnoreCase`.
3. **Espacios en blanco no eliminados:** varios campos (código,
   nombre de sala, ID de médico) no usaban `trim()`, así que un
   espacio de más al escribir hacía que "no se encontrara" algo que
   sí existía. Ahora se aplica `trim()` en todas esas lecturas.
4. **Paciente asignado dos veces:** no había validación que impidiera
   asignar el mismo paciente a una sala si ya estaba en otra (o en la
   misma). Se agregó la excepción `PacienteYaAsignadoException` para
   evitarlo.
5. **Días de hospitalización negativos:** `PacienteHospitalizado` no
   validaba este campo, así que `-10` se guardaba y se mostraba tal
   cual. Ahora lanza `IllegalArgumentException` si es negativo.
6. **`¿Agregar motivo?` sin `trim()`:** si el usuario escribía `" s"`
   (con un espacio antes), no coincidía con `"s"` y la cita se
   agendaba sin motivo aunque el usuario sí quería agregarlo. Ahora
   se le aplica `trim()` antes de comparar.
7. **Nombre y código vacíos al registrar:** no había validación de
   campos vacíos; ahora ambos lanzan `EntradaInvalidaException` si
   quedan en blanco.

## Diagrama de clases

Está en `diagrama-hospital.puml`. Para verlo:
- **En VS Code:** instala la extensión *PlantUML* de "jebbs" y
  presiona `Alt+D` con el archivo abierto.
- **Online:** copia el contenido del archivo en
  [plantuml.com/plantuml](https://www.plantuml.com/plantuml/uml/).
