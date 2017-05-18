package Web;

/**
 * Created by hongyu on 2017/5/17.
 */
public interface PassageDao {
    Passage selectPassageById(Passage passage);

    Passage selectPassageByTitle(Passage passage);

}
