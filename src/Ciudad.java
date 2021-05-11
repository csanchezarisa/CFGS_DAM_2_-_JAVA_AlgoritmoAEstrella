import java.util.ArrayList;

public class Ciudad implements Comparable {

    // Nombre de la ciudad
    private final String name;
    // Posición en el array de nombres donde se encuentra
    private final int position;
    // Coste que tiene llegar hasta este punto
    private final long cost;
    // Ciudades por las que ha tenido que pasar para llegar hasta el punto
    private final ArrayList<Ciudad> route;

    public Ciudad(String name, int position, long cost, ArrayList<Ciudad> route) {
        this.name = name;
        this.position = position;
        this.cost = cost;
        this.route = route;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public ArrayList<Ciudad> getRoute() {
        return route;
    }

    public long getCost() {
        return cost;
    }

    /** Método que permite hacer el sort y ordenar la lista con
     * las ciudades. Permite comparar la ciudad actual con las demás
     * y decidir cuál tiene menor coste */
    @Override
    public int compareTo(Object o) {
        Ciudad city = (Ciudad) o;
        return (int) (getCost() - city.getCost());
    }
}
