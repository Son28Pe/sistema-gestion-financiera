package repositorio;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// JSON escrito a mano (sin librerias). Solo maneja arreglos de objetos PLANOS: aisla el
// formato del resto del sistema. El parser tolera valores con o sin comillas.
public final class JsonUtil {

    private JsonUtil() {}

    // --- Escritura ---
    public static String escapar(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '"'  -> sb.append("\\\"");
                case '\\' -> sb.append("\\\\");
                case '\n' -> sb.append("\\n");
                case '\r' -> sb.append("\\r");
                case '\t' -> sb.append("\\t");
                default   -> sb.append(c);
            }
        }
        return sb.toString();
    }

    // --- Lectura ---
    // Cada objeto se devuelve como un mapa campo->valor (strings ya sin comillas ni escapes).
    public static List<Map<String, String>> parsearArreglo(String json) {
        List<Map<String, String>> objetos = new ArrayList<>();
        int[] pos = {0};
        saltarEspacios(json, pos);
        if (pos[0] >= json.length() || json.charAt(pos[0]) != '[') {
            return objetos; // vacio o formato inesperado
        }
        pos[0]++; // consumir '['
        while (true) {
            saltarSeparadores(json, pos);
            if (pos[0] >= json.length() || json.charAt(pos[0]) == ']') break;
            objetos.add(parsearObjeto(json, pos));
        }
        return objetos;
    }

    private static Map<String, String> parsearObjeto(String s, int[] pos) {
        Map<String, String> obj = new LinkedHashMap<>();
        pos[0]++; // consumir '{'
        while (true) {
            saltarSeparadores(s, pos);
            if (s.charAt(pos[0]) == '}') { pos[0]++; break; }
            String clave = parsearCadena(s, pos);
            saltarEspacios(s, pos);
            pos[0]++; // consumir ':'
            saltarEspacios(s, pos);
            obj.put(clave, parsearValor(s, pos));
        }
        return obj;
    }

    private static String parsearValor(String s, int[] pos) {
        if (s.charAt(pos[0]) == '"') {
            return parsearCadena(s, pos);
        }
        int ini = pos[0]; // bareword: numero/true/false/null hasta , } ] o espacio
        while (pos[0] < s.length() && ",}] \n\r\t".indexOf(s.charAt(pos[0])) < 0) {
            pos[0]++;
        }
        return s.substring(ini, pos[0]).trim();
    }

    private static String parsearCadena(String s, int[] pos) {
        StringBuilder sb = new StringBuilder();
        pos[0]++; // consumir comilla inicial
        while (s.charAt(pos[0]) != '"') {
            char c = s.charAt(pos[0]);
            if (c == '\\') {
                pos[0]++;
                char e = s.charAt(pos[0]);
                switch (e) {
                    case 'n' -> sb.append('\n');
                    case 'r' -> sb.append('\r');
                    case 't' -> sb.append('\t');
                    default  -> sb.append(e); // \" \\ y demas
                }
            } else {
                sb.append(c);
            }
            pos[0]++;
        }
        pos[0]++; // consumir comilla final
        return sb.toString();
    }

    private static void saltarEspacios(String s, int[] pos) {
        while (pos[0] < s.length() && Character.isWhitespace(s.charAt(pos[0]))) pos[0]++;
    }

    private static void saltarSeparadores(String s, int[] pos) {
        while (pos[0] < s.length()) {
            char c = s.charAt(pos[0]);
            if (Character.isWhitespace(c) || c == ',') pos[0]++;
            else break;
        }
    }
}
