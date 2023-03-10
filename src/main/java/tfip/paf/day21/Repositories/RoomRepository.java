package tfip.paf.day21.Repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import tfip.paf.day21.Model.Room;

@Repository
public class RoomRepository implements IRoomRepository {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String SQLcount = "select count(*) from room";
    private final String SQLselectAll = "select * from room";
    private final String SQLselectById = "select * from room where id = ?";
    private final String SQLdeleteById = "delete from room where id = ?";
    private final String SQLinsert = "insert into room (room_type,price) values (?,?)";
    private final String SQLupdateById = "update room set price = ? where id = ?";

    @Override
    public Integer count() {
        Integer result = jdbcTemplate.queryForObject(SQLcount, Integer.class);
        if (result == null) {
            return 0;
        }
        return result;
    }

    @Override
    public Boolean save(Room room) {
        Integer saved = jdbcTemplate.update(SQLinsert,room.getRoomType(),room.getPrice());
        return saved > 0;
    }

    @Override
    public List<Room> findAll() { //can use queryForRowset as well
        return jdbcTemplate.query(SQLselectAll,BeanPropertyRowMapper.newInstance(Room.class));
    }

    @Override
    public Room findById(Integer id) { //can use queryForRowset as well
        return jdbcTemplate.queryForObject(SQLselectById, BeanPropertyRowMapper.newInstance(Room.class), id);
    }

    @Override
    public Boolean updateById(Integer id, Float price) {
        Integer updated = jdbcTemplate.update(SQLupdateById,price,id);
        return updated > 0;
    }

    @Override
    public Boolean deleteById(Integer id) {
        Integer deleted = jdbcTemplate.update(SQLdeleteById,id);
        return deleted > 0;
    }

}

