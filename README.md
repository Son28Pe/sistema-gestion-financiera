# Sistema de Gestión Financiera

Proyecto final del curso **Diseño de Patrones**.
Aplicación de **consola en Java**, enfocada en la **calidad del diseño**: patrones GOF, principios SOLID y GRASP.

> 📌 Esta es una **guía general** para que el equipo trabaje alineado, no un contrato rígido.
> Ajústenla conforme avancen.

## Decisiones del equipo
- **Lenguaje:** Java 21
- **Entorno:** NetBeans / IntelliJ (sin Maven/Gradle, sin librerías externas)
- **Persistencia:** archivos **JSON** (escritos a mano, sin librerías)
- **Interfaz:** consola (menú por texto)

## Estructura de paquetes (bajo `src/`)

| Paquete        | Responsabilidad                                | Sugerido   |
|----------------|------------------------------------------------|------------|
| `modelo/`      | Entidades: Cliente, Cuenta, Transacción…       | Persona 1  |
| `singleton/`   | Configuración global (Singleton)               | Persona 1  |
| `factory/`     | Creación de cuentas / transacciones (Factory)  | Persona 1  |
| `state/`       | Estados de la cuenta (State)                   | Persona 2  |
| `observer/`    | Notificaciones ante movimientos (Observer)     | Persona 2  |
| `repositorio/` | Persistencia en JSON                           | Persona 2  |
| `controlador/` | CRUD y coordinación                            | Persona 2  |
| `facade/`      | Fachada del sistema (Facade)                   | Persona 3  |
| `decorator/`   | Comisiones / intereses (Decorator)             | Persona 3  |
| `vista/`       | Menú de consola                                | Persona 3  |
| `interfaces/`  | Contratos compartidos                          | Común      |
| `util/`        | Validaciones y utilidades                      | Común      |
| `main/`        | Punto de entrada                               | Común      |

## Patrones (mínimo 6: 2 creacionales, 2 estructurales, 2 de comportamiento)

| Patrón     | Tipo           | Sugerido   |
|------------|----------------|------------|
| Singleton  | Creacional     | Persona 1  |
| Factory    | Creacional     | Persona 1  |
| Facade     | Estructural    | Persona 3  |
| Decorator  | Estructural    | Persona 3  |
| State      | Comportamiento | Persona 2  |
| Observer   | Comportamiento | Persona 2  |

## Puntos de contacto (para no chocar en el merge)
- La **`Cuenta`** es compartida: Persona 1 la crea; Persona 2 le conecta el estado (State) y las notificaciones (Observer).
- La **`Facade`** (Persona 3) usa el **controlador** (Persona 2) para ejecutar las operaciones.
- El **controlador** depende de la *interfaz* de repositorio, no de la implementación JSON.

## Trabajo con Git
- `main`: base estable y compartida.
- `persona1` / `persona2` / `persona3`: cada quien en su rama.
- Traer `main` seguido para evitar conflictos grandes.

## Cómo ejecutar
Abrir el proyecto en el IDE y correr la clase de `main/`.
