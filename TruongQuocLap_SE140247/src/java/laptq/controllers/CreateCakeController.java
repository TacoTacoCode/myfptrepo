/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laptq.controllers;

import laptq.utills.XMLUtils;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Taco
 */
public class CreateCakeController extends HttpServlet {

    private static final String XML_FILE = "/WEB-INF/cake.xml";
    private static final String ERROR = "create.jsp";
    private static final String SUCCESS = "index.html";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String id = request.getParameter("txtID");
            String isAvailable = request.getParameter("isAvailable");
            String cookingTime = request.getParameter("txtTime");
            String name = request.getParameter("txtName");
            String description = request.getParameter("txtDes");
            String quantity = request.getParameter("txtQuantity");
            String price = request.getParameter("txtPrice");
            String lastName = request.getParameter("txtLast");
            String firstName = request.getParameter("txtFirst");
            String sex = request.getParameter("cboSex");
            String realPath = getServletContext().getRealPath("/");
            String filePath = realPath + XML_FILE;
            Document doc = XMLUtils.parseFileToDom(filePath);
            if (doc != null) {
                XPath xPath = XMLUtils.createXPath();
                String exp = "//cake[id='" + id + "']";
                NodeList cakeList = (NodeList) xPath.evaluate(exp, doc, XPathConstants.NODESET);
                if (cakeList.getLength() == 0) {
                    Element caketElement = doc.createElement("cake");
                    
                    Element idElement = doc.createElement("id");
                    idElement.setAttribute("isAvailable", (isAvailable.equals("1") ? "true" : "false"));
                    idElement.setAttribute("cookingTime", cookingTime);
                    idElement.setTextContent(id);
                    
                    Element nameElement = doc.createElement("name");
                    nameElement.setTextContent(name);

                    Element descriptionElement = doc.createElement("description");
                    descriptionElement.setTextContent(description);

                    Element quantityElement = doc.createElement("quantity");
                    quantityElement.setTextContent(quantity);

                    Element priceElement = doc.createElement("price");
                    priceElement.setTextContent(price);


                    Element chefElement = doc.createElement("chef");
                    chefElement.setAttribute("gender", sex.equals("0") ? "Female" : "Male");

                    Element firstNameElement = doc.createElement("firstname");
                    firstNameElement.setTextContent(firstName);

                    Element lastNameElement = doc.createElement("lastName");
                    lastNameElement.setTextContent(lastName);

                    chefElement.appendChild(firstNameElement);
                    chefElement.appendChild(lastNameElement);

                    caketElement.appendChild(idElement);
                    caketElement.appendChild(nameElement);
                    caketElement.appendChild(descriptionElement);
                    caketElement.appendChild(quantityElement);
                    caketElement.appendChild(priceElement);
                    caketElement.appendChild(chefElement);
                    NodeList listCake = doc.getElementsByTagName("bakery");

                    if (listCake != null) {
                        if (listCake.getLength() > 0) {
                            listCake.item(0).appendChild(caketElement);
                            boolean result = XMLUtils.transformDOMtoResult(doc, filePath);
                            if (result) {
                                url = SUCCESS;
                            }
                        }
                    }
                } else {
                    url = ERROR;
                    request.setAttribute("ERROR", "Id is used, changed");
                    System.out.println("trung cake ko add");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
