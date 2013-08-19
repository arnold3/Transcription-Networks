import java.util.ArrayList;
import java.util.HashSet;

public class Core{
	//The names of genes that are in the module
	HashSet<String> module; 
	
	//A list of gene names in the data set
	ArrayList<String> expressionNames;  
	
	//Transcription data: A matrix of doubles of genes x experiment
	double[][] expressionData;
	
	public Core(HashSet<String> module, 
			ArrayList<String> expressionNames,
			double[][] expressionData ){
		this.module=module;
		this.expressionNames=expressionNames;
		this.expressionData=expressionData;	
	}
	
	public double calculateDistance(double[] gene, double[] center){
		double distance=0;
		for(int i = 0; i<gene.length;i++){
			double tmp = Math.pow((gene[i]-center[i]),2);
			distance = distance + tmp;
		}
		return distance;
	}
	
	
	
	
	
	
}