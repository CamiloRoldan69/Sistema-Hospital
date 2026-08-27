# Guía de explicación del código (para sustentar el proyecto)

Esta guía explica, en lenguaje simple, los fragmentos de código que
más probablemente te van a preguntar. No hace falta memorizarla:
entiende la idea y explícala con tus palabras.

---

## 1. Clases abstractas (`Personal` y `Paciente`)

```java
public abstract class Personal {
    ...
    public abstract String generarReporte();
}
```

**Idea:** una clase abstracta es un "molde incompleto". No puedes
hacer `new Personal(...)` porque no tiene sentido — en el hospital
real nunca contratas a un "Personal genérico", contratas a un médico
o a un enfermero.

La clase sí puede tener código ya terminado (como los `getters`),
pero el método `generarReporte()` no tiene cuerpo, solo la firma
terminada en `;`. Eso **obliga** a cada subclase (`Medico`,
`Enfermero`) a escribir su propia versión. Si no lo hacen, el
programa no compila. Es la manera que tiene Java de decir: "toda
subclase de Personal SABE generar su reporte, pero cada una lo hace
distinto".

---

## 2. Herencia (`extends`)

```java
public class Medico extends Personal {
    public Medico(String nombre, String id, String especialidad) {
        super(nombre, id, especialidad);
        ...
    }
}
```

**Idea:** "un Médico ES-UN Personal". `Medico` hereda automáticamente
`nombre`, `id`, `especialidad` y los métodos `getNombre()`, `getId()`,
`getEspecialidad()` de `Personal`, sin tener que reescribirlos.

`super(...)` en la primera línea del constructor llama al
constructor de la clase padre para que inicialice esos atributos
heredados. Siempre debe ir primero.

---

## 3. Sobreescritura vs. sobrecarga (los dos "polimorfismos")

Estos dos conceptos se confunden mucho, pero son diferentes:

### Sobreescritura (`@Override`) — polimorfismo **dinámico**

```java
@Override
public String generarReporte() {
    return "=== MÉDICO ===" + ...;
}
```

Es cuando una subclase **reemplaza** un método que ya existía en la
clase padre (`generarReporte()` estaba en `Personal`, y `Medico` lo
reescribe con su propia versión). Se llama "dinámico" porque Java
decide, **mientras el programa corre**, cuál versión ejecutar según
el tipo real del objeto:

```java
for (Personal p : personal) {
    System.out.println(p.generarReporte()); // usa la versión correcta automáticamente
}
```

Esa línea no sabe si `p` es un `Medico` o un `Enfermero`, ¡y no le
importa! Java mira el objeto real en memoria y llama a la versión
correspondiente.

### Sobrecarga — polimorfismo **estático**

```java
public void agendarCita(String fecha) { ... }
public void agendarCita(String fecha, String motivo) { ... }
```

Es tener **dos métodos con el mismo nombre** pero diferente cantidad
(o tipo) de parámetros. Aquí Java decide cuál usar **antes de
ejecutar el programa**, con solo mirar cuántos argumentos le pasaste
al llamarlo. Por eso es "estático" (se resuelve en tiempo de
compilación, no de ejecución).

---

## 4. `final` en una clase

```java
public final class PacienteHospitalizado extends Paciente {
```

**Idea:** `final` aquí significa "esta clase es el final del
camino". Nadie puede escribir `class X extends
PacienteHospitalizado`. Se usa cuando el diseño dice que no tiene
sentido crear subtipos de esta clase.

---

## 5. Composición vs. agregación vs. asociación

Las tres son formas en que una clase "usa" a otra, pero con
distinto nivel de dependencia:

| Relación | Ejemplo en el proyecto | ¿Qué significa? |
|---|---|---|
| **Composición** | `Hospital` crea sus `Sala` en su propio constructor | Las salas nacen y mueren con el hospital. No existen "sueltas". |
| **Agregación** | `Sala` guarda una `ArrayList<Paciente>` | La sala y el paciente pueden existir cada uno sin el otro. Un paciente puede estar sin sala asignada. |
| **Asociación** | `Cita` guarda referencias a `Medico` y `Paciente` | Es la relación más libre: `Cita` simplemente "conoce" a los otros dos objetos, sin poseerlos. |

Fíjate en el constructor de `Hospital`:

```java
public Hospital(String nombre) {
    ...
    salas.add(new Sala("Urgencias", 10));  // composición: nace aquí adentro
    salas.add(new Sala("Pediatria", 8));
    salas.add(new Sala("Cirugia", 6));
}
```

Eso es composición: nadie de afuera le pasa las salas al hospital,
él mismo las crea.

---

## 6. Excepciones personalizadas (checked)

```java
public class CamaNoDisponibleException extends Exception {
    public CamaNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}
```

**Idea:** todas nuestras excepciones extienden `Exception` (no
`RuntimeException`). Eso las hace **"checked"**: el compilador
obliga a manejarlas, ya sea con `try/catch` o declarándolas con
`throws` en la firma del método. Esa obligación es justo lo que
evita que el programa se caiga por sorpresa: si un método puede
fallar, Java te fuerza a decidir qué hacer con ese fallo.

`super(mensaje)` guarda el texto de error dentro del objeto
excepción, para poder leerlo después con `e.getMessage()`.

### Por qué el programa nunca se cae

En `Sala.java`:

```java
public void agregarPaciente(Paciente p) throws CamaNoDisponibleException {
    if (pacientes.size() >= capacidad) {
        throw new CamaNoDisponibleException("Sala " + nombre + " sin camas disponibles.");
    }
    ...
}
```

`Sala` **lanza** (`throw`) la excepción, pero no la maneja — solo
avisa "esto puede fallar" con `throws` en la firma. Quien decide qué
hacer cuando falla es `App.java`:

```java
try {
    sala.agregarPaciente(paciente);
} catch (SalaNoEncontradaException | PacienteNoEncontradoException | CamaNoDisponibleException e) {
    System.out.println("Error: " + e.getMessage());
}
```

Si la sala está llena, el `catch` atrapa el error, imprime el
mensaje, y el programa **sigue corriendo normalmente** — vuelve a
mostrar el menú en vez de cerrarse.

### Multi-catch

```java
catch (SalaNoEncontradaException | PacienteNoEncontradoException | CamaNoDisponibleException e) {
```

El símbolo `|` permite capturar **varios tipos de excepción en un
solo bloque**, cuando el manejo (aquí, imprimir el mensaje) va a ser
igual sin importar cuál de las tres ocurrió. Ahorra repetir el mismo
`catch` tres veces.

### `IllegalArgumentException`: la otra clase de excepción

```java
if (edad < 0 || edad > 120) {
    throw new IllegalArgumentException("Edad inválida: " + edad);
}
```

Esta SÍ es "unchecked" (no obliga a poner `try/catch`). Java la usa
así por convención cuando el error es un **argumento incorrecto**
pasado a un método o constructor — algo que, en teoría, el código
que llama debería haber validado antes. Por eso se captura de forma
opcional en `App.java`, donde sí tiene sentido mostrarle el error al
usuario:

```java
} catch (IllegalArgumentException e) {
    System.out.println("Error: " + e.getMessage());
}
```

Dato curioso: `NumberFormatException` (la que lanza
`Integer.parseInt("abc")`) es en realidad una **subclase** de
`IllegalArgumentException`. Por eso ese mismo `catch` también
atrapa cuando el usuario escribe texto donde se esperaba un número.

---

## 7. Excepciones extra que se agregaron

Además de las 3 pedidas, se agregaron 4 más para que el programa
maneje más situaciones sin romperse:

- **`MedicoNoEncontradoException`**: se lanza en
  `Hospital.buscarMedico(id)` si el ID no pertenece a ningún médico.
- **`SalaNoEncontradaException`**: se lanza en `App.java` cuando
  `hospital.buscarSala(nombre)` devuelve `null` (ese método sigue el
  enunciado y retorna `null` si no encuentra la sala; `App.java`
  convierte ese `null` en una excepción para manejarlo igual que
  las demás).
- **`CodigoDuplicadoException`**: se lanza al registrar un paciente
  si el código ya está usado por otro paciente registrado.
- **`EntradaInvalidaException`**: se lanza cuando el usuario escribe
  algo que no es un número en el menú principal.

Todas siguen el mismo patrón: se lanzan (`throw`) en el lugar donde
se detecta el problema, y se capturan (`catch`) en `App.java`, que es
quien decide cómo comunicárselo al usuario.

---

## 8. El `instanceof`

```java
if (p instanceof Medico && p.getId().equalsIgnoreCase(id)) {
```

`personal` es una lista de `Personal`, pero adentro hay tanto
`Medico` como `Enfermero` mezclados. `instanceof` pregunta "¿este
objeto es realmente un Medico?" antes de tratarlo como tal. Es
necesario aquí porque solo los médicos tienen citas, así que
necesitamos filtrar solo esos.

---

## 9. Bugs encontrados al probar y cómo se corrigieron

Al probar el programa a propósito con datos "raros" aparecieron
varios errores reales. Vale la pena explicarlos, porque muestran que
entiendes el código y no solo lo copiaste:

**a) `tipo` sin validar por completo**

Antes:
```java
if (tipo == 1) {
    ... // Ambulatorio
} else {
    ... // Hospitalizado ← CUALQUIER otro número caía aquí
}
```
Si el usuario escribía `9`, el programa lo registraba como
Hospitalizado sin avisar nada raro. La corrección agrega un
`else if (tipo == 2)` y un `else` final que lanza
`EntradaInvalidaException` si no es ni `1` ni `2`.

**b) Comparaciones de texto inconsistentes**

`Hospital.buscarSala()` comparaba nombres con `equalsIgnoreCase`
(sin importar mayúsculas), pero `Sala.buscarPaciente()` comparaba
códigos con `equals` (sensible a mayúsculas). Eso hacía que el
sistema se comportara distinto según qué buscabas. Se unificó todo a
`equalsIgnoreCase`.

**c) Falta de `trim()` en varias lecturas**

Si el usuario escribía el código con un espacio de más (por accidente,
al copiar y pegar), la búsqueda fallaba aunque el paciente sí
existiera. Se agregó `.trim()` a todas las lecturas de código, nombre
de sala e ID de médico.

**d) Paciente asignable dos veces**

No existía ninguna validación que impidiera asignar el mismo paciente
a una sala si ya estaba en otra (o en la misma), lo que lo dejaba
duplicado dentro de las listas. Se agregó la excepción
`PacienteYaAsignadoException`, lanzada después de revisar todas las
salas del hospital antes de asignar.

**e) Días de hospitalización sin validar**

`PacienteHospitalizado` validaba `numeroCama` pero no
`diasHospitalizado`. Un valor como `-10` se guardaba sin problema y
aparecía tal cual en el reporte. Se agregó la misma clase de
validación que ya existía para `numeroCama`.

**f) `¿Agregar motivo?` sensible a espacios**

```java
if (conMotivo.equalsIgnoreCase("s")) {
```
`equalsIgnoreCase` resuelve mayúsculas/minúsculas, pero NO espacios.
Si el usuario escribía `" s"` (con un espacio antes), la comparación
fallaba silenciosamente y la cita se agendaba sin motivo, aunque el
usuario sí quería agregarlo. La corrección es simple: aplicar
`.trim()` al leer la respuesta, antes de comparar.

**g) Nombre y código vacíos**

Nada impedía dejar el nombre o el código en blanco (solo presionar
Enter) al registrar un paciente — se guardaba igual, con un campo
vacío que después se veía raro en cualquier reporte o búsqueda. Se
agregaron validaciones explícitas que lanzan `EntradaInvalidaException`
si alguno de los dos queda vacío tras el `trim()`.

**h) Mensaje de error "crudo" de Java al escribir texto en un campo numérico**

Al escribir `sss` en el campo Edad, el programa mostraba:
```
Error: For input string: "sss"
```
Ese es el mensaje interno que trae por defecto `NumberFormatException`
(la excepción que lanza `Integer.parseInt("sss")`). El programa NO se
caía — el `catch (IllegalArgumentException e)` sí lo atrapaba, porque
`NumberFormatException` es subclase de `IllegalArgumentException` —
pero el mensaje quedaba en inglés y poco claro, rompiendo la
consistencia con el resto de mensajes en español.

La solución fue agregar un `catch (NumberFormatException e)`
**específico, ANTES** del `catch (IllegalArgumentException e)`
genérico, con un mensaje propio:
```java
} catch (NumberFormatException e) {
    System.out.println("Error: Escribe solo números en tipo, edad, cama y días.");
} catch (IllegalArgumentException e) {
    System.out.println("Error: " + e.getMessage());
}
```
Dato de compilador: Java exige que la excepción MÁS ESPECÍFICA
(`NumberFormatException`) se capture ANTES que su superclase
(`IllegalArgumentException`) — si el orden fuera al revés, no
compilaría ("exception has already been caught"). Esto mismo se
aplicó también en `registrarPersonal()` (para el tipo de personal) y
en `crearSala()` (para la capacidad).

---

## 10. Opciones nuevas: registrar personal y crear salas

Antes, el único personal y las únicas salas eran los que
`App.java`/`Hospital` creaban al arrancar el programa (2 médicos, 1
enfermero, 3 salas). Para que el sistema sea realmente dinámico, se
agregaron dos opciones al menú que siguen el mismo patrón que ya
usaban `registrarPaciente()` y `asignarPacienteASala()`:

```java
private static void registrarPersonal(Hospital hospital) {
    try {
        ...
        // Evita ID repetido, igual que se evita código repetido
        // en registrarPaciente().
        for (Personal p : hospital.getPersonal()) {
            if (p.getId().equalsIgnoreCase(id)) {
                throw new PersonalDuplicadoException(id);
            }
        }
        ...
        Personal nuevo;
        if (tipo == 1) {
            nuevo = new Medico(nombre, id, especialidad);
        } else if (tipo == 2) {
            ...
            nuevo = new Enfermero(nombre, id, especialidad, turno);
        } else {
            throw new EntradaInvalidaException(...);
        }
        hospital.agregarPersonal(nuevo);
    } catch (...) { ... }
}
```

Fíjate en algo importante de POO aquí: la variable se declara como
`Personal nuevo` (el tipo padre), pero se le asigna un `Medico` o un
`Enfermero` según el caso. Eso funciona porque **ambos SON-UN
Personal** (herencia) — es el mismo principio que ya usábamos con
`Paciente nuevo` en `registrarPaciente()`.

Para las salas es el mismo patrón, pero más simple porque `Sala` no
tiene subclases:

```java
private static void crearSala(Hospital hospital) {
    try {
        ...
        if (hospital.buscarSala(nombre) != null) {
            throw new SalaDuplicadaException(nombre);
        }
        ...
        Sala nueva = new Sala(nombre, capacidad); // valida capacidad <= 0 adentro
        hospital.agregarSala(nueva);
    } catch (...) { ... }
}
```

Aquí se reutiliza `hospital.buscarSala(nombre)` (que ya existía) para
chequear si el nombre está libre: si no devuelve `null`, ya hay una
sala con ese nombre.

---

## 11. Cómo se conecta todo en `App.java`

El flujo típico de cualquier opción del menú es siempre el mismo
patrón:

1. Pedir datos al usuario (`sc.nextLine()`).
2. Intentar hacer la operación dentro de un `try`.
3. Si algo fue mal, una de las clases del modelo (`Sala`, `Medico`,
   `Hospital`...) lanza una excepción personalizada.
4. El `catch` correspondiente en `App.java` la atrapa e imprime un
   mensaje de error legible.
5. El programa vuelve a mostrar el menú — nunca se detiene por un
   error del usuario.

Ese patrón repetido es la razón por la que puedes escribir códigos
de paciente que no existen, nombres de sala mal escritos, o texto en
vez de números, y el programa **jamás se cae**: solo te avisa y te
deja intentar de nuevo.
