import java.util.Random;

class EquipoCalidad extends Thread {
    private Buzon buzonRevision;
    private Buzon buzonReproceso;
    private Buzon deposito;
    private int id;
    private int maxFallos;
    private static int productosAprobados;
    private int totalProductos;
    private int fallosActuales = 0;
    private static final Object lock = new Object();

    public EquipoCalidad(int id, Buzon buzonRevision, Buzon buzonReproceso, Buzon deposito, int totalProductos) {
        this.id = id;
        this.buzonRevision = buzonRevision;
        this.buzonReproceso = buzonReproceso;
        this.deposito = deposito;
        this.totalProductos = totalProductos;
        this.maxFallos = (int) Math.floor(totalProductos * 0.1);
    }

    @Override
    public void run() {
        Random rand = new Random();
        try {
            while (true) {
                String producto = buzonRevision.retirar();
                int resultado = rand.nextInt(100) + 1;

                synchronized (lock) {
                    if (fallosActuales < maxFallos && resultado % 7 == 0) {
                        System.out.println("Equipo de Calidad " + id + " rechaza " + producto);
                        fallosActuales++;
                        // El producto fue rechazado, se envia al buzon de reproceso
                        buzonReproceso.depositar(producto);
                    }

                    else {

                        System.out.println("Equipo de Calidad " + id + " aprueba " + producto);
                        // El producto fue aprobado se envia al deposito
                        deposito.depositar(producto);
                        productosAprobados++;

                        if (productosAprobados >= totalProductos) {
                            System.out.println("Meta alcanzada. Enviando senal de FIN.");
                            buzonReproceso.depositar("FIN");
                            break;
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
