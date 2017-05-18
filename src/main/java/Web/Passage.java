package Web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by hongyu on 2017/5/17.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Passage {
    private int id;
    private String title;
    private String content;
    private String symptom;

    @Override
    public String toString() {
        return "Passage{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", symptom='" + symptom + '\'' +
                '}';
    }
}
