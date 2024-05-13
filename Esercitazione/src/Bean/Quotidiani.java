package Bean;

import java.util.Scanner;

public class Quotidiani {
    private int id;
    private String nome;
    private double prezzo;
    private int aggio;
    private int cricevute = 0;
    private int cvendute = 0;

    Scanner inputN = new Scanner(System.in);
    Scanner inputT = new Scanner(System.in);

    public Quotidiani() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome){
        boolean ancora = true;

        do{
            if (nome.length()>2) {
                this.nome = nome;
                ancora = false;
            } else{
                System.out.println("Il nome della pubblicazione non è corretto, inseriscine uno giusto");
                nome = inputT.nextLine();
                
            }
        } while(ancora);
    }
    public String getNome(){
        return nome;
    }

    public void setCricevute(int cricevute){
        boolean ancora = true;
        do{
            if (cricevute>=0) {
                this.cricevute = cricevute;
                ancora = false;
            } else{
                System.out.println("Il numero inserito non è corretto, riprova!");
                cricevute = inputN.nextInt();
                
            }
        } while(ancora);
    }
    public int getCricevute(){
        return cricevute;
    }

    public void setPrezzo(double prezzo){
        boolean ancora = true;

        do{
            if (prezzo>0) {
                this.prezzo = prezzo;
                ancora = false;
            } else{
                System.out.println("Il prezzo inserito non è correto");
                System.out.println("Inseriscine un'altro");
                prezzo = inputN.nextDouble();

            }
        } while(ancora);
    }

    public double getPrezzo(){
        return prezzo;
    }

    public void setAggio(int aggio){
        boolean ancora = true;
        
        do{
            if (aggio>=5 && aggio<=20) {
                this.aggio = aggio;
                ancora = false;
            } else{
                System.out.println("L'aggio inserito non è corretto, riprova!");
                aggio = inputN.nextInt();
            }
        } while(ancora);
    }

    public int getAggio(){
        return aggio;
    }
    
    public void setCvendute(int cvendute){
        boolean ancora= true;

        do{
            if (cvendute>=0) {
                this.cvendute= cvendute;
                ancora = false;
            } else{
                System.out.println("Il numero inserito non è valdio, riprova");
                cvendute = inputN.nextInt();
            }

        } while(ancora);
    }

    public int getCvendute(){
        return cvendute;
    }


    // Metodo toString per la rappresentazione testuale dell'oggetto
    @Override
    public String toString() {
        return "Quotidiano [id=" + id + ", nome=" + nome + ", prezzo=" + prezzo + ", aggio=" + aggio + ", cricevute=" + cricevute + ", cvendute=" + cvendute + "]";
    }
}