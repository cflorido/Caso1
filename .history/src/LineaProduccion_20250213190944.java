import java.util.Scanner;

public class LineaProduccion {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el número de operarios en producción y calidad: ");
        int numOperarios = scanner.nextInt();
        System.out.print("Ingrese el número de productos a generar: ");
        int numProductos = scanner.nextInt();
        System.out.print("Ingrese el límite del buzón de revisión: ");
        int capacidadBuzonRevision = scanner.nextInt();

        BuzonRevision buzonRevision = new BuzonRevision(capacidadBuzonRevision);
        BuzonReproceso buzonReproceso = new BuzonReproceso();
        Deposito deposito = new Deposito();

        Productor[] productores = new Productor[numOperarios];
        EquipoCalidad[] equipoCalidad = new EquipoCalidad[numOperarios];

        for (int i = 0; i < numOperarios; i++) {
            productores[i] = new Productor(i, buzonRevision, buzonReproceso);
            equipoCalidad[i] = new EquipoCalidad(i, buzonRevision, buzonReproceso, deposito, numProductos,
                    numOperarios);

        }

        for (Productor p : productores)
            p.start();
        for (EquipoCalidad e : equipoCalidad)
            e.start();

        try {
            for (Productor p : productores)
                p.join();
            for (EquipoCalidad e : equipoCalidad)
                e.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Producción finalizada.");
        scanner.close();
    }
}
