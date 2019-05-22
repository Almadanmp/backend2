package pt.ipp.isep.dei.project.controller.controllerweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ipp.isep.dei.project.dto.GeographicAreaDTO;
import pt.ipp.isep.dei.project.model.geographicarea.GeographicAreaRepository;

import java.util.NoSuchElementException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/geographic_area_settings")
public class GASettingsWebController {

    @Autowired
    private GeographicAreaRepository geographicAreaRepo;

    /* User Story - 03 As a System Administrator I want to Create a new Geographic Area */

    /**
     * Method to create a DTO of Geographic Area
     *
     * @return ResponseEntity
     */
    @PostMapping(value = "/areas")
    public ResponseEntity<Object> createGeoArea(@RequestBody GeographicAreaDTO dto) {
        if (dto.getId() != null && dto.getName() != null && dto.getTypeArea() != null && dto.getLocal() != null) {
            if(geographicAreaRepo.addAndPersistDTO(dto)){
                Link link = linkTo(methodOn(GASettingsWebController.class).getAllGeographicAreas()).withRel("See all geographic areas");
            return new ResponseEntity<>("The Geographic Area has been created. To see all areas click : "+link, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("The Geographic Area hasn't been created. That Area already exists.", HttpStatus.CONFLICT);
        }}
        return new ResponseEntity<>("The Geographic Area hasn't been created. You have entered an" +
                " invalid Area.", HttpStatus.BAD_REQUEST);
    }

    /**
     * this method displays all the information of the Geographic Areas DTOs
     *
     * @return ResponseEntity with all the geographic areas
     */
    @GetMapping("/areas")
    public ResponseEntity<Object> getAllGeographicAreas() {
        return new ResponseEntity<>(geographicAreaRepo.getAllDTO(), HttpStatus.OK);
    }


    /**
     * Add daughter area to a mother area
     *
     * @param idAreaDaughter of the geoArea to be added
     * @param idAreaMother   of the geoArea with the daughter area
     * @return string with info if geoArea was added or not
     */
    @PutMapping("areas/{idMother}")
    public ResponseEntity<Object> addDaughterArea(@RequestBody long idAreaDaughter, @PathVariable("idMother") long idAreaMother) {
        try {
            if (geographicAreaRepo.addDaughterArea(idAreaDaughter, idAreaMother)) {
                return new ResponseEntity<>("The Geographic Area has been added.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("The Geographic Area hasn't been added. The daughter area is already contained in the mother area.", HttpStatus.CONFLICT);
            }
        } catch (NoSuchElementException ok) {
            return new ResponseEntity<>("There is no Geographic Area with that ID.", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Get daughter areas from an geo area
     * @param id mother area id
     * @return list of daughter areas on a mother area
     */
    @GetMapping("areas/{id}")
    public GeographicAreaDTO getGeographicArea(@PathVariable("id") long id) {
        return geographicAreaRepo.getDTOByIdWithMother(id);
    }

}
