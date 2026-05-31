package Proyecto.Interfaces;
import Proyecto.SugerenciaPlatillo;
import Proyecto.Enum.TipoPlatillo;

import Proyecto.Menu;
import java.util.ArrayList;

public interface ISugerenciaPlatillo {
	void enviarSugerencia(SugerenciaPlatillo sugerencia);
    void aprobarSugerencia(SugerenciaPlatillo sugerencia, Menu menu);
    ArrayList<SugerenciaPlatillo> listarSugerenciasPendientes();
	void rechazarSugerencia(SugerenciaPlatillo sugerencia);
	void sugerirPlatillo(String nombrePlatillo, String descripcion, double precioSugerido, TipoPlatillo tipo, 
            boolean alcoholica, boolean caliente, ArrayList<String> alergenos);
}
