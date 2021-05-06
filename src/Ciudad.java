import java.util.ArrayList;

public class Ciudad implements Comparable {

    // Nombre de la ciudad
    private String name;
    // Posición en el array de nombres donde se encuentra
    private int position;
    // Coste que tiene llegar hasta este punto
    private long cost;
    // Ciudades por las que ha tenido que pasar para llegar hasta el punto
    private ArrayList<Ciudad> route;

    public Ciudad(String name, int position, long cost, ArrayList<Ciudad> route) {
        this.name = name;
        this.position = position;
        this.cost = cost;
        this.route = route;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ArrayList<Ciudad> getRoute() {
        return route;
    }

    public void setRoute(ArrayList<Ciudad> route) {
        this.route = route;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public int compareTo(Ciudad city) {
        return (int) (getCost() - city.getCost());
    }

    @Override
    /** Método que permite hacer el sort y ordenar la lista con
     * las ciudades. Permite comparar la ciudad actual con las demás
     * y decidir cuál tiene menor coste */
    public int compareTo(Object o) {
        Ciudad city = (Ciudad) o;
        return (int) (getCost() - city.getCost());
    }
}
