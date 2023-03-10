package tfip.paf.day21.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tfip.paf.day21.Exception.ResourceNotFoundException;
import tfip.paf.day21.Model.Room;
import tfip.paf.day21.Services.RoomService;

@RestController
@RequestMapping(path="/api/rooms")
public class RoomController {
    
    @Autowired
    RoomService roomSvc;

    @GetMapping(path="/count")
    public ResponseEntity<Integer> getRoomCount() {
        return new ResponseEntity<Integer>(roomSvc.count(),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = roomSvc.findAll();
        if (rooms.isEmpty()) {
            throw new ResourceNotFoundException("No room found");
        } else {
            return ResponseEntity.ok().body(rooms);
        }
    }

    @GetMapping(path="{room-id}")
    public ResponseEntity<Room> getRoomById(@PathVariable ("room-id") Integer id) {
        Room room = roomSvc.findById(id);
        if (room == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(room);
        //return new ResponseEntity<Room>(room,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createRoom(@RequestBody Room room) {
        Boolean created = roomSvc.save(room);
        if (created) {
            return new ResponseEntity<String>("Save done",HttpStatus.CREATED);
        }
        return new ResponseEntity<String>("Save failed",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(path="/update")
    public ResponseEntity<String> updateById(@RequestParam Integer id, Float price) {
        Boolean updated = roomSvc.updateById(id, price);
        if (updated) {
            return new ResponseEntity<String>("Update done",HttpStatus.OK);
        }
        return new ResponseEntity<String>("Update failed",HttpStatus.OK);
    }

    @GetMapping(path="/delete")
    public ResponseEntity<String> deleteById(@RequestParam Integer id) {
        Boolean deleted = roomSvc.deleteById(id);
        if (deleted) {
            return new ResponseEntity<String>("Delete done",HttpStatus.OK);
        }
        return new ResponseEntity<String>("Delete failed",HttpStatus.OK);
    }

}
