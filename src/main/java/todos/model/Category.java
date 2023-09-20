package todos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document(collection = "categories")
@AllArgsConstructor
@Getter
public class Category {
    @Id private String id;
    @Setter private String name;

    @DBRef(lazy = true)
    @JsonIgnore
    @Setter
    private List<Todo> todos;

    public Category(String name) {
        this.name = name;
    }
}
