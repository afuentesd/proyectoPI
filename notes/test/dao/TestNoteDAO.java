package dao;
import static org.junit.Assert.assertEquals;

import java.sql.Connection;


import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;


import model.Note;




@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestNoteDAO {

	static DBConn dbConn;
	static NoteDAO noteDAO;
	static Connection conn;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dbConn = new DBConn();
		conn = dbConn.create();
	    noteDAO = new JDBCNoteDAOImpl();
		noteDAO.setConnection(conn);
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
		
		Note note01 = noteDAO.get(0);
		assertEquals(note01.getIdn(),0);
		assertEquals(note01.getTitle(),"PendingRebelList");

		Note note02 = noteDAO.get(1);
		assertEquals(note02.getIdn(),1);
		assertEquals(note02.getContent(),"Obi Wan Kenobi, Jyn Erso and Cassian Andor");
		
		Note note03 = noteDAO.get(2);
		assertEquals(note03.getIdn(),2);
		assertEquals(note03.getTitle(),"PendingImperialList");
		
		Note note04 = noteDAO.get(3);
		assertEquals(note04.getIdn(),3);
		assertEquals(note04.getContent(),"Moff Tarkin and Orson Krennic");
		
		Note note05 = noteDAO.get(4);
		assertEquals(note05.getIdn(),4);
		assertEquals(note05.getContent(),"A new bowcaster and a new Dejarik game");
		
		Note note06 = noteDAO.getAllBySearchTitle("PendingRebelList").iterator().next();
		assertEquals(note06.getContent(),"Leia Organa, Luke Skywalker and Han Solo");
		
		Note note07 = noteDAO.getAllBySearchContent("Kenobi").iterator().next();
		assertEquals(note07.getTitle(),"KnockedOutRebelList");
		
		Note note08 = noteDAO.getAllBySearchAll("ing").iterator().next();
		assertEquals(note08.getContent(),"Leia Organa, Luke Skywalker and Han Solo");
		
		
		noteDAO.getAllBySearchTitle("Rebel");
		noteDAO.getAllBySearchContent("and");
		noteDAO.getAllBySearchAll("Imperial");
						
		noteDAO.getAll();
		
		
		
	}
	
	
	@Test
	public void test2Add(){
		Note note01 = new Note();
		note01.setTitle("newNote");
		note01.setContent("new content");
		noteDAO.add(note01);
		
		Note note02 = noteDAO.getAllBySearchTitle("newNote").iterator().next();
		assertEquals(note01.getContent(),note02.getContent());
		
		noteDAO.getAll();
	}
	
	@Test
	public void test3Modify(){
		Note note01 = noteDAO.getAllBySearchTitle("newNote").iterator().next();
		note01.setTitle("newNoteUpdated");
		note01.setContent("new content updated");
		noteDAO.save(note01);
		
		Note note02 = noteDAO.getAllBySearchTitle("newNoteUpdated").iterator().next();
		assertEquals(note01.getContent(),note02.getContent());
		
		noteDAO.getAll();
	}
	
	@Test
	public void test4Delete(){
		
		 Note note01 = noteDAO.getAllBySearchTitle("newNoteUpdated").iterator().next();
		 int idNote= note01.getIdn();
		 noteDAO.delete(idNote);
		 
		 Note note02 = noteDAO.get(idNote);
		 assertEquals(null,note02);
		 
		noteDAO.getAll();
	}

}
