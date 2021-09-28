package com.firebasemessaging.resources;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.firebasemessaging.models.Order;
import com.firebasemessaging.models.Product;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

@Path("/messaging")
public class FirebaseMessagingResource 
{

    private static final String PRODUCTS_TOPIC = "products";

    @POST
    @Path("/initialize")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response initializeFirebase() 
    {
        try 
        {
            InputStream serviceAccount = this.getClass().getClassLoader()
                    .getResourceAsStream("/serviceAccountKey.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://ece-shop-default-rtdb.europe-west1.firebasedatabase.app").build();
            FirebaseApp.initializeApp(options);

            return Response.accepted().entity("FirebaseApp has been initialized on the server side.").build();
        } 
        catch (IOException e) 
        {
            Response response = Response.status(Status.NOT_FOUND).entity(e.getLocalizedMessage()).build();
            throw new NotFoundException(response);
        }
    }

    @POST
    @Path("/sendwelcome")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sendRegistrationMessage(@QueryParam("key") String key) 
    {
        Notification notification = new Notification("Welcome to Ece shop",
                "Thank you for registering, we hope you will have a good time using our app.");
        Message message = Message.builder().setNotification(notification).setToken(key).putData("type", "regular")
                .build();

        try 
        {
            String response = FirebaseMessaging.getInstance().send(message);
            return Response.ok().entity(response).build();
        } 
        catch (FirebaseMessagingException e) 
        {
            Response response = Response.status(Status.NOT_FOUND)
                    .entity(e.getErrorCode() + " : " + e.getLocalizedMessage()).build();
            throw new NotFoundException(response);
        }
    }

    @POST
    @Path("/informnew")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sendInformNewProduct(Product product) 
    {
        try 
        {
            Notification notification = new Notification("A new product is available for purchase.", product.getName()
                    + " has now been added and is already in stock, be the among the first to it.");
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(product);
            Message message = Message.builder().setTopic(PRODUCTS_TOPIC).setNotification(notification)
                    .putData("object", json)
                    .putData("type", "image")
                    .build();
            String response = FirebaseMessaging.getInstance().send(message);
            return Response.ok().entity(response).build();
        } 
        catch (FirebaseMessagingException | JsonProcessingException e) 
        {
            Response response = Response.status(Status.NOT_FOUND).entity(e.getLocalizedMessage())
            .build();
            throw new NotFoundException(response);
        }
   }

    @POST
    @Path("/informupdate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sendInformUpdate(@QueryParam("status") String status, Product product) 
    {
        String notifBody;
        int val = Integer.parseInt(status);
        if(val == 1)
        {
            notifBody = product.getName() + " is now back in stock and available at a new price.";
        }
        else if(val == 2)
        {
            notifBody = product.getName() + " is now available at a new price.";
        }
        else if(val == 3)
        {
            notifBody = product.getName() + " is now back in stock and available for purchase.";
        }
        else
        {
            Response response = Response.status(Status.BAD_REQUEST).entity("Invalid value provided for the status parameter in the query.")
            .build();
            throw new BadRequestException(response);
        }
        try 
        {
            Notification notification = new Notification("A product that is in your cart has been updated.", notifBody);
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(product);
            Message message = Message.builder().setTopic(product.getProductId()).setNotification(notification)
                    .putData("object", json)
                    .putData("type", "image")
                    .build();
            String response = FirebaseMessaging.getInstance().send(message);
            return Response.ok().entity(response).build();
        } 
        catch (FirebaseMessagingException | JsonProcessingException e) 
        {
            Response response = Response.status(Status.NOT_FOUND).entity(e.getLocalizedMessage())
            .build();
            throw new NotFoundException(response);
        }
   }

    @POST
    @Path("/sendthanks")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sendThankYouMessage(@QueryParam("id") String orderId) 
    {
        Notification notification = new Notification("Thank you for your purchase.",
                "Your order has been made and is on it's way, you will be notified in the future for any details.");
        Message message = Message.builder().setNotification(notification).setTopic(orderId).putData("type", "regular")
                .build();

        try 
        {
            String response = FirebaseMessaging.getInstance().send(message);
            return Response.ok().entity(response).build();
        } 
        catch (FirebaseMessagingException e) 
        {
            Response response = Response.status(Status.NOT_FOUND)
                    .entity(e.getErrorCode() + " : " + e.getLocalizedMessage()).build();
            throw new NotFoundException(response);
        }
    }

    @POST
    @Path("/orderchange")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sendOrderStatusChange(Order order) 
    {
        String title;
        String body;
        if(order.getStatus().equals("Completed"))
        {
            title = "One of your orders is now completed.";
            body = "Click here to view the details, you order has arrived at it's target destination";
        }
        else if(order.getStatus().equals("Cancelled"))
        {
            title = "One of your orders has been cancelled";
            body = "We regret to inform you that one of your orders was deemed as invalid and unable to be executed. Your spent money has been refunded, click here for further details";
        }
        else
        {
            Response response = Response.status(Status.BAD_REQUEST).entity("Invalid value provided in the request's body.")
            .build();
            throw new BadRequestException(response);
        }
        try 
        {
            Notification notification = new Notification(title, body);
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(order);
            Message message = Message.builder().setTopic(order.getOrderId()).setNotification(notification)
                    .putData("object", json)
                    .putData("type", "order_change")
                    .build();
            String response = FirebaseMessaging.getInstance().send(message);
            return Response.ok().entity(response).build();
        } 
        catch (FirebaseMessagingException | JsonProcessingException e) 
        {
            Response response = Response.status(Status.NOT_FOUND).entity(e.getLocalizedMessage())
            .build();
            throw new NotFoundException(response);
        }
   }
    
}