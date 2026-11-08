package server;

import java.time.Duration;
import java.time.LocalDateTime;

public class MonitorServidor extends Thread {

    private LocalDateTime inicioServidor;
    
    // Constructor: guarda la hora en la que comienza el monitor para calcular cuanto tiempo lleva funcionando
    public MonitorServidor(LocalDateTime inicioServidor) {
        this.inicioServidor = inicioServidor;
    }

    // Ejecuta la monitorización del servidor
    @Override
    public void run() {
        while (true) {
            try {
                // Espera 10 segundos antes de mostrar la información
                Thread.sleep(10000);

                // Obtener el número de clientes conectados
                int clientesConectados = Servidor.obtenerClientes().size();

                // Calcular el tiempo que lleva funcionando el servidor 
                //DURATION sierve para calcular cuanto tiempo ha pasado entre dos fechas 
                Duration tiempoActivo = Duration.between(inicioServidor, LocalDateTime.now());

                long horas = tiempoActivo.toHours();
                long minutos = tiempoActivo.toMinutes() % 60;
                long segundos = tiempoActivo.getSeconds() % 60;

                // Mostrar la información en la consola
                System.out.println("\n--- MONITORIZACIÓN DEL SERVIDOR ---");
                System.out.println("Clientes conectados: " + clientesConectados);
                System.out.println("Tiempo activo: " + horas + "h "
                        + minutos + "m " + segundos + "s");
                System.out.println("Último mensaje: " + Servidor.obtenerUltimoMensaje());
                System.out.println("-----------------------------------");
           
            } catch (InterruptedException e) {
                System.out.println("Monitor del servidor detenido.");
                break;
            }
        }
    }
}