package dao;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.sql.Connection;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import model.UsersNotes;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestUsersNotesDAO {

	static DBConn dbConn;
	static UsersNotesDAO usersNotesDAO;
	static Connection conn;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dbConn = new DBConn();
		conn = dbConn.create();
	    usersNotesDAO = new JDBCUsersNotesDAOImpl();
		usersNotesDAO.setConnection(conn);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
		dbConn.destroy(conn);
	}

	@Before
	public void setUpBeforeMethod() throws Exception {
	
	}

	@Test
	public void test1BaseData() {
		
		List<UsersNotes> usersNotesList = usersNotesDAO.getAll();
		
		UsersNotes usersNotes = usersNotesDAO.get(0,0);
		
		assertEquals(usersNotes.getOwner(),0);
		assertEquals(usersNotes.getArchived(),0);
		assertEquals(usersNotes.getPinned(),1);
		
		assertEquals(usersNotesList.get(0).getPinned(),usersNotes.getPinned());			
			
		
	}
	
	@Test
	public void test2BaseDataByUser() {
		
		List<UsersNotes> usersNotesList = usersNotesDAO.getAllByUser(0);
		for(UsersNotes usersNotes: usersNotesList)			
			assertEquals(usersNotes.getIdu(),0);			
	}
	
	@Test
	public void test3BaseDataByNote() {
		
		List<UsersNotes> usersNotesList = usersNotesDAO.getAllByNote(0);
		for(UsersNotes usersNotes: usersNotesList)			
			assertEquals(usersNotes.getIdn(),0);			
	}
	
	@Test
	public void test4Add(){
		UsersNotes usersNotes01 = new UsersNotes();
		usersNotes01.setIdu(3);
		usersNotes01.setIdn(4);
		usersNotes01.setOwner(0);
		usersNotes01.setArchived(0);
		usersNotes01.setPinned(1);
		usersNotesDAO.add(usersNotes01);
		
		UsersNotes usersNotes02 = usersNotesDAO.get(3,4);
		
		assertEquals(3,usersNotes02.getIdu());
		assertEquals(4,usersNotes02.getIdn());
		assertEquals(0,usersNotes02.getOwner());
		assertEquals(0,usersNotes02.getArchived());
		assertEquals(1,usersNotes02.getPinned());
		
		
		usersNotesDAO.getAll();	
		
	}
	
	

	
	
	@Test
	public void test5Modify(){
		
		UsersNotes usersNotes01 = usersNotesDAO.get(3,4);
		usersNotes01.setArchived(1);
		usersNotes01.setPinned(0);
		usersNotesDAO.save(usersNotes01);
		
		
		UsersNotes usersNotes02 = usersNotesDAO.get(3,4);
		assertEquals(3,usersNotes02.getIdu());
		assertEquals(4,usersNotes02.getIdn());
		assertEquals(0,usersNotes02.getOwner());
		assertEquals(1,usersNotes02.getArchived());
		assertEquals(0,usersNotes02.getPinned());
		
		
	}
	
	@Test
	public void test6Delete(){
		 
		usersNotesDAO.delete(3,4);
		List<UsersNotes> usersNotesList = usersNotesDAO.getAll();
		 
		 UsersNotes usersNotes01 = new UsersNotes();
		 usersNotes01.setIdu(3);
		 usersNotes01.setIdn(4);
		 usersNotes01.setOwner(0);
		 usersNotes01.setArchived(1);
		 usersNotes01.setPinned(0);
		 
		 UsersNotes usersNotes02 = new UsersNotes();
		 usersNotes01.setIdu(3);
		 usersNotes01.setIdn(4);
		 usersNotes01.setOwner(0);
		 usersNotes01.setArchived(0);
		 usersNotes01.setPinned(1);
		 
		for(model.UsersNotes usersNotes: usersNotesList) {
				assertNotEquals(usersNotes,usersNotes01);
				assertNotEquals(usersNotes,usersNotes02);
		}
		 usersNotesDAO.getAll();	
		 
	}

}
