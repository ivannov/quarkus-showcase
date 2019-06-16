package bg.jug.magman.authors;

import io.smallrye.reactive.messaging.kafka.KafkaMessage;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.LinkedBlockingDeque;

@ApplicationScoped
public class AuthorsFeeder {

    private static final Jsonb JSONB = JsonbBuilder.create();

    private BlockingQueue<Author> authorsFeed = new LinkedBlockingDeque<>();

    public void observeAuthorAdded(@ObservesAsync Author author) {
        System.out.println("Observed added author");
        authorsFeed.add(author);
    }

    @Outgoing("authors")
    public CompletionStage<KafkaMessage<String, String>> authorCreated() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String value = JSONB.toJson(authorsFeed.take());
                System.out.println("Sending " + value + " to Kafka");
                return KafkaMessage.of("key", value);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
