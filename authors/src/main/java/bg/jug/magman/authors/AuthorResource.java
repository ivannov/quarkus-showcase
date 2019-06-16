package bg.jug.magman.authors;

import io.smallrye.reactive.messaging.kafka.KafkaMessage;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.*;

@Path("/authors")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
public class AuthorResource {

    private Map<String, Author> authors = new ConcurrentHashMap<>();

    @GET
    public Collection<Author> listAll() {
        return authors.values();
    }

    @Inject
    private Event<Author> authorAddedEvent;

    @POST
    public Author addAuthor(Author author) {
        authors.put(author.email, author);
        authorAddedEvent.fireAsync(author);
        return authors.get(author.email);
    }

    @GET
    @Path("/name")
    public Author findByEmail(@QueryParam("email") String email) {
        return authors.get(email);
    }
}