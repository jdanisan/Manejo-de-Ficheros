# Manejo-de-Ficheros

1. Objetivo de la Práctica

El objetivo de esta práctica es que los alumnos implementen un sistema para gestionar jugadores de videojuegos, aplicando los conocimientos sobre los diferentes tipos de ficheros que han aprendido en clase (texto, binario, objetos, acceso aleatorio y XML). Los alumnos deberán ser capaces de:
Implementar operaciones de alta, baja, modificación, y listados de los jugadores.
Almacenar y recuperar la información de los jugadores utilizando distintas tecnologías de manejo de archivos.
Configurar la capa de datos que el programa usará para gestionar la información.
Aplicar buenas prácticas de programación, como modularidad, manejo de excepciones, y documentación.


2. Datos que se van a gestionar
   Los jugadores de videojuegos tendrán los siguientes atributos que deberán ser almacenados y gestionados:
   ID (user_id): Número entero único que identifica al jugador. (se puede utilizar el DNI)
   Nick (nick_name): Nombre de usuario o apodo del jugador (cadena de texto).
   Experiencia (experience): Nivel de experiencia del jugador (número entero).
   Nivel de vida (life_level): Representa los puntos de vida del jugador (número entero).
   Monedas (coins): Cantidad de monedas que posee el jugador (número entero).


3. Hitos que el alumno debe realizar
   Los alumnos deben implementar un programa con un menú que permita realizar las siguientes operaciones:
   3.1. Menú Principal:
   Alta de jugadores:
   Pedir al usuario que ingrese los datos del jugador (ID, nick, experiencia, nivel de vida, monedas).
   Guardar los datos en el tipo de fichero seleccionado en la configuración.
   Baja de jugadores:
   Solicitar al usuario el ID del jugador a eliminar.
   Eliminar el jugador del fichero.
   Modificación de jugadores:
   Solicitar el ID del jugador a modificar.
   Permitir modificar uno o más de sus datos (nick, experiencia, nivel de vida, monedas).
   Listado por código (ID):
   Solicitar el ID del jugador.
   Mostrar los datos del jugador cuyo ID coincide con el ingresado.
   Listado general:
   Mostrar todos los jugadores almacenados.
   Configuración (submenú):
   Permitir seleccionar entre diferentes tipos de almacenamiento:
   Fichero secuencial de texto (BufferedReader/BufferedWriter)
   Fichero secuencial binario (DataInputStream/DataOutputStream)
   Fichero de objetos binario (ObjectInputStream/ObjectOutputStream)
   Fichero de acceso aleatorio binario (RandomAccessFile)
   Fichero de texto XML (DOM)
   Salir: Terminar la ejecución del programa.

   4. Hitos Específicos por Tipo de Fichero
      El alumno deberá implementar las operaciones CRUD para cada tipo de fichero configurado en el programa:
      
   4.1. Fichero secuencial de texto
      Utilizar BufferedReader y BufferedWriter para leer y escribir los datos en un archivo de texto.
      Cada línea representará un jugador en el formato:

      [USER_ID = 0, NICK_NAME = Chechaaaaar, EXPERIENCE = 99, LIFE_LEVEL = 4, COINS = 5999]
      [USER_ID = 1, NICK_NAME = Pandora, EXPERIENCE = 45, LIFE_LEVEL = 2, COINS = 2000]
   
   4.2. Fichero secuencial binario
      Utilizar DataInputStream y DataOutputStream para leer y escribir los datos de forma binaria.
      Cada campo deberá ser leído y escrito en el mismo orden.
   
   4.3. Fichero de objetos binario
      Utilizar ObjectOutputStream y ObjectInputStream para serializar y deserializar objetos de la clase Jugador.
   
   4.4. Fichero de acceso aleatorio binario
      Utilizar RandomAccessFile para gestionar jugadores.
      Cada jugador debe tener un tamaño fijo en bytes para facilitar el acceso directo.

   4.5. Fichero de texto XML
      Utilizar DocumentBuilderFactory y DOM para leer y escribir archivos XML que contengan la información de los jugadores en una estructura jerárquica.

   5. Guía paso a paso para resolver la práctica.
   
   Paso 1: Definir la clase Jugador
      Crear la clase Jugador con los atributos id, nick, experience, life_level, coins.
      Incluir métodos getters y setters, y un método toString() para imprimir la información del jugador. Utiliza el IDE para generar dichos métodos.
  
   Paso 2: Implementar el menú principal
      Crear el menú principal con las opciones de alta, baja, modificación y listados.
      Implementar el submenú de configuración.
  
   Paso 3: Implementar cada tipo de fichero
      Para cada opción de almacenamiento, implementar las operaciones CRUD correspondientes.
      Realizar pruebas con cada tipo de fichero para asegurar que el programa funciona correctamente.

6. Recomendaciones adicionales
   Pruebas unitarias: realizar pruebas de cada parte del programa de manera modular, es probable que empieces trabajando como QA.
   Validación de datos: validar correctamente los datos de entrada (por ejemplo, que el id sea único y que los valores numéricos sean positivos y/o comprendidos en un rango de valor coherente).

Criterios de Evaluación
Correcto manejo de ficheros: Los alumnos deben ser capaces de implementar las diferentes formas de almacenamiento y manejo de datos (texto, binario, objetos, acceso aleatorio, XML con DOM) utilizando las herramientas adecuadas.
Implementación correcta del menú: El menú principal y los submenús deben funcionar correctamente, permitiendo la navegación entre las diferentes opciones de la aplicación.
Correcta implementación de las operaciones CRUD: Alta, baja, modificación, y los listados deben funcionar correctamente, incluyendo la validación de datos cuando sea necesario.
Modularidad y organización del código: El código debe estar organizado en clases y métodos de forma modular, lo que facilita su mantenimiento y comprensión.
Uso adecuado de estructuras de control y datos: Las estructuras de control como bucles y condicionales deben ser utilizadas correctamente, así como el manejo de arrays, listas u otras estructuras de datos.
Manejo de excepciones: El programa debe incluir el manejo de excepciones para asegurar que las operaciones con archivos no generen errores inesperados.
Interfaz de usuario: La interacción con el usuario debe ser clara y sencilla, con mensajes que faciliten la comprensión del funcionamiento del programa.
Documentación del código: Los alumnos deben comentar su código adecuadamente, explicando el funcionamiento de los métodos y las decisiones de diseño.
Creatividad y diseño: Se valorará si los alumnos agregan funcionalidad adicional o mejoran el diseño de la aplicación.
Funcionamiento global: La aplicación debe funcionar correctamente en su totalidad, ejecutando sin errores todas las operaciones requeridas