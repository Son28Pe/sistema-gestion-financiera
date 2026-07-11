# Integración — parte de Persona 2

Guía corta para conectar mi parte (State, Observer, repositorios, controladores) con la de los demás.

## Para Persona 1 (modelo)

La clase `Cuenta` es compartida. Mi stub ya funciona; la versión final debe **conservar**:

- Campo `IEstadoCuenta estado` y delegar en él: `depositar / retirar / bloquear / desbloquear / cerrar`.
- Implementar `ISujeto`: lista de `IObservador` + `notificar(evento)`.
- Getters/setters: `getSaldo/setSaldo`, `getEstado/setEstado`, `getNumero`, `getTitularDni`, `getTipo`.

Cuando tengas la **Factory de cuentas** y `CuentaCorriente`, hay 2 puntos marcados con
`// Integracion:` (en `ControladorCuenta` y `RepositorioCuentaJson`) donde se debe crear la
cuenta con tu Factory en vez de `new CuentaAhorros(...)`.

## Para Persona 3 (Facade + vista)

Tu `Facade` usa mis dos controladores. Cableado mínimo:

```java
var repoCli = new RepositorioClienteJson();
var repoCta = new RepositorioCuentaJson();
List<IObservador> obs = List.of(new NotificadorEmail(), new RegistroHistorial(), new AlertaFraude());

var ctrlClientes = new ControladorCliente(repoCli);
var ctrlCuentas  = new ControladorCuenta(repoCta, repoCli, obs);
```

Métodos:
- **Clientes:** `registrar(dni, nombre, correo)` · `buscar(dni)` · `listar()` · `modificar(dni, nombre, correo)` · `eliminar(dni)`
- **Cuentas:** `registrar(numero, dni, saldoInicial)` · `buscar` · `listar` · `depositar(numero, monto)` · `retirar` · `bloquear` · `desbloquear` · `cerrar` · `eliminar`

Las operaciones inválidas lanzan `OperacionInvalidaException` con un mensaje listo para mostrar.
Envuélvelas en `try/catch` en el menú.
