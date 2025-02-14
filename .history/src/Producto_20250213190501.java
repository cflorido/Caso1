class Producto {
  private String nombre;
  private static int contador = 0;
  private int id;
 

  public Producto(String nombre) {
      this.id = ++contador;
      this.nombre = nombre;
      
  }

  public String getNombre() {
      return nombre;
  }

  public void actualizarNombre() {
      this.nombre = nombre + id;
  }
  

  @Override
  public String toString() {
      return nombre ;
  }
}
