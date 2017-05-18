package Web;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by hongyu on 2017/5/17.
 */
@Mapper
public interface PassageMapper extends PassageDao {
    @Override
    Passage selectPassageById(@Param("passage") Passage passage);

    @Override
    Passage selectPassageByTitle(@Param("passage") Passage passage);

}
