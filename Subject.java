import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Subject {

	static String ID;
	File file;
	static final int nTests = 15; // 15 tests per
	static final int nMenuTypes = 3;
	static final int  nInputTypes = 3;

	int testsDone;
	int menusDone;
	int inputsDone;

	boolean didTrail;

	List<Path> pathsDone= new ArrayList<Path>();


	public Subject() {
		super();
		inputsDone=0;
		menusDone = 0;
		testsDone=0;
		didTrail=false;
	}


	public void setID(String iD) {
		ID = iD;
		file= new File(iD);
	}







}
