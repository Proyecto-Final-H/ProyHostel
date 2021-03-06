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

package domainapp.modules.simple.fixture;

import org.apache.isis.applib.fixturescripts.BuilderScriptAbstract;

import domainapp.modules.simple.huesped.Huesped;
import domainapp.modules.simple.huesped.RepoHuesped;
import domainapp.modules.simple.reserva.Reserva;
import domainapp.modules.simple.reserva.RepoReserva;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class SimpleObjectBuilder extends BuilderScriptAbstract<Huesped, SimpleObjectBuilder> {

    @Getter @Setter
    private String name;

    @Getter
    private Huesped object;

    @Override
    protected void execute(final ExecutionContext ec) {

        checkParam("name", ec, String.class);

//        object = wrap(repoHuesped).create(name);
    }

    @javax.inject.Inject
    RepoHuesped repoHuesped;
//    RepoHuesped RepoHuesped;
    @javax.inject.Inject
    RepoReserva repoReserva;

}
