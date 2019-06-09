package resources;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


import dao.JDBCUserDAOImpl;
import dao.UserDAO;
import exceptions.CustomBadRequestException;
import model.User;

@Path("/users")
public class UsersResource {

	  @Context
	  ServletContext sc;
	  @Context
	  UriInfo uriInfo;
  
	  @GET
	  @Produces(MediaType.APPLICATION_JSON)
	  public User getUserJSON(@Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		System.out.println(user.getUsername());
		User returnUser =userDao.get(user.getIdu());
		
		return returnUser; 
	  }
	  
	  @GET
	  @Path("/{userid: [0-9]+}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public User getUseridJSON(@PathParam("userid") long userid, @Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);
		
		User returnUser = null;
		User user = userDao.get(userid);
		if(user != null) {
		System.out.println(user.getUsername());
		 returnUser =user;
		}
		
		return returnUser; 
	  }
	  
	  @POST	  	  
	  @Consumes(MediaType.APPLICATION_JSON)
	  public Response post(User newUser, @Context HttpServletRequest request) throws Exception {	
		Connection conn = (Connection) sc.getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);  	 
		
		
		
		Response res;
		
		List<String> messages = new ArrayList<String>();

		if (!newUser.validate(messages))
			    throw new CustomBadRequestException("Errors in parameters");
		//save note in DB
		long id = userDao.add(newUser);

    	res = Response //return 201 and Location: /notes/newid
			   .created(
				uriInfo.getAbsolutePathBuilder()
					   .path(Long.toString(id))
					   .build())
			   .contentLocation(
				uriInfo.getAbsolutePathBuilder()
				       .path(Long.toString(id))
				       .build())
				.build();
	    return res; 
	  }
	  
	  
	  @PUT
	  @Path("/{userid: [0-9]+}")
		@Consumes(MediaType.APPLICATION_JSON)
	  public Response put(User userUpdate,
				@PathParam("userid") long userid,
				@Context HttpServletRequest request) throws Exception{
		  Connection conn = (Connection)sc.getAttribute("dbConn");
		  UserDAO userDao = new JDBCUserDAOImpl();
		  userDao.setConnection(conn);   
		  
		  HttpSession session = request.getSession();
		  User user = (User) session.getAttribute("user");
			
		  Response response = null;
					
		  //We check that the user exists
		  user = userDao.get(user.getIdu());
		  if (user != null){
					if (user.getIdu()!=userid) throw new CustomBadRequestException("Error in id");
					else 
					{
						List<String> messages = new ArrayList<String>();
						if (userUpdate.validate(messages)) userDao.save(userUpdate);						
						else throw new CustomBadRequestException("Errors in parameters");						
					}
				}
		  else throw new WebApplicationException(Response.Status.NOT_FOUND);			
		  
		  return response;
		}
	  
	  @DELETE
	  @Path("/{userid: [0-9]+}")	  
	  public Response deleteUser(@PathParam("userid") long userid,
			  					  @Context HttpServletRequest request) {
		  
		Connection conn = (Connection) sc.getAttribute("dbConn");
		 UserDAO userDao = new JDBCUserDAOImpl();
		 userDao.setConnection(conn);   
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		user = userDao.get(userid);
		if (user != null){
					userDao.delete(userid);
					return Response.noContent().build(); //204 no content 
		}
		else throw new CustomBadRequestException("Error in id");		
			
	  }
} 
