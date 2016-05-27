package interest_rate;
import javax.swing.*;
import java.awt.*;

public class HomeLoan extends JFrame{

	public HomeLoan(){

		this.setLocationRelativeTo(null);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Gains et pertes pour les pr�ts immobilier");
		this.setSize(600, 200);
		this.add(lab);
		
		// Data of the board : rate according to the grade and the type of loan
		Object[][] data = {
				{"Taux proposable", "0,84", "1,44", "1,84", "2,54", "3,04"},
				{"Gains ou pertes", "-32 171,77", "-13 047,62", "58 040,63", "23 404,51", "40 564,45"}
		};

		// Title of the columns
		String  title[] = {	"", 
				"Tr�s bon client",
				"Bon client",
				"Maison-m�re",
				"Client risqu�",
				"Client tr�s risqu�"};
		JTable board = new JTable(data, title) ;
		
		this.getContentPane().add(new JScrollPane(board));
		
	}   
	//	this.getContentPane().add(new JLabel("Gains et pertes pour un pr�t de 400 000 Euros sur 15 ans"));
	JLabel lab = new JLabel() ;
	

}


