import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.MenuSelectionManager;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("serial")
public class Menu extends JMenu implements ActionListener,MenuListener{

	//static int N = 9;
	//static Menu[] instances = new Menu[9];
	static List instances = new ArrayList();
	static String[] mode= new String[3];// = {"SCROLL KOTAČIĆEM","TIPKOVNICOM","KLASIČNO MIŠEM"};

	//static String[] mode = {"SCROLL KOTAČIĆEM","TIPKOVNICOM","KLASIČNO MIŠEM"};

	static int currentMenu = 0;
	//niti jedna varijabla nije static, svaki menu ima svoje variable
	public int N; //ne mora i nema potrebe da bude static
	public String[][][] menuLists = new String[N][][];

	public JMenuBar menuBar;
	JMenu menu0;
    public JMenuItem[][] menuItems; //kasnije

    public MenuSelectionManager msm;

    List<Path> allPaths= new ArrayList<Path>(); //from where we define test and trail paths

    List<Path> testPaths= new ArrayList<Path>(); //while testing paths = testPaths
    List<Path> trailPaths= new ArrayList<Path>(); //while in trail mode paths = trailPaths

    List<Path> paths= new ArrayList<Path>();

    public JFrame frame;

    boolean wrong = false;
    static boolean train = false;

    static boolean inSleep = false;

    Path currentPath;

    int n=1,m=9;
    public static void outln(Object o){
        //System.out.println(o.toString()); //u debugging modu
    }
    public static void out(Object o){
        //System.out.print(o.toString()); //u debugging modu
    }


    //kako radi shuffle
	//Collections.shuffle(Arrays.asList(array)); //mijenja raspored u menubaru
	//Collections.shuffle(Arrays.asList(Tools));//mijenja raspored u Tools
	//Collections.swap(Arrays.asList(Tools), 0, Arrays.asList(Tools).indexOf("Tools")); //vraća Tools na prvo mjesto

    public void shuffleMenus(){

		for(int i = 0;i<this.menuLists.length;i++){ //ili 2 ili
			for(int j = 0;j<this.menuLists[i].length;j++){
				String s= this.menuLists[i][j][0];
				//outln(s);
				Collections.shuffle(Arrays.asList(this.menuLists[i][j]));
				Collections.swap(Arrays.asList(this.menuLists[i][j]), 0, Arrays.asList(this.menuLists[i][j]).indexOf(s));
				//kod shufflanja "1" "2" "3", nećemo više dohvatiti isti broj putanja s istim uvjetima
			}
		}
	}
	public Menu(String[][][] menuLists, JFrame frame) {
		super();
		this.menuLists = menuLists;
		shuffleMenus();
		N = menuLists[0].length;
		this.frame=frame;
		this.menuBar=createMenuBar();
		instances.add(this);

	}


    public JMenuBar createMenuBar() {
      	outln("E: createMenuBar()");

	    menuBar = new JMenuBar();
	    menu0 = new JMenu("Start");
	    menu0.setName("Start");
	    //menu0.removeMouseListener(menu0.getMouseListeners()[0]); //disjeblat miš

	    menu0.addMenuListener(this); //TUUUU
	    menuBar.add(menu0);

	    for(int i = 0; i< N; i++){
			menuBar.add(createJMenu(menuLists[0][i], 1));
			menuBar.getComponent(i).setPreferredSize(new Dimension(55,22));
	    }
	    for(int i = 1; i< N+1; i++){
				menuBar.getComponent(i).setVisible(false);
				//System.out.println( "tu:"+((JMenuItem) menuBar.getComponent(i)).getText());
			}

	    setMenuSelectionManager();
	   // menu0.doClick();


	    printMenuBar();
	    outln("L: createMenuBar()");
	    return menuBar;
    }

    public void addMenuListenerToMenu0(){
  	  	  menu0.addMenuListener(this);
    }



    public JMenu createJMenu(String[] s, int lvl){

    	JMenu menu = new JMenu(s[0]);
    	menu.setName(s[0]); //mozda ne treba

		//menu.removeMouseListener(menu.getMouseListeners()[0]); //za disejblat mouse (i na menu0 ucini istos)
		//System.out.println(menu.getKeyListeners());
		// menu.removeKeyListener(menu.getKeyListeners()[1]);

    	 for(int i = 1; i<s.length;i++){

			 try{

				 int nth = Integer.parseInt(s[i])-1;
				 //System.out.println(nth);
				 menu.add(createJMenu(menuLists[lvl][nth],lvl+1));

			 }catch(Exception NumberFormatException ){

				 JMenuItem menuItem = new JMenuItem (s[i]);
				 menuItem.setName(s[i]);
				 menuItem.setPreferredSize(new Dimension(130,22)); //nije pretjerano važno
				 menuItem.addActionListener(this);
				 menu.add(menuItem);

			 }

    	 }

    	return menu;
    }

    public Path getRandomPath(){
    	outln("E: getRandomPath()");

    	int randomNum = ThreadLocalRandom.current().nextInt(0, this.paths.size());
    	currentPath=paths.get(randomNum);
    	outln("L: getRandomPath()");

    	return currentPath;

    }

    public void setMenuSelectionManager(){
    	msm =  MenuSelectionManager.defaultManager();
    }

	@Override

    public void actionPerformed(ActionEvent e) {
    	outln("E: actionPerformed() ActionListener");

        JMenuItem source = (JMenuItem)(e.getSource());
        System.out.println("Zadano:"+frame.getTitle());
        System.out.println("Odabrano: "+source.getText());

        menu0.addMenuListener(this);

        for(int i= 1 ; i<menuBar.getMenuCount();i++ ){
			menuBar.getMenu(i).setVisible(false);
			menuBar.getMenu(i).setArmed(false);

		}

        if(source.getText()==currentPath.finalSelection){

          	if(!train){
              	Timer.end=System.nanoTime();
                System.out.println("Vrijeme: " + Timer.getDuration());
          	}

        	//if(!train)((JLabel)frame.getContentPane().getComponent(1)).setText(" TOČNO");
			frame.setVisible(true);
        	frame.pack();

        	wrong=false;
        	paths.remove(currentPath);
        	System.out.println("ostalo:"+paths.size()+"\n");

        	if(paths.size()==0){

        		//inSleep=true;

    			for(int i= 1 ; i<Start.menu.menuBar.getMenuCount();i++ ){
        			Start.menu.menuBar.getMenu(i).setVisible(false);
        			Start.menu.menuBar.getMenu(i).setArmed(false); //poništi eventualne zaostale označene izbore u menubaru
        			//ne radi iz nekog razloga
    			}
    			frame.pack();
    			frame.setVisible(true);


    			//KeyAdapter KL= (KeyAdapter)frame.getKeyListeners()[0];
				//MouseAdapter ML=(MouseAdapter) frame.getMouseListeners()[0];
				//frame.removeKeyListener(frame.getKeyListeners()[0]);
				//frame.removeMouseListener(frame.getMouseListeners()[0]);


    			currentMenu++;

    			try{
    				Menu menu = (Menu) Menu.instances.get(currentMenu);
    	       		//Start.setMenu(menu);
            		//Start.menu.paths=Start.menu.testPaths;
    			}catch(Exception e1){

    				((JLabel)frame.getContentPane().getComponent(1)).setText("Testiranje gotovo, Zahvaljujemo se!");
    				frame.setVisible(true);
    				frame.pack();
    				Timer.writeToFile();

    				try {
						TimeUnit.SECONDS.sleep(3);

    				} catch (Exception e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
						System.exit(0);
					}

    				System.exit(0);


    			}



        		String title="Kada se pojavi putanja krenite riješavati: ";

        		if (currentMenu<=3){
        			title=title+mode[0];
        			System.out.println("tututu"+Menu.mode[0]+mode[0]);
            		if(!train)((JLabel)frame.getContentPane().getComponent(1)).setText(Menu.mode[0]);
        		}else if(currentMenu==4){
        			//title="Kliknite sada mišem na sivo područje i maknite ruku s miša. "+title+mode[1];
        			title=title+mode[1];
        			if(!train)((JLabel)frame.getContentPane().getComponent(1)).setText(mode[1]);
        		}else if (currentMenu>4 && currentMenu<7){
        			title=title+mode[1];
        			if(!train)((JLabel)frame.getContentPane().getComponent(1)).setText(mode[1]);
        		}else if(currentMenu==7){
        			//title="Vratite ruku na miš i zadržite se u sivom području do kraja testiranja. "+title+mode[2];
        			title=title+mode[2];
        			if(!train)((JLabel)frame.getContentPane().getComponent(1)).setText(mode[2]); ///
        		}else{
        			title=title+mode[2];
        			if(!train)((JLabel)frame.getContentPane().getComponent(1)).setText(mode[2]); ///
        		}

        		frame.setTitle(title); //kako ovo prođe a sve ispod ne
        		Menu.train=true; ///radi li? Za scroll ne...
        		//Start.area.setText(""); //zakaj ovo ne radi? sav se izblesira prije  :(
           		//frame.setMenuBar(null);
	    	    frame.requestFocusInWindow();
           		frame.setVisible(true);
           		frame.pack();

        		try {
        			TimeUnit.SECONDS.sleep(5);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				//frame.addKeyListener(Start.frameKeyAdapter);
				//frame.addMouseListener(Start.frameMouseAdapter);

    			Menu menu = (Menu) Menu.instances.get(currentMenu);
	       		Start.setMenu(menu);
        		Start.menu.paths=Start.menu.testPaths;

    			Menu.train=false;///
	    	    //Start.area.setText("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    			frame.setTitle(Start.menu.getRandomPath().getPath());
    			frame.setJMenuBar(Start.menu.menuBar);
           		frame.requestFocusInWindow();
    			frame.setVisible(true);

    			//frame.addKeyListener(KL);
				//frame.addMouseListener(ML);
				//menu0.addMenuListener(this); //ne pomaže
    	    	//Menu.inSleep=false;
    	    	//System.out.println("End Sleep");

    			frame.pack();

        	}else{

            	Path p= getRandomPath();
                frame.setTitle(p.getPath());
              	frame.setVisible(true);


        	}

        }else{

        	//DONE inkrementirati neku varijablu koja označava brojPogrešnih (do točnog odabira)
        	//TODO pratiti koliko smo udaljeni od točnog? -previše
        	//TODO getPath(String finalSelection)

          	wrong=true;
          	if(!train)Timer.countWrong();
         	//System.out.println(frame.getContentPane().getComponentCount());
         	//if(!train)((JLabel)frame.getContentPane().getComponent(1)).setText(" NETOČNO");
         	Toolkit.getDefaultToolkit().beep();
			//DONE VAŽNO kamo i kako zapisivati pogrešne? Definirati koliko su pogriješili?!
        }

    	outln("L: actionPerformed() ActionListener");
	}



	public void menuSelected(MenuEvent arg0) {
		outln("E: menuSelected() MenuListener menu0");

		outln("inSleep:"+inSleep);

		if(!inSleep){

			 if(!wrong && !train){
					System.out.println("Starting Time");

					//if(currentMenu>)
				  	//((JLabel)frame.getContentPane().getComponent(1)).setText("Vrijeme ide");

				  	Timer.start=System.nanoTime();
				 }
				 menu0.removeMenuListener(this);

				 for(int i= 0 ; i<menuBar.getMenuCount();i++ ){
					menuBar.getMenu(i).setVisible(true);
					menuBar.getMenu(i).setArmed(false); //poništi eventualne zaostale označene izbore u menubaru
					//menuBar.getMenu(i).setEnabled(true);//umjesto setVisible
				}

		}


	    outln("L: menuSelected() MenuListener menu0");
	}
	//mora biti private
	private void printMenuBar(){

		outln("E: printMenuBar()");
		JMenuBar menubar1 = menuBar;
		//outln(menubar1.getComponentCount());
		for (int j = 0; j < menubar1.getMenuCount(); j++) {
			JMenu menu1 = menubar1.getMenu(j);
			String path[] = new String[1];
			path[0]=menu1.getText();
	       	out(j+1+": ");
			outln(path[0]);
			ArrayList<Integer> steps = new ArrayList<Integer>();
			steps.add(j+1);
			printMenu(menu1,1,steps,path);
		}
		  System.out.println(allPaths.size());

		  	List<Path> tmpPaths = new ArrayList<Path>();
		    List<Path> tmpPaths2 = new ArrayList<Path>();
		    List<Path> tmpPaths3 = new ArrayList<Path>();

		    tmpPaths.addAll(Path.getPaths(allPaths,  3));
		    tmpPaths2.addAll(Path.getPaths(allPaths, 15, 2));
		    tmpPaths3.addAll(Path.getPaths(allPaths, 1));

			System.out.println("tmpPaths.size() "+ tmpPaths.size());
			System.out.println("tmpPaths2.size() "+ tmpPaths2.size());
			System.out.println("tmpPaths3.size() "+ tmpPaths3.size());

			int randomNum, randomNum2;

			//int n=1,m=0,k=0;
			int n=3,m=2,k=0;

			for (int i = 0; i < n; i++) {

				try{
					randomNum = ThreadLocalRandom.current().nextInt(0, tmpPaths.size());
					testPaths.add(tmpPaths.get(randomNum));
					tmpPaths.remove(randomNum);

				}catch (java.lang.IndexOutOfBoundsException e){
					m+=n;
					System.out.println("ex "+"m="+m);
					break;
				} catch( java.lang.IllegalArgumentException e){
					m+=n;
					System.out.println("ex2 "+"i="+i);
					break;
				}


			}

			for (int i = 0; i < m; i++) {

				try{
					randomNum2 = ThreadLocalRandom.current().nextInt(0, tmpPaths2.size());
					testPaths.add(tmpPaths2.get(randomNum2));
					tmpPaths2.remove(randomNum2);

				}catch ( java.lang.IndexOutOfBoundsException e){
					k+=m;
					System.out.println("ex1 "+"m="+m);
					break;
				} catch( java.lang.IllegalArgumentException e){
					System.out.println("ex2 "+"i="+i);
					k+=m;
					break;
				}



			}

			for (int i = 0; i < k; i++) {
				randomNum2 = ThreadLocalRandom.current().nextInt(0,tmpPaths3.size());
				testPaths.add(tmpPaths3.get(randomNum2));
				tmpPaths3.remove(randomNum2);
			}

			//testPaths.addAll(Path.getPaths(allPaths, 3));
			//testPaths.addAll(Path.getPaths(allPaths,  3));

			System.out.println("testPaths.size() "+testPaths.size());
			//System.out.println(testPaths);

			for (int i = 0; i < testPaths.size(); i++) {
				System.out.println((i+1)+" " + testPaths.get(i).getPath());

			}

			outln("L: printMenuBar()");
	}
	//mora biti private
	private void printMenu(JMenu menu1, int lvl, ArrayList<Integer> steps, String path[]){

		outln("E: printMenu()");
	    for (int j = 0; j < menu1.getMenuComponentCount(); j++) {

	        java.awt.Component comp = menu1.getMenuComponent(j);

	        if (comp instanceof JMenu) {
	        	JMenu menu = (JMenu) comp;
	        	for(int i = 0; i<lvl;i++)out("\t ");

	        	out(j+1+": ");
	            String[] newPath = new String[path.length+1];
	            for(int i=0 ; i< path.length;i++)newPath[i]=path[i];
	            newPath[newPath.length-1]=menu.getText();
//not actually a selectable path:
//	            for(i = 0; i< newPath.length;i++){
//            		out(newPath[i]+"/");
//            	}
//	            System.out.println("");
	            ///System.out.println(menu.getText());
	            @SuppressWarnings("unchecked")
				ArrayList<Integer> newSteps = (ArrayList<Integer>)steps.clone();
	            newSteps.add(j+1);
	            printMenu(menu,lvl+1,newSteps,newPath);
	        }
	        else if (comp instanceof JMenuItem) {
	            JMenuItem menuItem1 = (JMenuItem) comp;
	            for(int i = 0; i<lvl;i++)out("\t ");
	           	out(j+1+": ");

	            @SuppressWarnings("unchecked")
				ArrayList<Integer> newSteps = (ArrayList<Integer>)steps.clone();
	            //newSteps=steps <- pogresno!!
	            newSteps.add(j+1);
	            //System.out.println(newSteps.equals(steps)); <- ==TRUE!!


	            Path p = new Path(newSteps,new String[path.length+1],menuItem1.getText());
	            p.selections = new String[path.length+1];
	            for(int i=0 ; i< path.length;i++)p.selections[i]=path[i];
	            p.selections[p.selections.length-1]=menuItem1.getText();
	            p.setXYsub();
	            //p.steps=newSteps;
	            //p.finalSelection=menuItem1.getText();
	            ///System.out.println(p.finalSelection);

	            allPaths.add(p);
	        }

	    }
		outln("L: printMenu()");
	}
	@Override
	public void menuCanceled(MenuEvent arg0) {

	}


	@Override
	public void menuDeselected(MenuEvent arg0) {

	}




}
