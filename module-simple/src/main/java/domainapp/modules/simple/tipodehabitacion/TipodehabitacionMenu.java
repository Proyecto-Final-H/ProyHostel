package domainapp.modules.simple.tipodehabitacion;
import org.apache.isis.applib.annotation.*;

import java.util.List;

import domainapp.modules.simple.tipodehabitacion.Tipodesexo;
import domainapp.modules.simple.tipodehabitacion.Tipoprecio;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "simple.SimpleTipodehabitacionMenu",
        repositoryFor = Tipodehabitacion.class
)
@DomainServiceLayout(
        named = "",
        menuOrder = ""
)
public class TipodehabitacionMenu {

    @Action()
    @ActionLayout(named = "Crear Tipodehabitacion")
    @MemberOrder(sequence = "5")
    public Tipodehabitacion create(

            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Camas: ")
            final String camas,

            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Nombre: ")
            final String nombre,

            @ParameterLayout(named = "Tipo de sexo de Habitacion")
            final Tipodesexo tipodesexo,

            @ParameterLayout(named = "Tipo de Precio")
            final Tipoprecio tipoprecio,

            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Precio por persona: ")
            final String monto) {
        return tipodehabitacionrepository.create(camas, nombre, tipodesexo, tipoprecio, monto);
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Buscar Tipo de habitacion")
    @MemberOrder(sequence = "2")
    public Tipodehabitacion findByDni(
            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Por camas: ")
            final Tipodehabitacion tipodehabitacion) {

        return tipodehabitacion;
    }

    public List<Tipodehabitacion> choices0FindByDni() {return tipodehabitacionrepository.Listar();}


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listado de Tipo de Habitacion1")
    @MemberOrder(sequence = "3")
    public List<Tipodehabitacion> listAll() {
        List<Tipodehabitacion> tipodehabitaciones = tipodehabitacionrepository.Listar();
        return tipodehabitaciones;
    }


    @javax.inject.Inject
    TipodehabitacionRepositorio tipodehabitacionrepository;
}
