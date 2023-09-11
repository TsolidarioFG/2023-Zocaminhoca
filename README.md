# Zocaminhoca-TFG-2023

Solución software propuesta para la gestión de las tareas cotidianas de la cooperativa de consumo responsable Zocamiñoca. Cuenta con un backend para la gestión de los datos almacenados, así como para exponer las operaciones de consulta de datos y gestión de los mismos. Cuenta también con un bot de telegram para interactuar con las socias de la cooperativa, y ofrecerle las consultas de sus datos. Por último tiene un frontend para acceder a la funcionalidades de gestión de la cooperativa.

## Configuración

Antes de instalar el proyecto, es necesario configurar los parámetros para el despliegue local. Para esto es necesario modificar los archivos:

- /backend/src/main/resources/application.properties
- /backend/src/test/resources/application.properties
- /bot/zocatelebot/src/main/resources/application.properties
- /bot/zocatelebot/src/main/resources/logging.properties
- /frontend/ZocaFront/.env.local

dándole los valores deseados a los parámetros de estos archivos de configuración, para gestionar las rutas locales donde se almacenarán los diferentes archivos y la conexión a la base de datos. 
Se recomienda configurar el parámetro 
`spring.jpa.hibernate.ddl-auto=update`
en la primera ejecución para crear las tablas respectivas a las entidades de la aplicación, y cambiarlo luego al valor _none_, en las siguientes.
Por último, se recomienda ejecutar el script de inserción de tareas, localizado en la carpeta _/backend/src/main/resources/_, para insertar las tareas definidas inicialmenta por la cooperativa Zocamiñoca.

## Instalación

Una vez configurados los parámetros pertinentes, pasamos a generar los ejecutables java del backend y del bot.
En la carpeta _/backend/_ ejecutamos:
`mvn clean package`
y hacemos lo mismo en la carpeta _/bot/zocatelebot/_. Así generamos los .jar en las respectivas carpetas _/target/_

Para instalar las dependencias del frontend, basta con acceder a la carpeta _/frontedn/ZocaFront/_ y ejecutar
`npm install`

## Ejecución

Para la ejecución en local, basta con ejecutar el .jar del backend, usando el comando:
`java -jar nombre-del-archivo-backend.jar`

Para lanzar el bot, ejecutamos:
`java -jar nombre-del-archivo-bot.jar -Djava.util.logging.config.file=/ruta-archivo-logging.properties`

Y por último para ejecutar el frontend en local podemos utilizar el servidor local que nos proporciona NextJs y ejecutar desde la carpeta de _/frontend/ZocaFront/_:
`npm run dev`

Haciendo esto se lanzan las 3 partes del sistema.

## Creación del bot de Telegram

Para crear el bot de Telegram debemos abrir un chat con _@BotFather_ en Telegram, y seguir las instrucciones para la creación de un nuevo bot, guardando el token que nos genera. Una vez creado, el nombre y el token, debemos de introducirlos en los parámetros de _/bot/zocatelebot/src/main/resources/application.properties_ para que el programa local se conecte a la API del bot y reciba las actualizaciones.

## Documentación de la API

Para una facil comprensión y uso de la API se ha generado documentación con Swagger, que se puede consultar una vez arrancado el backend en la dirección:
`http://localhost:8080/swagger-ui/index.html`