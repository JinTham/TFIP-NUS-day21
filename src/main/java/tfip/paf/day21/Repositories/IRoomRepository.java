package tfip.paf.day21.Repositories;

import java.util.List;

import tfip.paf.day21.Model.Room;

public interface IRoomRepository {
    
    Integer count();

    Boolean save(Room room);
    
    List<Room> findAll();
    
    Room findById(Integer id);
    
    Boolean updateById(Integer id, Float price);

    Boolean deleteById(Integer id);

}
