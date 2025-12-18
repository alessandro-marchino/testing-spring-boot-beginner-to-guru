package guru.springframework.sfgpetclinic.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.inOrder;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.fauxspring.Model;
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

    @Nested
    @ExtendWith(MockitoExtension.class)
    class ProcessFindFormTest {
        @Captor
        ArgumentCaptor<String> stringArgumentCaptor;
        @Mock
        Model model;

        @BeforeEach
        void setUp() {
            given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture()))
                .willAnswer(invocation -> switch((String)invocation.getArgument(0)) {
                    case "%Buck%" -> List.of(new Owner(1L, "Joe", "Buck"));
                    case "%DontFindMe%" -> List.of();
                    case "%FindMe%" -> List.of(new Owner(1L, "Joe", "Buck"), new Owner(2L, "Jane", "Doe"));
                    default -> throw new RuntimeException("Invalid Argument");
                });
        }
    
        @Test
        void processFindFormWildcardStringAnnotation() {
            // Given
            Owner owner = new Owner(null, "Joe", "Buck");
    
            // When
            String viewName = controller.processFindForm(owner, bindingResult, null);
            // Then
            assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("%Buck%");
            assertThat(viewName).isEqualTo("redirect:/owners/1");
            then(model).shouldHaveNoInteractions();
        }
    
        @Test
        void processFindFormWildcardStringNotFound() {
            // Given
            Owner owner = new Owner(null, "Joe", "DontFindMe");
    
            // When
            String viewName = controller.processFindForm(owner, bindingResult, null);
            // Then
            assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("%DontFindMe%");
            assertThat(viewName).isEqualTo("owners/findOwners");
            then(bindingResult).should().rejectValue("lastName", "notFound", "not found");
            then(model).shouldHaveNoInteractions();
        }
    
        @Test
        void processFindFormWildcardStringMultiple() {
            // Given
            Owner owner = new Owner(null, "Jane", "FindMe");
            InOrder inOrder = inOrder(ownerService, model);
    
            // When
            String viewName = controller.processFindForm(owner, bindingResult, model);
            // Then
            assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("%FindMe%");
            assertThat(viewName).isEqualTo("owners/ownersList");
            inOrder.verify(ownerService).findAllByLastNameLike(anyString());
            inOrder.verify(model).addAttribute(anyString(), anyList());
        }
    
        @Test
        void processFindFormWildcardStringHasErrors() {
            // Given
            Owner owner = new Owner(null, "Jane", "Doe");
    
            // When
            assertThrows(RuntimeException.class, () -> controller.processFindForm(owner, bindingResult, null));
            // Then
            assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("%Doe%");
            then(model).shouldHaveNoInteractions();
        }
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
