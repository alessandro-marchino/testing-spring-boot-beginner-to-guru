package guru.springframework.sfgpetclinic.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {
    private static final String REDIRECT_OWNERS_5 = "redirect:/owners/5";
    private static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";

    @Mock
    OwnerService ownerService;
    @Mock
    BindingResult bindingResult;
    @InjectMocks
    OwnerController controller;
    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @Test
    void processFindFormWildcardString() {
        // Given
        Owner owner = new Owner(null, "Joe", "Buck");
        List<Owner> ownerList = List.of();
        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        given(ownerService.findAllByLastNameLike(captor.capture())).willReturn(ownerList);

        // When
        String viewName = controller.processFindForm(owner, bindingResult, null);
        // Then
        assertThat(captor.getValue()).isEqualToIgnoringCase("%Buck%");
        assertThat(viewName).isEqualTo("owners/findOwners");
        then(bindingResult).should().rejectValue("lastName", "notFound", "not found");
    }

    @Test
    void processFindFormWildcardStringAnnotation() {
        // Given
        Owner owner = new Owner(null, "Joe", "Buck");
        List<Owner> ownerList = List.of();
        given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture())).willReturn(ownerList);

        // When
        String viewName = controller.processFindForm(owner, bindingResult, null);
        // Then
        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("%Buck%");
        assertThat(viewName).isEqualTo("owners/findOwners");
        then(bindingResult).should().rejectValue("lastName", "notFound", "not found");
    }

    @Test
    void processCreationForm() {
        // Given
        Owner owner = new Owner(null, "Jim", "Bob");
        given(bindingResult.hasErrors()).willReturn(false);
        Owner savedOwner = new Owner(5L, "Jim", "Bob");
        given(ownerService.save(any(Owner.class))).willReturn(savedOwner);
        // When
        String result = controller.processCreationForm(owner, bindingResult);
        // Then
        assertThat(result).isEqualTo(REDIRECT_OWNERS_5);
        then(bindingResult).should().hasErrors();
        then(ownerService).should().save(any(Owner.class));
    }

    @Test
    void processCreationFormBindingHasErrors() {
        // Given
        Owner owner = new Owner(null, "Jim", "Bob");
        given(bindingResult.hasErrors()).willReturn(true);
        // When
        String result = controller.processCreationForm(owner, bindingResult);
        // Then
        assertThat(result).isEqualTo(OWNERS_CREATE_OR_UPDATE_OWNER_FORM);
        then(bindingResult).should().hasErrors();
        then(ownerService).shouldHaveNoInteractions();
    }
}
