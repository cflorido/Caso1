class Productor extends Thread {
    private BuzonRevision buzonRevision;
    private BuzonReproceso buzonReproceso;
    private int id;

    public Productor(int id, BuzonRevision buzonRevision, BuzonReproceso buzonReproceso) {
        this.id = id;
        this.buzonRevision = buzonRevision;
        this.buzonReproceso = buzonReproceso;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Producto producto;
                
                if (!buzonReproceso.estaVacio()) {
                    producto = buzonReproceso.retirar();
                    
                    if (producto.getNombre().equals("FIN")) {
                        System.out.println("Productor " + id + " recibe señal de FIN y termina.");
                        break;
                    }

                    System.out.println("Productor " + id + " reprocesa " + producto.getNombre());
                } else {
                    producto = new Producto("Producto-");
                    producto.actualizarNombre();
                    System.out.println("Productor " + id + " genera " + producto.getNombre());
                }

                buzonRevision.depositar(producto);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
