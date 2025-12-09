package guru.springframework.sfgpetclinic.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.fauxspring.ModelMapImpl;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.SpecialtyService;
import guru.springframework.sfgpetclinic.services.VetService;
import guru.springframework.sfgpetclinic.services.map.SpecialityMapService;
import guru.springframework.sfgpetclinic.services.map.VetMapService;

class VetControllerTest {
    private SpecialtyService specialtyService;
    private VetService service;
    private Model model;
    private VetController controller;

    @BeforeEach
    void setUp() {
        specialtyService = new SpecialityMapService();
        service = new VetMapService(specialtyService);
        controller = new VetController(service);
        model = new ModelMapImpl();

        service.save(new Vet(null, "Joe", "Buck", Set.of()));
        service.save(new Vet(null, "Jimmy", "Smith", Set.of()));
    }

    @SuppressWarnings("unchecked")
    @Test
    void testListVets() {
        String view = controller.listVets(model);
        assertEquals("vets/index", view);
        assertEquals(1, ((ModelMapImpl)model).getMap().size());
        assertEquals(2, ((Set<Vet>)((ModelMapImpl)model).getMap().get("vets")).size());
    }

}
