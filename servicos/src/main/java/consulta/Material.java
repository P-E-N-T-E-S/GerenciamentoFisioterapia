package consulta;


import java.util.HashMap;
import java.util.Map;

public class Material {
    int quantidade;
    String Nome;

    public void adicionar(Map<String, Integer> inventario, String item, int quantidade) {
        if (inventario.containsKey(item)) {
            inventario.computeIfPresent("Gaze", (key, value) -> value + 1);
        } else {
            inventario.put(item, quantidade);
        }
    }

    public void remover(Map<String, Integer> inventario, String item, int quantidade) {
        if (inventario.containsKey(item)) {
            inventario.computeIfPresent("Gaze", (key, value) -> value - quantidade);
        } else {
            System.out.println("Item não presente no sistema");
        }
        if (inventario.get(item) < 3) {
            System.out.println("Item com baixo estoque");
        }
    }
}
