package bg.jug.magman.content;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Metered;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Path("/content")
@Produces(MediaType.APPLICATION_JSON)
public class ArticleResource {

    @GET
    @Metered(name = "All articles listed", unit = MetricUnits.MINUTES)
    public List<Article> listAll() {
        return Article.listAll();
    }

    @POST
    @Transactional
    public Article newArticle(Article article) {
        article.author = Author.findById(article.author.id);
        article.publishDate = LocalDate.now();
        article.persist();
        return article;
    }

    @GET
    @Path("/author")
    public List<Article> listAllByAuthor(@QueryParam("email") String email) {
        return Author.findByEmail(email)
                .map(a -> new ArrayList<Article>(Article.find("author", a).list()))
                .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }
}