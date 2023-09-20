package todos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.Instant;

@Document(collection = "todos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Todo {
    @Id private String id;
    @Setter private String title;
    @Setter private String description;
    @Setter private boolean completed;

    @CreatedDate
    @Setter
    private Instant created;

    @LastModifiedDate
    @Setter
    private Instant lastModified;

    @DocumentReference
    @JsonIgnore
    private Category category;

    public Todo(String title, String description) {
        this.title = title;
        this.description = description;
        this.completed = false;
    }
}
