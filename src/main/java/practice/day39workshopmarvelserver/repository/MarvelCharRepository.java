package practice.day39workshopmarvelserver.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import practice.day39workshopmarvelserver.model.Comment;
import practice.day39workshopmarvelserver.model.MarvelChar;

@Repository
public class MarvelCharRepository {
    
    @Autowired
    private JdbcTemplate template;

    private String INSERT_CHAR_SQL = "insert ignore into characters values (?,?,?)";
    private String GET_CHAR_BY_ID_SQL = "select * from characters where id = ?";
    private String INSERT_COMMENT_SQL = "insert into comments (comments, char_id) values (?,?)";
    private String GET_COMMENTS_BY_CHAR_ID_SQL = "select * from comments where char_id = ?";

    public void saveCharacter(MarvelChar mChar){
        template.update(INSERT_CHAR_SQL, mChar.getId(), mChar.getName(), mChar.getThumbnail());
    }

    public MarvelChar getMarvelCharById(Integer id){
        List<MarvelChar> list = template.query(GET_CHAR_BY_ID_SQL, BeanPropertyRowMapper.newInstance(MarvelChar.class), id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    public void saveComment(Comment comment){
        template.update(INSERT_COMMENT_SQL, comment.getComments(), comment.getCharId());
    }

    public List<Comment> getComments(Integer charId){
        List<Comment> list = template.query(GET_COMMENTS_BY_CHAR_ID_SQL, BeanPropertyRowMapper.newInstance(Comment.class), charId);
        return list;
    }

    
}
