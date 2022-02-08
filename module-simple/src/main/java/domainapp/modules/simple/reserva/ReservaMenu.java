package domainapp.modules.simple.reserva;
import domainapp.modules.simple.habitacion.Habitacion;
import domainapp.modules.simple.habitacion.HabitacionRepositorio;
import domainapp.modules.simple.huesped.Huesped;
import domainapp.modules.simple.huesped.RepoHuesped;
import org.apache.isis.applib.annotation.*;
import java.util.List;
import org.joda.time.LocalDate;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "simple.SimpleReservaMenu",
        repositoryFor = Reserva.class
)
@DomainServiceLayout(
        named = "",
        menuOrder = ""
)
public class ReservaMenu {

    @Action()
    @ActionLayout(named = "Crear Reserva")
    @MemberOrder(sequence = "1")
    public Reserva create(

            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Nombre: ")
            final String nombre,

            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Huesped: ")
            final Huesped huesped,

            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Habitacion: ")
            final Habitacion habitacion,

            @ParameterLayout(named = "Fecha:")
            final LocalDate fechaAlta,

            @ParameterLayout(named = "Cantidad de Dias:")
             final int cantdias,

            @ParameterLayout(named = "Saldo a pagar:")
            final int precio
    )
    {
    return reservarepository.create(nombre,huesped,habitacion, fechaAlta, cantdias, precio);
		 }

    public List<Habitacion> choices2Create() {return habitacionRepository.Listar();}
    public List<Huesped> choices1Create(){ return huespedrepository.listAll();}

   @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listado de Reservas")
    @MemberOrder(sequence = "2")
    public List<Reserva> listAll() {
        return reservarepository.Listar();
    }

    @javax.inject.Inject
    RepoReserva reservarepository;

    @javax.inject.Inject
    RepoHuesped huespedrepository;

    @javax.inject.Inject
    HabitacionRepositorio habitacionRepository;


}
