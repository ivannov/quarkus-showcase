package bg.jug.magman.content;

import javax.transaction.Transactional;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/author")
@Produces(MediaType.APPLICATION_JSON)
public class AuthorResource {

    @POST
    @Transactional
    public Author addAuthor(Author author) {
        author.persist();
        return author;
    }
}
