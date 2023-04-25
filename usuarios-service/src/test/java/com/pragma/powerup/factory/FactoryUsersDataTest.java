package com.pragma.powerup.factory;

import com.pragma.powerup.domain.model.Rol;
import com.pragma.powerup.domain.model.Usuario;
import org.apache.catalina.User;

public class FactoryUsersDataTest {

    public static Usuario getUsuario(){
        Usuario usuario= new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Jarlin");
        usuario.setApellido("Fonseca");
        usuario.setCelular("+573238123367");
        usuario.setDocumentoDeIdentidad("1006287478");
        usuario.setCorreo("jarlin@gmail.com");
        usuario.setClave("password");
       // usuario.setRol(getRol());
        return usuario;
    }

    public static Usuario getUsuarioEmployee(){
        Usuario usuario= new Usuario();
        usuario.setId(2L);
        usuario.setNombre("Juan");
        usuario.setApellido("Sanchez");
        usuario.setCelular("+573251234565");
        usuario.setDocumentoDeIdentidad("955455211");
        usuario.setCorreo("juan@gmail.com");
        usuario.setClave("password");
        return usuario;
    }

    public static Usuario getUsuarioClient(){
        Usuario usuario= new Usuario();
        usuario.setId(3L);
        usuario.setNombre("James");
        usuario.setApellido("Rodriguez");
        usuario.setCelular("+573120231412");
        usuario.setDocumentoDeIdentidad("356554544");
        usuario.setCorreo("james@gmail.com");
        usuario.setClave("password");
        usuario.setRol(new Rol());
        return usuario;
    }

    public static Rol getRol(){
        Rol rol = new Rol();
        rol.setId(1L);
        rol.setNombre("ADMIN");
        rol.setDescripcion("Rol de Admin");
        return  rol;
    }
}
