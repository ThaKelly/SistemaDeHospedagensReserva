package poo.main;
  
import repositorio.RepositorioDoHotel;
import java.util.Scanner;
import gerenciamento.Terminal;

public class Main {
    public static void main(String[] args) {
        RepositorioDoHotel repositorio = new RepositorioDoHotel();

        Terminal terminal = new Terminal(repositorio);        
        
        System.out.println("Sistema de Gerenciamento de Reservas e Hospedagem");             
 
            terminal.iniciaOperacao();
    }                            

}
