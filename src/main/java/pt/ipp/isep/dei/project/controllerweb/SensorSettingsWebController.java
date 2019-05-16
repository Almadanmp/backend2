package pt.ipp.isep.dei.project.controllerweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pt.ipp.isep.dei.project.dto.AreaSensorDTO;
import pt.ipp.isep.dei.project.dto.GeographicAreaDTO;
import pt.ipp.isep.dei.project.dto.mappers.AreaSensorMapper;
import pt.ipp.isep.dei.project.dto.mappers.GeographicAreaMapper;
import pt.ipp.isep.dei.project.model.geographicarea.AreaSensor;
import pt.ipp.isep.dei.project.model.geographicarea.GeographicArea;
import pt.ipp.isep.dei.project.model.geographicarea.GeographicAreaRepository;

import java.net.URI;
import java.util.List;

@RestController
@ApplicationScope
@RequestMapping("/sensorsettings")
public class SensorSettingsWebController {

    @Autowired
    private GeographicAreaRepository geographicAreaRepository;

    // Part 1 - Geographical Areas

    @GetMapping("/areas")
    public List<GeographicAreaDTO> retrieveAllGeographicAreas() {
        return geographicAreaRepository.getAllDTO();
    }

    @GetMapping("/areas/{id}")
    public GeographicAreaDTO retrieveGA(@PathVariable long id) {
        return geographicAreaRepository.getDTOById(id);
    }

    // Part 2 - Sensors

    @GetMapping("/areas/{id}/sensors")
    public List<AreaSensorDTO> retrieveAllSensors(@PathVariable long id) {
        return geographicAreaRepository.getDTOById(id).getSensorDTOs();
    }

    @GetMapping

    @PostMapping("/areas/{id}/create")
    public ResponseEntity<AreaSensorDTO> createAreaSensor(@RequestBody AreaSensorDTO areaSensorDTO,
                                                          @PathVariable long id) {
        GeographicAreaDTO geoArea = geographicAreaRepository.getDTOById(id);
        geographicAreaRepository.addSensorDTO(geoArea, areaSensorDTO);
        geographicAreaRepository.updateAreaDTO(geoArea);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(areaSensorDTO.getId()).toUri();

        return ResponseEntity.created(location).build();
    }


    // us010 deactivate geo area sensor
    /**
     * WEb controller: get area sensor dto by id (and by area id)
     * @param idArea area id where the area sensor is
     * @param idSensor sensor id
     * @return ok status if the sensor with the selected id exists
     */
    @GetMapping("areas/{id}/sensors/{id2}")
    public AreaSensorDTO getAreaSensor(@PathVariable("id") long idArea, @PathVariable("id2") String idSensor) {
        return geographicAreaRepository.getAreaSensorByID(idSensor,idArea);
    }

    /**
     * US010 WEB controller: deactivate arya sensor with id sensor
     * @param idArea arya id where the arya sensor id
     * @param idSensor sensor id
     * @return ok status if the area sensor exists
     */
    @PutMapping("areas/{id}/sensors/{id2}")
    public ResponseEntity<Object> putAreaSensor(@PathVariable("id") long idArea, @PathVariable("id2") String idSensor) {
        GeographicAreaDTO geographicArea = geographicAreaRepository.getDTOById(idArea);
        AreaSensorDTO areaSensorDTO = geographicAreaRepository.getAreaSensorByID(idSensor,idArea);
        geographicAreaRepository.removeSensorDTO(geographicArea, idSensor);
        areaSensorDTO.setActive(false);
        geographicAreaRepository.addSensorDTO(geographicArea, areaSensorDTO);
        geographicAreaRepository.updateAreaDTO(geographicArea);
        if (!areaSensorDTO.getActive()) {
            return new ResponseEntity<>("Area Sensor is deactivated", HttpStatus.OK);
        }
        return new ResponseEntity<>("Area Sensor can't be deactivated", HttpStatus.NOT_FOUND);
    }


    /**
     * US011:
     * Method for removing area sensors from repository.
     * @param id geographic area id.
     * @param id2 area sensor id.
     * @return OK status if area sensor is found and removed or NOT_FOUND status if not found.
     */

    @DeleteMapping(value = "/areas/{id}/sensors/{id2}")
    public ResponseEntity<String> removeAreaSensor(@PathVariable long id, @PathVariable String id2) {
        GeographicAreaDTO geoArea = geographicAreaRepository.getDTOById(id);

        boolean removed = geographicAreaRepository.removeSensorDTO(geoArea, id2);
        geographicAreaRepository.updateAreaDTO(geoArea);

        if (removed) {
            return new ResponseEntity<>("body", HttpStatus.OK);
        }
        return new ResponseEntity<>("body", HttpStatus.NOT_FOUND);
    }
}

// CODE TO TEST ON POSTMAN
/*
{
        "id": "Teste2",
        "name": "Mae estou na BD",
        "typeSensor": "temperature",
        "units": "mm",
        "latitude": 6,
        "longitude": 6,
        "altitude": 6,
        "dateStartedFunctioning": "2018-10-12"
        }
*/

