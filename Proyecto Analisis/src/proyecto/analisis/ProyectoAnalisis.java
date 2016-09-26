/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto.analisis;

import java.io.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 *
 * @author Oscar Ordoñez
 */
public class ProyectoAnalisis {
    static int camiones = 0;
    static List<Double> x = new ArrayList();
    static List<Double> y = new ArrayList();
    static List<Double> distancias = new ArrayList();
    static List<Double> distanciasCamiones = new ArrayList();
    static double [][] matriz ;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        leerEntrada();
        matriz = new double[x.size()][x.size()];
        llenarMatriz();
        calcularDistancia();
        double distanciaCamion = calcularDistanciaCamion();
        double contador = 0;
        for (int i = 0; i < distancias.size(); i++) {
            if (contador < distanciaCamion) {
                contador+= distancias.get(i);
            }else{
                distanciasCamiones.add(contador);
                contador = distancias.get(i);
            }
        }
        System.exit(0);
    }
   
    
    public static void leerEntrada(){
        int cont = 0;
        String[] parts;
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File("/User"));
        int result = jFileChooser.showOpenDialog(new JFrame());
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jFileChooser.getSelectedFile();
            File archivo = null;
            FileReader fr = null;
            BufferedReader br = null;
            System.out.println(selectedFile.getAbsolutePath());
            try {
                archivo = new File (selectedFile.getAbsolutePath());
                fr = new FileReader (archivo);
                br = new BufferedReader(fr);
                String linea;
                while((linea=br.readLine())!=null){
                    if (cont == 0) {
                        camiones = Integer.parseInt(linea);
                        cont++;
                    } else {
                        parts = linea.split(",");
                        x.add(Double.parseDouble(parts[0]));
                        y.add(Double.parseDouble(parts[1]));
                    }
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }finally{
                try{                    
                    if( null != fr ){   
                        fr.close();     
                    }                  
                }catch (Exception e2){ 
                    e2.printStackTrace();
                }
            }
        }
    }
    
    public static void calcularDistancia(){
        double distancia = 0;
        for (int i = 1; i < x.size(); i++) {
            distancia = Math.sqrt(Math.pow(x.get(i)-x.get(0),2) + Math.pow(y.get(i)-y.get(0), 2));
            distancias.add(distancia);
            System.out.println(distancia);
        }
    }

    public static void llenarMatriz(){
        for (int i = 0; i < x.size(); i++) {
            for (int j = 0; j < y.size(); j++) {
                if (i == j) {
                    matriz[i][j] = 0;
                }else{
                    matriz[i][j] = Math.sqrt(Math.pow(x.get(j)-x.get(i),2) + Math.pow(y.get(j)-y.get(i), 2));
                }
            }
        }
    }
    
    public static double calcularDistanciaCamion(){
        double result = 0;
        for (int i = 0; i < distancias.size(); i++) {
            result+= distancias.get(i);
        }
        return result/camiones;
    }
}