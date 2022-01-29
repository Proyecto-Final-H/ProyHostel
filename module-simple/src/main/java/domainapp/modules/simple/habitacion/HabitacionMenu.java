package domainapp.modules.simple.habitacion;
import domainapp.modules.simple.tipodehabitacion.Tipodehabitacion;
import domainapp.modules.simple.tipodehabitacion.TipodehabitacionRepositorio;
import org.apache.isis.applib.annotation.*;

import java.util.List;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "simple.SimpleHabitacionMenu",
        repositoryFor = Habitacion.class
)
@DomainServiceLayout(
        named = "",
        menuOrder = ""
)
public class HabitacionMenu {

    @Action()
    @ActionLayout(named = "Crear Habitacion")
    @MemberOrder(sequence = "1")
    public Habitacion create(

            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Nombre: ")
            final String nombre,

            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Tipodehabitacion: ")
            final Tipodehabitacion tipodehabitacion,

            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Precio de Habitacion: ")
            final Integer preciohab)

{
        return habitacionrepository.create(nombre, tipodehabitacion, preciohab);
    }

    public List<Tipodehabitacion> choices1Create() {return tipodehabitacionRepository.Listar();}

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Buscar Habitacion")
    @MemberOrder(sequence = "2")
    public Habitacion findByNombre(
            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Por Nombre: ")
            final Habitacion habitacion) {

        return habitacion;
    }

    public List<Habitacion> choices0FindByNombre() {return habitacionrepository.Listar();}


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listado de Habitaciones")
    @MemberOrder(sequence = "3")
    public List<Habitacion> listAll() {
        return habitacionrepository.Listar();
    }

    @javax.inject.Inject
    HabitacionRepositorio habitacionrepository;

    @javax.inject.Inject
    TipodehabitacionRepositorio tipodehabitacionRepository;

}
