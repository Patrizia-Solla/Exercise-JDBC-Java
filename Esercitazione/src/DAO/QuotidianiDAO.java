package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import Bean.Quotidiani;

public class QuotidianiDAO {
    
    private Connection connection;
    Scanner inputNumeri = new Scanner(System.in);

    public QuotidianiDAO(Connection connection) {
        this.connection = connection;
    }

    public void aggiungiQuotidiano(Quotidiani quotidiani) throws SQLException {
        String query = "INSERT INTO quotidiani (nome, prezzo, aggio, cricevute, cvendute) VALUES (?, ?, ?, 0, 0)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, quotidiani.getNome());
            statement.setDouble(2, quotidiani.getPrezzo());
            statement.setInt(3, quotidiani.getAggio());
            statement.executeUpdate();
        }
    }

    public Quotidiani trovaQuotidianiperId (int idQuotidiani) throws SQLException {
        String query= "SELECT * FROM quotidiani WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idQuotidiani);
            try (ResultSet resultSet = statement.executeQuery()) {
                
                if (resultSet.next()) {
                    Quotidiani quotidiano = new Quotidiani();
                    quotidiano.setId(resultSet.getInt("id"));
                    quotidiano.setNome(resultSet.getString("nome"));
                    quotidiano.setPrezzo(resultSet.getDouble("prezzo"));
                    quotidiano.setAggio(resultSet.getInt("aggio"));
                    quotidiano.setCricevute(resultSet.getInt("cricevute"));
                    quotidiano.setCvendute(resultSet.getInt("cvendute"));
                    return quotidiano;
                } else {
                    return null;
                }
            }
        }
    }

    public int trovaIdQuotidiani(int posizione) throws SQLException{
        String query= "SELECT id FROM quotidiani ORDER BY id LIMIT 1 OFFSET ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
             statement.setInt(1, posizione);
             try(ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                } else {
                    throw new SQLException("Nessun record trovato");
                }             
            }
        }
    }

    public void trovaTuttiQuotidiani() throws SQLException{
        String query = "SELECT id, nome FROM quotidiani";
        try (PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery()) {
            int i = 1;
            while (resultSet.next()) {
                System.out.println((i++) + " - " + resultSet.getString("nome"));
            }
        }
    }

    public void aggiornaQuotidiano (Quotidiani quotidiani) throws SQLException {
        String query = "UPDATE quotidiani SET nome = ?, prezzo = ?, aggio = ?, cricevute = ?, cvendute = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, quotidiani.getNome());
            statement.setDouble(2, quotidiani.getPrezzo());
            statement.setInt(3, quotidiani.getAggio());
            statement.setInt(4, quotidiani.getCricevute());
            statement.setInt(5, quotidiani.getCvendute());
            statement.executeUpdate();
        }
    }


    //inizio gestione singolo quotidiano
    public void inserisciCopieRicevute(int idQuotidiani) throws SQLException{
        String query = "UPDATE quotidiani SET cricevute = ? WHERE id = ?";
        System.out.print("Inserisci il numero di copie: ");
        int copieRicevute = inputNumeri.nextInt();
        if(copieRicevute > 0){
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setInt(1, copieRicevute);
                statement.setInt(2, idQuotidiani);
                statement.executeUpdate();
                System.out.println("Nuovo numero di copie inserito correttamente.");
            }
        }else{
            System.out.println("Non e' possibile inserire un numero di copie negativo.");
        }
    }

    public void incrementaCopieVendute(int idQuotidiani) throws SQLException {
        ResultSet resultSet = null;
        String query = "SELECT cricevute, cvendute FROM quotidiani WHERE id = ?";
        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idQuotidiani);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int copieRicevute = resultSet.getInt("cricevute");
                int copieVendute = resultSet.getInt("cvendute");

                if ((copieVendute+1) <= copieRicevute) {
                    String queryDue = "UPDATE quotidiani SET cvendute = ? WHERE id = ?";
                    try(PreparedStatement statement2 = connection.prepareStatement(queryDue)){
                        statement2.setInt(1, ++copieVendute);
                        statement2.setInt(2, idQuotidiani);
                        statement2.executeUpdate();
                        System.out.println("Copie vendute incrementate con successo.");
                    }
                } else {
                    System.out.println("Impossibile incrementare le copie vendute. Numero massimo raggiunto.");
                }
            } else {
                System.out.println("Pubblicazione non trovata.");
            }
        }
    }
    
    public void modificaPrezzoCopertina(int idQuotidiani) throws SQLException {
        String query = "UPDATE quotidiani SET prezzo = ? WHERE id = ?";
        
        if(trovaQuotidianiperId(idQuotidiani).getCvendute() == 0){
            System.out.print("Inserisci il nuovo prezzo di copertina: ");
            double prezzo = inputNumeri.nextDouble();
            if(prezzo > 0){
                try(PreparedStatement statement = connection.prepareStatement(query)){
                    statement.setDouble(1, prezzo);
                    statement.setInt(2, idQuotidiani);
                    statement.executeUpdate();
                    System.out.println("Nuovo prezzo inserito correttamente.");
                }
            }else{
                System.out.println("Impossibile impostare un prezzo negativo.");
            }
    
        }else{
            System.out.println("Non è possibile modificare il prezzo dopo aver venduto delle copie.");
        }
    }

    public void modificaAggio(int idQuotidiani) throws SQLException {
        String query = "UPDATE quotidiani SET aggio = ? WHERE id = ?";

        if(trovaQuotidianiperId(idQuotidiani).getCvendute() == 0){ //se delle copie già sono state vendute (cvendute > 0)
            System.out.print("Inserisci il nuovo aggio: ");
            int aggio = inputNumeri.nextInt();
            if(aggio >= 5 && aggio <= 20){
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setDouble(1, aggio);
                    statement.setInt(2, idQuotidiani);
                    statement.executeUpdate();
                    System.out.println("Aggio inserito con successo.");
                }
            }else{
                System.out.println("L'aggio deve essere compreso tra 5 e 20.");
            }
        }else{
            System.out.println("Impossibile modificare l'aggio, copie già vendute");        
        }
    }

    public void azzeraCopieVenduteRicevute(int idQuotidiani) throws SQLException{
        String query = "UPDATE quotidiani SET cricevute = 0, cvendute = 0 WHERE id = ?";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, idQuotidiani);
            statement.executeUpdate();
            System.out.println("Copie ricevute e vendute azzerate con successo.");
        }
    }
    //fine gestione singolo quotidiano

    
    public double incassoGiornaliero() throws SQLException {
        String query = "SELECT SUM((prezzo * cvendute * aggio) / 100) AS Incasso_giornaliero FROM quotidiani"; //query corretta per calcolare l'incasso giornaliero l.
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("Incasso_giornaliero");
                } else {
                    return 0;
                }
            }
        }
    }

    
    public void eliminaQuotidiano(int idQuotidiani) throws SQLException {
        String query = "DELETE FROM quotidiani WHERE id = ?";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, idQuotidiani);
            statement.executeUpdate();
            System.out.println("Pubblicazione eliminata con successo.");
        }
    }

    public void chiudiConnessione() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}