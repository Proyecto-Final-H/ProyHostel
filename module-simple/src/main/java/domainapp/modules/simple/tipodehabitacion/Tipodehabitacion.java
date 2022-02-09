package domainapp.modules.simple.tipodehabitacion;
import domainapp.modules.simple.tipodehabitacion.Tipodesexo;
import domainapp.modules.simple.tipodehabitacion.Tipoprecio;import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.isis.applib.annotation.*;

import javax.jdo.annotations.*;
import java.util.List;

@PersistenceCapable(
        identityType = IdentityType.DATASTORE,
        schema = "simple",
        table = "Tipodehabitacion"
)
@DatastoreIdentity(
        strategy = IdGeneratorStrategy.IDENTITY,
        column = "id"
)
@Version(
        strategy = VersionStrategy.VERSION_NUMBER,
        column = "version"
)
@Queries({
        @Query(
                name = "find", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.tipodehabitacion.Tipodehabitacion "
                        + "ORDER BY nombre ASC"),
/*        @Query(
                name = "findByNombreContains", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.tipodehabitacion.Tipodehabitacion"
                        + "WHERE nombre.indexOf(:nombre) >= 0 "),*/

        @Query(
                name = "findByDni", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.tipodehabitacion.Tipodehabitacion "
                        + "WHERE camas == :camas "
                        + "ORDER BY camas ASC")
})
@Unique(name="Tipodehabitacion_dni_UNQ", members = {"camas"})
@DomainObject(
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
@Getter @Setter
public class Tipodehabitacion implements Comparable<Tipodehabitacion>{

    @Column(allowsNull = "false", length = 40)
    @Property()
    @Title()
    private String camas;

    @Column(allowsNull = "false", length = 40)
    @Property()
    @Title()
    private String nombre;

    @javax.jdo.annotations.Column(allowsNull = "true", length = 40)
    @lombok.NonNull
    @Property()
    private Tipodesexo tipodesexo;

    @javax.jdo.annotations.Column(allowsNull = "true", length = 40)
    @lombok.NonNull
    @Property()
    private Tipoprecio tipoprecio;

    @Column(allowsNull = "false", length = 40)
    @Property()
    private String monto;

    public Tipodehabitacion(
            String camas,
            String nombre,
            Tipodesexo tipodesexo,
            Tipoprecio tipoprecio,
            String monto
    ){

        this.camas = camas;
        this.nombre = nombre;
        this.monto = monto;

    }

/*    public Tipodehabitacion(
            String camas,
            String nombre,
            String monto,
  )
             {
        this.camas = camas;
        this.nombre = nombre;
        this.monto = monto;

    }*/

      public String getNombre(){
        return this.nombre;
    }

    @Action()
    @ActionLayout(named = "Editar")
    public Tipodehabitacion update(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Camas: ")
            final String camas,

            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Nombre: ")
            final String nombre,

           /* @ParameterLayout(named = "Genero de la Habitacion")
            final String tipodesexo,*/

            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Precio por Personas: ")
            final String monto
    )
    {

        this.camas = camas;
        this.nombre = nombre;
        this.monto = monto;
        return this;
    }

    public String default0Update() {return getCamas();}

    public String default1Update() {return getNombre();}

    public String default2Update() {return getMonto();}

    //region > compareTo, toString
    @Override
    public int compareTo(final Tipodehabitacion other) {
        return org.apache.isis.applib.util.ObjectContracts.compare(this, other, "camas");
    }

    @Override
    public String toString() {
        return org.apache.isis.applib.util.ObjectContracts.toString(this, "camas");
    }
    //endregion
    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    TitleService titleService;

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    MessageService messageService;

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    RepositoryService repositoryService;

    @javax.inject.Inject
    @NotPersistent
    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    TipodehabitacionRepositorio tipodehabitacionRepositorio;
}
