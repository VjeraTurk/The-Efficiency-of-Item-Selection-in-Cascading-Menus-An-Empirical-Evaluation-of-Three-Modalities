import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.JTextField;



public class Start {

		//prozor
		public static JFrame frame;
		//pokazivač na trenutni meni
	    public static JTextArea area;
		public static Menu menu;

		//varijable za kretanje kroz meni sa scrollom
	    static int current[];
	    static boolean onStart;
	    static boolean onMenubar;
	    static JMenuItem JM;
	    static Subject s;
	    static String[] FileHeader=new String[9];

	    // funkcija za ispis, skračuje System.out.println() u outln()
	    public static void outln(Object o){
	        //System.out.println(o.toString()); //koristi u debugging modu
	    }
	     public static void setMenu(Menu m){
	    	menu = m;
	     }
	     public static Menu getMenu(){
	    	return menu;
	     }
	    // funkcija koja inicijalizira prozor
	    public static void initFrame(){

	      	outln("E: initFrame()");
			frame = new JFrame();
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    frame.setAlwaysOnTop(true); //set Always on top
		    frame.setLayout(new BorderLayout());
		    int width=700;
		    int height=700;
		    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		    //ILI:
		    frame.setSize(dim);
			frame.setPreferredSize(dim);
			frame.setMinimumSize(dim);
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

		    //ILI:
			/*frame.setSize(width, height);
			frame.setPreferredSize(new Dimension(width,height));
			frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
			 */
			frame.setVisible(true);

		    area = new JTextArea();
	        area.setLineWrap(true);
	        area.setWrapStyleWord(true);
	        area.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

	        area.setText(
	        		"Dobrodošli! Molimo Vas da pročitate i slijedite ove upute\n\n"
	        		+ "Predviđeno trajanje testiranja je 10 minuta."
	        		+ "Testirat ćete 3 modaliteta unosa (kretanja kroz izbornik).\nSvaki se testira na 3 izbornika različitih složenosti - jednostavan, srednje složen, složen. \n"
	        		+ "modaliteti unosa su:\n"
	        		+ " a) klasično mišem "
	        		+ " b) klasično tipkovnicom "
	        		+ " c) korištenjem ISKLJUČIVO kotačića na mišu (scroll wheel)\n\n"

	        		+ "-vaš zadatak u svakom trenutku bit će izabrati putanju koju vidite na samom vrhu prozora koristeći trenutno zadani modalitet.\n"
	        		+ "-uzmite sada trenutak da UOČITE PUTANJU NA VRHU PROZORA!\n"
	        		+ "-ukoliko napravite pogrešan odabir, putanja ostaje ista dokle god ne napravite točan izbor.\n"
	        		+ "-o promjeni modaliteta biti ćete obavješteni na vrhu prozora, a trenutni modalitet pisat će Vam svo vrijeme na dnu prozora.\n\n"
	        		//+ "Bit ćete obavješteni na vrhu prozora kada da kursor odvedete u žuto područje i KLIKNETE na njega. Ukoliko izađete iz tog područja nećete moći napraviti odabir sve dok se ne vratite i ponovno kliknete na njega. \n"
	        		+ "Sada je predviđeno da prije testiranja isprobate 3 modaliteta kretanja, OSOBITO putanje koje otvaraju nove razine (podmenie): \n\n "
	        		+" a) klasično mišem \n\tZA START:  lijevi klik na \"Start\"\n \tPOTVRDA: lijevi klik\n"
	        		+ "!!! Isprobajte SADA 3-5 putanja ovim modalitetom !!!\n\n"

					+ "Pozicionirajte sad kursor bilo gdje na žutom području i kliknite. Maknite ruku s miša i  premjestite na tipkovnicu.\n"
					+ " b) klasično tipkovnicom (tipke lijevo/desno/dolje/gore i Enter)\n\tZA START: Enter (Return key) \n\tOTVARANJE PODMENIA: tipka desno \n\tPOTVRDA: Enter (Return key)\n"
	        		+ "!!! Isprobajte SADA 3-5 putanja ovim modalitetom i pritom uočite da se kroz izbornik možete kretati kružno tj. sa posljednjeg izbora skočiti na prvi.!!!\n\n"
	        		+ "Vratite ruku na miš, kliknite na žuto područje, nastojte ga ne micati i zadržite ga u žutom području.\n"
	        		+ " c) korištenjem ISKLJUČIVO kotačića (gore/dolje i klik na kotačić) \n\tZA START: klik na kotačić\n\tOTVARANJE PODMENIA: klik na kotačić\n\tPOTVRDA: klik na kotačić\n"
	        		+ "!!! Isprobajte SADA 3-5 putanja ovim modalitetom i pritom uočite da se kroz izbornika možete kretati kružno. !!!\n\n"

					+"NAPOMENA 1: prilikom korištenja tipkovnice i prilikom korištenja kotačića kursor zadržite svo vrijeme u ŽUTOM PODRUČJU na dnu prozora.\n"
	        		+"NAPOMENA 2: Usred svake promjene menija ekran ce se zamznuti nekoliko sekundi, nastojte tada ne pritiskati ništa.\n"
	        		+"NAPOMENA 3: prilikom korištenja kotačića nemate mogućnost povratka na prethodnu razinu, u slučaju pogreške odaberite pogrešan odabir i ponovno slijedite putanju.\n"
	        		+ "Vaš id je prvo slovo imena + prezime, bez kvačica, npr. Željka Markić: zmarkic\n"
	        		+ "Nakon što upišete vaš id u za to predviđen prostor na dnu prozora i (potvrdite unos pritiskom na Enter) započinje testiranje. Na kraju testiranja program će se sam ugasiti.\n");

	        area.setEditable(false);

		    JLabel out = new JLabel();
		    Font font = out.getFont();
		    out.setFont(new Font(font.getName(),Font.BOLD,40));
		    JTextField name = new JTextField();
		    name.setSize(new Dimension(50,50));
		    name.setPreferredSize(new Dimension(50,50));
		    name.setFont(new Font(font.getName(),Font.BOLD,20));

		    name.addActionListener(new ActionListener() {

		        public void actionPerformed(ActionEvent e) {
		        	outln("E: actionPerformed() name ActionListener ");
		        	String subjectName = ((JTextField)e.getSource()).getText();

		        	if(!subjectName.equals("")){
			    		s.setID(subjectName);

			    		Timer.addHeader(FileHeader);

		        		name.setVisible(false);
			        	name.removeActionListener(this);
			    	    frame.remove(name);
					    frame.getContentPane().add(out, BorderLayout.SOUTH);
			    	    //frame.remove(area);
			    	    area.setText("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

			    	    Menu.currentMenu++;
			    	    menu=(Menu) Menu.instances.get(Menu.currentMenu);
			    	    //frame.setTitle(menu.getRandomPath().getPath());
			    	    frame.requestFocusInWindow(); //ključno
			    	    frame.pack(); //ključno
			    		menu.train=false;//!!
			    		menu.wrong=false;
			    		menu.paths=menu.testPaths;

			    		frame.setJMenuBar(menu.menuBar);
			       		frame.setTitle("kada se pojavi putanja krenite riješavati:"+Menu.mode[0]);
			       	   out.setText(Menu.mode[0]);
		        		try {
							TimeUnit.SECONDS.sleep(5);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
		        		frame.setTitle(Start.menu.getRandomPath().getPath());

			    		frame.requestFocusInWindow(); //ključno
						frame.pack();//ključno
						frame.setVisible(true);

		        	}else{
		    	    	//menu.menu0.doClick();
		    	    }

		    		outln("L: actionPerformed() name ActionListener ");
		        }
		    }


		    );

		    frame.getContentPane().setSize(width, 200);
		    //frame.getContentPane().setBackground(Color.YELLOW);
		    frame.getContentPane().add(out, BorderLayout.SOUTH);
		    frame.getContentPane().add(name, BorderLayout.SOUTH);
		    frame.getContentPane().add(area, BorderLayout.NORTH);


		    frame.addKeyListener(frameKeyAdapter);
		    frame.addMouseListener(frameMouseAdapter);
		    frame.addMouseWheelListener(frameMouseWheelListener);
		    frame.pack();
	    }

	    public static void main(String[] args) {


	    	initFrame();
		    s= new Subject();

		    String[] File = {"File","New", "Open", "Close", "Close All", "Save", "Save As","Print","Export"};
			String[] Edit = {"Edit","Undo", "Redo", "Cut", "Copy","Paste", "Find"};
			String[] Source = {"Source","Toggle Comment","Format"};
			String[] Email = { "Email","Inbox", "Sent mail","Junk mail", "Login","Log out"};
			String[] Help = { "Help","Manual","Contact", "About"};

			String[] Select = {"Select","All", "None", "Invert", "Float", "8", "From Path", "Selection Editor", "Feather", "Sharpen", "Shrink", "Grow", "9", "Distort", "Rounded Rectangle", "Toggle Quick Mask", "Save to Channel", "To Path"};
			String[] View = {"View","New View", "Dot for Dot", "Zoom", "Shrink Wrap", "Fullscreen", "Navigation Window", "Display Filters","6", "Padding Color", "7"};

			String[] Image = {"Image","Duplicate", "Mode", "Transform", "Canvas Size", "Fit Canvas to Layers", "Fit Canvas to Selection", "Print Size", "Scale Image", "Crop to Selection", "Autocrop Image", "Zealous Crop", "Merge Visible Layers", "Flatten Image", "Align Visible Layers", "Guides", "Configure Grid", "Image Properties"};
			String[] Layer = {"Layer","5", "Duplicate Layer", "Anchor Layer", "Merge Down", "Delete Layer", "Stack", "Mask", "Transparency", "Transform", "Layer Boundary Size", "Layer to Image Size", "Scale Layer", "Crop to Selection", "Autocrop Layer"};
			String[] Tools = {"Tools","1", "2", "3", "4", "Paths", "Smart Picker", "Zoom", "Measure", "Text", "GEGL Operation", "New Toolbox", "Default Colors", "Swap Colors"};


			String[] SelectionTools = {"Selection Tools","Rectangle Select", "Ellipse Select", "Free Select", "Foreground Select", "Fuzzy Select", "By Color Select", "Intelligent Scissors"};
			String[] PaintTools = {"Paint Tools","Bucket Fill", "Blend", "Pencil", "Paintbrush", "Eraser", "Airbrush", "Ink", "Clone", "Heal", "Perspective Clone", "Blur/Sharpen", "Smudge", "Dodge/Burn"};
			String[] TransformTools = {"Transform Tools","Align","2", "Crop", "Rotate", "1", "Shear", "Perspective", "Flip", "Cage Transform"};
			String[] TransformTools2 = {"Transform Tools","Align","Move", "Crop", "Rotate", "Scale", "Shear", "Perspective", "Flip", "Cage Transform"};
			String[] ColorTools = {"Color Tools","Color Balance", "Color picker","Hue-Saturation", "Colorize", "Brightness-Contrast", "Threshold", "Levels", "Curves", "Posterize", "Desaturate"};
			String[] Show = {"Show", "Selection", "Layer Boundary", "Guides", "Grid", "Sample Points", "Menubar", "Rulers", "Scrollbars", "Statusbar"};
			String[] Snap = {"Snap", "Snap to Guides", "Snap to Grid", "Snap to Canvas Edges", "Snap to Active Path"};
			String[] New ={"New","New Layer", "New from Visible", "New Layer Group"};
			String[] ByColor = {"By Color", "3", "HSV", "HSL", "CMYK"};
			String[] ByColor2 = {"By Color", "RGB", "HSV", "HSL", "CMYK"};
			String[] Border = {"Border", "By layout", "4"};
			String[] Border2 = {"Border", "By layout", "By thickess"};

			String[] Scale ={"Scale", "Keep Ratio", "Resize"};
			String[] Move = {"Move", "Left", "Right", "Up", "Down"};
			String[] RGB = {"RGB", "Red", "Green", "Blue"};
			String[] Bythickness = {"By thickess", "1px", "2px", "5px"};

			String[][] array1 = {File,Edit,Source,Email,Help};
			String[][] array2 = {View,Image,Layer,Tools,Select};
			String[][] array3 = {File,Edit,Source,Email,Select,View,Image,Layer,Tools};

			String[][] subarray = {SelectionTools,PaintTools,TransformTools,ColorTools,New, Show, Snap, ByColor, Border};
			String[][] subarray2 = {SelectionTools,PaintTools,TransformTools2,ColorTools,New, Show, Snap, ByColor2, Border2};
			String[][] subsubarray = {Scale, Move, RGB, Bythickness};

			String[][][] menus1 ={array1};//stvaraju li null null neke probleme -da?
			String[][][] menus2 ={array2,subarray2}; //nema null, stvara li to probleme?
			String[][][] menus3 ={array3,subarray,subsubarray};

			Menu hardMenu0 = new Menu(menus3,frame);


		int ModalityGroup=Integer.parseInt(args[0]);// 0-5
		int MenuGroup = Integer.parseInt(args[1]); //1 ili 2;

	//Riješeno :

	//Modality, Menu:
	// 1,1
	// 1,2


		String[] header1={"1","2","3",   "3","2","1",  "2","3","1"};
		String[] header2={"3","2","1",   "2","1","3",  "1","3","2"};

		String[] mode0 = {"M","K","S"};
		String[] mode1 = {"S","M","K"};
		String[] mode2 = {"K","S","M"};
		String[] mode3 = {"M","S","K"};
		String[] mode4 = {"K","M","S"};
		String[] mode5 = {"S","K","M"};

		String[][] modes = {mode0,mode1,mode2,mode3,mode4,mode5};

		//Ekvivalent mode0 samo sa punim nazivima
		String S= "SCROLL KOTAČIĆEM";
		String M= "KLASIČNO MIŠEM";
		String K= "TIPKOVNICOM";

		String[] modeName0 = {M,K,S};
		String[] modeName1 = {S,M,K};
		String[] modeName2 = {K,S,M};
		String[] modeName3 = {M,S,K};
		String[] modeName4 = {K,M,S};
		String[] modeName5 = {S,K,M};

	    String[][] modeNames ={modeName0,modeName1,modeName2,modeName3,modeName4,modeName5};

		String[] header= new String[8];
		String[] mode = new String[3];

		Menu.mode=modeNames[ModalityGroup]; //Puni nazivi u Title i Label
		mode=modes[ModalityGroup]; // Kratice za header

		switch(MenuGroup){
			case 1 :
				new Menu(menus1,frame);
				new Menu(menus2,frame);
				new Menu(menus3,frame);

				new Menu(menus3,frame);
			    new Menu(menus2,frame);
				new Menu(menus1,frame);

				new Menu(menus2,frame);
				new Menu(menus3,frame);
				new Menu(menus1,frame);
				header=header1;
				break;

			case 2:
				new Menu(menus3,frame);
				new Menu(menus1,frame);
				new Menu(menus2,frame);

				new Menu(menus2,frame);
			    new Menu(menus1,frame);
				new Menu(menus3,frame);

				new Menu(menus1,frame);
				new Menu(menus3,frame);
				new Menu(menus2,frame);

				 header=header2;
			}

		FileHeader=new String[9];
			for (int i = 0; i < header.length; i++) {
				System.out.print(mode[i/3]+header[i]+ " ");
				FileHeader[i]=mode[i/3]+header[i];
			}

	//Timer.addHeader se zapisuje u actionPerformed nakon što imamo upisan id

		System.out.println("\n");

			//za kretanje pomoći scrolla
			//current[0] za kretanje po menubaru, current[1] za sve razine
		    current=new int[2];
		    current[0]=0;
		    current[1]=0;
		    //uklađuju klik na scroll wheel i rotaciju kotačića
		    onStart = true;
		    onMenubar = false;

		    menu=(Menu) Menu.instances.get(Menu.currentMenu);
			frame.setJMenuBar(menu.menuBar);
			frame.pack();
			frame.setVisible(true);
			//menu.paths=menu.testPaths;
			menu.paths=menu.allPaths; //u testu nek budu sve putanje
			menu.train=true;

			//menu.paths.equals(menu.testPaths);

			//Collections.copy(menu.testPaths, menu.paths);
			//Collections.copy(menu.testPaths, menu.paths);

			//Collections.copy(dest, src);
			//menu.paths=menu.testPaths;

			//System.out.println("allPaths.size() "+menu.allPaths.size());
			//System.out.println(menu.paths.size());
			//System.out.println(menu.paths);

			frame.setTitle(menu.getRandomPath().getPath());
			System.out.println(frame.getTitle());
			System.out.println(Menu.instances.size());

			//uvijek nakon swapa
			//kad nestane putanja definirati prijelaz na iduci test, obavjestiti Ispitanika

			//DONE definirati trailPaths i broj tih testova
			//TODO definirati testPaths i trailPaths i broj tih testova

			//DONE definirati prelaz sa složenosti na složenost
			//DONE definirati testPaths i trailPaths za novu složenost

				//DONE definirati prelaz sa modaliteta na modalitet
				//NEEDS TESTING BUT DONE između mijenjanja pripaziti i postavljati statične variable i potrebne flagove na početne vrijednosti

//IDEA Hardkodirane putanje: ručno se putanje mogu staviti u trail i test paths tako da se provjerava finalSelection i ako je jednak željenima doda se u path

			//DONE Zapis u  tablicu, csv Tarandek rekao u stupce, svaki par (složenost,modalitet) je svoj stupac 9 stupaca , sva vremena su redovi?
			//TODO negdje zapisati od kojeg pokušaja je pogodio? Na kraju statistika na kojem meniu i modalitetu se najviše griješilo.
			//TODO VAŽNO kamo i kako zapisivati pogrešne? Definirati koliko su pogriješili?!
			//DONE Zapis tablice u  datoteku
			//DONE tablica mora biti tipa Static kako bi ju djelili svi Meniji, u koji stupac zapisujemo može nam reći  varijabla currentMenu
			//TODO ne sakriva se menu kod prelaza riješiti ili u upute staviti ("kada se vaš izbor zamrzne n 5 sekundi obratite vrh prozora, to oznacava prijelaz na novu složenost menia)

	    }



	    static KeyAdapter frameKeyAdapter = new KeyAdapter() {

	         public void keyPressed(KeyEvent e) {
	 	    	outln("E: keyPressed() frameKeyAdapter ");

	 	    		int key = e.getKeyCode();

			           if (key == KeyEvent.VK_ENTER) {
			        	   //ako je pritisnut enter simuliraj klik mišem(odabir) starta
			        	   menu.menu0.doClick();
			           }
			     outln("L: keyPressed() frameKeyAdapter ");


	         	}

		   };

		  static MouseAdapter frameMouseAdapter= new MouseAdapter(){

		    	public void mouseClicked(MouseEvent evt) {
		    		outln("E: mouseClicked()");

			    		//ako je pritisnut upravo mouse wheel
			     		if((evt.getModifiers() & InputEvent.BUTTON2_MASK) != 0){

			     			//ako je pritisnut prvi puta - što označava početak zadatka
			     			// na početku je onStart = true
			       		    if (onStart ) {
			       				JM=menu.menu0;
			       		    	JM.doClick(); 	//simuliraj pritisak mišem (odabir) starta
			       		    	onStart=false;
			       		    	onMenubar=true;	//izvršavanje zadatka je započelo

			       		    }else if (onMenubar & current[0]>0){
			       		    //ako se nalazimo na menubaru (onMenubar==true) i ako nismo trenutno na "Start" već na nekom meniu u menubaru(current[0]>0)

			       		    	JM.doClick(); //simuliraj klik na meni u menubaru na kojem jest
			       		    	//to neće aktivirati actionPerformed() već će samo otvoriti meni
			       		    	((JMenu) JM).getItem(0).setArmed(true); //označi prvi izbor u tek otvorenom meniu
			       		    	onMenubar=false;

			       		    }else if (!onStart & !onMenubar){
			       		    	//sada smo u nekoj od pod razina, nismo niti na startu, niti na menubaru

			       		    	if(((JMenu) JM).getItem(current[1]) instanceof JMenu){//Ako je izbor (JItem) na kojem se nalaziš ujedno i meni (JMenu) koji vodi na iducu razinu
			       		    		JM.setArmed(false); //sada će ostat highlightan po defaultu, ali kasnije neće
			       		    		JM=(JMenuItem) ((JMenu) JM).getItem(current[1]); //dohvati menu na trenutnoj poziciji current[1]
				       		    	JM.doClick(); //simuliraj klik na taj meni, on se po defaultu otvara
			       		    		current[1]=0;	//current[1] sada će označavati trenutni izbor u novootvorenom meniu, na početku je on 0, prvi izbor
				       		    	((JMenu) JM).getItem(0).setArmed(true); //prvi izbor u tom meniu
				       		    	//onMenubar=false; //već je false, nema potrebe

			       		    	}else{												//Ako je izbor na kojem se nalaziš Item
			       		    		JM.setArmed(false);
			       		    		((JMenu) JM).getItem(current[1]).doClick();
			       		    		//simulira klik na taj item što će izazvat ActionPerformed u Menu
			       		    		onStart=true;
			       		    		onMenubar=false;
			       		    		current[0]=0;
			       		    		current[1]=0;
			       		    		//izuzetno važno
			       		    	}

			       		    }else{
			       		    	System.out.println("ne može");
			       		    	menu.menu0.setArmed(true);
			       		    }

			     		}else{
			     			outln("kliknut lijevi ili desni klik");
			     		}

		//	       		 	this.frame
				    		frame.requestFocusInWindow(); //ključno
							frame.pack();//ključno
							frame.setVisible(true);
			     			outln("L: mouseClicked()");





		    		}



		        };

		  static MouseWheelListener frameMouseWheelListener =new MouseWheelListener(){


				@Override
				public void mouseWheelMoved(MouseWheelEvent e) {
					outln("E: mouseWheelMoved()");
			        int notches = e.getWheelRotation();

			        if(onMenubar){ //ako je na menubaru

				        if (notches > 0) { // UP -> RIGHT
				        	current[0]++;
				        } else {//DOWN -> LEFT
				        	current[0]--;
				        }

				        if(current[0]>menu.menuBar.getMenuCount()-1)current[0]=1; //kružno kretanje
			            else if(current[0]<1)current[0]=menu.menuBar.getMenuCount()-1;//kružno kretanje

				        JM.setArmed(false);
				        JM=menu.menuBar.getMenu(current[0]); //dohvati trenutni
			        	JM.setArmed(true); //highlightaj trenutni
			        	JM.doClick(); //otvori trenutni highlightani

			        }else if (!onMenubar & !onStart){ //ako je na nekom od izbora

			        	JM.doClick(); //Jer se po defaultu zatvara :/

			        	((JMenu) JM).getItem(current[1]).setArmed(false);

				        if (notches > 0) {//UP
				        	current[1]++;
				        } else {//DOWN
				        	current[1]--;
				        }
				        //omogući kružno kretanje
			        	if(current[1]>((JMenu) JM).getItemCount()-1)current[1]=0;
			        	else if(current[1]<0)current[1]=((JMenu) JM).getItemCount()-1;

			        	((JMenu) JM).getItem(current[1]).setArmed(true);

			        }
			        outln("L: mouseWheelMoved()");
				}

		    };
}
