package domainapp.modules.simple.habitacion;
import domainapp.modules.simple.habitacion.Habitacion;
import domainapp.modules.simple.tipodehabitacion.Tipodehabitacion;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.repository.RepositoryService;

import java.util.List;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Habitacion.class
)
public class HabitacionRepositorio {

    @Programmatic
    public List<Habitacion> Listar() {

        return repositoryService.allMatches(
                new QueryDefault<>(
                        Habitacion.class,
                        "find"));
    }

    @Programmatic
    public List<Habitacion> Listar(Tipodehabitacion tipodehabitacion){

        return repositoryService.allMatches(
                new QueryDefault<>(
                        Habitacion.class,
                        "findByTipodehabitacion",
                        "tipodehabitacion", tipodehabitacion));
    }



    @Programmatic
    public Habitacion findByNombre(final String nombre) {

        return repositoryService.uniqueMatch(
                new QueryDefault<>(
                        Habitacion.class,
                        "findByNombre",
                        "nombre", nombre));
    }

    @Programmatic
    public List<Habitacion> findByNombreContains(final String nombre) {

        return repositoryService.allMatches(
                new QueryDefault<>(
                        Habitacion.class,
                        "findByNombreContains",
                        "nombre", nombre));
    }

    @Programmatic
    public Habitacion create(final String nombre, final Tipodehabitacion tipodehabitacion, final Integer preciohab) {

        final Habitacion habitacion = new Habitacion(nombre, tipodehabitacion, preciohab);
        repositoryService.persist(habitacion);
        return habitacion;
    }

    @Programmatic
    public Habitacion findOrCreate(final String nombre, final Tipodehabitacion tipodehabitacion, final Integer preciohab) {
        Habitacion habitacion = findByNombre(nombre);
        if (habitacion == null) {
            habitacion = create(nombre, tipodehabitacion, preciohab);
        }
        return habitacion;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
}
