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
package domainapp.modules.simple.gastos;


import java.io.IOException;
import java.util.List;

import org.datanucleus.query.typesafe.TypesafeQuery;
import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.value.Blob;
import domainapp.modules.simple.reportes.EjecutarReportes;
import net.sf.jasperreports.engine.JRException;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "simple.GastosMenu",
        repositoryFor = Gastos.class
)
@DomainServiceLayout(
        named = "Simple Objects",
        menuOrder = "10"
)
public class RepoGastos {

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<Gastos> listAll() {
        return repositoryService.allInstances(Gastos.class);
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "2")
    public List<Gastos> findByName(
            @ParameterLayout(named="Name")
            final String name
    ) {
        TypesafeQuery<Gastos> q = isisJdoSupport.newTypesafeQuery(Gastos.class);
        final QGastos cand = QGastos.candidate();
        q = q.filter(
                cand.name.indexOf(q.stringParameter("name")).ne(-1)
        );
        return q.setParameter("name", name)
                .executeList();
    }

    @Programmatic
    public Gastos findByNameExact(final String name) {
        TypesafeQuery<Gastos> q = isisJdoSupport.newTypesafeQuery(Gastos.class);
        final QGastos cand = QGastos.candidate();
        q = q.filter(
                cand.name.eq(q.stringParameter("name"))
        );
        return q.setParameter("name", name)
                .executeUnique();
    }
    @Programmatic
    public void ping() {
        TypesafeQuery<Gastos> q = isisJdoSupport.newTypesafeQuery(Gastos.class);
        final QGastos candidate = QGastos.candidate();
        q.range(0,2);
        q.orderBy(candidate.name.asc());
        q.executeList();
    }
    //Reporte
   @Action()
    @ActionLayout(named = "Exportar Listado de Gastos")
    public Blob exportarListado() throws JRException, IOException {
        EjecutarReportes ejecutarReportes = new EjecutarReportes();
        return ejecutarReportes.ListadoGastosPDF(repositoryService.allInstances(Gastos.class));//.ListadoEquiposPDF(repositoryService.allInstances(Equipo.class));
    }
    //Fin Reporte
    public static class CreateDomainEvent extends ActionDomainEvent<RepoGastos> {}
    @Action(domainEvent = CreateDomainEvent.class)
    @MemberOrder(sequence = "4")
    public Gastos create(
            @ParameterLayout(named="Name")
            final String name,
            @ParameterLayout(named = "Importe")
            final Integer importe,
            final Tipodegasto tipodegasto,
            @ParameterLayout(named= "Fecha")
            final LocalDate fecha,
            @ParameterLayout(named = "Numerofactura")
            final Integer numerofactura
            ) {
 	return repositoryService.persist(new Gastos(name,importe,numerofactura,fecha,tipodegasto));
   }

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
