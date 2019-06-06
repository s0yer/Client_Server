import java.io.*;
import java.net.*;
import java.util.*;

public class Servidor extends Thread {

    private int porta;
    private ServerSocket ouvido;
    private ObjectOutputStream output; // saida de dados
    private ObjectInputStream inStream; // entrada de dados
    private Vector lista = new Vector();
    private Jogador visitante;

    public Jogador(Socket s){
        connection=s; // armazena conex. do server
        try{
            output = new ObjectOutputStream(s.getOutputStream()); // fluxo saida de dados
            inStream = new ObjectInputStream(s.getInputStream()); // fluxo entrada de dados
            output.flush(); // clean buffer
        }catch (IOException e){

        }
    }
    public void conecta(int porta){
        this.porta = porta; // armazena variável numero da porta
        try{
            ouvido = new java.net.ServerSocket(porta); // tenta se conectar a porta
        }
        catch (java.io.IOException e){ // finaliza a aplicação se houver problemas
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println(" Servidor on, porta: " + porta );
    }

    public Servidor(String n, int gate){
        setName(n);
        conecta(gate);
    }

    private void informe(String m){
        try{
            output.writeObject(m);
            output.flush();
        }catch (java.io.IOException e){
            System.out.println("Erro de envio mensagem");
            this.closeConnection();
            servidorOn=false;
        }
    }


    public void run(){
       String conversa;
       int k;
       while (servidorOn){
           conversa = ouvir(false);
           k = conversa.indexOf("bye"); // cliente desconectando do servidor, x >= 0
           if (x >= 0){
               this.servidorOn = false;
               this.interrupt();
               Servidor.this.removeJogador(this);
               System.out.println(" Requisição de exclusão recebida");
               informe("[exit]");
           }
           if (conversa.indexOf("[nome?]") > -1){
               nome=conversa.substring(7);
           }
           if (conversa.indexOf("[mais +]") > -1){
               System.out.println(" Recebi de " + nome + ":" + conversa.substring(12));
               informe("[mais +]");

               // protocolos de comunicação: [nome?], [mais +], [exit]
           }
       }
    }



    public static void main(String[] args){

    }
}
