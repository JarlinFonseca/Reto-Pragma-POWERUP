package com.pragma.powerup.domain.usecase;


import com.pragma.powerup.domain.api.IOrderServicePort;
import com.pragma.powerup.domain.exception.ClientAuthMustBeEqualsClientOrderException;
import com.pragma.powerup.domain.exception.ClientHasAnOrderException;
import com.pragma.powerup.domain.exception.DishIsInactiveException;
import com.pragma.powerup.domain.exception.DishNotExistException;
import com.pragma.powerup.domain.exception.DishRestaurantIdNotIsEqualsOrderException;
import com.pragma.powerup.domain.exception.NoCancelOrderStatusCanceladoException;
import com.pragma.powerup.domain.exception.NoCancelOrdersPreparacionOrListoException;
import com.pragma.powerup.domain.exception.NoDataFoundException;
import com.pragma.powerup.domain.exception.OnlyCancelOrderStatusPendienteException;
import com.pragma.powerup.domain.exception.OrderNotExistException;
import com.pragma.powerup.domain.exception.OrderRestaurantMustBeEqualsEmployeeRestaurantException;
import com.pragma.powerup.domain.exception.OwnerNotAuthenticatedException;
import com.pragma.powerup.domain.exception.PinNotIsEqualsException;
import com.pragma.powerup.domain.exception.RestaurantEmployeeNotExistException;
import com.pragma.powerup.domain.exception.RestaurantNotExistException;
import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.domain.model.OrderDishModel;
import com.pragma.powerup.domain.model.OrderModel;
import com.pragma.powerup.domain.model.RestaurantEmployeeModel;
import com.pragma.powerup.domain.model.RestaurantModel;
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
//import com.pragma.powerup.infrastructure.exception.NoDataFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class OrderUseCase implements IOrderServicePort {

    private  final IOrderPersistencePort orderPersistencePort;

    private final IToken token;

    private final IRestaurantPersistencePort restaurantPersistencePort;

    private final IDishPersistencePort dishPersistencePort;

    private final IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort;

    private  final ITwilioFeignClientPort twilioFeignClientPort;

    private final IUserFeignClientPort userFeignClientPort;

    public OrderUseCase(IOrderPersistencePort orderPersistencePort, IToken token, IRestaurantPersistencePort restaurantPersistencePort, IDishPersistencePort dishPersistencePort, IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort, ITwilioFeignClientPort twilioFeignClientPort, IUserFeignClientPort userFeignClientPort) {
        this.orderPersistencePort = orderPersistencePort;
        this.token = token;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.dishPersistencePort = dishPersistencePort;
        this.restaurantEmployeePersistencePort = restaurantEmployeePersistencePort;
        this.twilioFeignClientPort = twilioFeignClientPort;
        this.userFeignClientPort = userFeignClientPort;
    }

    @Override
    public void saveOrder(OrderRequestModel orderRequestModel) {
        Date date = new Date();
        String bearerToken = token.getBearerToken();
        if(bearerToken==null) throw new OwnerNotAuthenticatedException();
        Long idClientAuth = token.getUsuarioAutenticadoId(bearerToken);

        List<String> estados = List.of("PENDIENTE", "EN_PREPARACION", "LISTO");

        if(orderPersistencePort.existsByIdClienteAndEstado(idClientAuth, estados.get(0)) ||
           orderPersistencePort.existsByIdClienteAndEstado(idClientAuth, estados.get(1)) ||
           orderPersistencePort.existsByIdClienteAndEstado(idClientAuth, estados.get(2))) throw new ClientHasAnOrderException();

        Long idRestaurante = orderRequestModel.getResturanteId();

        RestaurantModel restaurantModel= restaurantPersistencePort.getRestaurantById(idRestaurante);
        if(restaurantModel==null) throw new RestaurantNotExistException();
        OrderModel orderModel2 = new OrderModel(-1L, idClientAuth,date,"PENDIENTE",null,restaurantModel);

        List<OrderDishRequestModel> orderDishes = orderRequestModel.getPlatos();
        if(orderDishes.isEmpty()){
            throw new NoDataFoundException();
        }
        for (int i=0; i<orderDishes.size();i++) {
            DishModel dishModel = dishPersistencePort.getDishById(orderDishes.get(i).getIdPlatos());
            if (dishModel == null) throw new DishNotExistException();
            if (dishModel.getRestauranteId().getId() != orderModel2.getRestaurante().getId()) throw new DishRestaurantIdNotIsEqualsOrderException();
            if(!dishModel.getActivo()) throw new DishIsInactiveException();
        }
        OrderModel order =orderPersistencePort.saveOrder(orderModel2);


        List<OrderDishModel> orderDishesEmpty = new ArrayList<>();
        for (int i=0; i<orderDishes.size();i++){
            DishModel dishModel= dishPersistencePort.getDishById(orderDishes.get(i).getIdPlatos());
                OrderDishModel orderDish = new OrderDishModel(-1L, order,dishModel, String.valueOf(orderDishes.get(i).getCantidad()));
                orderDishesEmpty.add(orderDish);
        }

        orderPersistencePort.saveOrderDish(orderDishesEmpty);
    }

    @Override
    public List<OrderResponseModel> getAllOrdersWithPagination(Integer page, Integer size, String estado) {
        String bearerToken = token.getBearerToken();
        if(bearerToken==null) throw new OwnerNotAuthenticatedException();
        Long idEmployeeAuth = token.getUsuarioAutenticadoId(bearerToken);
        RestaurantEmployeeModel restaurantEmployeeModel= restaurantEmployeePersistencePort.findByPersonId(String.valueOf(idEmployeeAuth));

        List<OrderResponseModel> listaPedidosResponse = new ArrayList<>();
        Long restauranteId = Long.parseLong(restaurantEmployeeModel.getRestaurantId());
        List<OrderModel> pedidos = orderPersistencePort.getAllOrdersWithPagination(page, size,restauranteId ,estado);

        for (int i=0; i<pedidos.size();i++){
            OrderResponseModel orderResponseModel = new OrderResponseModel();
            orderResponseModel.setId(pedidos.get(i).getId());
            orderResponseModel.setIdCliente(pedidos.get(i).getIdCliente());
            if(pedidos.get(i).getChef()==null) orderResponseModel.setIdChef(null);
            else orderResponseModel.setIdChef(pedidos.get(i).getChef().getId());
            orderResponseModel.setFecha(pedidos.get(i).getFecha());
            orderResponseModel.setPedidoPlatos(new ArrayList<>());

            List<OrderDishModel>  pedidoPlatos = orderPersistencePort.getAllOrdersByPedido(pedidos.get(i).getId());
            for (int k=0; k<pedidoPlatos.size(); k++){
                DishModel dishModel= dishPersistencePort.getDishById(pedidoPlatos.get(k).getPlato().getId());
                 OrderDishResponseModel orderDishResponseModel = new OrderDishResponseModel();
                 orderDishResponseModel.setId(dishModel.getId());
                 orderDishResponseModel.setNombre(dishModel.getNombre());
                 orderDishResponseModel.setPrecio(dishModel.getPrecio());
                 orderDishResponseModel.setDescripcion(dishModel.getDescripcion());
                 orderDishResponseModel.setUrlImagen(dishModel.getUrlImagen());
                 orderDishResponseModel.setCategoriaId(dishModel.getCategoriaId());
                 orderDishResponseModel.setCantidad(pedidoPlatos.get(k).getCantidad());

                orderResponseModel.getPedidoPlatos().add(orderDishResponseModel);
            }
             listaPedidosResponse.add(orderResponseModel);
        }
         return listaPedidosResponse;
    }

    @Override
    public void takeOrderAndUpdateStatus(Long idOrder, String estado) {
        if(!estado.equals("EN_PREPARACION")) throw new NoDataFoundException();
        if(Boolean.FALSE.equals(orderPersistencePort.existsByIdAndEstado(idOrder, "PENDIENTE"))) throw new NoDataFoundException();

        String bearerToken = token.getBearerToken();
        if(bearerToken==null) throw new OwnerNotAuthenticatedException();
        Long idEmployeeAuth = token.getUsuarioAutenticadoId(bearerToken);
        RestaurantEmployeeModel restaurantEmployeeModel= restaurantEmployeePersistencePort.findByPersonId(String.valueOf(idEmployeeAuth));
        if(restaurantEmployeeModel==null) throw new RestaurantEmployeeNotExistException();
        OrderModel orderModel= orderPersistencePort.getOrderById(idOrder);
        if(orderModel==null) throw new OrderNotExistException();

        Long idRestaurantEmployeeAuth = Long.valueOf(restaurantEmployeeModel.getRestaurantId());
        Long idRestaurantOrder = orderModel.getRestaurante().getId();

        if(idRestaurantEmployeeAuth!=idRestaurantOrder) throw new OrderRestaurantMustBeEqualsEmployeeRestaurantException();

        orderModel.setChef(restaurantEmployeeModel);
        orderModel.setEstado(estado);

        orderPersistencePort.saveOrder(orderModel);
    }

    @Override
    public void updateAndNotifyOrderReady(Long idOrder) {
        if(Boolean.FALSE.equals(orderPersistencePort.existsByIdAndEstado(idOrder, "EN_PREPARACION"))) throw new NoDataFoundException();
        String bearerToken = token.getBearerToken();
        if(bearerToken==null) throw new OwnerNotAuthenticatedException();
        Long idEmployeeAuth = token.getUsuarioAutenticadoId(bearerToken);
        RestaurantEmployeeModel restaurantEmployeeModel= restaurantEmployeePersistencePort.findByPersonId(String.valueOf(idEmployeeAuth));
        if(restaurantEmployeeModel==null) throw new RestaurantEmployeeNotExistException();
        OrderModel orderModel= orderPersistencePort.getOrderById(idOrder);
        if(orderModel==null) throw new OrderNotExistException();

        Long idRestaurantEmployeeAuth = Long.valueOf(restaurantEmployeeModel.getRestaurantId());
        Long idRestaurantOrder = orderModel.getRestaurante().getId();

        if(idRestaurantEmployeeAuth!=idRestaurantOrder) throw new OrderRestaurantMustBeEqualsEmployeeRestaurantException();

        orderModel.setEstado("LISTO");
        orderPersistencePort.saveOrder(orderModel);

        UserModel userModel = userFeignClientPort.getUserById(orderModel.getIdCliente());
        String nombreCliente = userModel.getNombre();
        String pin = validatePin(userModel);

        String mensaje = "Buen día, señor(a) " + nombreCliente.toUpperCase() + ", su pedido ya está listo para recoger.\nRecuerda mostrar el siguiente pin " + pin + " para poder entregar tu pedido.";
        String numeroCelular = "+573238123367";
        // No coloco el celular del cliente, ya que Twilio solo deja enviar mensajes al celular de la cuenta creada
       // String numeroCel = userModel.getCelular();

        SmsMessageModel smsMessageModel = new SmsMessageModel(numeroCelular, mensaje);

        twilioFeignClientPort.sendSmsMessage(smsMessageModel);
    }

    @Override
    public void deliverOrder(Long idOrder, String pin) {
        if(Boolean.FALSE.equals(orderPersistencePort.existsByIdAndEstado(idOrder, "LISTO"))) throw new NoDataFoundException();
        String bearerToken = token.getBearerToken();
        if(bearerToken==null) throw new OwnerNotAuthenticatedException();
        Long idEmployeeAuth = token.getUsuarioAutenticadoId(bearerToken);
        RestaurantEmployeeModel restaurantEmployeeModel= restaurantEmployeePersistencePort.findByPersonId(String.valueOf(idEmployeeAuth));
        if(restaurantEmployeeModel==null) throw new RestaurantEmployeeNotExistException();
        OrderModel orderModel= orderPersistencePort.getOrderById(idOrder);
        if(orderModel==null) throw new OrderNotExistException();

        Long idRestaurantEmployeeAuth = Long.valueOf(restaurantEmployeeModel.getRestaurantId());
        Long idRestaurantOrder = orderModel.getRestaurante().getId();

        if(idRestaurantEmployeeAuth!=idRestaurantOrder) throw new OrderRestaurantMustBeEqualsEmployeeRestaurantException();

        UserModel userModel = userFeignClientPort.getUserById(orderModel.getIdCliente());
       String pin2 = validatePin(userModel);

        if(!(validatePin(userModel)).equals(pin) && !pin.equals(0)) throw new PinNotIsEqualsException();


        orderModel.setEstado("ENTREGADO");
        orderPersistencePort.saveOrder(orderModel);
    }

    @Override
    public void cancelOrder(Long idOrder) {
        String bearerToken = token.getBearerToken();
        if(bearerToken==null) throw new OwnerNotAuthenticatedException();
        Long idClientAuth = token.getUsuarioAutenticadoId(bearerToken);

        OrderModel orderModel= orderPersistencePort.getOrderById(idOrder);
        if(orderModel==null) throw new OrderNotExistException();
        Long idClientOrder = orderModel.getIdCliente();

        if(idClientAuth!=idClientOrder) throw new ClientAuthMustBeEqualsClientOrderException();
        if(orderPersistencePort.existsByIdAndEstado(idOrder, "EN_PREPARACION")|| orderPersistencePort.existsByIdAndEstado(idOrder, "LISTO") ){
            SmsMessageModel smsMessageModel= new SmsMessageModel("+573238123367","Lo sentimos, tu pedido ya está en preparación y no puede cancelarse.");
            twilioFeignClientPort.sendSmsMessage(smsMessageModel);
            throw new NoCancelOrdersPreparacionOrListoException();
        }else if(orderPersistencePort.existsByIdAndEstado(idOrder, "CANCELADO")) throw new NoCancelOrderStatusCanceladoException();
        else if(Boolean.FALSE.equals(orderPersistencePort.existsByIdAndEstado(idOrder, "PENDIENTE"))) throw new OnlyCancelOrderStatusPendienteException();


        orderModel.setEstado("CANCELADO");
        orderPersistencePort.saveOrder(orderModel);
    }

    public String validatePin(UserModel userModel){
        String pinDocumento = userModel.getDocumentoDeIdentidad();
        String pinNombre = userModel.getNombre();
        String pinApellido = userModel.getApellido();
        String pin = pinNombre.substring(pinNombre.length()-2)+pinDocumento.substring(pinDocumento.length()-4)+pinApellido.substring(pinApellido.length()-2);
        return  pin;
    }



}
