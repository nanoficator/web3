package servlet;

import model.BankClient;
import service.BankClientService;
import util.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MoneyTransactionServlet extends HttpServlet {

    BankClientService bankClientService = new BankClientService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       resp.getWriter().println(PageGenerator.getInstance().getPage("moneyTransactionPage.html", new HashMap<>()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        BankClient sender = new BankClientService().getClientByName(req.getParameter("senderName"));
        String nameTo = req.getParameter("nameTo");
        Long count = Long.parseLong(req.getParameter("count"));
        if (new BankClientService().sendMoneyToClient(sender, nameTo, count)) {
            pageVariables.put("message", "The transaction was successful");
            resp.getWriter().println(PageGenerator.getInstance().getPage("resultPage.html", pageVariables));
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            pageVariables.put("message", "transaction rejected");
            resp.getWriter().println(PageGenerator.getInstance().getPage("resultPage.html", pageVariables));
        }
    }
}
