package util;

import java.util.ArrayList;
import java.util.List;
import model.Category;
import model.Question;

public class CategoryQuestionProvider {

    public static List<Question> getQuestions(Category category) {
        switch (category) {
            case ESTADISTICA: return estadistica();
            case GEOGRAFIA: return geografia();
            case DEPORTES: return deportes();
            case INGLES: return ingles(); 
            default: return new ArrayList<>();
        }
    }

    private static List<Question> estadistica() {
        List<Question> list = new ArrayList<>();
        list.add(new Question("¿Qué mide la desviación estándar?",
                new String[]{"Dispersión", "Tendencia central", "Simetría", "Moda"},
                "Dispersión", Category.ESTADISTICA));
        list.add(new Question("La media de 2, 4, 6, 8 es:",
                new String[]{"4", "5", "6", "7"},
                "5", Category.ESTADISTICA));
        list.add(new Question("¿Qué gráfico se usa para distribuciones de frecuencias?",
                new String[]{"Histograma", "Diagrama de caja", "Mapa", "Diagrama de red"},
                "Histograma", Category.ESTADISTICA));
        list.add(new Question("La mediana es:",
                new String[]{"Valor central ordenado", "Promedio", "Frecuencia más alta", "Intervalo"},
                "Valor central ordenado", Category.ESTADISTICA));
        list.add(new Question("Una muestra es:",
                new String[]{"Parte de la población", "Toda la población", "Error", "Hipótesis"},
                "Parte de la población", Category.ESTADISTICA));
        list.add(new Question("El sesgo describe:",
                new String[]{"Asimetría", "Variabilidad", "Tamaño", "Error absoluto"},
                "Asimetría", Category.ESTADISTICA));
        list.add(new Question("Correlación cercana a 0 indica:",
                new String[]{"Sin relación lineal", "Relación perfecta", "Causalidad", "Error"},
                "Sin relación lineal", Category.ESTADISTICA));
        list.add(new Question("La moda es:",
                new String[]{"El valor más frecuente", "El promedio", "El rango", "El mínimo"},
                "El valor más frecuente", Category.ESTADISTICA));
        list.add(new Question("Intervalo de confianza expresa:",
                new String[]{"Rango probable del parámetro", "Desviación exacta", "Error absoluto", "Valor fijo"},
                "Rango probable del parámetro", Category.ESTADISTICA));
        list.add(new Question("Un outlier es:",
                new String[]{"Valor extremo", "Valor medio", "Valor normal", "Intervalo"},
                "Valor extremo", Category.ESTADISTICA));
        return list;
    }

    private static List<Question> geografia() {
        List<Question> list = new ArrayList<>();
        list.add(new Question("Capital de Japón:",
                new String[]{"Osaka", "Tokio", "Kyoto", "Nagasaki"},
                "Tokio", Category.GEOGRAFIA));
        list.add(new Question("Río más largo del mundo:",
                new String[]{"Amazonas", "Nilo", "Yangtsé", "Misisipi"},
                "Nilo", Category.GEOGRAFIA));
        list.add(new Question("Monte más alto:",
                new String[]{"K2", "Everest", "Kilimanjaro", "Denali"},
                "Everest", Category.GEOGRAFIA));
        list.add(new Question("Océano más grande:",
                new String[]{"Atlántico", "Índico", "Pacífico", "Ártico"},
                "Pacífico", Category.GEOGRAFIA));
        list.add(new Question("Continente con más países:",
                new String[]{"Europa", "África", "Asia", "América"},
                "África", Category.GEOGRAFIA));
        list.add(new Question("Capital de Canadá:",
                new String[]{"Toronto", "Ottawa", "Vancouver", "Montreal"},
                "Ottawa", Category.GEOGRAFIA));
        list.add(new Question("Desierto más grande:",
                new String[]{"Sahara", "Gobi", "Arabia", "Kalahari"},
                "Sahara", Category.GEOGRAFIA));
        list.add(new Question("Isla más grande:",
                new String[]{"Australia", "Groenlandia", "Madagascar", "Borneo"},
                "Groenlandia", Category.GEOGRAFIA));
        list.add(new Question("Mar que conecta Europa y África:",
                new String[]{"Negro", "Mediterráneo", "Rojo", "Báltico"},
                "Mediterráneo", Category.GEOGRAFIA));
        list.add(new Question("País con mayor población:",
                new String[]{"India", "China", "EE.UU.", "Indonesia"},
                "China", Category.GEOGRAFIA));
        return list;
    }

    private static List<Question> deportes() {
        List<Question> list = new ArrayList<>();
        list.add(new Question("Número de jugadores en cancha (fútbol):",
                new String[]{"9", "10", "11", "12"},
                "11", Category.DEPORTES));
        list.add(new Question("Deporte de Wimbledon:",
                new String[]{"Tenis", "Golf", "Rugby", "Cricket"},
                "Tenis", Category.DEPORTES));
        list.add(new Question("Balón anaranjado típico de:",
                new String[]{"Vóley", "Baloncesto", "Handball", "Béisbol"},
                "Baloncesto", Category.DEPORTES));
        list.add(new Question("Messi es famoso por:",
                new String[]{"Natación", "Fútbol", "Baloncesto", "Ciclismo"},
                "Fútbol", Category.DEPORTES));
        list.add(new Question("Gran Slam pertenece a:",
                new String[]{"Tenis", "Boxeo", "Atletismo", "Hockey"},
                "Tenis", Category.DEPORTES));
        list.add(new Question("Maratón son:",
                new String[]{"21 km", "5 km", "42 km", "60 km"},
                "42 km", Category.DEPORTES));
        list.add(new Question("Deporte con tabla sobre olas:",
                new String[]{"Surf", "Esquí", "Skate", "Remo"},
                "Surf", Category.DEPORTES));
        list.add(new Question("Goles se anotan en:",
                new String[]{"Canasta", "Meta", "Base", "Plataforma"},
                "Meta", Category.DEPORTES));
        list.add(new Question("Pelota pequeña blanca en:",
                new String[]{"Tenis", "Rugby", "Golf", "Balonmano"},
                "Golf", Category.DEPORTES));
        list.add(new Question("Natación estilo ‘crawl’ también llamado:",
                new String[]{"Mariposa", "Espalda", "Libre", "Pecho"},
                "Libre", Category.DEPORTES));
        return list;
    }

    private static List<Question> ingles() {
        List<Question> list = new ArrayList<>();
        list.add(new Question("Traduce: 'House'", new String[]{"Casa","Perro","Agua","Cielo"}, "Casa", Category.INGLES));
        list.add(new Question("'Book' significa:", new String[]{"Mesa","Libro","Bolsa","Barco"}, "Libro", Category.INGLES));
        list.add(new Question("Plural de 'Child':", new String[]{"Childs","Childes","Children","Child"}, "Children", Category.INGLES));
        list.add(new Question("'To eat' es:", new String[]{"Comer","Dormir","Beber","Cortar"}, "Comer", Category.INGLES));
        list.add(new Question("Pasado de 'Go':", new String[]{"Goed","Went","Go","Gone"}, "Went", Category.INGLES));
        list.add(new Question("Traduce: 'Apple'", new String[]{"Manzana","Mesa","Mapa","Camisa"}, "Manzana", Category.INGLES));
        list.add(new Question("'Happy' es:", new String[]{"Triste","Feliz","Cansado","Enojado"}, "Feliz", Category.INGLES));
        list.add(new Question("Adjetivo opuesto a 'Hot':", new String[]{"Hard","Cold","Cool","Hold"}, "Cold", Category.INGLES));
        list.add(new Question("Pronombre para 'ellos':", new String[]{"We","They","Them","Those"}, "They", Category.INGLES));
        list.add(new Question("Forma correcta: 'I ___ soccer.'", new String[]{"play","plays","playing","played"}, "play", Category.INGLES));
        return list;
    }
}