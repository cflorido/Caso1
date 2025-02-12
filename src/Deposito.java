import java.util.LinkedList;
import java.util.Queue;

class Deposito {
    private Queue<Producto> productos;

    public Deposito() {
        this.productos = new LinkedList<>();
    }

    public synchronized void depositar(Producto producto) throws InterruptedException {
        productos.add(producto);
        notifyAll(); // Notifica a los hilos en espera
    }

    public synchronized Producto retirar() throws InterruptedException {
        while (productos.isEmpty()) {
            try{
            wait(); // Espera pasiva si está vacío
        } catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
        }
        return productos.poll();
    }

    public synchronized boolean estaVacio() {
        return productos.isEmpty();
    }
}
