package tests.usuario.administrador;

import Proyecto.*;

import Proyecto.Enum.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.lang.reflect.Field;
import java.nio.file.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestPersistenciaManager {

	@TempDir
    Path tempDir;

    private ArrayList<CuentaUsuario> cuentas;
    private ArrayList<Producto> productos;
    private Menu menu;

    @BeforeEach
    void setUp() throws Exception {
        redirigirRutas();

        cuentas  = new ArrayList<>();
        productos = new ArrayList<>();

        Cliente cliente = new Cliente("Juan", 1, "juan01", "pass", Rol.CLIENTE,
                "juan@mail.com", 123456, "Pérez",
                new ArrayList<>(), null, null, false);
        cliente.setPuntosFidelidad(100);
        cliente.setPuntosGenerados(200);
        cuentas.add(cliente);

        ArrayList<Turno> turnos = new ArrayList<>();
        turnos.add(new Turno("Lunes", "08:00", "16:00", "2025-06-15"));
        Mesero mesero = new Mesero("Carlos", 2, "carlos01", "pass", Rol.EMPLEADO,
                turnos, TipoEmpleado.MESERO, false,
                new ArrayList<>(), new ArrayList<>(), null, new ArrayList<>());
        cuentas.add(mesero);

        Cocinero cocinero = new Cocinero("Ana", 3, "ana01", "pass", Rol.EMPLEADO,
                new ArrayList<>(), TipoEmpleado.COCINERO, false, new ArrayList<>());
        cuentas.add(cocinero);

        Caracteristicas car = new Caracteristicas(Categoria.ESTRATEGIA, 10);
        car.setNombre("Catan");
        car.setAnioPublicacion(1995);
        car.setEmpresaMatriz("Kosmos");
        car.setNumeroJugadores(4);
        car.setEstado("Bueno");
        car.setRestriccionEdad(8);
        car.setDificil(false);

        Producto juego = new Producto(50000, "Catan", "Juego de estrategia",
                false, false, new ArrayList<>(),
                TipoProducto.JUEGOMESAPRESTAMO, 10, car, 5, false);
        juego.setCantidadDisponible(3);
        productos.add(juego);

        Producto bebida = new Producto(8000, "Agua", "Agua natural",
                false, false, new ArrayList<>(),
                TipoProducto.BEBIDA, 20);
        productos.add(bebida);
        
        InventarioJuegoPrestamo inventarioPrestamo = new InventarioJuegoPrestamo(new ArrayList<>());
        inventarioPrestamo.agregarJuego(juego);
        ArrayList<Producto> menuProductos = new ArrayList<>();
        menuProductos.add(bebida);
        menu = new Menu(menuProductos, inventarioPrestamo);
    }

    private void redirigirRutas() throws Exception {
        Files.createDirectories(tempDir.resolve("Data"));
        setRuta("RUTA_CUENTAS",         tempDir.resolve("Data/cuentas.csv").toString());
        setRuta("RUTA_CLIENTES",         tempDir.resolve("Data/clientes.csv").toString());
        setRuta("RUTA_EMPLEADOS",        tempDir.resolve("Data/empleados.csv").toString());
        setRuta("RUTA_PRODUCTOS",        tempDir.resolve("Data/productos.csv").toString());
        setRuta("RUTA_CARACTERISTICAS",  tempDir.resolve("Data/caracteristicas.csv").toString());
        setRuta("RUTA_TURNOS",           tempDir.resolve("Data/turnos.csv").toString());
        setRuta("RUTA_MESEROS_JUEGOS",   tempDir.resolve("Data/meseros_juegos.csv").toString());
        setRuta("RUTA_ULTIMO_ID",        tempDir.resolve("Data/ultimo_id.txt").toString());
        setRuta("RUTA_MENU",             tempDir.resolve("Data/menu.csv").toString());
    }

    private void setRuta(String campo, String valor) throws Exception {
        Field f = PersistenciaManager.class.getDeclaredField(campo);
        f.setAccessible(true);
        f.set(null, valor);
    }

    @Test
    void guardarYCargar_cliente_recuperaDatosCorrectos() {
        PersistenciaManager.guardarTodo(cuentas, productos, menu);
        ArrayList<CuentaUsuario> cargadas = PersistenciaManager.cargarCuentas();

        Cliente c = (Cliente) cargadas.stream()
                .filter(cu -> cu instanceof Cliente)
                .findFirst().orElse(null);

        assertNotNull(c);
        assertEquals("Juan",          c.getNombre());
        assertEquals("juan@mail.com", c.getEmail());
        assertEquals(123456,          c.getCedula());
        assertEquals("Pérez",         c.getApellido());
        assertEquals(100,             c.getPuntosFidelidad());
        assertEquals(200,             c.getPuntosGenerados());
    }

    @Test
    void guardarYCargar_mesero_recuperaTurno() {
        PersistenciaManager.guardarTodo(cuentas, productos, menu);
        ArrayList<CuentaUsuario> cargadas = PersistenciaManager.cargarCuentas();

        Mesero m = (Mesero) cargadas.stream()
                .filter(cu -> cu instanceof Mesero)
                .findFirst().orElse(null);

        assertNotNull(m);
        assertEquals(1, m.getTurnos().size());
        assertEquals("2025-06-15", m.getTurnos().get(0).getFecha());
        assertEquals("Lunes",      m.getTurnos().get(0).getDiaSemana());
    }

    @Test
    void guardarYCargar_cocinero_tipoEmpleadoCorrecto() {
        PersistenciaManager.guardarTodo(cuentas, productos, menu);
        ArrayList<CuentaUsuario> cargadas = PersistenciaManager.cargarCuentas();

        Empleado cocinero = (Empleado) cargadas.stream()
                .filter(cu -> cu instanceof Cocinero)
                .findFirst().orElse(null);

        assertNotNull(cocinero);
        assertEquals(TipoEmpleado.COCINERO, cocinero.getTipoEmpleado());
    }

    @Test
    void guardarYCargar_productoJuego_recuperaCaracteristicas() {
        PersistenciaManager.guardarTodo(cuentas, productos, menu);
        ArrayList<Producto> cargados = PersistenciaManager.cargarProductos();

        Producto juego = cargados.stream()
                .filter(p -> p.getIdentificador() == 10)
                .findFirst().orElse(null);

        assertNotNull(juego);
        assertEquals("Catan",                    juego.getNombreProducto());
        assertEquals(TipoProducto.JUEGOMESAPRESTAMO, juego.getTipoProducto());
        assertNotNull(juego.getCaracteristicas());
        assertEquals(4,              juego.getCaracteristicas().getNumeroJugadores());
        assertEquals(Categoria.ESTRATEGIA, juego.getCaracteristicas().getCategoria());
        assertEquals(3,              juego.getCantidadDisponible());
    }

    @Test
    void guardarYCargar_productoBebida_sinCaracteristicas() {
        PersistenciaManager.guardarTodo(cuentas, productos, menu);
        ArrayList<Producto> cargados = PersistenciaManager.cargarProductos();

        Producto bebida = cargados.stream()
                .filter(p -> p.getIdentificador() == 20)
                .findFirst().orElse(null);

        assertNotNull(bebida);
        assertEquals("Agua", bebida.getNombreProducto());
        assertNull(bebida.getCaracteristicas());
    }

    @Test
    void guardarYCargar_meseroJuegos_relacionCorrecta() {
        Mesero mesero = (Mesero) cuentas.stream()
                .filter(c -> c instanceof Mesero).findFirst().orElse(null);
        mesero.agregarJuegoQueExplica(productos.get(0));

        PersistenciaManager.guardarTodo(cuentas, productos, menu);

        ArrayList<CuentaUsuario> cargadas = PersistenciaManager.cargarCuentas();
        ArrayList<Producto> cargados      = PersistenciaManager.cargarProductos();
        PersistenciaManager.cargarJuegosMeseros(cargadas, cargados);

        Mesero m = (Mesero) cargadas.stream()
                .filter(c -> c instanceof Mesero).findFirst().orElse(null);

        assertNotNull(m);
        assertEquals(1, m.getJuegosQueExplica().size());
        assertEquals(10, m.getJuegosQueExplica().get(0).getIdentificador());
    }
    
    @Test
    void guardarYCargar_menu_recuperaMenuCorrectamente() {
        PersistenciaManager.guardarTodo(cuentas, productos, menu);
        ArrayList<Producto> cargados = PersistenciaManager.cargarProductos();
        Menu menuCargado = PersistenciaManager.cargarMenu(cargados);
        
        assertNotNull(menuCargado);
        assertEquals(1, menuCargado.getMenuProductos().size());
        assertEquals(20, menuCargado.getMenuProductos().get(0).getIdentificador());
        assertEquals(1, menuCargado.getInventarioJuegos().getProductos().size());
        assertEquals(10, menuCargado.getInventarioJuegos().getProductos().get(0).getIdentificador());
    }

    @Test
    void cargarCuentas_archivoVacio_retornaListaVacia() {
        ArrayList<CuentaUsuario> cargadas = PersistenciaManager.cargarCuentas();
        assertTrue(cargadas.isEmpty());
    }

    @Test
    void cargarProductos_archivoVacio_retornaListaVacia() {
        ArrayList<Producto> cargados = PersistenciaManager.cargarProductos();
        assertTrue(cargados.isEmpty());
    }

    @Test
    void guardarYCargar_cantidadCuentas_coincide() {
        PersistenciaManager.guardarTodo(cuentas, productos, menu);
        ArrayList<CuentaUsuario> cargadas = PersistenciaManager.cargarCuentas();
        assertEquals(cuentas.size(), cargadas.size());
    }

    @Test
    void guardarYCargar_cantidadProductos_coincide() {
        PersistenciaManager.guardarTodo(cuentas, productos, menu);
        ArrayList<Producto> cargados = PersistenciaManager.cargarProductos();
        assertEquals(productos.size(), cargados.size());
    }
}