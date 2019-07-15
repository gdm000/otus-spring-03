package edu.otus.spring03.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import edu.otus.spring03.domain.MongoBook;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Random;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {
    private final int BOOKS_STUB_COUNT = 1000;



    private MongoBook classicMongoBook = MongoBook.builder().genre("Classic").author("Author1").name("Eugene Onegin").build();
    private MongoBook sciFiMongoBook = MongoBook.builder().genre("SciFi").author("Author2").name("Stranger in a Strange Land").build();

    @ChangeSet(order = "000", id = "dropDB", author = "pankrashkinvs", runAlways = true)
    public void dropDB(MongoDatabase database){
        database.drop();
    }



    @ChangeSet(order = "001", id = "initBooks", author = "pankrashkinvs", runAlways = true)
    public void initBooks(MongoTemplate template){
        String[] genres = {"Classic", "SciFi"};
        String[] authors = {"Author1", "Author2", "Author3"};
        Random random = new Random();
        for (int i = 0; i < BOOKS_STUB_COUNT; i++) {
            MongoBook mongoBook = MongoBook.builder().genre(genres[random.nextInt(2)]).author(authors[random.nextInt(3)]).name("Name "+ i).build();
            template.save(mongoBook);
        }
    }
}
