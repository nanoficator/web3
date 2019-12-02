package servlet;

import exception.DBException;
import model.BankClient;
import service.BankClientService;
import util.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println(PageGenerator.getInstance().getPage("registrationPage.html", new HashMap<>()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        try {
            BankClient newBankClient = new BankClient(req.getParameter("name"), req.getParameter("password"), Long.parseLong(req.getParameter("money")));
            if (new BankClientService().addClient(newBankClient)) {
                pageVariables.put("message", "Add client successful");
                resp.getWriter().println(PageGenerator.getInstance().getPage("resultPage.html", pageVariables));
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                pageVariables.put("message", "Client not add");
                resp.getWriter().println(PageGenerator.getInstance().getPage("resultPage.html", pageVariables));
            }
        } catch (DBException e) {
            throw new IOException(e);
        }
    }
}
