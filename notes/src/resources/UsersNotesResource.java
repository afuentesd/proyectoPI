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
import dao.JDBCUsersNotesDAOImpl;
import dao.UserDAO;
import dao.UsersNotesDAO;
import exceptions.CustomBadRequestException;
import exceptions.CustomNotFoundException;
import model.User;
import model.UsersNotes;



@Path("/usersnotes")
public class UsersNotesResource {

	  @Context
	  ServletContext sc;
	  @Context
	  UriInfo uriInfo;
	  
	  @GET
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<UsersNotes> getUsersNotesJSON(@Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		UsersNotesDAO usersnotesDao = new JDBCUsersNotesDAOImpl();
		usersnotesDao.setConnection(conn);
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		List<UsersNotes> usersnotes;
		
		usersnotes = usersnotesDao.getAllByUser(user.getIdu());
		
	    return usersnotes; 
	  }
	  
	  @GET
	  @Path("/{noteid: [0-9]+}")	  
	  @Produces(MediaType.APPLICATION_JSON)
	  public UsersNotes getUserNoteJSON(@PathParam("noteid") long noteid,
			  					@Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		UsersNotesDAO usersnotesDao = new JDBCUsersNotesDAOImpl();
		usersnotesDao.setConnection(conn);
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		UsersNotes usersnotes = new UsersNotes();
		usersnotes = usersnotesDao.get(user.getIdu(), noteid );
		
		if (usersnotes != null) return usersnotes;
		else throw new CustomNotFoundException("Order ("+ noteid + ") is not found");		   
	  }
	  
	 
	  
	  @POST	 
	  @Path("/{noteid: [0-9]+}")
	  @Consumes(MediaType.APPLICATION_JSON)
	  public Response post(String username,
			  						@PathParam("noteid") long noteid,
			  						@Context HttpServletRequest request) throws Exception {	
		Connection conn = (Connection) sc.getAttribute("dbConn");
		UsersNotesDAO usersnotesDao = new JDBCUsersNotesDAOImpl();
		usersnotesDao.setConnection(conn);  	 
		UsersNotes newUsersNotes = new UsersNotes();
		
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);
		User user = userDao.get(username);
		if(user != null) {
			newUsersNotes.setIdu(user.getIdu());
		
		newUsersNotes.setArchived(0);
		newUsersNotes.setPinned(0);
		newUsersNotes.setOwner(0);
		newUsersNotes.setIdn((int)noteid);
		List<String> messages = new ArrayList<String>();

		if (!newUsersNotes.validate(messages)) {
			    throw new CustomBadRequestException("Errors in parameters");
		}else {		
		usersnotesDao.add(newUsersNotes);
		}
	  }
		Response res;
		

		

    	res = Response //return 201 and Location: /notes/newid
			   .created(
				uriInfo.getAbsolutePathBuilder()
					   .path(Long.toString(newUsersNotes.getIdn()))
					   .build())
			   .contentLocation(
				uriInfo.getAbsolutePathBuilder()
				       .path(Long.toString(newUsersNotes.getIdn()))
				       .build())
				.build();
	    return res; 
	  }  
	  
	  
	  
	  @PUT
	  @Path("/{noteid: [0-9]+}")
		@Consumes(MediaType.APPLICATION_JSON)
		public Response put(UsersNotes usersnotesUpdate,
							@PathParam("noteid") long noteid,
							@Context HttpServletRequest request) throws Exception{
		  Connection conn = (Connection)sc.getAttribute("dbConn");
		  UsersNotesDAO usersnotesDao = new JDBCUsersNotesDAOImpl();
		  usersnotesDao.setConnection(conn);
		  
		  
			
		  Response response = null;
					
		  //We check that the order exists
		  UsersNotes usersnotes = usersnotesDao.get(usersnotesUpdate.getIdu(), usersnotesUpdate.getIdn());
		  if (usersnotes != null && usersnotesUpdate.getIdn() == noteid){
					
			  List<String> messages = new ArrayList<String>();
			  if (usersnotesUpdate.validate(messages)) usersnotesDao.save(usersnotesUpdate);						
			  else throw new CustomBadRequestException("Errors in parameters");						
					
				}
		  else throw new WebApplicationException(Response.Status.NOT_FOUND);			
		  
		  return response;
		}
	  
	  
	  

	@DELETE
	  @Path("/{noteid: [0-9]+}")	  
	  public Response deleteOrder(@PathParam("noteid") long noteid,
			  					  @Context HttpServletRequest request) {
		  
		Connection conn = (Connection) sc.getAttribute("dbConn");
		UsersNotesDAO usersnotesDao = new JDBCUsersNotesDAOImpl();
		usersnotesDao.setConnection(conn);
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		UsersNotes usersnotes = usersnotesDao.get(user.getIdu(), noteid);
		if (usersnotes != null){
			usersnotesDao.delete(user.getIdu(),noteid);
			return Response.noContent().build(); //204 no content 
		}
		else throw new CustomBadRequestException("Error in user or id");		
			
	  }
	  
} 
