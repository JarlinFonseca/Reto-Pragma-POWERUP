package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.exception.NoCancelOrdersPreparacionOrListoException;
import com.pragma.powerup.domain.exception.OrderRestaurantMustBeEqualsEmployeeRestaurantException;
import com.pragma.powerup.domain.exception.RestaurantEmployeeNotExistException;
import com.pragma.powerup.domain.model.CategoryModel;
import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.domain.model.OrderDishModel;
import com.pragma.powerup.domain.model.OrderModel;
import com.pragma.powerup.domain.model.RestaurantEmployeeModel;
import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.domain.model.RolModel;
import com.pragma.powerup.domain.model.SmsMessageModel;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.model.orders.OrderDishRequestModel;
import com.pragma.powerup.domain.model.orders.OrderDishResponseModel;
import com.pragma.powerup.domain.model.orders.OrderRequestModel;
import com.pragma.powerup.domain.model.orders.OrderResponseModel;
import com.pragma.powerup.domain.spi.bearertoken.IToken;
import com.pragma.powerup.domain.spi.feignclients.ITwilioFeignClientPort;
import com.pragma.powerup.domain.spi.feignclients.IUserFeignClientPort;
import com.pragma.powerup.domain.spi.persistence.IDishPersistencePort;
import com.pragma.powerup.domain.spi.persistence.IOrderPersistencePort;
import com.pragma.powerup.domain.spi.persistence.IRestaurantEmployeePersistencePort;
import com.pragma.powerup.domain.spi.persistence.IRestaurantPersistencePort;
import com.pragma.powerup.factory.FactoryDishDataTest;
import com.pragma.powerup.domain.exception.NoDataFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class OrderUseCaseTest {

    @InjectMocks
    OrderUseCase orderUseCase;

    @Mock
    IOrderPersistencePort orderPersistencePort;

    @Mock
    IToken token;

    @Mock
    IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    IDishPersistencePort dishPersistencePort;

    @Mock
    IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort;

    @Mock
    ITwilioFeignClientPort twilioFeignClientPort;

    @Mock
    IUserFeignClientPort userFeignClientPort;

    @Test
    void saveOrder() {
        OrderRequestModel orderRequestModel = new OrderRequestModel();
        orderRequestModel.setResturanteId(1L);
        List<OrderDishRequestModel> orderDishes = new ArrayList<>();
        OrderDishRequestModel orderDishRequestModel = new OrderDishRequestModel();
        orderDishRequestModel.setIdPlatos(1L);
        orderDishRequestModel.setCantidad(2L);
        orderDishes.add(orderDishRequestModel);
        orderRequestModel.setPlatos(orderDishes);


        Mockito.when(token.getBearerToken()).thenReturn("BearerToken");
        Mockito.when(token.getUsuarioAutenticadoId("BearerToken")).thenReturn(1L);

        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setId(1L);
        Mockito.when(restaurantPersistencePort.getRestaurantById(ArgumentMatchers.anyLong())).thenReturn(restaurantModel);

        DishModel dishModel = new DishModel();
        dishModel.setId(1L);
        dishModel.setActivo(true);
        dishModel.setRestauranteId(restaurantModel);
        Mockito.when(dishPersistencePort.getDishById(ArgumentMatchers.anyLong())).thenReturn(dishModel);

        Mockito.when(orderPersistencePort.saveOrder(ArgumentMatchers.any(OrderModel.class))).thenReturn(new OrderModel());

        // Act
        orderUseCase.saveOrder(orderRequestModel);

        // Assert
        Mockito.verify(orderPersistencePort, Mockito.times(1)).existsByIdClienteAndEstado(1L, "PENDIENTE");
        Mockito.verify(orderPersistencePort, Mockito.times(1)).existsByIdClienteAndEstado(1L, "EN_PREPARACION");
        Mockito.verify(orderPersistencePort, Mockito.times(1)).existsByIdClienteAndEstado(1L, "LISTO");
        Mockito.verify(orderPersistencePort, Mockito.times(1)).saveOrder(ArgumentMatchers.any(OrderModel.class));
        Mockito.verify(orderPersistencePort, Mockito.times(1)).saveOrderDish(ArgumentMatchers.anyList());
    }

    @Test
    void MustSaveAnOrder(){
        // Mock token to return a valid bearer token
        Mockito.when(token.getBearerToken()).thenReturn("validBearerToken");
        // Mock token to return a valid user id for the authenticated user
        Mockito.when(token.getUsuarioAutenticadoId("validBearerToken")).thenReturn(1L);
        // Mock restaurantPersistencePort to return a valid restaurant model
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setId(1L);
        Mockito.when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(restaurantModel);
        // Mock dishPersistencePort to return a valid dish model
        DishModel dishModel = new DishModel();
        dishModel.setId(1L);
        dishModel.setActivo(true);
        dishModel.setRestauranteId(restaurantModel);
        Mockito.when(dishPersistencePort.getDishById(1L)).thenReturn(dishModel);
        // Mock orderPersistencePort to return null when checking for existing orders
        Mockito.when(orderPersistencePort.existsByIdClienteAndEstado(Mockito.any(Long.class), Mockito.any(String.class))).thenReturn(false);
        // Mock orderPersistencePort to return a saved order model when saving an order
        OrderModel savedOrderModel = new OrderModel();
        savedOrderModel.setId(1L);
        Mockito.when(orderPersistencePort.saveOrder(Mockito.any(OrderModel.class))).thenReturn(savedOrderModel);

        // Create a valid OrderRequestModel
        OrderRequestModel orderRequestModel = new OrderRequestModel();
        orderRequestModel.setResturanteId(1L);
        List<OrderDishRequestModel> orderDishes = new ArrayList<>();
        OrderDishRequestModel orderDish = new OrderDishRequestModel();
        orderDish.setIdPlatos(1L);
        orderDish.setCantidad(1L);

        orderDishes.add(orderDish);
        orderRequestModel.setPlatos(orderDishes);

        // Call the saveOrder method and assert that it saved the order
        orderUseCase.saveOrder(orderRequestModel);

        Mockito.verify(orderPersistencePort, Mockito.times(1)).existsByIdClienteAndEstado(1L, "PENDIENTE");
        Mockito.verify(orderPersistencePort, Mockito.times(1)).existsByIdClienteAndEstado(1L, "EN_PREPARACION");
        Mockito.verify(orderPersistencePort, Mockito.times(1)).existsByIdClienteAndEstado(1L, "LISTO");
        Mockito.verify(orderPersistencePort, Mockito.times(1)).saveOrder(ArgumentMatchers.any(OrderModel.class));
        Mockito.verify(orderPersistencePort, Mockito.times(1)).saveOrderDish(ArgumentMatchers.anyList());
    }


    @Test
    void getAllOrdersWithPagination() {
        String bearerToken = "BearerToken";
        Long idEmployeeAuth = 1234L;
        RestaurantEmployeeModel restaurantEmployeeModel = new RestaurantEmployeeModel();
        restaurantEmployeeModel.setRestaurantId("5678");
        List<OrderModel> orders = new ArrayList<>();
        OrderModel order1 = new OrderModel();
        order1.setId(1L);
        order1.setIdCliente(999L);
        RestaurantEmployeeModel chef = new RestaurantEmployeeModel();
        chef.setId(5678L);
        order1.setChef(chef);
        order1.setFecha(new Date());
        orders.add(order1);
        List<OrderDishModel> orderDishes = new ArrayList<>();
        OrderDishModel orderDish1 = new OrderDishModel();

        DishModel dish = new DishModel();
        CategoryModel categoryModel= FactoryDishDataTest.getCategory();
        categoryModel.setId(1L);
        categoryModel.setNombre("Almuerzo");
        categoryModel.setDescripcion("Almuerzo OK");
        dish.setId(123L);
        dish.setNombre("Pastas Bolañensas");
        dish.setPrecio("25000");
        dish.setDescripcion("Pastas buenas");
        dish.setUrlImagen("pastasURL");
        dish.setCategoriaId(categoryModel);

        orderDish1.setId(1L);
        orderDish1.setCantidad("2");
        orderDish1.setPlato(dish);

        orderDishes.add(orderDish1);


        Mockito.when(token.getBearerToken()).thenReturn(bearerToken);
        Mockito.when(token.getUsuarioAutenticadoId(bearerToken)).thenReturn(idEmployeeAuth);
        Mockito.when(restaurantEmployeePersistencePort.findByPersonId(String.valueOf(idEmployeeAuth))).thenReturn(restaurantEmployeeModel);
        Mockito.when(orderPersistencePort.getAllOrdersWithPagination(1, 10, Long.parseLong(restaurantEmployeeModel.getRestaurantId()), "PENDIENTE")).thenReturn(orders);
        Mockito.when(orderPersistencePort.getAllOrdersByPedido(order1.getId())).thenReturn(orderDishes);
        Mockito.when(dishPersistencePort.getDishById(orderDish1.getPlato().getId())).thenReturn(dish);

        // call the method under test
        List<OrderResponseModel> result = orderUseCase.getAllOrdersWithPagination(1, 10, "PENDIENTE");

        // verify the result
        assertEquals(1, result.size());
        OrderResponseModel orderResponseModel = result.get(0);
        assertEquals(order1.getId(), orderResponseModel.getId());
        assertEquals(order1.getIdCliente(), orderResponseModel.getIdCliente());
        assertEquals(order1.getChef().getId(), orderResponseModel.getIdChef());
        assertEquals(order1.getFecha(), orderResponseModel.getFecha());
        assertEquals(1, orderResponseModel.getPedidoPlatos().size());
        OrderDishResponseModel orderDishResponseModel = orderResponseModel.getPedidoPlatos().get(0);
        assertEquals(dish.getId(), orderDishResponseModel.getId());
        assertEquals(dish.getNombre(), orderDishResponseModel.getNombre());
        assertEquals(dish.getPrecio(), orderDishResponseModel.getPrecio());
        assertEquals(dish.getDescripcion(), orderDishResponseModel.getDescripcion());
        assertEquals(dish.getUrlImagen(), orderDishResponseModel.getUrlImagen());
        assertEquals(dish.getCategoriaId(), orderDishResponseModel.getCategoriaId());
        assertEquals(orderDish1.getCantidad(), orderDishResponseModel.getCantidad());
    }

    @Test
    void takeOrderAndUpdateStatus() {
        Long idOrder = 1L;
        String estado = "EN_PREPARACION";

        Mockito.when(orderPersistencePort.existsByIdAndEstado(idOrder, "PENDIENTE")).thenReturn(Boolean.TRUE);
        Mockito.when(token.getBearerToken()).thenReturn("token");
        Mockito.when(token.getUsuarioAutenticadoId("token")).thenReturn(2L);

        OrderModel orderModel = new OrderModel();
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setId(3L);
        orderModel.setRestaurante(restaurantModel);
        Mockito.when(orderPersistencePort.getOrderById(idOrder)).thenReturn(orderModel);

        RestaurantEmployeeModel employeeModel = new RestaurantEmployeeModel();
        employeeModel.setPersonId("2");
        employeeModel.setRestaurantId(String.valueOf(restaurantModel.getId()));
        Mockito.when(restaurantEmployeePersistencePort.findByPersonId(employeeModel.getPersonId())).thenReturn(employeeModel);

        // Act
        orderUseCase.takeOrderAndUpdateStatus(idOrder, estado);

        // Assert
        Mockito.verify(orderPersistencePort, Mockito.times(1)).existsByIdAndEstado(idOrder, "PENDIENTE");
        Mockito.verify(orderPersistencePort, Mockito.times(1)).getOrderById(idOrder);
        Mockito.verify(orderPersistencePort, Mockito.times(1)).saveOrder(orderModel);
    }

    @Test
    public void takeOrderAndUpdateStatus_whenEstadoIsNotEnPreparacion_thenThrowNoDataFoundException() {
        // Arrange
        Long idOrder = 1L;
        String estado = "PENDIENTE";

        // Act and Assert
        assertThrows(NoDataFoundException.class, () -> {
            orderUseCase.takeOrderAndUpdateStatus(idOrder, estado);
        });
    }

    @Test
    public void takeOrderAndUpdateStatus_whenOrderNotExists_thenThrowOrderNotExistException() {
        Long idOrder = 1L;
        String estado = "EN_PREPARACION";

        Mockito.when(orderPersistencePort.existsByIdAndEstado(idOrder, "PENDIENTE")).thenReturn(Boolean.FALSE);

        // Act and Assert
        assertThrows(NoDataFoundException.class, () -> {
            orderUseCase.takeOrderAndUpdateStatus(idOrder, estado);
        });
    }

    @Test
    public void takeOrderAndUpdateStatus_whenEmployeeNotExists_thenThrowRestaurantEmployeeNotExistException() {
        // Arrange
        Long idOrder = 1L;
        String estado = "EN_PREPARACION";

        Mockito.when(orderPersistencePort.existsByIdAndEstado(idOrder, "PENDIENTE")).thenReturn(Boolean.TRUE);
        Mockito.when(token.getBearerToken()).thenReturn("token");
        Mockito.when(token.getUsuarioAutenticadoId("token")).thenReturn(2L);
        Mockito.when(restaurantEmployeePersistencePort.findByPersonId("2")).thenReturn(null);

        // Act and Assert
        assertThrows(RestaurantEmployeeNotExistException.class, () -> {
            orderUseCase.takeOrderAndUpdateStatus(idOrder, estado);
        });
    }

    @Test
    public void takeOrderAndUpdateStatus_whenOrderRestaurantNotEqualsEmployeeRestaurant_thenThrowOrderRestaurantMustBeEqualsEmployeeRestaurantException() {
        // Arrange
        Long idOrder = 1L;
        String estado = "EN_PREPARACION";

        Mockito.when(orderPersistencePort.existsByIdAndEstado(idOrder, "PENDIENTE")).thenReturn(Boolean.TRUE);
        Mockito.when(token.getBearerToken()).thenReturn("token");
        Mockito.when(token.getUsuarioAutenticadoId("token")).thenReturn(2L);

        RestaurantEmployeeModel employeeModel = new RestaurantEmployeeModel();
        employeeModel.setPersonId("2");
        employeeModel.setRestaurantId("4");
        Mockito.when(restaurantEmployeePersistencePort.findByPersonId(employeeModel.getPersonId())).thenReturn(employeeModel);

        OrderModel orderModel = new OrderModel();
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setId(3L);
        orderModel.setRestaurante(restaurantModel);
        Mockito.when(orderPersistencePort.getOrderById(idOrder)).thenReturn(orderModel);

        // Act and Assert
        assertThrows(OrderRestaurantMustBeEqualsEmployeeRestaurantException.class, () -> {
            orderUseCase.takeOrderAndUpdateStatus(idOrder, estado);
        });
    }

    @Test
    void updateAndNotifyOrderReady() {
        Long idOrder = 1L;

        // Mocking the dependencies of the method
        Mockito.when(orderPersistencePort.existsByIdAndEstado(idOrder, "EN_PREPARACION")).thenReturn(true);
        Mockito.when(token.getBearerToken()).thenReturn("token");
        Mockito.when(token.getUsuarioAutenticadoId("token")).thenReturn(1L);
        Mockito.when(restaurantEmployeePersistencePort.findByPersonId("1")).thenReturn(new RestaurantEmployeeModel(1L,"1","70"));

        OrderModel orderModel = new OrderModel();
        orderModel.setId(1L);
        orderModel.setIdCliente(1L);
        orderModel.setFecha(new Date());
        orderModel.setRestaurante(new RestaurantModel(1L,"La Nota", "656645", "Jardin Plaza", "+57321212321", "url", 80L));
        orderModel.setChef(new RestaurantEmployeeModel(1L,"1","70"));
        orderModel.setEstado("EN_PREPARACION");
        Mockito.when(orderPersistencePort.getOrderById(idOrder)).thenReturn(orderModel);

        UserModel userModel = new UserModel(1L,"Juan","Larxo","5454565","+573211444","juan@gmail.com","password",new RolModel(4L,"CLIENTE","Cliente"));
        Mockito.when(userFeignClientPort.getUserById(1L)).thenReturn(userModel);

      //  Mockito.when(twilioFeignClientPort.sendSmsMessage(Mockito.any(SmsMessageModel.class))).thenReturn(void);

        // Calling the method under test
        orderUseCase.updateAndNotifyOrderReady(idOrder);

        // Verifying that the method called the dependencies with the expected parameters
        Mockito.verify(orderPersistencePort).existsByIdAndEstado(idOrder, "EN_PREPARACION");
        Mockito.verify(token).getBearerToken();
        Mockito.verify(token).getUsuarioAutenticadoId("token");
        Mockito.verify(restaurantEmployeePersistencePort).findByPersonId("1");
        Mockito. verify(orderPersistencePort).getOrderById(idOrder);
        Mockito.verify(orderPersistencePort).saveOrder(Mockito.any(OrderModel.class));
        Mockito.verify(userFeignClientPort).getUserById(1L);
        Mockito.verify(twilioFeignClientPort).sendSmsMessage(Mockito.any(SmsMessageModel.class));
    }

    @Test
    void deliverOrder() {
        Long idOrder = 1L;
        String pin = "se8552ez";
        RestaurantModel restaurantModel= FactoryDishDataTest.getRestaurante();
        restaurantModel.setId(7L);

        OrderModel orderModel = new OrderModel();
         orderModel.setId(1L);
         orderModel.setEstado("LISTO");
         orderModel.setFecha(new Date());
         orderModel.setIdCliente(1L);
         orderModel.setRestaurante(restaurantModel);
         orderModel.setChef(new RestaurantEmployeeModel(1L,"7", "70"));
        UserModel userModel = new UserModel();
        userModel.setId(1L);
        userModel.setNombre("Jose");
        userModel.setApellido("Fernandez");
        userModel.setCelular("+573212124565");
        userModel.setCorreo("jose@gmail.com");
        userModel.setDocumentoDeIdentidad("1098848552");
        userModel.setClave("password");
        userModel.setRol(new RolModel(4L, "CLIENTE", "Cliente"));
        Mockito.when(orderPersistencePort.existsByIdAndEstado(idOrder, "LISTO")).thenReturn(true);
        Mockito.when(token.getBearerToken()).thenReturn("token");
        Mockito.when(token.getUsuarioAutenticadoId("token")).thenReturn(1L);
        Mockito.when(restaurantEmployeePersistencePort.findByPersonId("1")).thenReturn(new RestaurantEmployeeModel(1L,"7", "70"));
        Mockito.when(orderPersistencePort.getOrderById(idOrder)).thenReturn(orderModel);
        Mockito.when(userFeignClientPort.getUserById(orderModel.getIdCliente())).thenReturn(userModel);
        OrderUseCase orderUseCaseMock = Mockito.mock(OrderUseCase.class);
        Mockito.when(orderUseCaseMock.validatePin(userModel)).thenReturn("se8552ez");
        // Execution
        orderUseCase.deliverOrder(idOrder, pin);

        // Verification
        assertEquals(7L, orderModel.getRestaurante().getId());
        Mockito.verify(orderPersistencePort).existsByIdAndEstado(idOrder, "LISTO");
        Mockito.verify(token).getBearerToken();
        Mockito.verify(token).getUsuarioAutenticadoId("token");
        Mockito.verify(restaurantEmployeePersistencePort).findByPersonId("1");
        Mockito.verify(orderPersistencePort).getOrderById(idOrder);
        Mockito.verify(userFeignClientPort).getUserById(orderModel.getIdCliente());
        Mockito.verify(orderPersistencePort).saveOrder(orderModel);
    }

    @Test
    void cancelOrder() {
        Long idOrder = 1L;
        Long idClientAuth = 1L;

        // Mocking Token
        Mockito.when(token.getBearerToken()).thenReturn("token");
        Mockito.when(token.getUsuarioAutenticadoId("token")).thenReturn(idClientAuth);

        // Mocking Order
        OrderModel orderModel = new OrderModel();
        orderModel.setId(idOrder);
        orderModel.setIdCliente(idClientAuth);
        orderModel.setEstado("PENDIENTE");
        Mockito.when(orderPersistencePort.getOrderById(idOrder)).thenReturn(orderModel);
        Mockito.when(orderPersistencePort.existsByIdAndEstado(idOrder, "PENDIENTE")).thenReturn(true);

        // Calling Method
        orderUseCase.cancelOrder(idOrder);

        // Verifying Method Calls
        Mockito.verify(orderPersistencePort, Mockito.times(1)).saveOrder(orderModel);
    }

    @Test
    public void cancelOrderExceptionStatusPreparacionOrListo() {
        Long idOrder = 1L;
        Long idClientAuth = 1L;

        // Mocking Token
        Mockito.when(token.getBearerToken()).thenReturn("testToken");
        Mockito.when(token.getUsuarioAutenticadoId("testToken")).thenReturn(idClientAuth);

        // Mocking Order
        OrderModel orderModel = new OrderModel();
        orderModel.setId(idOrder);
        orderModel.setIdCliente(idClientAuth);
        orderModel.setEstado("EN_PREPARACION");
        Mockito.when(orderPersistencePort.getOrderById(idOrder)).thenReturn(orderModel);
        Mockito.when(orderPersistencePort.existsByIdAndEstado(idOrder, "EN_PREPARACION")).thenReturn(true);

        // Calling Method and Expecting Exception
        assertThrows(NoCancelOrdersPreparacionOrListoException.class, () -> {
            orderUseCase.cancelOrder(idOrder);
        });

        // Verifying Method Calls
        Mockito.verify(orderPersistencePort, Mockito.never()).saveOrder(orderModel);
        Mockito.verify(twilioFeignClientPort, Mockito.times(1)).sendSmsMessage(Mockito.any(SmsMessageModel.class));
    }
}