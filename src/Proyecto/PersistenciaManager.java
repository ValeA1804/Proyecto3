package Proyecto;

import java.io.*;
import java.util.ArrayList;

import Proyecto.Enum.Categoria;
import Proyecto.Enum.Rol;
import Proyecto.Enum.TipoEmpleado;
import Proyecto.Enum.TipoProducto;

public class PersistenciaManager {
	private static String RUTA_CUENTAS         = "Data/cuentas.csv";
	private static String RUTA_CLIENTES        = "Data/clientes.csv";
	private static String RUTA_EMPLEADOS       = "Data/empleados.csv";
	private static String RUTA_PRODUCTOS       = "Data/productos.csv";
	private static String RUTA_CARACTERISTICAS = "Data/caracteristicas.csv";
	private static String RUTA_TURNOS          = "Data/turnos.csv";
	private static String RUTA_MESEROS_JUEGOS  = "Data/meseros_juegos.csv";
	private static String RUTA_ULTIMO_ID       = "Data/ultimo_id.txt";
	private static String RUTA_MENU = "Data/menu.csv";


	public static void setRutasTest(String cuentas, String clientes, String empleados,
	        String productos, String caracteristicas, String turnos,
	        String meserosJuegos, String ultimoId, String menu) {
	    RUTA_CUENTAS         = cuentas;
	    RUTA_CLIENTES        = clientes;
	    RUTA_EMPLEADOS       = empleados;
	    RUTA_PRODUCTOS       = productos;
	    RUTA_CARACTERISTICAS = caracteristicas;
	    RUTA_TURNOS          = turnos;
	    RUTA_MESEROS_JUEGOS  = meserosJuegos;
	    RUTA_ULTIMO_ID       = ultimoId;
	    RUTA_MENU 			= menu;

	}

    public static void guardarTodo(ArrayList<CuentaUsuario> cuentas, ArrayList<Producto> productos, Menu menu) {
       guardarCuentas(cuentas);
       guardarClientes(cuentas);
       guardarEmpleados(cuentas);
       guardarProductos(productos);
       guardarCaracteristicas(productos);
       guardarTurnos(cuentas);
       guardarMeserosJuegos(cuentas);
       guardarUltimoId();
       guardarMenu(menu);

   }
   

     

    public static void guardarCuentas(ArrayList<CuentaUsuario> cuentas) {
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(RUTA_CUENTAS), "UTF-8"))) {
            bw.write("id,nombre,usuario,contraseña,rol");
            bw.newLine();
            for (CuentaUsuario cuenta : cuentas) {
                bw.write(cuenta.getId() + "," +
                         cuenta.getNombre() + "," +
                         cuenta.getUsuario() + "," +
                         cuenta.getContraseña() + "," +
                         cuenta.getRol());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error guardando cuentas: " + e.getMessage());
        }
    }

    public static void guardarClientes(ArrayList<CuentaUsuario> cuentas) {
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(RUTA_CLIENTES), "UTF-8"))) {
            bw.write("id,email,cedula,apellido,puntosFidelidad,puntosGenerados,pedidoActivo");
            bw.newLine();
            for (CuentaUsuario cuenta : cuentas) {
                if (cuenta instanceof Cliente) {
                    Cliente c = (Cliente) cuenta;
                    bw.write(c.getId() + "," +
                             c.getEmail() + "," +
                             c.getCedula() + "," +
                             c.getApellido() + "," +
                             c.getPuntosFidelidad() + "," +
                             c.getPuntosGenerados() + "," +
                             c.isPedidoActivo());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error guardando clientes: " + e.getMessage());
        }
    }

    public static void guardarEmpleados(ArrayList<CuentaUsuario> cuentas) {
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(RUTA_EMPLEADOS), "UTF-8"))) {
            bw.write("id,tipoEmpleado");
            bw.newLine();
            for (CuentaUsuario cuenta : cuentas) {
                if (cuenta instanceof Empleado) {
                    Empleado e = (Empleado) cuenta;
                    bw.write(e.getId() + "," + e.getTipoEmpleado());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error guardando empleados: " + e.getMessage());
        }
    }

    public static void guardarProductos(ArrayList<Producto> productos) {
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(RUTA_PRODUCTOS), "UTF-8"))) {
            bw.write("identificador,nombreProducto,precio,descripcion,alcoholica,caliente,tipoProducto,stock,cantidadDisponible,alergenos");
            bw.newLine();
            for (Producto p : productos) {
                String alergenos = "";
                if (p.getAlergenos() != null && !p.getAlergenos().isEmpty()) {
                    alergenos = String.join("|", p.getAlergenos());
                }
                bw.write(p.getIdentificador() + "," +
                         p.getNombreProducto() + "," +
                         p.getPrecio() + "," +
                         p.getDescripcion() + "," +
                         p.isAlcoholica() + "," +
                         p.isCaliente() + "," +
                         p.getTipoProducto() + "," +
                         p.getStock() + "," +
                         p.getCantidadDisponible() + "," +
                         alergenos);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error guardando productos: " + e.getMessage());
        }
    }

    public static void guardarCaracteristicas(ArrayList<Producto> productos) {
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(RUTA_CARACTERISTICAS), "UTF-8"))) {
            bw.write("idProducto,nombre,anioPublicacion,empresaMatriz,numeroJugadores,categoria,estado,restriccionEdad,dificil");
            bw.newLine();
            for (Producto p : productos) {
                if (p.getCaracteristicas() != null) {
                    Caracteristicas c = p.getCaracteristicas();
                    bw.write(p.getIdentificador() + "," +
                             c.getNombre() + "," +
                             c.getAnioPublicacion() + "," +
                             c.getEmpresaMatriz() + "," +
                             c.getNumeroJugadores() + "," +
                             c.getCategoria() + "," +
                             c.getEstado() + "," +
                             c.getRestriccionEdad() + "," +
                             c.isDificil());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error guardando caracteristicas: " + e.getMessage());
        }
    }

    public static void guardarTurnos(ArrayList<CuentaUsuario> cuentas) {
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(RUTA_TURNOS), "UTF-8"))) {
            bw.write("idEmpleado,diaSemana,horaInicio,horaSalida,fecha");
            bw.newLine();
            for (CuentaUsuario cuenta : cuentas) {
                if (cuenta instanceof Empleado) {
                    Empleado e = (Empleado) cuenta;
                    for (Turno t : e.getTurnos()) {
                        bw.write(e.getId() + "," +
                                 t.getDiaSemana() + "," +
                                 t.getHoraInicio() + "," +
                                 t.getHoraSalida() + "," +
                                 t.getFecha());
                        bw.newLine();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error guardando turnos: " + e.getMessage());
        }
    }

    public static void guardarMeserosJuegos(ArrayList<CuentaUsuario> cuentas) {
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(RUTA_MESEROS_JUEGOS), "UTF-8"))) {
            bw.write("idMesero,idJuego");
            bw.newLine();
            for (CuentaUsuario cuenta : cuentas) {
                if (cuenta instanceof Mesero) {
                    Mesero m = (Mesero) cuenta;
                    for (Producto juego : m.getJuegosQueExplica()) {
                        bw.write(m.getId() + "," + juego.getIdentificador());
                        bw.newLine();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error guardando juegos de meseros: " + e.getMessage());
        }
    }

    public static void guardarUltimoId() {
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(RUTA_ULTIMO_ID), "UTF-8"))) {
            bw.write(String.valueOf(Pedido.getUltimoId()));
        } catch (IOException e) {
            System.out.println("Error guardando último ID: " + e.getMessage());
        }
    }

    public static ArrayList<CuentaUsuario> cargarCuentas() {
        ArrayList<CuentaUsuario> cuentas = new ArrayList<>();
        ArrayList<String[]> datosClientes  = leerCSV(RUTA_CLIENTES);
        ArrayList<String[]> datosEmpleados = leerCSV(RUTA_EMPLEADOS);
        ArrayList<String[]> datosTurnos    = leerCSV(RUTA_TURNOS);

        cargarUltimoId();

        for (String[] linea : leerCSV(RUTA_CUENTAS)) {
            if (linea.length < 5) continue;

            int    id         = Integer.parseInt(linea[0].trim());
            String nombre     = linea[1].trim();
            String usuario    = linea[2].trim();
            String contraseña = linea[3].trim();
            Rol    rol        = Rol.valueOf(linea[4].trim());

            if (rol == Rol.CLIENTE) {
                String  email            = "";
                int     cedula           = 0;
                String  apellido         = "";
                int     puntosFidelidad  = 0;
                int     puntosGenerados  = 0;
                boolean pedidoActivo     = false;

                for (String[] dc : datosClientes) {
                    if (dc.length > 0 && Integer.parseInt(dc[0].trim()) == id) {
                        email           = dc.length > 1 ? dc[1].trim() : "";
                        cedula          = dc.length > 2 ? Integer.parseInt(dc[2].trim()) : 0;
                        apellido        = dc.length > 3 ? dc[3].trim() : "";
                        puntosFidelidad = dc.length > 4 ? Integer.parseInt(dc[4].trim()) : 0;
                        puntosGenerados = dc.length > 5 ? Integer.parseInt(dc[5].trim()) : 0;
                        pedidoActivo    = dc.length > 6 ? Boolean.parseBoolean(dc[6].trim()) : false;
                        break;
                    }
                }

                // FIX: constructor real de Cliente
                Cliente c = new Cliente(nombre, id, usuario, contraseña, rol,
                        email, cedula, apellido,
                        new ArrayList<>(), null, null, false);
                c.setPuntosFidelidad(puntosFidelidad);
                c.setPuntosGenerados(puntosGenerados);
                c.setPedidoActivo(pedidoActivo);
                cuentas.add(c);

            } else if (rol == Rol.EMPLEADO) {
                TipoEmpleado tipo = TipoEmpleado.MESERO;

                for (String[] de : datosEmpleados) {
                    if (de.length > 0 && Integer.parseInt(de[0].trim()) == id) {
                        tipo = TipoEmpleado.valueOf(de[1].trim());
                        break;
                    }
                }

                ArrayList<Turno> turnos = new ArrayList<>();
                for (String[] dt : datosTurnos) {
                    if (dt.length >= 5 && Integer.parseInt(dt[0].trim()) == id) {
                        turnos.add(new Turno(dt[1].trim(), dt[2].trim(), dt[3].trim(), dt[4].trim()));
                    }
                }

                Empleado emp;
                if (tipo == TipoEmpleado.MESERO) {
                    emp = new Mesero(nombre, id, usuario, contraseña, Rol.EMPLEADO,
                            turnos, tipo, false,
                            new ArrayList<>(), new ArrayList<>(), null, new ArrayList<>());
                } else {
                    emp = new Cocinero(nombre, id, usuario, contraseña, Rol.EMPLEADO,
                            turnos, tipo, false, new ArrayList<>());
                }
                cuentas.add(emp);

            } else if (rol == Rol.ADMINISTRADOR) {
                cuentas.add(new Administrador(nombre, id, usuario, contraseña,
                        rol,
                        new InventarioJuegoVenta(new ArrayList<>()),
                        new InventarioJuegoPrestamo(new ArrayList<>()),
                        new ArrayList<>()));
            }
        }

        return cuentas;
    }
    
    public static void guardarMenu(Menu menu) {
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(RUTA_MENU), "UTF-8"))) {
            bw.write("idProducto,esDeInventario");
            bw.newLine();
            
            // Guardar productos del menú
            for (Producto p : menu.getMenuProductos()) {
                bw.write(p.getIdentificador() + ",false");
                bw.newLine();
            }
            
            // Guardar productos del inventario de préstamo
            for (Producto p : menu.getInventarioJuegos().getProductos()) {
                bw.write(p.getIdentificador() + ",true");
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error guardando menú: " + e.getMessage());
        }
    }
    public static void cargarUltimoId() {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(RUTA_ULTIMO_ID), "UTF-8"))) {
            String linea = br.readLine();
            if (linea != null && !linea.trim().isEmpty()) {
                Pedido.setUltimoId(Integer.parseInt(linea.trim()));
            }
        } catch (IOException e) {
            System.out.println("No se pudo cargar último ID, usando 0: " + e.getMessage());
        }
    }

    public static void cargarJuegosMeseros(ArrayList<CuentaUsuario> cuentas, ArrayList<Producto> productos) {
        for (String[] linea : leerCSV(RUTA_MESEROS_JUEGOS)) {
            if (linea.length < 2) continue;

            int idMesero = Integer.parseInt(linea[0].trim());
            int idJuego  = Integer.parseInt(linea[1].trim());

            Mesero  mesero = null;
            Producto juego = null;

            for (CuentaUsuario cuenta : cuentas) {
                if (cuenta instanceof Mesero && cuenta.getId() == idMesero) {
                    mesero = (Mesero) cuenta;
                    break;
                }
            }
            for (Producto p : productos) {
                if (p.getIdentificador() == idJuego) {
                    juego = p;
                    break;
                }
            }

            if (mesero != null && juego != null) {
                mesero.agregarJuegoQueExplica(juego);
            }
        }
    }

    public static ArrayList<Producto> cargarProductos() {
        ArrayList<Producto> productos = new ArrayList<>();
        ArrayList<String[]> datosCaracteristicas = leerCSV(RUTA_CARACTERISTICAS);

        for (String[] linea : leerCSV(RUTA_PRODUCTOS)) {
            if (linea.length < 9) continue;

            int          identificador    = Integer.parseInt(linea[0].trim());
            String       nombre           = linea[1].trim();
            double       precio           = Double.parseDouble(linea[2].trim());
            String       descripcion      = linea[3].trim();
            boolean      alcoholica       = Boolean.parseBoolean(linea[4].trim());
            boolean      caliente         = Boolean.parseBoolean(linea[5].trim());
            TipoProducto tipo             = TipoProducto.valueOf(linea[6].trim());
            int          stock            = Integer.parseInt(linea[7].trim());
            int          cantidadDisponible = Integer.parseInt(linea[8].trim());

            ArrayList<String> alergenos = new ArrayList<>();
            if (linea.length > 9 && !linea[9].trim().isEmpty()) {
                for (String a : linea[9].split("\\|")) {
                    alergenos.add(a.trim());
                }
            }

            Producto p;
            if (tipo == TipoProducto.JUEGOMESAPRESTAMO || tipo == TipoProducto.JUEGOMESAVENTA) {
                Caracteristicas car = null;
                for (String[] dc : datosCaracteristicas) {
                    if (dc.length > 0 && Integer.parseInt(dc[0].trim()) == identificador) {
                        Categoria categoria = Categoria.valueOf(dc[5].trim());
                        car = new Caracteristicas(categoria, identificador);
                        car.setNombre(dc[1].trim());
                        car.setAnioPublicacion(Integer.parseInt(dc[2].trim()));
                        car.setEmpresaMatriz(dc[3].trim());
                        car.setNumeroJugadores(Integer.parseInt(dc[4].trim()));
                        car.setEstado(dc[6].trim());
                        car.setRestriccionEdad(Integer.parseInt(dc[7].trim()));
                        car.setDificil(Boolean.parseBoolean(dc[8].trim()));
                        break;
                    }
                }
                // FIX: último parámetro era `caliente` (incorrecto); debe ser `false` (requiereMesa)
                p = new Producto(precio, nombre, descripcion, alcoholica, caliente,
                        alergenos, tipo, identificador, car, stock, false);
            } else {
                p = new Producto(precio, nombre, descripcion, alcoholica, caliente,
                        alergenos, tipo, identificador);
            }

            p.setCantidadDisponible(cantidadDisponible);
            productos.add(p);
        }

        return productos;
    }
    public static Menu cargarMenu(ArrayList<Producto> todosLosProductos) {
        ArrayList<Producto> menuProductos = new ArrayList<>();
        InventarioJuegoPrestamo inventarioJuegos = new InventarioJuegoPrestamo(new ArrayList<>());
        
        for (String[] linea : leerCSV(RUTA_MENU)) {
            if (linea.length < 2) continue;
            
            int idProducto = Integer.parseInt(linea[0].trim());
            boolean esDeInventario = Boolean.parseBoolean(linea[1].trim());
            
            for (Producto p : todosLosProductos) {
                if (p.getIdentificador() == idProducto) {
                    if (esDeInventario) {
                        inventarioJuegos.agregarJuego(p);
                    } else {
                        menuProductos.add(p);
                    }
                }
            }
        }
        
        return new Menu(menuProductos, inventarioJuegos);
    }

    
    private static ArrayList<String[]> leerCSV(String ruta) {
        ArrayList<String[]> filas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(ruta), "UTF-8"))) {
            String  linea;
            boolean primeraLinea = true;
            while ((linea = br.readLine()) != null) {
                if (primeraLinea) { primeraLinea = false; continue; }
                if (!linea.trim().isEmpty()) {
                    filas.add(linea.split(",", -1));
                }
            }
        } catch (IOException e) {
            // Archivo no existe aún, es normal
        }
        return filas;
    }
}