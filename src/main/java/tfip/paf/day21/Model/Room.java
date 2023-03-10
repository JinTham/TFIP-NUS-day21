package tfip.paf.day21.Model;

public class Room {
    private Integer id;
    private String roomType;
    private Float price;
    
    public Room() {
    }

    public Room(Integer id, String roomType, Float price) {
        this.id = id;
        this.roomType = roomType;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

}

