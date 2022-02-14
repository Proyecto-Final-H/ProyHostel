package domainapp.modules.simple.reportes;

public class CajaRepo {
    public String name;
    public Integer importe;
    public String fecha;
    public String numerofactura;
    public String condicionvent;
    public String condicioniva;

    public CajaRepo(String name,Integer importe, String fecha,  String numerofactura,String condicioniva, String condicionvent)
    {
      this.name = name;
      this.importe = importe;
      this.fecha = fecha;
      this.numerofactura = numerofactura;
      this.condicionvent = condicionvent;
      this.condicioniva = condicioniva;

    }

    public CajaRepo() {
    }
    public String getName(){return  this.name;}
    public String getNumerofactura(){return this.numerofactura;}
    public Integer getimporte(){return  this.importe;}
    public String getFecha () { return this.fecha; }
    public String getCondicionvent(){return  this.condicionvent;}
    public String getCondicioniva(){return  this.condicioniva;}

}
