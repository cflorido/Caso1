class Productor extends Thread {
    private Buzon buzonRevision;
    private Buzon buzonReproceso;
    private int id;

    public Productor(int id, Buzon buzonRevision, Buzon buzonReproceso) {
        this.id = id;
        this.buzonRevision = buzonRevision;
        this.buzonReproceso = buzonReproceso;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String producto;
                // La tarea de reprocesar tiene prioridad sobre la de generar
                if (!buzonReproceso.estaVacio()) {
                    producto = buzonReproceso.retirar();
                    if (producto.equals("FIN")) {
                        System.out.println("Productor " + id + " termina.");
                        break;
                    }
                    System.out.println("Productor " + id + " reprocesa " + producto);
                } else {
                    producto = "Producto-" + id + " - time: " + System.currentTimeMillis();
                    System.out.println("Productor " + id + " genera " + producto);
                }
                // Despues de reprocesar o generar, se almacenan los productos en ell buzon de
                // revision
                buzonRevision.depositar(producto);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}