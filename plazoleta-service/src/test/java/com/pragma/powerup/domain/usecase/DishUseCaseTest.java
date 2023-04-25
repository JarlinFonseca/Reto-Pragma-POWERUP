
package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.ICategoryServicePort;
import com.pragma.powerup.domain.exception.CategoryNotExistException;
import com.pragma.powerup.domain.exception.RestaurantNotExistException;
import com.pragma.powerup.domain.model.CategoryModel;
import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.domain.spi.bearertoken.IToken;
import com.pragma.powerup.domain.spi.persistence.IDishPersistencePort;
import com.pragma.powerup.domain.spi.persistence.IRestaurantPersistencePort;
import com.pragma.powerup.factory.FactoryDishDataTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.management.MonitorInfo;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
class DishUseCaseTest {

    @InjectMocks
    DishUseCase dishUseCase;

    @Mock
    IDishPersistencePort dishPersistencePort;


    @Mock
     IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private IToken token;

    @Mock
    private ICategoryServicePort categoryServicePort;

    @Test
    public void mustSaveADish() {
        RestaurantModel restaurantModel= FactoryDishDataTest.getRestaurante();
        CategoryModel categoryModel = FactoryDishDataTest.getCategory();
        DishModel dishModel = FactoryDishDataTest.getDish();


        Mockito.when(restaurantPersistencePort.getRestaurantById(dishModel.getRestauranteId().getId())).thenReturn(restaurantModel);
        Mockito.when(categoryServicePort.getCategoryById(dishModel.getRestauranteId().getId())).thenReturn(categoryModel);
        Mockito.when(token.getBearerToken()).thenReturn("validToken");
        Mockito.when(token.getUsuarioAutenticadoId("validToken")).thenReturn(20L);

        dishUseCase.saveDish(dishModel);

        // Assert
        Mockito.verify(dishPersistencePort).saveDish(dishModel);
    }

    @Test
    void saveDish_InvalidRestaurant_ThrowsRestaurantNotExistException() {
        RestaurantModel restaurantModel= FactoryDishDataTest.getRestaurante();
        restaurantModel.setId(null);
        CategoryModel categoryModel = FactoryDishDataTest.getCategory();
        DishModel dishModel = FactoryDishDataTest.getDish();

        Mockito.when(restaurantPersistencePort.getRestaurantById(restaurantModel.getId())).thenReturn(null);
        Mockito.when(categoryServicePort.getCategoryById(categoryModel.getId())).thenReturn(categoryModel);

        // Act & Assert
        assertThrows(RestaurantNotExistException.class, () -> dishUseCase.saveDish(dishModel));
    }

    @Test
    void saveDish_InvalidCategory_ThrowsCategoryNotExistException() {
        RestaurantModel restaurantModel= FactoryDishDataTest.getRestaurante();
        CategoryModel categoryModel = FactoryDishDataTest.getCategory();
        categoryModel.setId(null);
        DishModel dishModel = FactoryDishDataTest.getDish();

        Mockito.when(restaurantPersistencePort.getRestaurantById(restaurantModel.getId())).thenReturn(restaurantModel);
        Mockito.when(categoryServicePort.getCategoryById(categoryModel.getId())).thenReturn(null);

        // Act & Assert
        assertThrows(CategoryNotExistException.class, () -> dishUseCase.saveDish(dishModel));
    }

    @Test
    public void mustUpdateADish() {
        DishModel dishModel = FactoryDishDataTest.getDish();
        //Update
        DishModel dishModel2 = FactoryDishDataTest.getDish();
        dishModel2.setPrecio("77000");
        dishModel2.setDescripcion("Plato con 10 presas de pollo, mas ensalada de repollo y 4 vasos de gasesosa");

        RestaurantModel restaurantModel = FactoryDishDataTest.getRestaurante();
        Mockito.when(dishPersistencePort.getDishById(dishModel.getId())).thenReturn(dishModel2);
        Mockito.when(token.getBearerToken()).thenReturn("validToken");
        Mockito.when(token.getUsuarioAutenticadoId("validToken")).thenReturn(20L);
        Mockito.when(restaurantPersistencePort.getRestaurantById(dishModel2.getRestauranteId().getId())).thenReturn(restaurantModel);

        // Act
        dishUseCase.updateDish(dishModel.getId(), dishModel);

        // Assert
        Mockito.verify(dishPersistencePort, Mockito.times(1)).getDishById(dishModel2.getId());
        Mockito.verify(token, Mockito.times(1)).getBearerToken();
        Mockito.verify(token, Mockito.times(1)).getUsuarioAutenticadoId("validToken");
        Mockito.verify(restaurantPersistencePort, Mockito.times(1)).getRestaurantById(dishModel2.getRestauranteId().getId());
        Mockito.verify(dishPersistencePort, Mockito.times(1)).saveDish(dishModel2);
        assertEquals(dishModel.getDescripcion(), dishModel2.getDescripcion());
        assertEquals(dishModel.getPrecio(), dishModel2.getPrecio());
    }

    @Test
    public void updateEnableDisableDish() {
        Long idDish = 1L;
        Long activoInactivo = 1L;
        DishModel dishModel = new DishModel();
        dishModel.setId(idDish);
        dishModel.setActivo(false);
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setIdPropietario(2L);
        dishModel.setRestauranteId(restaurantModel);
        Mockito.when(dishPersistencePort.getDishById(idDish)).thenReturn(dishModel);
        Mockito.when(token.getBearerToken()).thenReturn("validToken");
        Mockito.when(token.getUsuarioAutenticadoId("validToken")).thenReturn(2L);
        Mockito.when(restaurantPersistencePort.getRestaurantById(restaurantModel.getId())).thenReturn(restaurantModel);

        // Act
        dishUseCase.updateEnableDisableDish(idDish, activoInactivo);

        // Assert
        Assertions.assertTrue(dishModel.getActivo());
    }

    @Test
    public void getAllDishesByRestaurant(){
        Long idRestaurante = 1L;
        Integer page = 0;
        Integer size = 10;

        List<DishModel> expectedDishes = new ArrayList<>();
        DishModel dish1 = FactoryDishDataTest.getDish();
        expectedDishes.add(dish1);
        DishModel dish2 =FactoryDishDataTest.getDish2();
        expectedDishes.add(dish2);

        Mockito.when(dishPersistencePort.findAllByRestauranteId(idRestaurante, page, size)).thenReturn(expectedDishes);

        List<DishModel> actualDishes = dishUseCase.findAllByRestauranteId(idRestaurante, page, size);

        assertEquals(expectedDishes, actualDishes);
        Mockito.verify(dishPersistencePort).findAllByRestauranteId(idRestaurante, page, size);
    }
}
