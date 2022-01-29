package domainapp.modules.simple.tipodehabitacion;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.repository.RepositoryService;

import java.util.List;

import domainapp.modules.simple.tipodehabitacion.Tipodesexo;
import domainapp.modules.simple.tipodehabitacion.Tipoprecio;

@DomainService(
        nature = NatureOfService.DOMAIN,
        repositoryFor = Tipodehabitacion.class
)
public class TipodehabitacionRepositorio {

    @Programmatic
    public List<Tipodehabitacion> Listar() {

        return repositoryService.allMatches(
                new QueryDefault<>(
                        Tipodehabitacion.class,
                        "find"));
    }

    @Programmatic
    public Tipodehabitacion findByDni(final String camas) {

        return repositoryService.uniqueMatch(
                new QueryDefault<>(
                        Tipodehabitacion.class,
                        "findByDni",
                        "camas", camas));
    }

    @Programmatic
    public List<Tipodehabitacion> findByNombreContains(final String nombre) {

        return repositoryService.allMatches(
                new QueryDefault<>(
                        Tipodehabitacion.class,
                        "findByNombreContains",
                        "nombre", nombre));
    }

    @Programmatic
    public Tipodehabitacion create(
            final String camas,
            final String nombre,
            final Tipodesexo tipodesexo,
            final Tipoprecio tipoprecio,
            final String monto) {

        final Tipodehabitacion tipodehabitacion = new Tipodehabitacion(camas, nombre, tipodesexo, tipoprecio, monto);
        repositoryService.persist(tipodehabitacion);
        return tipodehabitacion;
    }

    @Programmatic
    public Tipodehabitacion findOrCreate(
            final String camas,
            final String nombre,
            final Tipodesexo tipodesexo,
            final Tipoprecio tipoprecio,
            final String monto) {

        Tipodehabitacion tipodehabitacion = findByDni(camas);
        if (tipodehabitacion == null) {
            tipodehabitacion = create(camas, nombre, tipodesexo, tipoprecio, monto);
        }
        return tipodehabitacion;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
}
