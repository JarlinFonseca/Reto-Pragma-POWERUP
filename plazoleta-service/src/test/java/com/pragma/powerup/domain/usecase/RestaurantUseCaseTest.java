
package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.exception.OwnerMustOnlyOwnARestaurantException;
import com.pragma.powerup.domain.exception.UserMustBeOwnerException;
import com.pragma.powerup.domain.exception.UserNotExistException;
import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.domain.model.RolModel;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.feignclients.IUserFeignClientPort;
import com.pragma.powerup.domain.spi.persistence.IRestaurantPersistencePort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
class RestaurantUseCaseTest {

    @InjectMocks
    RestaurantUseCase restaurantUseCase;

    @Mock
    IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
     IUserFeignClientPort userFeignClient;

    @Test
    void mustSaveARestaurant() {
        RestaurantModel restaurantModel= new RestaurantModel();

        restaurantModel.setId(1L);
        restaurantModel.setNombre("Frisby");
        restaurantModel.setNit("1654144544");
        restaurantModel.setDireccion("Unicentro");
        restaurantModel.setTelefono("3126544545");
        restaurantModel.setUrlLogo("URL");
        restaurantModel.setIdPropietario(2L);

        UserModel userModel = new UserModel();
        userModel.setRol(new RolModel(2L,"PROPIETARIO", "Propietario"));

        Mockito.when(userFeignClient.existsUserById(restaurantModel.getIdPropietario())).thenReturn(true);
        Mockito.when(userFeignClient.getUserById(restaurantModel.getIdPropietario())).thenReturn(userModel);
        Mockito.when(restaurantPersistencePort.getRestaurantByIdPropietario(userModel.getId())).thenReturn(null);

        restaurantUseCase.saveRestaurant(restaurantModel);

        Mockito.verify(restaurantPersistencePort).saveRestaurant(Mockito.any(RestaurantModel.class));
    }

    @Test
    void saveRestaurantWithNonExistingUser() {
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setId(1L);
        restaurantModel.setNombre("Frisby");
        restaurantModel.setNit("1654144544");
        restaurantModel.setDireccion("Unicentro");
        restaurantModel.setTelefono("3126544545");
        restaurantModel.setUrlLogo("URL");
        restaurantModel.setIdPropietario(2L);

        Mockito.when(userFeignClient.existsUserById(restaurantModel.getIdPropietario())).thenReturn(false);
        Assertions.assertThrows(UserNotExistException.class, () -> restaurantUseCase.saveRestaurant(restaurantModel));
    }

    @Test
    void saveRestaurantWithNonOwnerUser() {
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setId(1L);
        restaurantModel.setNombre("Frisby");
        restaurantModel.setNit("1654144544");
        restaurantModel.setDireccion("Unicentro");
        restaurantModel.setTelefono("3126544545");
        restaurantModel.setUrlLogo("URL");
        restaurantModel.setIdPropietario(3L);


        UserModel userModel = new UserModel();
        userModel.setRol(new RolModel(3L,"EMPLEADO", "Empleado"));


        Mockito.when(userFeignClient.existsUserById(restaurantModel.getIdPropietario())).thenReturn(true);
        Mockito.when(userFeignClient.getUserById(restaurantModel.getIdPropietario())).thenReturn(userModel);

        Assertions.assertThrows(UserMustBeOwnerException.class, () -> restaurantUseCase.saveRestaurant(restaurantModel));
    }

    @Test
    void saveRestaurantWithOwnerAlreadyOwningARestaurant() {
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setId(1L);
        restaurantModel.setNombre("Frisby");
        restaurantModel.setNit("1654144544");
        restaurantModel.setDireccion("Unicentro");
        restaurantModel.setTelefono("3126544545");
        restaurantModel.setUrlLogo("URL");
        restaurantModel.setIdPropietario(2L);

        UserModel userModel = new UserModel();
        userModel.setRol(new RolModel(2L,"PROPIETARIO", "Propietario"));

        RestaurantModel existingRestaurantModel = new RestaurantModel();

        Mockito.when(userFeignClient.existsUserById(restaurantModel.getIdPropietario())).thenReturn(true);
        Mockito.when(userFeignClient.getUserById(restaurantModel.getIdPropietario())).thenReturn(userModel);
        Mockito.when(restaurantPersistencePort.getRestaurantByIdPropietario(userModel.getId())).thenReturn(existingRestaurantModel);

        Assertions.assertThrows(OwnerMustOnlyOwnARestaurantException.class, () -> restaurantUseCase.saveRestaurant(restaurantModel));
    }

    @Test
    public void getRestaurantsWithPagination() {
        // Configuramos los valores de prueba
        Integer page = 1;
        Integer size = 10;
        List<RestaurantModel> expectedList = Arrays.asList(new RestaurantModel(), new RestaurantModel(), new RestaurantModel());

        // Configuramos el mock para que devuelva los resultados esperados
        Mockito.when(restaurantPersistencePort.getRestaurantsWithPagination(page, size)).thenReturn(expectedList);

        // Ejecutamos el método de prueba
        List<RestaurantModel> resultList = restaurantUseCase.getRestaurantsWithPagination(page, size);

        // Verificamos que los resultados sean los esperados
        assertEquals(expectedList, resultList);

        // Verificamos que se haya llamado al método del mock
        Mockito.verify(restaurantPersistencePort).getRestaurantsWithPagination(page, size);
    }
}
