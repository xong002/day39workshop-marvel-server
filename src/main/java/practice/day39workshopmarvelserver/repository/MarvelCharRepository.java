package practice.day39workshopmarvelserver.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import practice.day39workshopmarvelserver.model.MarvelChar;

@Repository
public class MarvelCharRepository {
    
    @Autowired
    private JdbcTemplate template;

    private String INSERT_ROW_SQL = "insert into characters values (?,?,?)";

    public void saveCharacter(MarvelChar mChar){
        template.update(INSERT_ROW_SQL, mChar.getId(), mChar.getName(), mChar.getThumbnail());
    }

}
