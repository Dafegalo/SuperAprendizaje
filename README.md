# SuperAprendizaje - Juego de Preguntas Aleatorias

## Descripción General
SuperAprendizaje es una aplicación de preguntas y respuestas competitiva donde el usuario puede desafiar a la computadora respondiendo preguntas seleccionadas aleatoriamente. El juego incluye configuración de probabilidad de error, gestión de preguntas y un sistema de puntuación para seguir el desempeño de los jugadores.

## Estructura del Proyecto
El proyecto sigue la arquitectura Modelo-Vista-Controlador (MVC):

- **Modelo**: Representa los datos y la lógica del juego.
  - `Game.java`: Maneja el estado del juego.
  - `Question.java`: Representa una pregunta.
  - `QuestionBank.java`: Gestiona la colección de preguntas.
  - `Player.java`: Representa a un jugador.
  - `Score.java`: Gestiona el sistema de puntuación.

- **Vista**: Maneja la interfaz gráfica de usuario (Java Swing).
  - `GameView.java`: Muestra el estado del juego y mensajes.
  - `QuestionView.java`: Presenta preguntas y opciones al usuario.
  - `ScoreView.java`: Muestra la puntuación actual.

- **Controlador**: Gestiona el flujo del juego y las interacciones del usuario.
  - `GameController.java`: Controla la lógica y el flujo del juego.
  - `QuestionController.java`: Gestiona la carga y validación de preguntas.
  - `ScoreController.java`: Maneja los cálculos y actualizaciones de puntuación.

## Configuración
La configuración de la probabilidad de error permite ajustar la probabilidad de respuestas incorrectas por parte de la computadora. Esto se gestiona mediante la clase `ErrorProbabilityConfig.java`.

## Instrucciones de Instalación y Ejecución

1. **Clonar el repositorio**
   ```sh
   git clone <URL-del-repositorio>
   cd SuperAprendizaje
   ```

2. **Compilar y ejecutar con Maven**
   - Asegúrate de tener [Maven](https://maven.apache.org/) y Java instalados.
   - Para compilar el proyecto:
     ```sh
     mvn clean compile
     ```
   - Para ejecutar la aplicación:
     ```sh
     mvn exec:java -Dexec.mainClass="main.Main"
     ```

3. **Abrir en un IDE**
   - Puedes importar el proyecto en tu IDE favorito (por ejemplo, IntelliJ IDEA o VS Code con soporte para Maven).

## Cómo Funciona el Juego

- Al iniciar, se muestra una ventana gráfica con la bienvenida.
- El usuario responde preguntas seleccionando una opción en la interfaz gráfica.
- El juego alterna turnos entre el usuario y la computadora (según configuración).
- La puntuación se actualiza y muestra después de cada respuesta.
- El juego termina tras un número determinado de rondas o preguntas, mostrando el puntaje final.

## Contribución
¡Siéntete libre de contribuir al proyecto enviando issues o pull requests! Tus sugerencias y mejoras son bienvenidas.

## Licencia
Este proyecto está bajo la Licencia MIT. Consulta el archivo LICENSE para más detalles.