/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package domainapp.modules.simple.reserva;

import java.util.List;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Queries;
import javax.jdo.annotations.Query;
import javax.jdo.annotations.VersionStrategy;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.joda.time.LocalDate;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.schema.utils.jaxbadapters.JodaDateTimeStringAdapter;
import org.isisaddons.wicket.fullcalendar2.cpt.applib.CalendarEvent;
import org.isisaddons.wicket.fullcalendar2.cpt.applib.CalendarEventable;
import domainapp.modules.simple.habitacion.HabitacionRepositorio;
import domainapp.modules.simple.habitacion.Habitacion;
import domainapp.modules.simple.huesped.Huesped;
import domainapp.modules.simple.huesped.RepoHuesped;
import domainapp.modules.simple.tipodehabitacion.Tipodehabitacion;
import lombok.AccessLevel;
import static org.apache.isis.applib.annotation.CommandReification.ENABLED;
import static org.apache.isis.applib.annotation.SemanticsOf.IDEMPOTENT;
import static org.apache.isis.applib.annotation.SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE;
import lombok.Getter;
import lombok.Setter;
import javax.jdo.annotations.*;
@lombok.Getter @lombok.Setter
//@lombok.RequiredArgsConstructor

@PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Reserva"
)
@DatastoreIdentity(
        strategy= IdGeneratorStrategy.IDENTITY,
        column = "id")

@Version(
        strategy = VersionStrategy.VERSION_NUMBER,
        column = "version")

@Queries({
        @Query(
                        name = "find", language = "JDOQL",
                        value = "SELECT "
                                + "FROM domainapp.modules.simple.reserva.Reserva  "
                                + "ORDER BY nombre ASC"),
                @Query(
                        name = "findByNombre", language = "JDOQL",
                        value = "SELECT "
                                + "FROM domainapp.modules.simple.reserva.Reserva "
                                + "WHERE nombre == :nombre "),
        @Query(
                name = "findByHuesped", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.reserva.Reserva "
                        + "WHERE huesped == :huesped "
                        + "ORDER BY nombre ASC"),
     })

        @Unique(name = "Reserva_nombre_UNQ", members = { "nombre" })
        @DomainObject(
                editing = Editing.DISABLED
        )
        @DomainObjectLayout(
                bookmarking = BookmarkPolicy.AS_ROOT
        )

public class Reserva implements Comparable<Reserva>, CalendarEventable{
    @Column(allowsNull = "false", length =40)
    @Property()
    private String nombre;

    @Column(allowsNull = "false")
    @Property()
    private Huesped  huesped;

    @Column(allowsNull = "false")
    @Property()
    private Habitacion habitacion;


    @Column(allowsNull ="false")
    @Property()
    @Title( prepend= " Fecha de Reserva ")
    @XmlJavaTypeAdapter(JodaDateTimeStringAdapter.ForJaxb.class)
    private LocalDate fechaAlta;

    @Column(allowsNull = "false")
    @Property()
    private int cantdias;

    @Column(allowsNull = "false")
    @Property()
    private int precio;
/*    public String title(){
        return getNombre();
    }*/
      public String Huesped(){
        return this.huesped.toString();
    }
    public String Habitacion(){
        return this.habitacion.toString();
    }

public Reserva(
        final String nombre,
        final Huesped huesped,
        final Habitacion habitacion,
        final LocalDate fechaAlta,
        final int cantdias,
        final int precio
){
    this.nombre = nombre;
    this.huesped = huesped;
    this.habitacion = habitacion;
    this.fechaAlta =fechaAlta;
    this.cantdias = cantdias;
    this.precio = precio;
}
    @Action()
    @ActionLayout(named = "Editar")
    public Reserva update(

            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Estadia Nº: ")
            final String nombre,
			@Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Huesped: ")
            final Huesped huesped,

            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Habitacion: ")
           final Habitacion habitacion,

            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Fecha de  Reserva: ")
            final LocalDate fechaAlta,

            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Cantidad de dias de estadia: ")
             final int cantdias,

            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Saldo a Pagar: ")
            final int precio
    )
    {
        this.nombre = nombre;
        this.huesped = huesped;
        this.habitacion = habitacion;
        this.fechaAlta = fechaAlta;
        this.cantdias = cantdias;
        this.precio = precio;
        return this;
    }

    public String default0Update() {return getNombre();}

    @Override
    public int compareTo(final Reserva other) {
        return org.apache.isis.applib.util.ObjectContracts.compare(this, other, "nombre");
    }

    @Override
    public String toString() {
        return org.apache.isis.applib.util.ObjectContracts.toString(this, "nombre");
    }


    //Estados
    @javax.jdo.annotations.Column(allowsNull = "true", name = "estado")
    @Property()
    private EstadoReserva estado;
    //fin parte Estados

    //Estados ok
    @Programmatic
    public void CambiarEstado (EstadoReserva estado){
        this.estado = estado;
    }
    @Action()
    @ActionLayout(named = "Reservar Habitacion")
    public Reserva Reservada(){
        CambiarEstado(EstadoReserva.Reservada);
        messageService.informUser("Habitacion Reservada");
        return this;
    }
    @Action(semantics = SemanticsOf.IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(named = "Ingresar Habitacion")
    public Reserva Ocupada(){
       /* if (getEstado().equals(EstadoReserva.Liberada)) {
            messageService.warnUser("No es posible Reservar la habitacion ya esta ocupada!");
        } else {*/
        CambiarEstado(EstadoReserva.Ocupada);
        messageService.informUser("Puede ingresar a la Habitacion");
      //  }
        return this;
    }
    @Action(semantics = SemanticsOf.IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(named = "Salida de la Habitacion")
    public Reserva Liberada(){
        CambiarEstado(EstadoReserva.Liberada);
        messageService.informUser("Habitacion Liberada");
        return this;
    }
    public boolean hideReservada(){return  this.estado == EstadoReserva.Reservada;}
   public boolean hideOcupada(){return  this.estado == EstadoReserva.Ocupada;}
    public boolean hideLiberada(){return  this.estado == EstadoReserva.Liberada;}
    //fin Estados


    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    public void delete() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        repositoryService.remove(this);
    }

    @Programmatic
    @Override
    public String getCalendarName() {
        return huesped.getApellido();
    }
// LocalDate.to getFechaAlta()+""+   " "+ huesped.getDni()
        @Programmatic
        @Override
        public CalendarEvent toCalendarEvent() {
               return new CalendarEvent(getFechaAlta().toDateTimeAtStartOfDay(),
                       getCalendarName(),
                       getNombre());
            }
          //return null;

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    RepositoryService repositoryService;

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    RepoHuesped repoHuesped;

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    HabitacionRepositorio habitacionRepository;

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
  RepoHuesped huespedRepository;

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    HabitacionRepositorio HabitacionRepositorio;

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    TitleService titleService;

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    MessageService messageService;
}