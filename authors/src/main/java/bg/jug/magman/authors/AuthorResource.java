package bg.jug.magman.authors;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import io.smallrye.reactive.messaging.annotations.Emitter;
import io.smallrye.reactive.messaging.annotations.Stream;
import io.smallrye.reactive.messaging.kafka.KafkaMessage;

@Path("/authors")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
public class AuthorResource {

    private static final Jsonb JSONB = JsonbBuilder.create();

    private Map<String, Author> authors = new ConcurrentHashMap<>();

    @Inject @Stream("authors") Emitter<KafkaMessage<String, String>> authorsEmitter;

    @GET
    public Collection<Author> listAll() {
        return authors.values();
    }

    @POST
    public Author addAuthor(Author author) {
        authors.put(author.email, author);
        authorsEmitter.send(KafkaMessage.of("key", JSONB.toJson(author)));
        return authors.get(author.email);
    }

    @GET
    @Path("/name")
    public Author findByEmail(@QueryParam("email") String email) {
        return authors.get(email);
    }
}