package Proyecto;

import java.time.LocalDate;
import java.util.ArrayList;
import Proyecto.Enum.TipoProducto;
import Proyecto.Interfaces.ICompra;

public class Compra implements ICompra{
	// Constantes
    private static double IVA = 0.19;           // Para todo
    private static double IMPUESTOCONSUMO = 0.08; // Solo para PASTELERIA Y BEBIDA
    private static double PROPINASUGERIDA = 0.10;
    private static double DESCUENTOEMPLEADO = 0.20;
    private static double DESCUENTOCLIENTE = 0.10;
    
    // Atributos
    private String fecha;
    private ArrayList<Producto> productos;  // Lista de productos comprados Solo si un empleado lo compra directamente
    //Si un cliente hace la compra, los productos ya estan por medio de la clase Pedido
    private double subtotal;                // Suma de precio de los productos 
    private double descuento;
    private double impuestos;
    private double propina;
    private double total;
    private int puntosFidelidad;
    private String codigoDescuento;
    private String estado; 
    private Pedido pedido;
	
    
    //Constructor
 // Constructor
 	public Compra(String estado, double descuento, String codigoDescuento, int puntosFidelidad,
 			double IVA, double propina, String fecha) {
 		this.estado = estado;
 		this.descuento = descuento;
 		this.codigoDescuento = codigoDescuento;
 		this.puntosFidelidad = puntosFidelidad;
 		this.fecha = fecha;
 		this.propina = propina;

 		this.productos = new ArrayList<>();
 		this.pedido = null;

 		Compra.IVA = IVA;

 		//apenas se crea la compra calcula el valor del precio final, al comienzo es cero
 		this.subtotal = calcularSubtotal();
 		this.impuestos = calcularImpuestos();
 		this.total = calcularTotal();
 	}
 
 	
 	
 	
    
	//Setters and Getters
	public Pedido getPedido() {
		return pedido;
	}
    public static double getIVA() {
  		return IVA;
  	}
  	public static double getIMPUESTOCONSUMO() {
  		return IMPUESTOCONSUMO;
  	}
  	public static double getPROPINASUGERIDA() {
  		return PROPINASUGERIDA;
  	}
  	public static double getDESCUENTOEMPLEADO() {
  		return DESCUENTOEMPLEADO;
  	}
  	public static double getDESCUENTOCLIENTE() {
  		return DESCUENTOCLIENTE;
  	}
  	public String getFecha() {
  		return fecha;
  	}
  	public ArrayList<Producto> getProductos() {
  		return productos;
  	}
  	public double getSubtotal() {
  		return subtotal;
  	}
  	public double getDescuento() {
  		return descuento;
  	}
  	public double getImpuestos() {
  		return impuestos;
  	}
  	public double getPropina() {
  		return propina;
  	}
  	public double getTotal() {
  		return total;
  	}
  	public int getPuntosFidelidad() {
  		return puntosFidelidad;
  	}
  	public String getCodigoDescuento() {
  		return codigoDescuento;
  	}
  	public String getEstado() {
  		return estado;
  	}
  	public static void setIVA(double iVA) {
  		IVA = iVA;
  	}
  	public static void setIMPUESTOCONSUMO(double iMPUESTOCONSUMO) {
  		IMPUESTOCONSUMO = iMPUESTOCONSUMO;
  	}
  	public static void setPROPINASUGERIDA(double pROPINASUGERIDA) {
  		PROPINASUGERIDA = pROPINASUGERIDA;
  	}
  	public static void setDESCUENTOEMPLEADO(double dESCUENTOEMPLEADO) {
  		DESCUENTOEMPLEADO = dESCUENTOEMPLEADO;
  	}
  	public static void setDESCUENTOCLIENTE(double dESCUENTOCLIENTE) {
  		DESCUENTOCLIENTE = dESCUENTOCLIENTE;
  	}
  	public void setFecha(String fecha) {
  		this.fecha = fecha;
  	}
  	public void setProductos(ArrayList<Producto> productos) {
  		this.productos = productos;
  		//si se modifican los productos se debe recalcular el total de la compra
  		recalcularCompra();
  	}
  	public void setSubtotal(double subtotal) {
  		this.subtotal = subtotal;
  	}
  	public void setDescuento(double descuento) {
  		this.descuento = descuento;
  		//si se modiica el descuento se tiene que recalcular todo
  		recalcularCompra();
  	}
  	public void setImpuestos(double impuestos) {
  		this.impuestos = impuestos;
  	}
  	public void setPropina(double propina) {
  		this.propina = propina;
  		//si se modifica la propina se debe recalcular todo
  		recalcularCompra();
  	}
  	public void setTotal(double total) {
  		this.total = total;
  	}
  	public void setPuntosFidelidad(int puntosFidelidad) {
  		this.puntosFidelidad = puntosFidelidad;
  	}
  	public void setCodigoDescuento(String codigoDescuento) {
  		this.codigoDescuento = codigoDescuento;
  	}
  	public void setEstado(String estado) {
  		this.estado = estado;
  	}
  	public void setPedido(Pedido pedido) {
  		this.pedido = pedido;
  		//si se asocia un pedido a la compra la compra se debe calcular con los 
  		//productos de ese pedido
  		recalcularCompra();
  	}
  	
  	//Métodos auxiliares
  	
  	//esto metodo decide de donde salen los productos
  	@Override
	public ArrayList<Producto> obtenerProductosCompra() {
		if (pedido != null) //verifica si hay pedido, sino usa directamente Prodcutos
		{
			return pedido.getProductos();
		}
		return productos;
	}
  	
  	
  	
  	//obtiene la lista de productos, la recorre y suma los precios
  	private double calcularSubtotal() {
		double suma = 0;
		ArrayList<Producto> productosCompra = obtenerProductosCompra();

		for (Producto producto : productosCompra) {
			suma += producto.getPrecio();
		}

		return suma;
	}
  	

  	//calcula los impuestos totales y si hay ebbidas o pasteleria), mas adelante se suman al total
  	//
  	@Override
	public double calcularImpuestos() {
		double ivaCalculado = subtotal * IVA; //el iva se aplica sobre todo el subttoal
		double impuestoConsumoCalculado = 0;

		ArrayList<Producto> productosCompra = obtenerProductosCompra();

		//re verifica si hay productos de pasteleria o bebidas para sumar el impuesto al consumo
		for (Producto producto : productosCompra) {
			if (producto.getTipoProducto() == TipoProducto.BEBIDA ||
				producto.getTipoProducto() == TipoProducto.PASTELERIA) {
				impuestoConsumoCalculado += producto.getPrecio() * IMPUESTOCONSUMO;
			}
		}

		return ivaCalculado + impuestoConsumoCalculado;
	}

  	
  	//calcula descuento
  	private double calcularValorDescuento() {
		return subtotal * descuento;
	}
  	
  	//propina
  	private double calcularValorPropina() {
		return subtotal * propina;
	}
  	
  	//total de la compra
  	@Override
	public double calcularTotal() {
		double valorDescuento = calcularValorDescuento();
		double valorPropina = calcularValorPropina();

		return subtotal - valorDescuento + impuestos + valorPropina;
	}
  	
  	//se recalcula la compra por si se agregan cosas (cada vez que cambie algo importante
  	@Override
	public void recalcularCompra() {
		this.subtotal = calcularSubtotal();
		this.impuestos = calcularImpuestos();
		this.total = calcularTotal();
	}
  	
  	@Override
  	public void finalizarCompra(CuentaUsuario usuario, double propinaElegida) {
  	    this.propina = propinaElegida;
  	    this.fecha = LocalDate.now().toString();
  	    this.estado = "COMPLETADA";
  	    
  	    // Calcular puntos de fidelidad (1% del total)
  	    this.puntosFidelidad = (int)(this.total * 0.01);
  	    
  	    // Acumular puntos si el usuario es cliente
  	    if (usuario instanceof Cliente) {
  	        Cliente cliente = (Cliente) usuario;
  	        cliente.acumularPuntos(this.puntosFidelidad);
  	    }
  	    
  	    recalcularCompra();
  	    System.out.println("Compra finalizada. Total: $" + this.total);
  	    System.out.println("Puntos acumulados: " + this.puntosFidelidad);
  	}

  	@Override
  	public void aplicarDescuentoPorPuntos(Cliente cliente, int puntosARedimir) {
  	    int puntosRedimidos = cliente.redimirPuntos(puntosARedimir);
  	    double descuentoPuntos = puntosRedimidos; // 1 punto = $1
  	    double nuevoDescuento = this.descuento + (descuentoPuntos / this.subtotal);
  	    if (nuevoDescuento > 1.0) nuevoDescuento = 1.0;
  	    this.setDescuento(nuevoDescuento);
  	}
  	
  	
  	
  	
  		
  	
}
