package Web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hongyu on 2017/5/17.
 */

@Service("passageService")
public class PassageServiceImpl implements PassageService{
    private PassageDao passageDao;

    @Autowired
    public PassageServiceImpl(PassageDao passageDao) {
        this.passageDao = passageDao;
    }

    @Override
    public Passage findPassageById(Passage passage) {
        return passageDao.selectPassageById(passage);
    }

    @Override
    public Passage findPassageByTitle(Passage passage) {
        return passageDao.selectPassageByTitle(passage);
    }
}
