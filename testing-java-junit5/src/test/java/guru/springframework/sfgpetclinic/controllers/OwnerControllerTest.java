package guru.springframework.sfgpetclinic.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
