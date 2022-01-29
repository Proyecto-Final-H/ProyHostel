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
package domainapp.modules.simple.habitacion;
import domainapp.modules.simple.tipodehabitacion.Tipodehabitacion;
import domainapp.modules.simple.tipodehabitacion.TipodehabitacionRepositorio;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.isis.applib.annotation.*;

import javax.jdo.annotations.*;
import java.util.List;

@PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Habitacion"
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
                         + "FROM domainapp.modules.simple.habitacion.Habitacion  "
                         + "ORDER BY nombre ASC"),
        @Query(
                name = "findByNombre", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.habitacion.Habitacion "
                        + "WHERE nombre == :nombre "),
         @Query(
                name = "findByTipodehabitacion", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.habitacion.Habitacion "
                        + "WHERE tipodehabitacion == :tipodehabitacion "
                        + "ORDER BY nombre ASC"),

})

@Unique(name = "Habitacion_nombre_UNQ", members = { "nombre" })
@DomainObject(
        editing = Editing.DISABLED
)
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_ROOT
)
@Getter @Setter
public class Habitacion implements Comparable<Habitacion> {

    @Column(allowsNull = "false", length = 40)
    @Property()
    private String nombre;

    @Column(allowsNull = "false")
    @Property()
    private Tipodehabitacion tipodehabitacion;

    @Column(allowsNull ="false")
    @Property()
    private Integer preciohab;

    public String title(){
        return getNombre();
    }

    // public String RepoNombre(){ return this.nombre; }
    public String RepoTipodehabitacion(){ return this.tipodehabitacion.toString(); }

    public Habitacion(){}

    public Habitacion(
            final String nombre,
            final Tipodehabitacion tipodehabitacion,
            final Integer preciohab
            ){

        this.nombre = nombre;
        this.tipodehabitacion = tipodehabitacion;
        this.preciohab = preciohab;

    }

    @Action()
    @ActionLayout(named = "Editar")
    public Habitacion update(

            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Nombre: ")
            final String nombre,

            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Tipo de habitacion: ")
            final Tipodehabitacion tipodehabitacion,

             @Parameter(optionality = Optionality.MANDATORY)
             @ParameterLayout(named = "Precio por  Habitacion: ")
             final Integer preciohab)

            {

        this.nombre = nombre;
        this.tipodehabitacion = tipodehabitacion;
        this.preciohab = preciohab;
        return this;
    }

     public String default0Update() {return getNombre();}

     public Tipodehabitacion default1Update() {return getTipodehabitacion();}
    // public List<Tipodehabitacion> choices1Update() { return tipodehabitacionRepository.Listar();}

    //region > compareTo, toString
    @Override
    public int compareTo(final Habitacion other) {
        return org.apache.isis.applib.util.ObjectContracts.compare(this, other, "nombre");
    }

    @Override
    public String toString() {
        return org.apache.isis.applib.util.ObjectContracts.toString(this, "nombre");
    }
    //endregion

    @javax.inject.Inject
    @NotPersistent
    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    TipodehabitacionRepositorio tipodehabitacionRepository;

    @javax.inject.Inject
    @NotPersistent
    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    domainapp.modules.simple.habitacion.HabitacionRepositorio habitacionRepository;

}
