/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dominio.Cliente;
import datos.ClienteDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Francisco Larrocca
 */
@WebServlet("/ServletControlador")
public class ServletControlador extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        //Test:
        System.err.println("Accion:" + accion);
        if (accion != null) {
            switch (accion) {
                case "editar":
                    this.editarCliente(request, response);
                    break;
                case "eliminar":
                    this.eliminarCliente(request, response);
                    break;
                default:
                    this.accionDefault(request, response);
                    break;
            }
        } else {
            this.accionDefault(request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        //Test:
        System.err.println("Accion:" + accion);
        if (accion != null) {
            switch (accion) {
                case "insertar":
                    this.insertarCliente(request, response);
                    break;
                case "modificar":
                    this.modificarCliente(request, response);
                    break;
                default:
                    this.accionDefault(request, response);
                    break;
            }
        } else {
            this.accionDefault(request, response);
        }
    }

    private double calcularSaldoTotal(List<Cliente> clientes) {
        double saldoTotal = 0;

        for (Cliente c : clientes) {
            saldoTotal = saldoTotal + c.getSaldo();
        }
        return saldoTotal;
    }

    private void insertarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Recuparar valores del formulario agregarCliente:
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");

        double saldo = 0;
        String saldoString = request.getParameter("saldo");

        if (saldoString != null && !"".equals(saldoString)) {
            saldo = Double.parseDouble(saldoString);
        }

        //Crear objeto cliente con los datos recibidos:
        Cliente cli = new Cliente(nombre, apellido, email, telefono, saldo);

        //Insertar el objeto en la base de datos:
        ClienteDAO cliDAO = new ClienteDAO();
        int registrosModificados = cliDAO.insert(cli);

        System.out.println("Registros modificados (insertar): " + registrosModificados);

        //Redirigimos a la accion por default:
        this.accionDefault(request, response);
    }

    private void accionDefault(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ClienteDAO cliDAO = new ClienteDAO();
        List<Cliente> clientes = cliDAO.listar();
        System.out.println("Clientes:" + clientes);

        HttpSession session = request.getSession();
        session.setAttribute("clientes", clientes);
        session.setAttribute("totalClientes", clientes.size());
        session.setAttribute("saldoTotal", this.calcularSaldoTotal(clientes));

        //request.getRequestDispatcher("clientes.jsp").forward(request, response); El URL no cambia (se duplica la info)
        response.sendRedirect("clientes.jsp");
    }

    private void editarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ClienteDAO cliDAO = new ClienteDAO();
        //Recuperamos idCliente:
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));

        Cliente cli = cliDAO.encontrarCliente(new Cliente(idCliente));

        request.setAttribute("cliente", cli);

        String jspEditar = "/WEB-INF/paginas/cliente/editarCliente.jsp";

        request.getRequestDispatcher(jspEditar).forward(request, response);
    }

    private void modificarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Recuparar valores del formulario agregarCliente:
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");

        double saldo = 0;
        String saldoString = request.getParameter("saldo");

        if (saldoString != null && !"".equals(saldoString)) {
            saldo = Double.parseDouble(saldoString);
        }

        //Crear objeto cliente con los datos recibidos:
        Cliente cli = new Cliente(idCliente, nombre, apellido, email, telefono, saldo);

        //Actualizar/modificar el objeto en la base de datos:
        ClienteDAO cliDAO = new ClienteDAO();
        int registrosModificados = cliDAO.update(cli);

        System.out.println("Registros modificados (insertar): " + registrosModificados);

        //Redirigimos a la accion por default:
        this.accionDefault(request, response);
    }

    private void eliminarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Recuparar valores del formulario agregarCliente:
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));

        //Crear objeto cliente con los datos recibidos:
        Cliente cli = new Cliente(idCliente);

        //Eliminar el objeto en la base de datos:
        ClienteDAO cliDAO = new ClienteDAO();
        int registrosModificados = cliDAO.delete(cli);

        System.out.println("Registros modificados (eliminar): " + registrosModificados);

        //Redirigimos a la accion por default:
        this.accionDefault(request, response);

    }
}
