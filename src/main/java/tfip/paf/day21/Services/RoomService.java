package tfip.paf.day21.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tfip.paf.day21.Model.Room;
import tfip.paf.day21.Repositories.RoomRepository;

@Service
public class RoomService{
    
    @Autowired
    RoomRepository roomRepo;

    public Integer count() {
        return roomRepo.count();
    }

    public Boolean save(Room room) {
        return roomRepo.save(room);
    }

    public List<Room> findAll() {
        return roomRepo.findAll();
    }

    public Room findById(Integer id) {
        return roomRepo.findById(id);
    }

    public Boolean updateById(Integer id, Float price) {
        return roomRepo.updateById(id, price);
    }

    public Boolean deleteById(Integer id) {
        return roomRepo.deleteById(id);
    }

}
