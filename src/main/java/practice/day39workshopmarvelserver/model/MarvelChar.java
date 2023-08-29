package practice.day39workshopmarvelserver.model;

import jakarta.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarvelChar {
    private Integer id;
    private String name;
    private String thumbnail;

    public void toMarvelChar(JsonObject o){
        this.id = o.getInt("id");
        this.name = o.getString("name");
        this.thumbnail = o.getJsonObject("thumbnail").getString("path") + "." + o.getJsonObject("thumbnail").getString("extension");
    }


}
