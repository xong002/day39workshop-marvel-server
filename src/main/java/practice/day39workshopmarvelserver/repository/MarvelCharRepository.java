package practice.day39workshopmarvelserver.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import practice.day39workshopmarvelserver.model.MarvelChar;

@Repository
public class MarvelCharRepository {
    
    @Autowired
    private JdbcTemplate template;

    private String INSERT_ROW_SQL = "insert ignore into characters values (?,?,?)";
    private String GET_ROW_BY_ID_SQL = "select * from characters where id = ?";

    public void saveCharacter(MarvelChar mChar){
        template.update(INSERT_ROW_SQL, mChar.getId(), mChar.getName(), mChar.getThumbnail());
    }

    public MarvelChar getMarvelCharById(Integer id){
        List<MarvelChar> list = template.query(GET_ROW_BY_ID_SQL, BeanPropertyRowMapper.newInstance(MarvelChar.class), id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

}
