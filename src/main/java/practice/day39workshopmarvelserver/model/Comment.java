package practice.day39workshopmarvelserver.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Integer id;
    private String comments;
    private Integer charId;
    private Date timestamp;
}
