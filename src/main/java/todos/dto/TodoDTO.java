package todos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import todos.model.Category;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TodoDTO {
    private String id;
    private String title;
    private String description;
    private boolean completed;
    private Category category;
    private Instant created;
    private Instant lastModified;
}

