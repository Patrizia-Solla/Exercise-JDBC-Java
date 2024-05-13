package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import Bean.Quotidiani;
import DAO.QuotidianiDAO;

public class App {
    public static void main(String[] args) throws Exception {

    try{
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/edicola", "root", "");
        QuotidianiDAO quotidianoDAO = new QuotidianiDAO(connection);
        Scanner inputNumeri = new Scanner(System.in);
        Scanner inputTesti = new Scanner(System.in);
        
        Quotidiani quotidiano;
        int idQuotidiano = 0;
        boolean ancora = true;
        do {
            System.out.println();
            System.out.println("OPERAZIONI DISPONIBILI");
            System.out.println("1 - Aggiungi pubblicazione");
            System.out.println("2 - Gestisci pubblicazione");
            System.out.println("3 - Ottieni rendiconto giornaliero");
            System.out.println("4 - Elimina pubblicazione");
            System.out.println("5 - Chiudi il programma");
            System.out.print("Digitare il numero corrispondente: ");
            switch (inputNumeri.nextInt()) {
                case 1:
                    quotidiano = new Quotidiani();
                    System.out.print("Inserisci il nome del quotidiano: ");
                    quotidiano.setNome(inputTesti.nextLine());

                    System.out.print("Inserisci il prezzo del quotidiano: ");
                    quotidiano.setPrezzo(inputNumeri.nextDouble());

                    System.out.print("Inserisci l'aggio del quotidiano: ");
                    quotidiano.setAggio(inputNumeri.nextInt());

                    quotidianoDAO.aggiungiQuotidiano(quotidiano);

                    System.out.println("Quotidiano aggiunto con successo.");

                break;

                case 2: 
                        quotidianoDAO.trovaTuttiQuotidiani();
                        System.out.print("Quale pubblicazione vuoi gestire? Inserire il numero corrispondente: ");
                        idQuotidiano = quotidianoDAO.trovaIdQuotidiani(inputNumeri.nextInt()-1);
                       boolean gestisciPubbicazione = true;
                       while (gestisciPubbicazione) {
                        
                       
                        System.out.println("Che operazione di gestione vuoi svolgere?");
                        System.out.println("1 - Inserisci copie ricevute");
                        System.out.println("2 - Incrementa le copie vendute di 1");
                        System.out.println("3 - Modifica prezzo di copertina (possibile solo se non si sono vendute copie)");
                        System.out.println("4 - Modifica aggio (possibile solo se non si sono vendute copie)");
                        System.out.println("5 - Azzerare copie vendute e ricevute");
                        System.out.println("6 - Torna indietro");;

                        switch (inputNumeri.nextInt()) {
                            case 1:
                                quotidianoDAO.inserisciCopieRicevute(idQuotidiano);
                                break;
                            case 2:
                                quotidianoDAO.incrementaCopieVendute(idQuotidiano);
                                break;

                            case 3:
                                quotidianoDAO.modificaPrezzoCopertina(idQuotidiano);
                                break;

                            case 4:
                                quotidianoDAO.modificaAggio(idQuotidiano);
                                break;     

                            case 5:
                                quotidianoDAO.azzeraCopieVenduteRicevute(idQuotidiano);
                                break; 
                               
                            case 6: 
                                gestisciPubbicazione= false;
                                break;   

                            default: System.out.println("Errore, operazione non riconosciuta.");
                                break;
                        }
                    }
                    break;

                case 3:
                    System.out.println("L'incasso giornaliero e': " + quotidianoDAO.incassoGiornaliero());    
                    break;

                case 4:
                        quotidianoDAO.trovaTuttiQuotidiani();

                        System.out.print("Quale pubblicazione vuoi eliminare? Inserire il numero corrispondente: ");
                        idQuotidiano = quotidianoDAO.trovaIdQuotidiani(inputNumeri.nextInt()-1);
                        quotidianoDAO.eliminaQuotidiano(idQuotidiano);                    
                    break;

                case 5:
                    System.out.println("Chiusura in corso...");
                    quotidianoDAO.chiudiConnessione();
                    ancora = false;
                    System.exit(0);
                    break;

                default:
                    System.out.println("Errore! Scelta non valida, riprova");
            }
        } while (ancora);

        inputNumeri.close();
        inputTesti.close();
        
    }catch (SQLException e) {
        e.printStackTrace();
    }

    }
}

