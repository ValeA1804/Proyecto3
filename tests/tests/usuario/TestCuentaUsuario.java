package tests.usuario;

import Proyecto.*;
import Proyecto.Enum.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestCuentaUsuario {

    private Cliente cliente;
    private Cocinero cocinero;

    @BeforeEach
    void setUp() {
        cliente = new Cliente("Juan", 1, "juan01", "pass123", Rol.CLIENTE,
                "juan@mail.com", 123456, "Pérez", new ArrayList<>(), null, null, false);

        cocinero = new Cocinero("Ana", 2, "ana01", "pass456", Rol.EMPLEADO,
                new ArrayList<>(), TipoEmpleado.COCINERO, false, new ArrayList<>());
    }

    @Test
    void getNombre_retornaNombreCorrecto() {
        assertEquals("Juan", cliente.getNombre());
    }

    @Test
    void getId_retornaIdCorrecto() {
        assertEquals(1, cliente.getId());
    }

    @Test
    void getUsuario_retornaUsuarioCorrecto() {
        assertEquals("juan01", cliente.getUsuario());
    }

    @Test
    void getContraseña_retornaContraseñaCorrecta() {
        assertEquals("pass123", cliente.getContraseña());
    }

    @Test
    void getRol_cliente_retornaRolCliente() {
        assertEquals(Rol.CLIENTE, cliente.getRol());
    }

    @Test
    void getRol_empleado_retornaRolEmpleado() {
        assertEquals(Rol.EMPLEADO, cocinero.getRol());
    }

    @Test
    void setNombre_actualizaNombre() {
        cliente.setNombre("Pedro");
        assertEquals("Pedro", cliente.getNombre());
    }

    @Test
    void setUsuario_actualizaUsuario() {
        cliente.setUsuario("pedro99");
        assertEquals("pedro99", cliente.getUsuario());
    }

    @Test
    void setContraseña_actualizaContraseña() {
        cliente.setContraseña("nueva123");
        assertEquals("nueva123", cliente.getContraseña());
    }

    @Test
    void setRol_actualizaRol() {
        cliente.setRol(Rol.ADMINISTRADOR);
        assertEquals(Rol.ADMINISTRADOR, cliente.getRol());
    }
}