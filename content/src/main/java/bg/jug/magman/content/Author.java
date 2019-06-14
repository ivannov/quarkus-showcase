package bg.jug.magman.content;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import java.util.Optional;

@Entity
public class Author extends PanacheEntity {

    public String name;
    public String email;

    public static Optional<Author> findByEmail(String email) {
        return Optional.ofNullable(find("email", email).firstResult());
    }
}
