/*package teste;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;


import com.implantodontia.dominio.consulta.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class MaterialTest {

    private Material material;
    private Map<String, Integer> inventario;

    @BeforeEach
    void setup() {
        material = new Material();
        inventario = new HashMap<>();
    }

    @Test
    public void CadastrandoMaterialNovo() {
        material.adicionar(inventario, "Gaze", 1);

        assertEquals(1, inventario.get("Gaze"));
    }

    @Test
    public void CadastrarMaterialPresente() {
        material.adicionar(inventario, "Gaze", 1);
        material.adicionar(inventario, "Gaze", 1);

        assertEquals(2, inventario.get("Gaze"));
    }

    @Test
    public void ConsumirMaterial() {
        Material material = new Material();
        Map<String, Integer> inventario = new HashMap<>();
        System.out.println(inventario);
        material.adicionar(inventario, "Gaze", 5);

        material.remover(inventario, "Gaze", 1);
        assertEquals(4, inventario.get("Gaze"));
    }

    @Test
    public void ConsumirMaterialEsvaindo() {
        material = new Material();
        inventario = new HashMap<>();
        System.out.println(inventario);
        material.adicionar(inventario, "Gaze", 1);
        material.adicionar(inventario, "Gaze", 1);

        material.remover(inventario, "Gaze", 2);
        assertEquals(0, inventario.get("Gaze"));
    }
}*/
