package Web;

/**
 * Created by hongyu on 2017/5/17.
 */
public interface PassageService {
    Passage findPassageById(Passage passage);

    Passage findPassageByTitle(Passage passage);
}
