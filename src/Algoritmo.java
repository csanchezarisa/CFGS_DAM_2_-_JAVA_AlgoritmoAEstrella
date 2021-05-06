import java.util.ArrayList;
import java.util.Collections;

public class Algoritmo {

    /** Busca la mejor ruta entre dos puntos. Devuelve un objeto Ciudad con
     * los datos del destino. Null si no lo ha podido encontrar
     * @param names String[] con los nombres importados
     * @param distances int[][] con los costes de las distancias entre puntos
     * @param from String con el nombre de la ciudad origen
     * @param to String con el nombre de la ciudad destino
     * @return Objeto Ciudad con la info del punto final. Null si no ha encontrado la ruta */
    public static Ciudad getBestRoute(String[] names, int[][] distances, String from, String to) {

        Ciudad origin = null;

        for (int i = names.length - 1; i >= 0; i--) {
            if (names[i].equalsIgnoreCase(from)) {
                origin = new Ciudad(names[i], i, 0, new ArrayList<>());
            }
        }

        return searchAlgorithm(origin, new ArrayList<>(), names, distances, to);
    }

    /** Algoritmo de búsqueda A* que recorre las distancias y devuelve la ruta más corta
     * @param N Nodo a analizar
     * @param L Lista con los nodos pendientes de analizar
     * @param names String[] con los nombres de los puntos
     * @param distances int[][] con los costes de las distancias entre puntos
     * @param to Ciudad destino
     * @return Objeto ciudad con el punto final de la ruta. Null si no ha encontrado nada */
    private static Ciudad searchAlgorithm(Ciudad N, ArrayList<Ciudad> L, String[] names, int[][] distances, String to) {

        // Si el nodo a estudiar 'N' es el destino. Se devuelve N
        if (N.getName().equalsIgnoreCase(to)) {
            return N;
        }

        // Si N no es solución
        // Se generan los subnodos y se añaden a la lista 'L'
        L.addAll(generateSubNodes(N, names, distances));

        // Si la lista queda vacía se devuelve un Null
        if (L.size() == 0)
            return null;

        // Se eliminan duplicados y se queda con el que tenga el coste mas bajo
        deleteDuplicates(L);

        // Se ordenan los elementos de la lista de menor a mayor coste
        Collections.sort(L);


        // Se extrae en nodo a analizar y se elimina de la lista
        N = L.get(0);
        L.remove(0);

        return searchAlgorithm(N, L, names, distances, to);
    }

    /** Genera los subnodos de un nodo, teniendo en cuenta la ruta que ya trae el punto
     * y los devuelve en forma de ArrayList
     * @param N Ciudad con el Nodo al que hay que generar los puntos
     * @param names String[] con los nombres de los nodos
     * @param distances int[][] con los costes de las distancias
     * @return ArrayList<Ciudad> con los subnodos generados */
    private static ArrayList<Ciudad> generateSubNodes(Ciudad N, String[] names, int[][] distances) {

        ArrayList<Ciudad> subNodes = new ArrayList<>();

        for (int i = 0; i < distances[N.getPosition()].length; i++) {
            if (distances[N.getPosition()][i] > 0) {

                boolean createCity = true;

                for (Ciudad city : N.getRoute()) {
                    if (names[i].equalsIgnoreCase(city.getName())) {
                        createCity = false;
                        break;
                    }
                }

                if (createCity) {

                    ArrayList<Ciudad> route = new ArrayList<>();
                    route.addAll(N.getRoute());
                    route.add(N);

                    Ciudad subCity = new Ciudad(
                            names[i],
                            i,
                            distances[N.getPosition()][i] + N.getCost(),
                            route
                    );

                    subNodes.add(subCity);
                }
            }
        }

        return subNodes;
    }

    /** Elimina los duplicados de la lista manteniendo los que tengan menos costes
     * @param L ArrayList de ciudades para analizar */
    private static void deleteDuplicates(ArrayList<Ciudad> L) {
        ArrayList<Ciudad> duplicates = new ArrayList<>();

        for (Ciudad city: L) {
            for (Ciudad subCity: L) {

                // Si hay algun nodo que se llame igual se añade a la lista de duplicados
                // el que tenga más coste
                if (city != subCity && city.getName().equalsIgnoreCase(subCity.getName())) {
                    if (city.getCost() > subCity.getCost() && !duplicates.contains(city))
                        duplicates.add(city);
                    else if (!duplicates.contains(subCity))
                        duplicates.add(subCity);
                }
            }
        }

        L.removeAll(duplicates);
    }

}
