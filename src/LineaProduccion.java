import java.util.Scanner;

public class LineaProduccion {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el nmero de operarios en produccion y calidad: ");
        int numOperarios = scanner.nextInt();
        System.out.print("Ingrese el numero de productos a generar: ");
        int numProductos = scanner.nextInt();
        System.out.print("Ingrese el limite del buzon de revision: ");
        int capacidadBuzonRevision = scanner.nextInt();

        Buzon buzonRevision = new Buzon(capacidadBuzonRevision, "REVISION");
        Buzon buzonReproceso = new Buzon(Integer.MAX_VALUE, "REPROCESO");
        Buzon deposito = new Buzon(Integer.MAX_VALUE, "DEPOSITO");

        Productor[] productores = new Productor[numOperarios];
        EquipoCalidad[] equipoCalidad = new EquipoCalidad[numOperarios];

        for (int i = 0; i < numOperarios; i++) {
            productores[i] = new Productor(i, buzonRevision, buzonReproceso);
            equipoCalidad[i] = new EquipoCalidad(i, buzonRevision, buzonReproceso, deposito, numProductos);
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

        System.out.println(" Producción finalizada.");
    }
}