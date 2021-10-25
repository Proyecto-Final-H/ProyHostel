package domainapp.modules.simple.reportes;

public class CajaRepo {
    private String name;
    public CajaRepo(String name,String condicionvent,String condicioniva){
      this.name = name;
    }

    public CajaRepo(){}
        public String getName(){return  this.name;}
}
