
import java.util.ArrayList;
import java.util.List;

public class Path {

	//String path = new String();
	List<Integer> steps;
	String[] selections;
	String finalSelection;
	int x,y,sub;

	public static List<Path>getPaths(List<Path>p, int x, int y, int sub){

		List<Path> paths = new ArrayList();
		Path temp;

		for (int i = 0; i < p.size(); i++) {
			temp=p.get(i);
			if(temp.x==x && temp.y==y && temp.sub==sub){
				paths.add(temp);
				System.out.println(temp);
			};
		}
		//System.out.println(paths.size());

		///ovdje
		p.removeAll(paths);
		return paths;
	}

	public static List<Path>getPaths(List<Path>p, int y, int sub){

		List<Path> paths = new ArrayList();
		Path temp;
		for (int i = 0; i < p.size(); i++) {
			temp=p.get(i);
			if(temp.y>=y && temp.sub==sub){
				paths.add(temp);
			};
		}
		p.removeAll(paths);
		return paths;
	}

	public static List<Path>getPaths(List<Path>p, int sub){

		List<Path> paths = new ArrayList();
		Path temp;
		for (int i = 0; i < p.size(); i++) {
			temp=p.get(i);
			if(temp.sub==sub){
				paths.add(temp);
			};
		}
		p.removeAll(paths);
		return paths;
	}

	public Path(List<Integer> steps, String[] selections, String finalSelection) {
		super();
		this.steps = steps;
		this.selections = selections;
		this.finalSelection = finalSelection;
	}

	public void setXYsub(){
		this.x=this.steps.get(0)-1;// samo na menubaru -1 zbog starta


		for(int i = 1; i<steps.size();i++){ //dal da ide od 0 i korke u desno broji kao prema dolje?
			this.y+=this.steps.get(i);
		}
		this.sub=this.steps.size()-1;

		//System.out.print("x="+x+" y="+ y+" sub="+sub+" ");

	}
	@Override
	public String toString() {

		String s= new String();
        int i =0;
		for(; i< selections.length-1;i++){
      		s=s+selections[i]+"->";
        }
        s=s+selections[i]+ " steps:" +steps;
        return s;
	}

	public String getPath() {

		String s= new String();
        int i =0;
		for(; i< selections.length-1;i++){
      		s=s+selections[i]+"->";
        }
        s=s+selections[i];
        return s;
	}


}
