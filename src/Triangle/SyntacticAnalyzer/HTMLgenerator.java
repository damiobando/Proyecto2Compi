/*
    Clase encargada de generar los archivos HTML.

    Fecha de incio : 20/04/2023
    Fecha final : 
    Encargada: Raquel Arguedas
 */

package Triangle.SyntacticAnalyzer;

import static Triangle.SyntacticAnalyzer.SourceFile.EOT;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static jdk.nashorn.internal.runtime.JSType.isNumber;

/**
 * 
 * @author raque
 */
public class HTMLgenerator {  
    
    //Atributos
    private String code = "";
    private String fileString = "";
    private String filename = "";
    
    //Constructor
    public HTMLgenerator(String filename) {
        FileReader fr = null;
        try {
            fr = new FileReader(new File(filename));
            BufferedReader br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null){
                code += line + "\n"; // se le añade las lineas al string que guarda el código
            }fr.close();
        } catch (Exception ex) {
            System.out.println("Hubo un error");
        }
        
        this.filename = filename.replace(".tri", ".html"); // toma el nombre del archivo .tri y lo guarda en el mismo lugar con la extension html
        
        if (!hayErroresLexicos())
            generarArchivo();
    }
    
    public boolean hayErroresLexicos(){
        String[] codeLines = code.split("\n"); 
        
        for (String line : codeLines){ 
            char[] array = line.toCharArray();
            for (int i = 0; i<array.length; i++){
                if (array[i] == '!') break;         // si se encuentra un comentario no sigue revisando
                
                if (array[i] == ' '){               // verifica que se cumpla como se debe ingresar un caracter literal
                    if (i+1<array.length && array[i+1] == '\''){
                        if (i+3<array.length && array[i+3] != '\''){
                            System.out.println("AAA");
                            return true;
                        }
                    }
                }
                
                if (array[i] != '\n' && array[i] != '\r' && array[i] != '\t' && 
                    array[i] != 'a' && array[i] != 'b' && array[i] != 'c' && array[i] != 'd' && array[i] != 'e' &&
                    array[i] != 'f' && array[i] != 'g' && array[i] != 'h' && array[i] != 'i' && array[i] != 'j' &&
                    array[i] != 'k' && array[i] != 'l' && array[i] != 'm' && array[i] != 'n' && array[i] != 'o' &&
                    array[i] != 'p' && array[i] != 'q' && array[i] != 'r' && array[i] != 's' && array[i] != 't' &&
                    array[i] != 'u' && array[i] != 'v' && array[i] != 'w' && array[i] != 'x' && array[i] != 'y' &&
                    array[i] != 'z' &&
                    array[i] != 'A' && array[i] != 'B' && array[i] != 'C' && array[i] != 'D' && array[i] != 'E' &&
                    array[i] != 'F' && array[i] != 'G' && array[i]!= 'H' && array[i] != 'I' && array[i] != 'J' &&
                    array[i] != 'K' && array[i] != 'L' && array[i] != 'M' && array[i] != 'N' && array[i] != 'O' &&
                    array[i] != 'P' && array[i] != 'Q' && array[i] != 'R' && array[i] != 'S' && array[i] != 'T' &&
                    array[i] != 'U' && array[i] != 'V' && array[i] != 'W' && array[i] != 'X' && array[i] != 'Y' &&
                    array[i] != 'Z' &&
                    array[i] !='0' &&  array[i] != '1' && array[i] != '2' && array[i] != '3' && array[i] != '4' &&
                    array[i] != '5' &&  array[i] != '6'&& array[i] != '7' && array[i] != '8' && array[i] != '9' &&
                    array[i] != '+'&&  array[i] != '-'&&  array[i] != '*'&& array[i] != '/'&&  array[i] != '='&&
                    array[i] != '<'&&  array[i] != '>'&&  array[i] != '\\'&&  array[i] != '&'&&  array[i] != '@'&&
                    array[i] != '%'&&  array[i] != '^'&&  array[i] != '?' && array[i] != '\'' &&  array[i] != ' ' && 
                    array[i] != '.'&&  array[i] != ':'&&  array[i] != ';'&&   array[i] != ','&& array[i] != '~'&& 
                    array[i] != '|'&&  array[i] != '$'&&  array[i] != '('  && array[i] != ')'&& array[i]!= '['&& array[i] != ']'&&
                    array[i] != '{'&& array[i] != '}')
                    return true;
                    
            }
        }
        return false;
    }
    
    public void generarArchivo(){
        
        this.fileString += "<p style=\"font-family: 'Courier', monospace;\">"; // escribe el estilo del texto del html
        
        String[] codeLines = code.split("\n"); 
                
        for (String line : codeLines){                              //lee cada línea   
            char[] array = line.toCharArray();
            char tempChar;
            String tempString = "";
            
            for (int i = 0; i<array.length; i++){                   //recorre los caracteres de la línea
                tempChar = array[i];
                if (tempChar == '!'){
                    verificarString(tempString);
                    tempString = ""; 
                    while (i<array.length){                         //empieza un comentario, por ende lo que quede de la linea es comentario
                        tempString += array[i];
                        i++;
                    }
                    this.fileString += "<font color='#00b300'>" + tempString + "</font>"; // añade el comentario con su formato
                    tempString = "";
                } else if (tempChar == ' '){
                    verificarString(tempString);
                    tempString = "";
                    this.fileString += "&nbsp"; // escribe el espacio
                } else if (tempChar == '\t'){
                    verificarString(tempString);
                    tempString = "";
                    this.fileString += "&nbsp &nbsp &nbsp &nbsp"; // escribe el tab
                }else 
                    tempString += tempChar;
            }
            verificarString(tempString);
            this.fileString += "<br>"; // escribe el salto de línea
        }
        
        endOfDoc();
        
    }
    
    public void verificarString(String s){ //verifica como debe escribirlo
        if (!s.equals("")){ 
            //si es una palabra reservada
            if ( 4 <= Token.getKind(s)  && Token.getKind(s) <= 31)
                this.fileString += "<b>" + s + "</b>";
            
            //si es un número o un CharLiteral
            else if ( s.chars().allMatch( Character::isDigit ) || ( s.startsWith("'") && s.endsWith("'") && s.length()==3) )
               this.fileString += "<font color='#0000cd'>" + s + "</font>";
            
            //cualquier otro
            else {
                if (s.equals(";")){
                    this.fileString += "&#59";
                }
                else this.fileString += s;
            }
                
        }
    }
    
         
    // escribe el final del html
    public void endOfDoc(){
        this.fileString += "</p>";               // cierra el formato del archivo HTML
        
        File file = new File(filename);          //crea la instancia del archivo
        Path path = Paths.get(filename);  
        
        try{  
            Path p = Files.createFile(path);     //crea el archivo en la carpeta donde se encuentre el .tri
        }catch (Exception e){}  
        
        try {
            try (FileWriter fw = new FileWriter(file); BufferedWriter bf = new BufferedWriter(fw); PrintWriter printer = new PrintWriter(bf)) {
                printer.print(fileString);       //escribe el código HTML
            }catch (IOException e) {System.out.println("no pudo escribirlo"); e.printStackTrace();}
        }
        catch (Exception s) {
            System.out.println("No se pudo crear el archivo, error:" + s);
        }
    }
  
}
