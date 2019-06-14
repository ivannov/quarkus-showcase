package bg.jug.magman.content;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class Article extends PanacheEntity {

    public String title;
    public String content;
    public LocalDate publishDate;
    @ManyToOne
    public Author author;
}
