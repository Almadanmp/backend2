package pt.ipp.isep.dei.project.controllerWeb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.ApplicationScope;
import pt.ipp.isep.dei.project.dto.AreaSensorDTO;
import pt.ipp.isep.dei.project.dto.GeographicAreaDTO;
import pt.ipp.isep.dei.project.dto.mappers.AreaSensorMapper;
import pt.ipp.isep.dei.project.model.geographicarea.AreaSensor;
import pt.ipp.isep.dei.project.model.geographicarea.GeographicArea;
import pt.ipp.isep.dei.project.model.geographicarea.GeographicAreaRepository;

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

    @PostMapping("/create")
//    public ResponseEntity<AreaSensorDTO> createAreaSensor(@RequestBody AreaSensorDTO areaSensorDTO) {
    public void createAreaSensor(@RequestBody AreaSensorDTO areaSensorDTO) {
//        geographicAreaRepository.addAndPersistDTO();
//        geographicAreaRepository.updateGeoArea(cenas);

//        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
//                .buildAndExpand(areaSensorDTO.getId()).toUri();

//        return ResponseEntity.created(location).build();

//        System.out.println(areaSensorDTO.getName());
//        System.out.println(areaSensorDTO.getId());
//        System.out.println(areaSensorDTO.getAltitude());
        AreaSensor testando = AreaSensorMapper.dtoToObjectMinimalist(areaSensorDTO);
        List<GeographicArea> listaGA = geographicAreaRepository.getAll();
        GeographicArea area = listaGA.get(0);
        area.addSensor(testando);
        geographicAreaRepository.updateGeoArea(area);
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

