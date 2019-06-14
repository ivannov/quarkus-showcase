package bg.jug.magman.authors;

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

    @GET
    public Collection<Author> listAll() {
        return authors.values();
    }

    @POST
    public Author addAuthor(Author author) {
        authors.put(author.email, author);
        return authors.get(author.email);
    }

    @GET
    @Path("/name")
    public Author findByEmail(@QueryParam("email") String email) {
        return authors.get(email);
    }
}