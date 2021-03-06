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
package domainapp.modules.simple.huesped;
import java.util.regex.Pattern;

import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;
import com.google.common.collect.ComparisonChain;
import org.apache.isis.applib.annotation.Action;
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

import lombok.AccessLevel;
import static org.apache.isis.applib.annotation.CommandReification.ENABLED;
import static org.apache.isis.applib.annotation.SemanticsOf.IDEMPOTENT;
import static org.apache.isis.applib.annotation.SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE;

@PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "huesped"
)
@DatastoreIdentity(
        strategy= IdGeneratorStrategy.IDENTITY,
        column="id")
@Version(strategy= VersionStrategy.VERSION_NUMBER,
        column="version")
/*
@Queries({
        @Query(
                name = "find", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.huesped.Huesped"
                       ),
})*/
@javax.jdo.annotations.Unique(name="Huesped_name_UNQ", members = {"name"})
@DomainObject(editing = Editing.DISABLED)
@DomainObjectLayout()
@lombok.Getter @lombok.Setter
@lombok.RequiredArgsConstructor

public class Huesped implements Comparable<Huesped> {

    @javax.jdo.annotations.Column(allowsNull = "false", length = 40)
    @lombok.NonNull
    @Property()
    @Title(prepend = "Huesped: ")
    private String name;

    @javax.jdo.annotations.Column(allowsNull = "true", length = 4000)
    @Property(editing = Editing.ENABLED)
    private String notes;

    @javax.jdo.annotations.Column(allowsNull = "true", length = 40)
    @lombok.NonNull
    @Property()
    @Title( prepend= ",")
    private String apellido;

    @javax.jdo.annotations.Column(allowsNull = "true", length = 40)
    @lombok.NonNull
    @Property()
    @Title( prepend= " DNI: ")
    private String dni;

    @javax.jdo.annotations.Column(allowsNull = "true", length = 40)
    @lombok.NonNull
    @Property()
    @Title( prepend= " Tel:")
    private String numTelefono;

    @javax.jdo.annotations.Column(allowsNull = "true", length = 40)
    @Property(editing = Editing.ENABLED,
            regexPattern = "(\\w+\\.)*\\w+@(\\w+\\.)+[A-Za-z]+",
            regexPatternFlags= Pattern.CASE_INSENSITIVE,
            regexPatternReplacement = "Mail incorrecto, debe ser un email valido (contiene un '@' simbolo)"
    )
    @lombok.NonNull
    private String email;

    @javax.jdo.annotations.Column(allowsNull = "true", length = 40)
    @lombok.NonNull
    @Property()
    private Pais pais;


    @Action(semantics = IDEMPOTENT, command = ENABLED, publishing = Publishing.ENABLED, associateWith = "name")
    public Huesped updateName(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Nombre")
            final String name,
            @ParameterLayout(named = "Apellido")
            final String apellido,
            @ParameterLayout(named = "DNI")
             final String dni,
            @ParameterLayout(named = "Numero de Telefono")
            final String numTelefono,
            @ParameterLayout(named = "Email")
            final String email,
            @ParameterLayout(named = "Pais")
            final Pais pais
                            ) {
        setName(name);
        setApellido(apellido);
        setDni(dni);
        setNumTelefono(numTelefono);
        setEmail(email);
        return this;
    }

    public String default0UpdateName() {
        return getName();
    }

    public TranslatableString validate0UpdateName(final String name) {
        return name != null && name.contains("!") ? TranslatableString.tr("Exclamation mark is not allowed") : null;
    }

//inicio Boton de Borrar
    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    public void delete() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        repositoryService.remove(this);
    }

//fin boton de borrar
    @Override
    public String toString() {
        return getName();
    }

    public int compareTo(final Huesped other) {
        return ComparisonChain.start()
                .compare(this.getName(), other.getName())
                .result();
    }


    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    RepositoryService repositoryService;

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    TitleService titleService;

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    MessageService messageService;

}