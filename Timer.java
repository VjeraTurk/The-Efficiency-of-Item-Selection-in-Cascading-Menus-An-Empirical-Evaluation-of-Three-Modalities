import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Timer {

	static double start;
	static double end;

	static final int m=5,n=9; 	//9 mozemo zamjeniti sa Menu.instances.size()-1

	static double allTimes[][]=new double[5][9];
	static int allWrong[][]=new int[5][9];

	static double allAverages[]=new double[9];
	static int wrongCount[]=new int[9];

	static int i=0,j=0;
	static double mil = (double) 1000000000.00;

	static StringBuilder builder = new StringBuilder();
	static StringBuilder builderAvg = new StringBuilder();
	static StringBuilder builderWrong = new StringBuilder();
	static StringBuilder builderAllWrong = new StringBuilder();


	public static double getDuration() {

		allTimes[i][j]=(double)(end - start)/(double)mil;
		i++;
		if(i==5){
			j++;
			i=0;
		}
		//System.out.println(allTimes);

	for(int l = 0;l<m;l++){
		for(int k = 0;k<9;k++){
			System.out.print(allTimes[l][k]+" ");
		}
		System.out.print("\n");
	}
		return (double)(end - start)/(double)mil;
	}

	public static void countWrong(){
		allWrong[i][j]++;
		for(int l = 0;l<m;l++){
			for(int k = 0;k<n;k++){
				System.out.print(allWrong[l][k]+" ");
			}
			System.out.print("\n");
		}
	}


	static void addHeader(String[] FileHeader){

		builderAvg.append(Subject.ID+"");
		builderAvg.append(",");
		builderWrong.append(Subject.ID+"");
		builderWrong.append(",");

		for(int i=0;i<FileHeader.length-1;i++){
			builder.append(FileHeader[i]+",");
			builderAvg.append(FileHeader[i]+",");
			builderWrong.append(FileHeader[i]+",");
			builderAllWrong.append(FileHeader[i]+",");
		}

		builder.append(FileHeader[FileHeader.length-1]+"\n");
		builderAvg.append(FileHeader[FileHeader.length-1]+"\n");
		builderWrong.append(FileHeader[FileHeader.length-1]+"\n");
		builderAllWrong.append(FileHeader[FileHeader.length-1]+"\n");

		builderAvg.append(",");
		builderWrong.append(",");

	}

	static void writeToFile(){


		for(int j = 0; j < n; j++){
			double sum=0;
			int wrongSum=0;

			for(int i = 0; i < m; i++){
				   sum=sum+allTimes[i][j];
				   wrongSum=wrongSum+allWrong[i][j];

			}//for each row

			allAverages[i]=sum/m;
			wrongCount[i]=wrongSum;

			builderAvg.append(allAverages[i]+"");
			builderWrong.append(wrongCount[i]+"");

			if(j < allAverages.length - 1)//if this is not the last row element
				builderAvg.append(",");

			if(j < wrongCount.length - 1)//if this is not the last row element
				builderWrong.append(",");


		}//for each column
		builderAvg.append("\n");
		builderWrong.append("\n");


		for(int i = 0; i < m; i++)//for each row
		{

			for(int j = 0; j < n; j++)//for each column
			{

			  builder.append(allTimes[i][j]+"");//append to the output string
		      	if(j < n - 1)//if this is not the last row element
		      		builder.append(",");//then add comma (if you don't like commas you can use spaces)

			  builderAllWrong.append(allWrong[i][j]+"");//append to the output string
		      	if(j < n - 1)//if this is not the last row element
		      		builderAllWrong.append(",");//then add comma (if you don't like commas you can use spaces)


			}
			builder.append("\n");//append new line at the end of the row
			builderAllWrong.append("\n");//append new line at the end of the row

		}


		BufferedWriter writer;
		BufferedWriter writerAvg;
		BufferedWriter writerWrong;
		BufferedWriter writerAllWrong;
		try {
			writer = new BufferedWriter(new FileWriter("All_"+Subject.ID+".csv"));
			writerAvg = new BufferedWriter(new FileWriter("Avg_"+Subject.ID+".csv"));
			writerWrong = new BufferedWriter(new FileWriter("Wrong_"+Subject.ID+".csv"));
			writerAllWrong = new BufferedWriter(new FileWriter("AllWrong_"+Subject.ID+".csv"));

			writer.write(builder.toString());//save the string representation of the allTimes
			writerAvg.write(builderAvg.toString());//save the string representation of the allTimes
			writerWrong.write(builderWrong.toString());//save the string representation of the allTimes
			writerAllWrong.write(builderAllWrong.toString());//save the string representation of the allTimes

			writer.close();
			writerAvg.close();
			writerWrong.close();
			writerAllWrong.close();

		} catch (IOException e) {

			e.printStackTrace();
		}
//TODO zapisivati allWrong u csv ili ÄŤak raÄŤunati neki average od allWrong, moĹľda bolje sumu
	}


}