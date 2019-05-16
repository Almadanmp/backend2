package pt.ipp.isep.dei.project.model.house;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ipp.isep.dei.project.repository.HouseCrudeRepo;

import java.util.List;

@Service
public class HouseRepository {

    @Autowired
    HouseCrudeRepo houseCrudeRepo;

    public HouseRepository(HouseCrudeRepo houseCrudeRepo){
        this.houseCrudeRepo = houseCrudeRepo;
    }

    /**
     * This method goes to the house crud repository finds the house
     * and returns the house ID.
     * @return house ID
     * **/
    public String getHouseId() {
        List<House> houseList = houseCrudeRepo.findAll();
        House house = houseList.get(0);
        return house.getId();
    }

}