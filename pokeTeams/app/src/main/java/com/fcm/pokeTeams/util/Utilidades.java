/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fcm.pokeTeams.util;

import com.fcm.pokeTeams.modelos.EVsEnvoltorio;
import com.fcm.pokeTeams.modelos.EstadisticasEnvoltorio;
import com.fcm.pokeTeams.modelos.Habilidad;
import com.fcm.pokeTeams.modelos.HabilidadesEnvoltorio;
import com.fcm.pokeTeams.modelos.IVsEnvoltorio;
import com.fcm.pokeTeams.modelos.Miembro;
import com.fcm.pokeTeams.modelos.Movimiento;
import com.fcm.pokeTeams.modelos.MovimientoEnvoltorio;
import com.fcm.pokeTeams.modelos.Pokemon;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import org.controlsfx.validation.ValidationSupport;

/**
 *
 * @author DFran49
 */
public class Utilidades {

    public Utilidades() {
    }
    
    public Image getImage(String b64) {
        byte[] bytesAnormal = Base64.getDecoder().decode(b64);
        ByteArrayInputStream is = new ByteArrayInputStream(bytesAnormal);
        return new Image(is);
    }
    
    public String codificarImagen(Image img) {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(img, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] imageBytes = null;
        try {
            ImageIO.write(bufferedImage, "png", baos);
            imageBytes = baos.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(Utilidades.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Base64.getEncoder().encodeToString(imageBytes);
    }
    
    public void recuperarImagenBBDD(String b64, ImageView imagen) {
        imagen.setImage(getImage(b64));
    }
    
    public void crearTooltip(String msg, Node n) {
        Tooltip tooltip = new Tooltip(msg);
        tooltip.setStyle("-fx-font-size: 24px;");
        tooltip.setShowDelay(Duration.millis(300));
        tooltip.autoFixProperty().set(true);
        tooltip.consumeAutoHidingEventsProperty().set(true);
        tooltip.hideOnEscapeProperty().set(true);
        Tooltip.install(n, tooltip);
    }
    
    public HabilidadesEnvoltorio leerHabilidades(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, HabilidadesEnvoltorio.class);
    }
    
    public String escribirStats(EstadisticasEnvoltorio e) {
        Gson gson = new Gson();
        return gson.toJson(e);
    }
    
    public String escribirHabilidades(HabilidadesEnvoltorio h) {
        Gson gson = new Gson();
        return gson.toJson(h);
    }
    
    public String escribirMovimientos(MovimientoEnvoltorio m) {
        Gson gson = new Gson();
        System.out.println(gson.toJson(m));
        return gson.toJson(m);
    }
    
    public String escribirEVs(EVsEnvoltorio i) {
        Gson gson = new Gson();
        return gson.toJson(i);
    }
    
    public String escribirIVs(IVsEnvoltorio e) {
        Gson gson = new Gson();
        return gson.toJson(e);
    }
    
    public void leerMovimientos(Miembro m, List<TextField> lt) {
        Gson gson = new Gson();
        MovimientoEnvoltorio listMovimientos = gson.fromJson(m.getMovimientos(), MovimientoEnvoltorio.class);
        List<TextField> listText = new ArrayList<>(lt);
        Movimiento movimiento = null;
        try {
            for (int i = 0; i < listMovimientos.getSize(); i++) {
                movimiento = listMovimientos.getMovimiento(i);
                listText.get(i).setText(movimiento.getMovimiento());
            }
        } catch (JsonSyntaxException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    public IVsEnvoltorio leerIVs(Miembro m) {
        Gson gson = new Gson();
        IVsEnvoltorio listIVs = gson.fromJson(m.getIvs(), IVsEnvoltorio.class);
        return listIVs;
    }
    
    public EVsEnvoltorio leerEVs(Miembro m) {
        Gson gson = new Gson();
        EVsEnvoltorio listEVs = gson.fromJson(m.getEvs(), EVsEnvoltorio.class);
        return listEVs;
    }
    
    //public ValidationSupport crearValidacion() {}
}
