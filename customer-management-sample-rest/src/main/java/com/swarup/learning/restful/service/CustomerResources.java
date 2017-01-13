package com.swarup.learning.restful.service;

import com.swarup.learning.restful.model.Customer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by swaroop on 11/01/17.
 */
@Path("/customers")
public class CustomerResources {

    private Map<Integer, Customer> customerDatabase = new ConcurrentHashMap<Integer, Customer>();
    private AtomicInteger idGenerator = new AtomicInteger();

    @POST
    @Consumes("application/xml")
    public Response createCustomer(final InputStream inputStream) {
        final Customer customer = buildCustomer(inputStream);
        final int customerId = idGenerator.incrementAndGet();
        customer.setId(customerId);
        customerDatabase.put(customerId, customer);
        System.out.println("Created a customer with id :: " + customerId);
        return Response.created(URI.create("/customers/" + customerId)).build();
    }

    @GET
    @Path("{id}")
    @Produces("application/xml")
    public StreamingOutput getCustomer(final @PathParam("id") int id) {
        final Customer customer = customerDatabase.get(id);
        if(customer == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return new StreamingOutput() {
            @Override
            public void write(OutputStream outputStream) throws IOException, WebApplicationException {
                outputCustomer(outputStream, customer);
            }
        };
    }

    @PUT
    @Path("{id}")
    @Consumes("application/xml")
    public void updateCustomer(final @PathParam("id") int id, final InputStream inputStream) {
        final Customer customer = customerDatabase.get(id);
        if(customer == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        final Customer update = buildCustomer(inputStream);
        customer.setCity(update.getCity());
        customer.setFirstName(update.getFirstName());
        customer.setLastName(update.getLastName());
    }

    private Customer buildCustomer(final InputStream inputStream) {
        final Customer customer = new Customer();
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(inputStream);
            Element root = document.getDocumentElement();
            if(root.getAttribute("id") != null && !root.getAttribute("id").trim().equals("")) {
                customer.setId(Integer.valueOf(root.getAttribute("id")));
            }
            NodeList nodes = root.getChildNodes();
            for(int i=0; i<nodes.getLength(); i++) {
                Element element = (Element)nodes.item(i);
                if(element.getTagName().equals("first-name")) {
                    customer.setFirstName(element.getTextContent());
                }
                else if (element.getTagName().equals("last-name")) {
                    customer.setLastName(element.getTextContent());
                }
                else if (element.getTagName().equals("city")) {
                    customer.setCity(element.getTextContent());
                }
            }
        } catch (final Exception exception) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        return customer;
    }

    private void outputCustomer(final OutputStream outputStream, final Customer customer) {
        PrintStream writer = new PrintStream(outputStream);
        writer.println("<customer id=\"" + customer.getId()+"\">");
        writer.println("<first-name>" + customer.getFirstName()+"</first-name>");
        writer.println("<last-name>" + customer.getLastName()+"</last-name>");
        writer.println("<city>" + customer.getCity()+"</city>");
        writer.println("</customer>");
    }
}
