package bg.jug.magman.authors;

import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Path("/authors")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
public class AuthorResource {

    private Map<String, Author> authors = new ConcurrentHashMap<>();
    private Author lastAuthor;

    @GET
    public Collection<Author> listAll() {
        return authors.values();
    }

    @POST
    public Author addAuthor(Author author) {
        authors.put(author.email, author);
        this.lastAuthor = author;
        authorCreated();
        return authors.get(author.email);
    }

    @Outgoing("authors")
    public Author authorCreated() {
        System.out.println("Called with new author: " + lastAuthor);
        return lastAuthor;
    }

    @GET
    @Path("/name")
    public Author findByEmail(@QueryParam("email") String email) {
        return authors.get(email);
    }
}