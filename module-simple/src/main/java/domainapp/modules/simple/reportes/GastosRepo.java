package domainapp.modules.simple.reportes;

public class GastosRepo {
    public String name;
    public Integer importe;
    public Integer numerofactura;
    public String tipodegasto;
    public String fecha;

    public GastosRepo(String name, Integer importe,Integer numerofactura, String tipodegasto, String fecha) {
        this.name = name;
        this.importe = importe;
        this.numerofactura = numerofactura;
        this.tipodegasto = tipodegasto;
        this.fecha = fecha;
    }
    public GastosRepo() {
    }
    public String getName(){ return  this.name; }
    public Integer getImporte(){return this.importe;}
    public Integer getNumerofactura(){return this.numerofactura;}
    public String getTipodegasto(){return this.tipodegasto;}
    public String getFecha() {
        return fecha;
    }

}
