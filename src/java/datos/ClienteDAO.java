/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import dominio.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Francisco Larrocca
 */
public class ClienteDAO {

    private static final String SQL_SELECT = "SELECT * FROM cliente;";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM cliente WHERE id_cliente = ?;";
    private static final String SQL_INSERT = "INSERT INTO cliente (nombre, apellido, email, telefono, saldo) VALUES(?,?,?,?,?);";
    private static final String SQL_UPDATE = "UPDATE cliente SET nombre=?, apellido=?, email=?, telefono=?, saldo=? WHERE id_cliente=?;";
    private static final String SQL_DELETE = "DELETE FROM cliente WHERE id_cliente=?;";

    public List<Cliente> listar() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Cliente> listaClientes = new ArrayList<>();
        Cliente cli;
        try {
            con = Conexion.obtenerConexion();
            ps = con.prepareStatement(SQL_SELECT);

            rs = ps.executeQuery();

            while (rs.next()) {
                int idCliente = rs.getInt("id_cliente");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String email = rs.getString("email");
                String telefono = rs.getString("telefono");
                double saldo = rs.getDouble("saldo");

                cli = new Cliente(idCliente, nombre, apellido, email, telefono, saldo);

                listaClientes.add(cli);
            }

        } catch (SQLException ex) {
            System.out.println("Ocurrio un error en la base de datos: (listar)" + ex.getMessage());
        } finally {

            Conexion.close(ps);
            Conexion.close(con);
        }
        return listaClientes;
    }

    public Cliente encontrarCliente(Cliente cliBuscar) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = Conexion.obtenerConexion();
            ps = con.prepareStatement(SQL_SELECT_BY_ID);
            ps.setInt(1, cliBuscar.getIdCliente());

            rs = ps.executeQuery();
            rs.absolute(1); //Nos posicionamos en el primer registro devuelto.

            String nombre = rs.getString("nombre");
            String apellido = rs.getString("apellido");
            String email = rs.getString("email");
            String telefono = rs.getString("telefono");
            double saldo = rs.getDouble("saldo");

            cliBuscar.setNombre(nombre);
            cliBuscar.setApellido(apellido);
            cliBuscar.setEmail(email);
            cliBuscar.setTelefono(telefono);
            cliBuscar.setSaldo(saldo);

        } catch (SQLException ex) {
            System.out.println("Ocurrio un error en la base de datos: (encontrarCliente)" + ex.getMessage());
        } finally {

            Conexion.close(ps);
            Conexion.close(con);
        }
        return cliBuscar;
    }

    public int insert(Cliente cli) {
        Connection con = null;
        PreparedStatement ps = null;

        int rows = 0;
        try {
            con = Conexion.obtenerConexion();
            ps = con.prepareStatement(SQL_INSERT);

            ps.setString(1, cli.getNombre());
            ps.setString(2, cli.getApellido());
            ps.setString(3, cli.getEmail());
            ps.setString(4, cli.getTelefono());
            ps.setDouble(5, cli.getSaldo());

            rows = ps.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Ocurrio un error en la base de datos: (insert)" + ex.getMessage());
        } finally {

            Conexion.close(ps);
            Conexion.close(con);
        }
        return rows;
    }

    public int update(Cliente cli) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int rows = 0;
        try {
            con = Conexion.obtenerConexion();
            ps = con.prepareStatement(SQL_UPDATE);

            ps.setString(1, cli.getNombre());
            ps.setString(2, cli.getApellido());
            ps.setString(3, cli.getEmail());
            ps.setString(4, cli.getTelefono());
            ps.setDouble(5, cli.getSaldo());
            ps.setInt(6, cli.getIdCliente());

            rows = ps.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Ocurrio un error en la base de datos: (update)" + ex.getMessage());
        } finally {

            Conexion.close(ps);
            Conexion.close(con);
        }
        return rows;
    }

    public int delete(Cliente cli) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int rows = 0;
        try {
            con = Conexion.obtenerConexion();
            ps = con.prepareStatement(SQL_DELETE);

            ps.setInt(1, cli.getIdCliente());

            rows = ps.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Ocurrio un error en la base de datos: (delete)" + ex.getMessage());
        } finally {

            Conexion.close(ps);
            Conexion.close(con);
        }
        return rows;
    }
}
