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

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.google.common.collect.ComparisonChain;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.schema.utils.jaxbadapters.JodaDateTimeStringAdapter;

import org.isisaddons.wicket.fullcalendar2.cpt.applib.CalendarEvent;
import org.isisaddons.wicket.fullcalendar2.cpt.applib.CalendarEventable;
import lombok.AccessLevel;
import static org.apache.isis.applib.annotation.CommandReification.ENABLED;
import static org.apache.isis.applib.annotation.SemanticsOf.IDEMPOTENT;
import static org.apache.isis.applib.annotation.SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE;

@javax.jdo.annotations.PersistenceCapable(identityType=IdentityType.DATASTORE, schema = "simple")
@javax.jdo.annotations.DatastoreIdentity(strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column="id")
@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column="version")
@javax.jdo.annotations.Unique(name="Reserva_name_UNQ", members = {"name"})
@DomainObject(auditing = Auditing.ENABLED)
@DomainObjectLayout()  // causes UI events to be triggered
@lombok.Getter @lombok.Setter
@lombok.RequiredArgsConstructor
public class Reserva implements Comparable<Reserva>, CalendarEventable{
//clase principal de Reserva
    @javax.jdo.annotations.Column(allowsNull = "false", length = 40)
    @lombok.NonNull
    @Property() // editing disabled by default, see isis.properties
    @Title(prepend = "Reserva: ")
    private String name;
    //Estados
    @javax.jdo.annotations.Column(allowsNull = "true", name = "estado")
    @Property()
    private EstadoReserva estado;
    //fin parte Estados

    @javax.jdo.annotations.Column(allowsNull = "true")
    @lombok.NonNull
    @Property() // editing disabled by default, see isis.properties
    @XmlJavaTypeAdapter(JodaDateTimeStringAdapter.ForJaxb.class)
    private LocalDate fechaAlta;
    @javax.jdo.annotations.Column(allowsNull = "true", length = 40)
    @Property(editing = Editing.ENABLED)
    private String notes;





    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith = "name")
    //Estados
    public String iconName(){
        if (this.estado == EstadoReserva.Reservada){
            return "Reservada";
        }else{
            return "Ocupada";
        }
    }
    //fin parte Estados
    public LocalDate RepoFechaAlta() { return this.fechaAlta; }
    public Reserva updateName(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Nombre")
            final String name,

            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Fecha de Alta")
            final LocalDate fechaAlta
                            ) {
        setName(name);
        this.fechaAlta = fechaAlta;
       // this.fechaAlta = fechaAlta;
        
        return this;
    }
    //Estados
    @Programmatic
    public void CambiarEstado (EstadoReserva estado){
        this.estado = estado;
    }
    
    @Action()
    @ActionLayout(named = "Reservar")
    public Reserva Reservada(){
        CambiarEstado(EstadoReserva.Reservada);
        return this;
    }
    @Action(semantics = SemanticsOf.IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(named = "Ingreso Habitacion ocupada")
    public Reserva Ocupada(){
        CambiarEstado(EstadoReserva.Ocupada);
        return this;
    }
    public boolean hideReservada(){return  this.estado == EstadoReserva.Reservada;}
    public boolean hideOcupada(){return  this.estado == EstadoReserva.Ocupada;}
    //fin Estados

    public String default0UpdateName() {
        return getName();
    }

    public TranslatableString validate0UpdateName(final String name) {
        return name != null && name.contains("!") ? TranslatableString.tr("Exclamation mark is not allowed") : null;
    }


    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    public void delete() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        repositoryService.remove(this);
    }


    @Override
    public String toString() {
        return getName();
    }

    public int compareTo(final Reserva other) {
        return ComparisonChain.start()
                .compare(this.getName(), other.getName())
                .result();
    }

        @Override public String getCalendarName() {
            return null;
        }

        @Override public CalendarEvent toCalendarEvent() {
            return null;
        }
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
//
//    @javax.inject.Inject
//    @javax.jdo.annotations.NotPersistent
//    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
//    RepoHabitacion repoHabitacion;

}