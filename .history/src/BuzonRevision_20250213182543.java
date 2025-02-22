import java.util.LinkedList;
import java.util.Queue;

class BuzonRevision {
    private Queue<Producto> productos;
    private int capacidad;

    public BuzonRevision(int capacidad) {
        this.productos = new LinkedList<>();
        this.capacidad = capacidad;
    }

    public synchronized void depositar(Producto producto) throws InterruptedException {
        System.out.println("se depositó en buzon de revision " + producto.getNombre());
        while (productos.size() >= capacidad) {
            try{

            
                wait(); }
                catch(InterruptedException e){
                    Thread.currentThread().interrupt();
                }
        }
        productos.add(producto);
        notifyAll(); // Notifica a los hilos en espera
    }

    public synchronized Producto retirar() throws InterruptedException {
        while (productos.isEmpty()) {
            wait(); // Espera pasiva si está vacío
        }
        Producto producto = productos.poll();
        notifyAll(); // Notifica a los hilos en espera
        return producto;
    }

    public synchronized boolean estaVacio() {
        return productos.isEmpty();
    }
}
