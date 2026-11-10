# CHAT MULTIUSUARIO

## Iniciar el programa
Para iniciar el servidor habra que abrir el archivo **Servidor.java** y ejecutarlo.

Sabremos que el servidor se habra iniciado correctamente puesto que se postrara por consola la frase "Servidor iniciado en el puerto 5000". Si se muestra dicho mensaje significara que el servidor ha arrancado como es debido.

Una vez el servidor este iniciado tocara iniciar la aplicacion.

El metodo para hacer esto es sencillo, puesto que solo se debe abrir la clase **App.java**, lo que abrira la ventana del chat sin ninguna conexion

## Conectarse al chat con un usuario
Una vez la aplicacion sea inicializada el usuario debera de introducir los datos necesarios para conectarse al chat.

Dicha informacion consta de la siguiente con un ejemplo: 
* IP
* Puerto
* Usuario

Una vez la informacion sea introducida correctamente el usuario debera pulsar en el boton **conectar**, lo cual cambiara el texto que se encuentra en la esquina superior derecha de **desconectado** a **conectado**

## Enviar mensajes una vez conectado al servidor
Una vez el usuario se conecta al servidor este podra enviar mensajes al chat para que lo vean otros usuarios, esto se hace escribiendo en la barra inferior que se encuentra en el centro para posteriormente pulsar el boton enviar, de modo que el chat mostrara el nombre de usuario anterior al texto de su mensaje.

Si lo que el usuario quiere es enviar mensaje a una sola persona, ya sea por que es informacion personal o algo que solo esa persona deba saber el usuario podra pulsar la opcion donde pone **privado**, lo que hara aparecer una caja de texto donde el usuario escribira quien sera el receptor del mensaje privado.

Una vez enviado un mensaje privado solo el usuario receptor lo podra ver, sabiendo que es pribado puesto que a la derecha de la persona que ha enviado el mensaje aparecera entre parentesis la palabra **PRIVADO**

En caso de querer desconectarse del servidor para iniciar como otro usuario, con otra IP o simplemente dejar de usar el chat simplemente habra que darle al boton que dice **desconectar** y una vez pulsado se cambiara el texto de su derecha.