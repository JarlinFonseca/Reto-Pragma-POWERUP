package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IUsuarioServicePort;
import com.pragma.powerup.domain.exception.DomainException;
import com.pragma.powerup.domain.model.RestaurantEmployeeModel;
import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.domain.model.Rol;
import com.pragma.powerup.domain.model.Usuario;
import com.pragma.powerup.domain.spi.feignclients.IRestaurantEmployeeFeignClientPort;
import com.pragma.powerup.domain.spi.feignclients.IRestaurantFeingClientPort;
import com.pragma.powerup.domain.spi.passwordencoder.IUsuarioPasswordEncoderPort;
import com.pragma.powerup.domain.spi.persistence.IUsuarioPersistencePort;
import com.pragma.powerup.domain.spi.token.IToken;


import java.util.Arrays;
import java.util.List;


public class UsuarioUseCase implements IUsuarioServicePort {
    private final IUsuarioPersistencePort usuarioPersistencePort;

    private  final IUsuarioPasswordEncoderPort usuarioPasswordEncoderPort;

    private  final IRestaurantEmployeeFeignClientPort restaurantEmployeeFeignClientPort;

    private final IToken token;

    private final IRestaurantFeingClientPort restaurantFeingClientPort;

    public UsuarioUseCase(IUsuarioPersistencePort usuarioPersistencePort, IUsuarioPasswordEncoderPort usuarioPasswordEncoderPort, IRestaurantEmployeeFeignClientPort restaurantEmployeeFeignClientPort, IToken token, IRestaurantFeingClientPort restaurantFeingClientPort) {
        this.usuarioPersistencePort = usuarioPersistencePort;
        this.usuarioPasswordEncoderPort = usuarioPasswordEncoderPort;
        this.restaurantEmployeeFeignClientPort = restaurantEmployeeFeignClientPort;
        this.token = token;
        this.restaurantFeingClientPort = restaurantFeingClientPort;
    }

    @Override
    public void saveUser(Usuario usuario) {
        validateRolesAuthAndNot(usuario);
        usuario.setClave(usuarioPasswordEncoderPort.encode(usuario.getClave()));
        usuarioPersistencePort.saveUser(usuario);
    }

    private void validateRolesAuthAndNot(Usuario usuario){
        String bearerToken = token.getBearerToken();
        Rol rol = new Rol();
        String rolS = "";
        if(!(bearerToken==null)) {

            rolS = token.getUsuarioAutenticadoRol(bearerToken);
            System.out.println(rolS);
        }

        if(rolS.equals("PROPIETARIO")){
            //Puede crear empleados
            rol.setId(3L);
        }else if(rolS.equals("ADMIN")){
            //Puede crear propietarios
            rol.setId(2L);
        }else{
            if(usuario.getRol().getId()==null){
                rol.setId(4L);
            }else
            if(usuario.getRol().getId()==1){
                //Si entra aqui, se registra un ADMIN
                System.out.println("Se esta registrando un ADMIN");
            }

        }
        //Si el Rol no es nulo, puede setearse al usuario, (Se valida ya que al ADMIN solo se le pasa el rol en el body del JSON)
        if(!(rol.getId()==null)){
            usuario.setRol(rol);
        }
    }

    @Override
    public void saveRestaurantEmployee(Usuario usuario) {
        RestaurantEmployeeModel restaurantEmployeeModel = new RestaurantEmployeeModel();
        String bearerToken = token.getBearerToken();
        Long idOwnerAuth = token.getUsuarioAutenticadoId(bearerToken);

        RestaurantModel restaurantModel = restaurantFeingClientPort.getRestaurantByIdPropietario(idOwnerAuth);
        String restaurantId = String.valueOf(restaurantModel.getId());
        System.out.println(restaurantId);
        String employee_id = String.valueOf(usuarioPersistencePort.getUserByCorreo(usuario.getCorreo()).getId());
        System.out.println(employee_id);

        restaurantEmployeeModel.setRestaurantId(restaurantId);
        restaurantEmployeeModel.setPersonId(employee_id);
        restaurantEmployeeFeignClientPort.saveRestaurantEmployee(restaurantEmployeeModel);

    }

    @Override
    public Usuario getUserById(Long id) {
        return usuarioPersistencePort.getUserById(id);
    }

    @Override
    public Usuario getUserByCorreo(String correo) {
        return usuarioPersistencePort.getUserByCorreo(correo);
    }

    @Override
    public Boolean existsUserById(Long id) {
        return usuarioPersistencePort.existsUserById(id);
    }


    @Override
    public List<Usuario> getAllUsers() {
        return usuarioPersistencePort.getAllUsers();
    }

    @Override
    public void deleteUserById(Long id) {
        usuarioPersistencePort.deleteUserById(id);
    }

}
