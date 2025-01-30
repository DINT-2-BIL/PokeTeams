/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.fcm.pokeTeams.enums;

/**
 *
 * @author DFran49
 */
public enum VistasControladores {
    LOGIN("controllerLogIn", "logIn", "Iniciar sesión", "Pokeball.png"),
    SIGNIN("controllerSignIn", "signIn", "Registrarse", "Pokeball.png"),
    INICIO("controllerCore", "core_v1", "PokeTeams", "Pokeball.png"),
    ENTRADAPOKEMON("controllerTarjetaPokemon", "tarjeta_pokemon_v1", "Tarjeta pokemon", ""),
    POKEMON("controllerPokedex", "emergente_pokemon_v1", "Pokemon", ""),
    ADDEDITPOKEMON("controllerAñadirPokemon", "emergente_añadir_pokemon_v1", "Añadir/editar pokemon", "Plusle.png"),
    ENTRADAEQUIPO("controllerEquipos", "tarjeta_equipo_v1", "Tarjeta equipo", ""),
    ADDEQUIPO("controllerAñadirEquipo", "popUp_añadir_equipo_v1", "Añadir equipo", "Plusle.png"),
    EDITEQUIPO("controllerEditarEquipo", "popUp_editar_equipo", "Editar equipo", "Smeargle.png"),
    EQUIPO("controllerEquipo", "emergente_añadir_equipo_v1", "Ventana de equipo", "Maushold.png"),
    ENTRADAMIEMBRO("controllerTarjetaMiembro", "tarjeta_miembro_equipo_v1", "Tarjeta miembro", ""),
    ENTRADAADDMIEMBRO("controllerTarjetaAñadirMiembro", "tarjeta_añadir_miembro_v1", "Tarjeta añadir miembro", ""),
    MIEMBRO("controllerMiembro", "emergente_miembro", "Miembro", ""),
    ADDEDITMIEMBRO("controllerAñadirMiembro", "emergente_añadir_pokemon_equipo_v1", "Añadir/editar miembro", "Plusle.png"),
    EDITNOMBRE("controllerPopUpCambioNombre", "popUp_cambiar_nombre", "Cambiar nombre", "Klink.png"),
    EDITGENERO("controllerPopUpCambioGenero", "popUp_cambiar_genero", "Cambiar género", "Klink.png"),
    EDITCONTRASEÑA("controllerPopUpCambioContraseña", "popUp_cambiar_contraseña", "Cambiar contraseña", "Klink.png"),
    ELIMINAR("controllerEliminar", "popUp_eliminar", "Eliminar ", "Trubbish.png"),
    CONFIRMAR("controllerConfirmar", "popUp_confirmar_cambios", "Confirmar", "Victini.png");
    
    private final String controlador;
    private final String vista;
    private final String titulo;
    private final String rutaIcon;

    private VistasControladores(String controlador, String vista, String titulo, String rutaIcon) {
        this.controlador = controlador;
        this.vista = vista;
        this.titulo = titulo;
        this.rutaIcon = rutaIcon;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getRutaIcon() {
        return rutaIcon;
    }

    public String getControlador() {
        return controlador;
    }

    public String getVista() {
        return vista;
    }
}
