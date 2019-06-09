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

import dao.JDBCNoteDAOImpl;
import dao.JDBCUsersNotesDAOImpl;
import dao.NoteDAO;
import dao.UsersNotesDAO;
import exceptions.CustomBadRequestException;
import exceptions.CustomNotFoundException;
import model.Note;
import model.User;
import model.UsersNotes;



@Path("/notes")
public class NotesResource {

	  @Context
	  ServletContext sc;
	  @Context
	  UriInfo uriInfo;
	  
	  @GET
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<Note> getNotesJSON(@Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		NoteDAO noteDao = new JDBCNoteDAOImpl();
		noteDao.setConnection(conn);		
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		UsersNotesDAO usersnotesDao = new JDBCUsersNotesDAOImpl();
		usersnotesDao.setConnection(conn);
		List<UsersNotes> usersnotes;
		usersnotes = usersnotesDao.getAllByUser(user.getIdu());
		
		
		List<Note> notes = new ArrayList<Note>();
		
		for(int i=0;i<usersnotes.size();i++) {
			if (usersnotes.get(i).getArchived() == 0) {
			if(usersnotes.get(i).getPinned() == 1) {
				notes.add(0, noteDao.get(usersnotes.get(i).getIdn()));
			}else {
			notes.add(noteDao.get(usersnotes.get(i).getIdn()));
			}
		}
		}
		
		
	    return notes; 
	  }
	  
	  @GET
	  @Path("/archived")
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<Note> getNotesArchivedJSON(@Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		NoteDAO noteDao = new JDBCNoteDAOImpl();
		noteDao.setConnection(conn);		
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		UsersNotesDAO usersnotesDao = new JDBCUsersNotesDAOImpl();
		usersnotesDao.setConnection(conn);
		List<UsersNotes> usersnotes;
		usersnotes = usersnotesDao.getAllByUser(user.getIdu());
		
		
		List<Note> notes = new ArrayList<Note>();
		
		for(int i=0;i<usersnotes.size();i++) {
			if (usersnotes.get(i).getArchived() == 1) {
			if(usersnotes.get(i).getPinned() == 1) {
				notes.add(0, noteDao.get(usersnotes.get(i).getIdn()));
			}else {
			notes.add(noteDao.get(usersnotes.get(i).getIdn()));
			}
		}
		}
		
		
	    return notes; 
	  }
	  
	  @GET
	  @Path("/{noteid: [0-9]+}")	  
	  @Produces(MediaType.APPLICATION_JSON)
	  public Note getNoteJSON(@PathParam("noteid") long noteid,
			  					@Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		NoteDAO noteDao = new JDBCNoteDAOImpl();
		noteDao.setConnection(conn);
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		UsersNotesDAO usersnotesDao = new JDBCUsersNotesDAOImpl();
		usersnotesDao.setConnection(conn);
		UsersNotes usernote;
		
		Note note = noteDao.get(noteid);
		usernote = usersnotesDao.get(user.getIdu(),note.getIdn());
		
		
		if (note != null && usernote != null ) return note;
		else throw new CustomNotFoundException("Note ("+ noteid + ") is not found");		   
	  }
	  
	  @GET
	  @Path("/search/{search:[a-zA-Z0-9_%20]+}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<Note> getNotesSearchJSON(@PathParam("search") String search, @Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		NoteDAO noteDao = new JDBCNoteDAOImpl();
		noteDao.setConnection(conn);	
		
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		UsersNotesDAO usersnotesDao = new JDBCUsersNotesDAOImpl();
		usersnotesDao.setConnection(conn);
		UsersNotes usersnotes=null;
		
		
		List<Note> allnotes = new ArrayList<Note>();
		List<Note> notes = new ArrayList<Note>();
		allnotes= noteDao.getAllBySearchAll(search);
		
		
	
		
		for(int i=0;i<allnotes.size();i++) {
			usersnotes=usersnotesDao.get(user.getIdu(), allnotes.get(i).getIdn());
			if(usersnotes !=null) {
				notes.add(allnotes.get(i));
			}
		usersnotes = null;
		}
		
		
	    return notes; 
	  }
	  
	  
	  
	 
	  
	  @POST	  	  
	  @Consumes(MediaType.APPLICATION_JSON)
	  public Response post(Note newNote, @Context HttpServletRequest request) throws Exception {	
		Connection conn = (Connection) sc.getAttribute("dbConn");
		NoteDAO noteDao = new JDBCNoteDAOImpl();
		noteDao.setConnection(conn);  	 
		System.out.println("newNota "+ newNote.getTitle());
		UsersNotesDAO usersnotesDao = new JDBCUsersNotesDAOImpl();
		usersnotesDao.setConnection(conn);  
		
		UsersNotes usersnotes = new UsersNotes();
		
		Response res;
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		List<String> messages = new ArrayList<String>();

		if (!newNote.validate(messages))
			    throw new CustomBadRequestException("Errors in parameters");
		//save note in DB
		long id = noteDao.add(newNote);
		usersnotes.setIdu(user.getIdu());
		usersnotes.setIdn((int)id);
		usersnotes.setOwner(1);
		usersnotes.setArchived(0);
		usersnotes.setPinned(0);
		
		usersnotesDao.add(usersnotes);
		
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
	  @Path("/{noteid: [0-9]+}")
		@Consumes(MediaType.APPLICATION_JSON)
		public Response put(Note noteUpdate,
							@PathParam("noteid") long noteid,
							@Context HttpServletRequest request) throws Exception{
		  Connection conn = (Connection)sc.getAttribute("dbConn");
		  NoteDAO noteDao = new JDBCNoteDAOImpl();
		  noteDao.setConnection(conn);   
		  
		 
			
		  Response response = null;
					
		  //We check that the note exists
		  Note note = noteDao.get(noteUpdate.getIdn());
		  if (note != null){
					if (note.getIdn()!=noteid) throw new CustomBadRequestException("Error in id");
					else 
					{
						List<String> messages = new ArrayList<String>();
						if (noteUpdate.validate(messages)) noteDao.save(noteUpdate);						
						else throw new CustomBadRequestException("Errors in parameters");						
					}
				}
		  else throw new WebApplicationException(Response.Status.NOT_FOUND);			
		  
		  return response;
		}
	  
	  
	  

	@DELETE
	  @Path("/{noteid: [0-9]+}")	  
	  public Response deleteNote(@PathParam("noteid") long noteid,
			  					  @Context HttpServletRequest request) {
		  
		Connection conn = (Connection) sc.getAttribute("dbConn");
		NoteDAO noteDao = new JDBCNoteDAOImpl();
		noteDao.setConnection(conn); 
		
		UsersNotesDAO usersnotesDao = new JDBCUsersNotesDAOImpl();
		usersnotesDao.setConnection(conn);  
	
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		UsersNotes usernote = usersnotesDao.get(user.getIdu(), noteid);
		Note note = noteDao.get(noteid);
		if(usernote.getOwner() == 1 && note!= null) {
			List<UsersNotes> usersnotes =new ArrayList<UsersNotes>();
			usersnotes=usersnotesDao.getAllByNote(noteid);
			
			for(int i=0;i<usersnotes.size();i++) {
				usersnotesDao.delete(usersnotes.get(i).getIdu(), usersnotes.get(i).getIdn());
			}
			noteDao.delete(noteid);
			return Response.noContent().build(); //204 no content 
		}else {		
		if (note != null){
					
					usersnotesDao.delete(user.getIdu(), noteid);
					return Response.noContent().build(); //204 no content 
		}
		else throw new CustomBadRequestException("Error in id");		
			
	  }
	}
	  
} 
