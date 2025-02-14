import java.util.Random;

class EquipoCalidad extends Thread {
    private BuzonRevision buzonRevision;
    private BuzonReproceso buzonReproceso;
    private Deposito deposito;
    private int id;
    private int maxFallos;
    private static int productosAprobados;
    private int totalProductos;
    private int fallosActuales = 0;
    private static final Object lock = new Object();
    private int numProductores;
    private boolean metaAlcanzada = false;

    public EquipoCalidad(int id, BuzonRevision buzonRevision, BuzonReproceso buzonReproceso, Deposito deposito,
            int totalProductos, int numProductores) {
        this.id = id;
        this.buzonRevision = buzonRevision;
        this.buzonReproceso = buzonReproceso;
        this.deposito = deposito;
        this.totalProductos = totalProductos;
        this.numProductores = numProductores;
        this.maxFallos = (int) Math.floor(totalProductos * 0.1);
    }

    @Override
    public void run() {
        Random rand = new Random();
        try {
            while (true) {
                if (buzonRevision.estaVacio() && deposito.cantidadProductos() >= totalProductos) {
                    System.out.println("Equipo de Calidad " + id + " finaliza.");
                    break;
                }
                Producto producto = buzonRevision.retirar();
                int resultado = rand.nextInt(100) + 1;

                synchronized (lock) {
                    if (fallosActuales < maxFallos && resultado % 7 == 0) {
                        System.out.println("Equipo de Calidad " + id + " rechaza " + producto.getNombre());
                        fallosActuales++;
                        buzonReproceso.depositar(producto);
                    } else {
                        System.out.println("Equipo de Calidad " + id + " aprueba " + producto.getNombre());
                        deposito.depositar(producto);
                        productosAprobados++;
                    }

                    if (deposito.cantidadProductos() >= totalProductos && !metaAlcanzada) {
                        metaAlcanzada = true;
                        System.out.println("Meta alcanzada equipo de calidad " + id + ".Enviando señal de FIN ");
                        buzonReproceso.depositar(new Producto("FIN"));

                    }

                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
