package com.nexusvault.msusers.service;

import com.nexusvault.msusers.model.UserModel;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define las reglas de negocio para la administración de los perfiles de usuario.
 */
public interface UserService {
    /**
     * Recupera todos los perfiles de usuario del sistema.
     *
     * @return Una lista de entidades {@link UserModel}.
     */
    List<UserModel> obtenerTodosLosPerfiles();
    
    /**
     * Obtiene el perfil de un usuario a partir de su ID de autenticación remoto.
     *
     * @param authId El ID de autenticación del usuario.
     * @return Un {@link Optional} con el perfil del usuario.
     */
    Optional<UserModel> obtenerPorAuthId(Long authId);
    
    /**
     * Obtiene el perfil de un usuario a partir de su apodo o nickname.
     *
     * @param nickname El nickname del usuario.
     * @return Un {@link Optional} con el perfil del usuario.
     */
    Optional<UserModel> obtenerPorNickname(String nickname);
    
    /**
     * Registra y crea un nuevo perfil de usuario en el sistema.
     *
     * @param userModel El objeto con los datos del nuevo perfil.
     * @return El perfil persistido y creado en la base de datos.
     */
    // Nuevos métodos para completar el CRUD
    UserModel crearPerfil(UserModel userModel);
    
    /**
     * Actualiza la información de un perfil de usuario existente.
     *
     * @param id El identificador del perfil a actualizar.
     * @param userModel Los datos a modificar.
     * @return Un {@link Optional} con el perfil de usuario actualizado.
     */
    Optional<UserModel> actualizarPerfil(Long id, UserModel userModel);
    
    /**
     * Elimina físicamente un perfil de usuario mediante su identificador primario.
     *
     * @param id El ID del perfil a remover.
     */
    void eliminarPerfil(Long id);
}