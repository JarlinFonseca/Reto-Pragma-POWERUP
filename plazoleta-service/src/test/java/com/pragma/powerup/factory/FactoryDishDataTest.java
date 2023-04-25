package com.pragma.powerup.factory;

import com.pragma.powerup.domain.model.CategoryModel;
import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.domain.model.RestaurantModel;

public class FactoryDishDataTest {

    public static DishModel getDish(){
        DishModel dishModel = new DishModel();
        dishModel.setId(1L);
        dishModel.setNombre("Pollo Frito");
        dishModel.setPrecio("73000");
        dishModel.setDescripcion("Sabroso chicken");
        dishModel.setUrlImagen("URL");
        dishModel.setActivo(true);
        dishModel.setRestauranteId(getRestaurante());
        dishModel.setCategoriaId(getCategory());
        return  dishModel;
    }

    public static DishModel getDish2(){
        DishModel dishModel = new DishModel();
        dishModel.setId(2L);
        dishModel.setNombre("Plato Tradicional");
        dishModel.setPrecio("23000");
        dishModel.setDescripcion("Gran plato");
        dishModel.setUrlImagen("URL");
        dishModel.setActivo(true);
        dishModel.setRestauranteId(getRestaurante());
        dishModel.setCategoriaId(getCategory());
        return  dishModel;
    }

    public static RestaurantModel getRestaurante(){
        RestaurantModel restaurantModel= new RestaurantModel();
        restaurantModel.setId(1L);
        restaurantModel.setNombre("Frisby");
        restaurantModel.setNit("1654144544");
        restaurantModel.setDireccion("Unicentro");
        restaurantModel.setTelefono("3126544545");
        restaurantModel.setUrlLogo("URL");
        restaurantModel.setIdPropietario(20L);

        return restaurantModel;
    }

    public static CategoryModel getCategory(){
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setId(1L);
        categoryModel.setNombre("Comidas rapidas");
        categoryModel.setDescripcion("Rapidas");
        return categoryModel;
    }


}
