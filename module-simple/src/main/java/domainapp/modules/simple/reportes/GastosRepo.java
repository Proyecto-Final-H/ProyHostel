package domainapp.modules.simple.reportes;

public class GastosRepo {
    private String name;
    public GastosRepo(String name){
        this.name = name;
    }
    public GastosRepo(){}
    public String getName(){ return  this.name; }
}
