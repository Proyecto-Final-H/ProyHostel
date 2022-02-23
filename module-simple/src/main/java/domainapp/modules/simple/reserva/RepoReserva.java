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

import domainapp.modules.simple.huesped.Huesped;
import java.util.List;
import org.joda.time.LocalDate;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.applib.services.repository.RepositoryService;
import domainapp.modules.simple.habitacion.Habitacion;

@DomainService(
        nature = NatureOfService.DOMAIN,
       objectType = "simple.ReservaMenu",
        repositoryFor = Reserva.class
)

public  class RepoReserva {

    @Programmatic
    public List<Reserva> Listar(){
        return repositoryService.allMatches(
                new QueryDefault<>(
                        Reserva.class,
                        "find"));
}
    @Programmatic
    public List<Reserva> Listar(Habitacion habitacion){

        return repositoryService.allMatches(
                new QueryDefault<>(
                        Reserva.class,
                        "findByHabitacion",
                        "habitacion", habitacion));

    }
    @Programmatic
    public List<Reserva> Listar(Huesped huesped){

        return repositoryService.allMatches(
                new QueryDefault<>(
                        Reserva.class,
                        "findByHuesped",
                        "huesped", huesped));

    }
    @Programmatic
    public Reserva findByNombre(final String nombre) {

        return repositoryService.uniqueMatch(
                new QueryDefault<>(
                        Reserva.class,
                        "findByNombre",
                        "nombre", nombre));
    }
    @Programmatic
    public List<Reserva> findByNombreContains(final String nombre) {

        return repositoryService.allMatches(
                new QueryDefault<>(
                        Reserva.class,
                        "findByNombreContains",
                        "nombre", nombre));
    }
    @Programmatic
    public Reserva create(final String nombre, final Huesped huesped, final Habitacion habitacion,  final LocalDate fechaAlta, final int cantdias, final int precio ) {

        final Reserva reserva = new Reserva(nombre,
                huesped,
                habitacion,
                fechaAlta,
                cantdias,
                precio
        );
        repositoryService.persist(reserva);
        return reserva;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
