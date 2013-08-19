import java.util.ArrayList;
import java.util.HashSet;


public class Main {
	
	public final static int maxModuleSize = 3;
	public static ArrayList<String> bindingGenes;
	public static ArrayList<String> bindingTFs;
	public static double[][] bindingData;
	public static ArrayList<String> expressionGenes;
	public static ArrayList<String> expressionExperiment;
	public static double[][] expressionData;
	
	public static double p1 = .005;
	public static double p2 = .05;
	
	public static void main(String[] args){
		readInputs();
		runGRAM();
	}
	
	private static void runGRAM() {
		for (String gene_i : bindingGenes) {
			//HashSet<String> tf_T = getInitialStrictSet(gene_i)
		}
	}
	
	private static double[] calculateCenter(HashSet<String> moduleGenes){
		if(moduleGenes.size()<3){
			return null;
		}
		String[] moduleGenesArray=(String[]) moduleGenes.toArray();
		
		for(int i = 0; i<moduleGenesArray.length; i++){
			for(int j=i+1; j<moduleGenesArray.length;j++){
				for(int k = j+1; k<moduleGenesArray.length;k++){
					moduleGenesArray
				}
			}
		}
		
		
		return double[] center;
	}
	
	private static double calculateDistance(double[] geneExpression, double[] center){
		double distance=0;
		for(int i = 0; i<geneExpression.length;i++){
			double tmp = Math.pow((geneExpression[i]-center[i]),2);
			distance = distance + tmp;
		}
		return distance;
	}
	
	
	//hard coding inputs for now
	public static void readInputs() {
		bindingGenes = new ArrayList<String>();
		bindingTFs = new ArrayList<String>();
		bindingData = new double[][] {
				{.002, .003, .002},
				{.003, .001, .004},
				{.004, .004, .04}};
		expressionGenes = new ArrayList<String>();
		expressionExperiment = new ArrayList<String>();
		expressionData = new double[][] {
				{.002, .003, .002, .002},
				{.003, .001, .004, .003},
				{.004, .004, .002, .002}};
		bindingGenes.add("G_1");
		bindingGenes.add("G_2");
		bindingGenes.add("G_3");
		bindingTFs.add("TF_A");
		bindingTFs.add("TF_B");
		bindingTFs.add("TF_C");
		expressionGenes.add("G_1");
		expressionGenes.add("G_2");
		expressionGenes.add("G_3");
		expressionExperiment.add("Ex_1");
		expressionExperiment.add("Ex_2");
		expressionExperiment.add("Ex_3");
		expressionExperiment.add("Ex_4");
	}

}
