/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laptq.controllers;

import laptq.dtos.CakeDTO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import laptq.utills.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Taco
 */
public class ViewController extends HttpServlet {

    private static final String XML_FILE = "/WEB-INF/cake.xml";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String realPath = getServletContext().getRealPath("/");
            String filePath = realPath + XML_FILE;
            Document doc = XMLUtils.parseFileToDom(filePath);
            if (doc != null) {
                XPath xPath = XMLUtils.createXPath();
                String exp = "//cake";
                NodeList cakeList = (NodeList) xPath.evaluate(exp, doc, XPathConstants.NODESET);
                List<CakeDTO> listResult = new ArrayList<>();
                if (cakeList != null) {
                    for (int i = 0; i < cakeList.getLength(); i++) {
                        Node node = cakeList.item(i);
                        CakeDTO dto = new CakeDTO();

                        NodeList children = node.getChildNodes();
                        for (int j = 0; j < children.getLength(); j++) {
                            Node tmp = children.item(j);
                            if (tmp.getNodeName().equals("id")) {
                                dto.setId(tmp.getTextContent().trim());
                                String isAvailable = tmp.getAttributes().getNamedItem("isAvailable").getNodeValue();
                                dto.setIsAvailable("true".equals(isAvailable));
                                String cookingTime = tmp.getAttributes().getNamedItem("cookingTime").getNodeValue();
                                dto.setCookingTime(Integer.parseInt(cookingTime));
                            }
                            if (tmp.getNodeName().equals("name")) {
                                dto.setName(tmp.getTextContent().trim());
                            } else if (tmp.getNodeName().equals("price")) {
                                dto.setPrice(Integer.parseInt(tmp.getTextContent().trim()));
                            } 
                        }
                        listResult.add(dto);
                    }
                }
                request.setAttribute("INFO", listResult);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher("view.jsp").forward(request, response);
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
