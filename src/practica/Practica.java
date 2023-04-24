/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package practica;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author pinar
 */
public class Practica implements Runnable {

    /**
     * @param args the command line arguments
     */
    private int fila;
     private static int tam = 8;
    private static int[][] matriz = new int [tam][tam];

    private Practica(int fila) {
      this.fila = fila;  
    }
     
     @Override
    public void run() {
         for (int i = 0; i < tam; i++) {
             matriz[fila][i] *= 10;
         }
    }
    
    public static void main(String[] args){    
        Random rand = new Random(System.nanoTime());

        double tiempo_inicio, tiempo_final;
        
        for(int i = 0; i < matriz.length; i++){
            for(int j = 0; j < matriz[0].length; j++){
                matriz[i][j] = rand.nextInt(10);
            }
        }
        
        tiempo_inicio = System.nanoTime();
        
        ExecutorService pool  = Executors.newFixedThreadPool(8);
        
        for (int i = 0; i < tam; i++) {
            Runnable runnable = new Practica(i);
            pool.execute(runnable);
        }
        
        pool.shutdown();
        while(!pool.isTerminated());
        
//        Thread[] hilos = new Thread[tam];
//        
//        for(int i = 0; i < hilos.length; i++){
//            Runnable runnable = new Practica(i);
//            hilos[i] = new Thread(runnable);
//            hilos[i].start();
//            
//        }
//        
//        
//        for(int i = 0; i < hilos.length; i++){
//            try{
//                hilos[i].join();                
//            }catch(Exception ex){}
//        }
    
        tiempo_final = System.nanoTime() - tiempo_inicio;
        
        System.out.println((tiempo_final/10000000) + " milisegundos");
        
        for(int i = 0; i < matriz.length; i++){
            for(int j = 0; j < matriz[0].length; j++){
                System.out.print(matriz[i][j] +" ");
            }
            System.out.println();
        }  
    }
    
}
