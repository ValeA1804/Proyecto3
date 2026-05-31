package Proyecto;

import java.time.LocalDate;
import java.util.ArrayList;

import Proyecto.Enum.Categoria;
import Proyecto.Enum.Rol;
import Proyecto.Enum.TipoProducto;
import Proyecto.Interfaces.IPrestamo;
import Proyecto.Interfaces.IPedidoCliente;
import Proyecto.Interfaces.IReserva;
import Proyecto.Interfaces.PuntosFidelidad;
import Proyecto.Interfaces.IVenta;
import Proyecto.Interfaces.Comprar;


public class Cliente extends CuentaUsuario implements IPrestamo, IPedidoCliente, IReserva, PuntosFidelidad, IVenta, Comprar {
	private String email;
	private int cedula;
	private String apellido;
	private int puntosFidelidad;
	private int puntosGenerados;
	private ArrayList<Reserva> reservas;
	private ArrayList<Producto> juegosFavoritos;
	
	private ArrayList<Producto> juegosPrestados;  
    private Reserva miReserva;
    private ArrayList<Producto> solicitudesPendientes;  
    private Mesa mesaAsignada;                           
    private boolean pedidoActivo; 
    private ArrayList<Torneo> torneosInscritos;
	
	//Constructor
	public Cliente(String nombre, int id, String usuario, String contraseña, Rol rol, String email, int cedula,
			String apellido, ArrayList<Reserva> reservas, Reserva miReserva,
			Mesa mesaAsignada, boolean pedidoActivo) {
		super(nombre, id, usuario, contraseña, rol);
		this.email = email;
		this.cedula = cedula;
		this.apellido = apellido;
		this.puntosFidelidad = 0;
		this.puntosGenerados = 0;
		this.reservas = reservas;
		this.juegosFavoritos = new ArrayList<>(); 
		this.juegosPrestados = new ArrayList<>();
        this.miReserva = null;
        this.solicitudesPendientes = new ArrayList<>();
        this.mesaAsignada = null;
        this.pedidoActivo = false;
        this.torneosInscritos = new ArrayList<>();
	}

	//Getters and Setters

	public String getEmail() {
		return email;
	}

	public Reserva getMiReserva() {
		return miReserva;
	}

	public void setMiReserva(Reserva miReserva) {
		this.miReserva = miReserva;
	}

	public int getCedula() {
		return cedula;
	}

	public String getApellido() {
		return apellido;
	}

	public int getPuntosFidelidad() {
		return puntosFidelidad;
	}

	public int getPuntosGenerados() {
		return puntosGenerados;
	}
	
	public ArrayList<Producto> getJuegosFavoritos() {
		return juegosFavoritos;
	}

	public ArrayList<Reserva> getReservas() {
		return reservas;
	}
	
	public ArrayList<Torneo> getTorneosInscritos() {
	    return torneosInscritos;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setCedula(int cedula) {
		this.cedula = cedula;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public void setPuntosFidelidad(int puntosFidelidad) {
		this.puntosFidelidad = puntosFidelidad;
	}

	public void setPuntosGenerados(int puntosGenerados) {
		this.puntosGenerados = puntosGenerados;
	}

	public void setReservas(ArrayList<Reserva> reservas) {
		this.reservas = reservas;
	}
		
	public ArrayList<Producto> getSolicitudesPendientes() {
		return solicitudesPendientes;
	}

	public Mesa getMesaAsignada() {
		return mesaAsignada;
	}

	public boolean isPedidoActivo() {
		return pedidoActivo;
	}

	public void setSolicitudesPendientes(ArrayList<Producto> solicitudesPendientes) {
		this.solicitudesPendientes = solicitudesPendientes;
	}

	public void setMesaAsignada(Mesa mesaAsignada) {
		this.mesaAsignada = mesaAsignada;
	}

	public void setPedidoActivo(boolean pedidoActivo) {
		this.pedidoActivo = pedidoActivo;
	}
	
	private int calcularPersonasEnMesas() {
	    int total = 0;
	    for (Reserva r : reservas) {
	        total += r.getNumeroTotalPersonas();
	    }
	    return total;
	}

	public void agregarJuegoFavorito(Producto producto) {
		juegosFavoritos.add(producto);
	}

	public void eliminarJuegoFavorito(Producto producto) {
		juegosFavoritos.remove(producto);
	}
	
	public void confirmarSolicitud(Producto producto) {
        solicitudesPendientes.remove(producto);
    }
    
    public boolean tieneSolicitudPendiente(Producto producto) {
        return solicitudesPendientes.contains(producto);
    }

	
    
    @Override
    public boolean verificarDisponibilidad(Producto juego) {
        return juego.getCantidadDisponible() > 0;
    }
    @Override
	 public boolean verificarRestricciones(Producto juego, Cafeteria cafeteria) {
    	
        if (miReserva == null) {
            System.out.println("No hay una reserva activa.");
            return false;
        }
        
        
        Caracteristicas carac = juego.getCaracteristicas();
        
     
        if (miReserva.getNumeroTotalPersonas() > carac.getNumeroJugadores()) {
            System.out.println("El juego soporta máximo " + carac.getNumeroJugadores() + 
                               " jugadores, pero la mesa tiene " + miReserva.getNumeroTotalPersonas());
            return false;
        }
        
        
        if (miReserva.getCantidadMenoresEdad() > 0 && carac.getRestriccionEdad() >= 18) {
            System.out.println("El juego es para mayores de 18 años y hay menores de edad en la mesa.");
            return false;
        }
        
         
        if (miReserva.getCantidadMenoresCincoAnios() > 0 && carac.getRestriccionEdad() >= 5) {
            System.out.println("El juego es para mayores de 5 años y hay niños pequeños.");
        }
        
        return true;
	 }
    

	@Override
	public boolean prestarJuego(Producto juego, CuentaUsuario usuario) {
		
        if (!verificarDisponibilidad(juego)) {
            System.out.println("El juego no está disponible.");
            return false;
        }
        
        
        if (!verificarRestricciones(juego, null)) { //No necesita la cafetería como parametro
            System.out.println("El juego no cumple con las restricciones de la mesa.");
            return false;
        }
        
        
        if (juego.getTipoProducto() != TipoProducto.JUEGOMESAPRESTAMO) {
            System.out.println("Este producto no es un juego de préstamo.");
            return false;
        }
        
        if (miReserva == null) {
            System.out.println("Debes tener una reserva activa para pedir juegos prestados.");
            return false;
        }
        
        
        if (juegosPrestados.size() >= 2) {
            System.out.println("Ya tienes 2 juegos prestados. Devuelve uno para pedir otro.");
            return false;
        }
        
        
        if (miReserva.tieneBebidaCaliente() && 
            juego.getCaracteristicas().getCategoria() == Categoria.ACCION) {
            System.out.println("No se puede prestar un juego de ACCIÓN en una mesa con bebidas calientes.");
            return false;
        }
        
        
        juego.setCantidadDisponible(juego.getCantidadDisponible() - 1);
        juegosPrestados.add(juego);
        
        System.out.println("Préstamo exitoso.");
        System.out.println("   Juegos prestados actualmente: " + juegosPrestados.size() + "/2");
        return true;
    }
		

	@Override
	public void devolverJuego(Producto juego) {
		if (!juegosPrestados.contains(juego)) {
            System.out.println("No tienes este juego prestado.");
            return;
        }
        
        juego.setCantidadDisponible(juego.getCantidadDisponible() + 1);
        juegosPrestados.remove(juego);
        
        
        HistorialPrestamo historial = new HistorialPrestamo();
        historial.registrarDevolucion(java.time.LocalDate.now().toString(), 
                                       juego.getCaracteristicas().getEstado());
        
        System.out.println("Juego devuelto");
		
	}
	//IMPLEMENTAR RESERVA 
	@Override
	public Reserva crearReserva(Cafeteria cafeteria, int numeroTotalPersonas, int cantidadMenoresEdad, int cantidadMenoresCincoAnios, int id, String horaInicio, String horaFin, String fecha, Mesa mesa) {
	    
	    if (!mesa.isDisponible()) {
	        System.out.println("Mesa no disponible");
	        return null;
	    }
	    
	    if (mesa.getCapacidad() < numeroTotalPersonas) {
	        System.out.println("Mesa no tiene capacidad para la Reserva");
	        return null;
	    }
	    
	    int personasActuales = calcularPersonasEnMesas();
	    if (personasActuales + numeroTotalPersonas > cafeteria.getCapacidadMaxima()) {
	        System.out.println("Cafetería llena");
	        return null;
	    }
	    
	    Reserva nuevaReserva = new Reserva(numeroTotalPersonas, cantidadMenoresEdad, cantidadMenoresCincoAnios, id, horaInicio, fecha, mesa, false);
	    
	    this.reservas.add(nuevaReserva);
	    this.miReserva = nuevaReserva;
	    mesa.setDisponible(false);
	    
	    System.out.println("Reserva creada para " + numeroTotalPersonas + " personas");
	    return nuevaReserva;
	}


	@Override
	public void cancelarReserva(Reserva reserva) {
	    if (reserva == null) {
	        System.out.println("No hay reserva para cancelar.");
	        return;
	    }
	    
	    if (!reserva.isActiva()) {
	        System.out.println("Esta reserva ya fue cancelada o no está activa.");
	        return;
	    }
	    
	    Mesa mesa = reserva.getMesa();
	    if (mesa != null) {
	        mesa.setDisponible(true);
	    }
	    
	    reserva.setActiva(false);
	    
	    if (this.miReserva == reserva) {
	        this.miReserva = null;
	    }
	    
	    this.reservas.remove(reserva);
	    System.out.println("Reserva cancelada.");
	}

	@Override
	public boolean verificarDisponibilidad(Mesa mesa, String fecha, String hora) {
	    if (mesa == null) {
	        System.out.println("Mesa no válida.");
	        return false;
	    }
	    
	    if (!mesa.isDisponible()) {
	        System.out.println("Mesa #" + mesa.getNumeroMesa() + " no está disponible.");
	        return false;
	    }
	    
	    if (this.miReserva != null && this.miReserva.isActiva()) {
	        System.out.println("Ya tienes una reserva activa. Solo puedes tener una reserva a la vez.");
	        return false;
	    }
	    
	    System.out.println("Mesa #" + mesa.getNumeroMesa() + " disponible para " + fecha + " a las " + hora);
	    return true;
	}

	
	//IMPLEMENTACIÓN SISTEMA PUNTOS DE FIDELIDAD
	@Override
	public void acumularPuntos(int puntos) {
		this.puntosFidelidad += puntos;
        this.puntosGenerados += puntos;
        System.out.println("Has acumulado " + puntos + " puntos. Total: " + this.puntosFidelidad);
	}

	@Override
	public int redimirPuntos(int puntos) {
		if (puntos <= this.puntosFidelidad) {
            this.puntosFidelidad -= puntos;
            System.out.println("Redimiste " + puntos + " puntos.");
            return puntos;
        }
        System.out.println("No hay suficientes puntos.");
        return 0;
	}

	@Override
	public int consultarPuntos() {
		 return this.puntosFidelidad;
    }
	//IMPLEMENTACIÓN HACER PEDIDO

	@Override
	public void solicitarProducto(Producto producto) {
		if (!pedidoActivo) {
            System.out.println("No hay pedido activo. Un mesero debe abrir tu pedido.");
            return;
        }
        if (mesaAsignada == null) {
            System.out.println("No tienes mesa asignada.");
            return;
        }
        
        // Verificar restricciones (edad, alcohol, etc.)
        if (!verificarRestriccionesProducto(producto)) {
            System.out.println("No puedes solicitar este producto.");
            return;
        }
        
        solicitudesPendientes.add(producto);
        System.out.println("Solicitud enviada: " + producto.getNombreProducto());
        System.out.println("Esperando confirmación del mesero...");
		
	}

	@Override
	public void cancelarSolicitud(Producto producto) {
		if (solicitudesPendientes.remove(producto)) {
            System.out.println("Solicitud cancelada: " + producto.getNombreProducto());
        } else {
            System.out.println("No tienes una solicitud pendiente de este producto.");
        }
		
	}

	@Override
	public double calcularTotal() {
		// Esto lo obtiene del pedido confirmado, no de solicitudes
        // El mesero le puede mostrar al cliente
        return 0; // Se implementa cuando el mesero consulta el pedido real
	}
	
	 private boolean verificarRestriccionesProducto(Producto producto) {
		 Reserva reservaActiva = getMiReserva();
		    
		    if (reservaActiva == null) {
		        System.out.println("No tienes una reserva.");
		        return false;
		    }
		    
		    Mesa mesa = reservaActiva.getMesa();
		    if (mesa == null) {
		        System.out.println("No hay mesa asignada.");
		        return false;
		    }
		    
		    // BEBIDAS ALCÓHOLICAS con menores de edad 
		    if (producto.isAlcoholica()) {
		        if (reservaActiva.getCantidadMenoresEdad() > 0) {
		            System.out.println("No se pueden servir bebidas alcohólicas en una mesa con menores de edad.");
		            return false;
		        }
		    }
		    
		    //  BEBIDAS CALIENTES con juegos de ACCIÓN
		 
		    if (producto.isCaliente() && producto.getTipoProducto() == TipoProducto.BEBIDA) {
		        if (mesa.tieneJuegoAccion()) {
		            System.out.println("No se pueden servir bebidas calientes en una mesa con un juego de ACCIÓN.");
		            System.out.println("Riesgo de derrame y daño al juego");
		            return false;
		        }
		    }
		    
		    // ALERGENOS en pastelería
		 
		    if (producto.getTipoProducto() == TipoProducto.PASTELERIA) {
		        if (producto.getAlergenos() != null && !producto.getAlergenos().isEmpty()) {
		            System.out.println("️ALERTA DE ALERGENOS: Este producto contiene: " + String.join(", ", producto.getAlergenos()));
	
		        }
		    }
		    
		    // Menores de 5 años (si aplica a algún producto)
		    
		    if (reservaActiva.getCantidadMenoresCincoAnios() > 0) {
		        if (producto.getCaracteristicas() != null && 
		            producto.getCaracteristicas().getRestriccionEdad() >= 5) {
		            System.out.println("Este producto tiene restricción de edad para mayores de " + 
		                               producto.getCaracteristicas().getRestriccionEdad() + " años.");
		            System.out.println("Hay niños menores de 5 años en la mesa.");
		            return false;
		        }
		    }
		    
		    return true;
	    }

	 //IMPLEMENTAR COMPRA DE UN PRODCUTO

	 @Override
	 public Compra realizarCompra(ArrayList<Producto> productos, CuentaUsuario usuario, double propina, String codigoDescuento) {
	     if (productos == null || productos.isEmpty()) {
	         System.out.println("No hay productos para comprar.");
	         return null;
	     }
	     
	     // Calcular descuento según el usuario
	     double porcentajeDescuento = 0;
	     if (usuario instanceof Empleado) { //Si es un empleado 
	         porcentajeDescuento = Compra.getDESCUENTOEMPLEADO();
	     } else if (codigoDescuento != null && !codigoDescuento.isEmpty()) {
	         porcentajeDescuento = Compra.getDESCUENTOCLIENTE();
	     }
	     
	     // Crear la compra
	     Compra compra = new Compra("PENDIENTE", porcentajeDescuento, codigoDescuento, 0, 
	                                 Compra.getIVA(), propina, LocalDate.now().toString());
	     compra.setProductos(productos);
	     
	     // Finalizar la compra (esto asigna puntos automáticamente)
	     compra.finalizarCompra(this, propina);
	     
	     return compra;
	 }

	 @Override
	 public double calcularSubtotal(ArrayList<Producto> productos) {
	     // Crear compra temporal solo para calcular
	     Compra temp = new Compra("TEMP", 0, null, 0, Compra.getIVA(), 0, LocalDate.now().toString());
	     temp.setProductos(productos);
	     return temp.getSubtotal();
	 }

	 @Override
	 public double calcularDescuento(double subtotal, CuentaUsuario usuario, String codigoDescuento) {
	     if (usuario instanceof Empleado) {
	         return subtotal * Compra.getDESCUENTOEMPLEADO();
	     } else if (codigoDescuento != null && !codigoDescuento.isEmpty()) {
	         return subtotal * Compra.getDESCUENTOCLIENTE();
	     }
	     return 0;
	 }

	 @Override
	 public double calcularImpuestos(double subtotal, ArrayList<Producto> productos) {
	     Compra temp = new Compra("TEMP", 0, null, 0, Compra.getIVA(), 0, LocalDate.now().toString());
	     temp.setProductos(productos);
	     return temp.getImpuestos();
	 }

	 @Override
	 public double calcularTotal(double subtotal, double descuento, double impuestos, double propina) {
	     return subtotal - descuento + impuestos + propina;
	 }

	 // Método para comprar juego directamente
	 @Override
	 public void comprarJuego(Producto juego, InventarioJuegoVenta inventario) {
	     if (juego == null || !inventario.getJuegosVenta().contains(juego)) {
	         System.out.println("Juego no disponible para venta.");
	         return;
	     }
	     
	     ArrayList<Producto> productos = new ArrayList<>();
	     productos.add(juego);
	     
	     System.out.print("¿Tiene código de descuento? (s/n): ");
	     String respuesta = Main.scanner.nextLine();
	     String codigo = respuesta.equalsIgnoreCase("s") ? Main.scanner.nextLine() : null;
	     
	     Compra compra = realizarCompra(productos, this, 0, codigo);
	     if (compra != null) {
	         inventario.getJuegosVenta().remove(juego);
	         System.out.println("Venta exitosa");
	     }
	 }

	 public void inscribirEnTorneo(Torneo torneo) {
		    if (!torneosInscritos.contains(torneo)) {
		        torneosInscritos.add(torneo);
		    }
		}
	 
	 public void cancelarInscripcionTorneo(Torneo torneo) {
		    torneosInscritos.remove(torneo);
		}
	 
	 public boolean estaInscritoEnTorneo(Torneo torneo) {
		    return torneosInscritos.contains(torneo);
		}
    
}
