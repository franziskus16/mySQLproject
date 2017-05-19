/**
 * Created by francescgimenez on 18/5/17.
 */
import java.sql.*;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in); //Sirve para recoger texto por consola
    static int select = -1; //opción elegida del usuario
    //static int num1 = 0, num2 = 0; //Variables
    static String player;
    public static void main(String[] args) {
        //Mientras la opción elegida sea 0, preguntamos al usuario
        while(select != 5){
            //Try catch para evitar que el programa termine si hay un error
            try{
                System.out.println("Choose option: \n" +
                        "1.- Create player \n" +
                        "2.- Show table \n" +
                        "3.- Search Player \n" +
                        "4.- Delete Player \n" +
                        "5.- Salir");
                //Recoger una variable por consola
                select = Integer.parseInt(scanner.nextLine());
                //Ejemplo de switch case en Java
                switch(select){
                    case 1:
                        createPlayer();
                        //System.out.println(num1+” + “+num2+” = “+(num1+num2));
                        break;
                    case 2:
                        showTable();
                        //pideNumeros();
                        //System.out.println(num1+” - “+num2+” = “+(num1-num2));
                        break;
                    case 3:
                        SearchByID(1);
                        //pideNumeros();
                        //System.out.println(num1+” * “+num2+” = “+(num1*num2));
                        break;
                    case 4:
                        DeleteByID();
                        //pideNumeros();
                        //System.out.println(num1+” / “+num2+” = “+(num1/num2));
                        break;
                    case 5:
                        System.out.println("Bye!");
                        break;
                    default:
                        System.out.println("Unknown option");
                        break;
                }
                System.out.println("\n"); //Mostrar un salto de línea en Java
            }catch(Exception e){
                System.out.println("Uoop! Error!");
            }
        }
    }
    //Método para recoger variables por consola
    /*public static void pideNumeros(){
        System.out.println(“Write the player to create:“);
        player = Integer.parseInt(scanner.nextLine());
        *//*System.out.println(“Introduce número 2:“);
        num2 = Integer.parseInt(scanner.nextLine());*//*
        //Mostrar un salto de línea en Java
        System.out.println(“\n”);
    }*/
    public static void createPlayer(){
        try {
            System.out.println("Write the player to create:");
            //player = String.parseString(scanner.nextLine());
            System.out.println("\n");
            //Class.forName(“com.mysql.jdbc.Driver”);
            //Singleton dbConexion = null;
            Connection c = Singleton.getConnection();
            PreparedStatement st;
            st = c.prepareStatement("INSERT INTO Players VALUES (?,?,?,?,?,?)");
            st.setInt(1, 11);  //el 1 indica que se sustituira el primer ‘?’ con el valor en int de 1234
            st.setString(2, "Juan"); //el 2 indica que se sustituira el segundo ‘?’ por el valor en double de 123.45
            st.setString(3, "Vasini");  //el 3 indica que se sustiruira el tercer ‘?’ por la cadena “hola”
            st.setString(4, "Cabrera");  //el 3 indica que se sustiruira el tercer ‘?’ por la cadena “hola”
            st.setString(5, "cocacola");  //el 3 indica que se sustiruira el tercer ‘?’ por la cadena “hola”
            st.setString(6, "cocacola@pedroj.com");  //el 3 indica que se sustiruira el tercer ‘?’ por la cadena “hola”
            //los tipos de variables deben coincidir con los tipos definidos en las columnas de la tabla 1en la que se insertaran
            if(st.executeUpdate()==1){
                System.out.println("insert done");
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

   /* public static void showTable(){
        try {
            System.out.println("Write the table you want to show:");
            //player = String.parseString(scanner.nextLine());
            System.out.println("\n");

            //Class.forName(“com.mysql.jdbc.Driver”);

            //Singleton dbConexion = null;
            Connection c = Singleton.getConnection();
            PreparedStatement st;
            st = c.prepareStatement("SELECT * FROM Players");

            //st.setString(1, “Players”); //el 2 indica que se sustituira el segundo ‘?’ por el valor en double de 123.45

            //los tipos de variables deben coincidir con los tipos definidos en las columnas de la tabla 1en la que se insertaran
            // execute select SQL stetement
            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                Integer id = rs.getInt("ID");
                String name = rs.getString("NOMBRE");
                String apellido1 = rs.getString("APELLIDO1");
                String apellido2 = rs.getString("APELLIDO2");
                        String password = rs.getString("PASSWORD");
                String email = rs.getString("EMAIL");


                System.out.println("id : " + id);
                System.out.println("nombre : " + name);
                System.out.println("apellido1 : " + apellido1);
                System.out.println("apellido2 : " + apellido2);
                System.out.println("password : " + password);
                System.out.println("email : " + email);

            }

        }
        catch (SQLException ex) {

            ex.printStackTrace();
        }
    }*/

    public static void showTable() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch(ClassNotFoundException e) {
            System.out.println("Class not found "+ e);
        }
        try {
            Connection con = Singleton.getConnection();

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM players");
            System.out.println("id   nombre   apellido1   apellido2   password   email");

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String apellido1 = rs.getString("apellido1");
                String apellido2 = rs.getString("apellido2");
                String password = rs.getString("password");
                String email = rs.getString("email");

                System.out.println(id+"   "+nombre+"    "+apellido1+"   "+apellido2+"   "+password+"    "+email);
            }
        } catch(SQLException e) {
            System.out.println("SQL exception occured" + e);
        }
    }

        public static void SearchByID(int id) {

        try{
            Connection con = Singleton.getConnection();

                  PreparedStatement stmt = con.prepareStatement("SELECT * FROM players where id = ?");
                    stmt.setInt(1, id);
                    ResultSet rs = stmt.executeQuery();
                  rs.next();
            System.out.println("show player by ID");
            System.out.println(rs.getString("nombre"));

        }

        catch (SQLException ex) {

            ex.printStackTrace();
        }
        }

        public static void DeleteByID(){

        try{
            //1.get a connection to database
            Connection con = Singleton.getConnection();
            //2.create a statement
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM players where id = ?");
            //3.execute SQL query
            String sql = "delete from players where apellido1='Stevenson'";

            int rowsaffected = stmt.executeUpdate(sql);

            System.out.println(rowsaffected + "rowsaffected");
            System.out.println("delete complete");

        }
        catch (SQLException ex) {

            ex.printStackTrace();
        }


        }


}