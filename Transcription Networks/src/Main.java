import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class Main {
	
	public static final int minModuleSize = 3;
	public static ArrayList<String> bindingGenes;
	public static ArrayList<String> bindingTFs;
	public static double[][] bindingData;
	public static ArrayList<String> expressionGenes;
	public static ArrayList<String> expressionExperiment;
	public static double[][] expressionData;
	
	public static final double p1 = .005;
	public static final double p2 = .05;
	public static final double SN = .1;
	
	public static void main(String[] args){
		
		readInputs();
		//runGRAM();
		HashSet<String>moduleGenes = new HashSet<String>();
		moduleGenes.add("G_1");
		moduleGenes.add("G_2");
		moduleGenes.add("G_3");
		moduleGenes.add("G_4");
		calculateCenter(moduleGenes, SN);
	
	}
	
	private static void runGRAM() {
		HashSet<HashSet<String>> exploredTfSets = new HashSet<HashSet<String>>();
		HashSet<String> completedGenes = new HashSet<String>();
		HashMap<HashSet<String>, HashSet<String>> completedModules = new HashMap<HashSet<String>, HashSet<String>>();
		for (String gene_i : bindingGenes) {
			HashSet<String> tf_T = getTFSet(gene_i, p1);
			ArrayList<HashSet<String>> tf_subsetsofT = getAllSubsets(tf_T.toArray(new String[0]));
			for (HashSet<String> tf_F : tf_subsetsofT) {
				/*for (String s: tf_F) {
				System.out.print(s + " ");
				}
				System.out.println("\n");*/
				if (exploredTfSets.contains(tf_F)) continue;
				HashSet<String> genes_G = getGenesForTFs(tf_F, p1);
				int n = genes_G.size();
				double sn = SN; //deal with calculating this later
				double[] c = calculateCenter(genes_G, sn);
<<<<<<< HEAD
				HashSet<String> genes_M; // finish implementig this part 
			
				
				
=======
				HashSet<String> genes_M = new HashSet<String>(); //= getGenesFor
				if (genes_M.size() > minModuleSize) {
					completedModules.put(genes_M, tf_F);
					completedGenes.addAll(genes_M);
				}
			}
		}
		printModules(completedModules);
	}

	private static void printModules(
			HashMap<HashSet<String>, HashSet<String>> completedModules) {
		for (HashSet<String> geneSet : completedModules.keySet()) {
			System.out.print("Gene in Module: ");
			for (String gene: geneSet) {
				System.out.print(gene + " ");
>>>>>>> b6a659dc8ab93797dbf49f9bbd135a11e4cc51e8
			}
			System.out.print("\nFactors for Module: ");
			for (String tf: completedModules.get(geneSet)) {
				System.out.print(tf + " ");
			}
			System.out.print("\n\n");
		}
		
	}

	private static HashSet<String> getGenesForTFs(HashSet<String> tf_F, double p) {
		HashSet<String> genes = new HashSet<String>();
		for (String gene : bindingGenes) {
			boolean add = true;
			for (String tf : tf_F) {
				if (bindingData[bindingGenes.indexOf(gene)][bindingTFs.indexOf(tf)] > p) {
					add = false;
					break;
				}
			}
			if (add) {
				genes.add(gene);
			}
		}
		return genes;
	}

	private static ArrayList<HashSet<String>> getAllSubsets(String[] tfs) {
		ArrayList<HashSet<String>> listOfSubsets = new ArrayList<HashSet<String>>();
		HashSet<String> currentSet = new HashSet<String>();
		int size = (int) Math.pow(2, tfs.length);
		for (int i = size-1; i > 0; i--) {
			int n = i;
			int d = 0;
			while(d < tfs.length) {
				if (n%2 == 1) {
					currentSet.add(tfs[d]);
				}
				n = n/2;
				d++;
			}
			listOfSubsets.add(currentSet);
			currentSet = new HashSet<String>(); 
		}
		return listOfSubsets;
	}

	private static HashSet<String> getTFSet(String gene_i, double p) {
		int index = bindingGenes.indexOf(gene_i);
		HashSet<String> tfs = new HashSet<String>();
		for (int i = 0; i < bindingData[index].length; i++) {
			if (bindingData[index][i] < p) {
				tfs.add(bindingTFs.get(i));
			}
		}
		return tfs;
	}

	
	private static double[] calculateCenter(HashSet<String> moduleGenes, double radius){
		
		if(moduleGenes.size()<minModuleSize){
			return null;
		}
		
		int maxGenes=0;
		String[] moduleGenesArray=(String[]) moduleGenes.toArray(new String[0]);
		double[] center = new double[expressionData[0].length];
		for(int i = 0; i<moduleGenesArray.length; i++){
			for(int j=i+1; j<moduleGenesArray.length;j++){
				for(int k = j+1; k<moduleGenesArray.length;k++){
					String[] triplet = {moduleGenesArray[i], moduleGenesArray[j], moduleGenesArray[k]};
					int[] tripletExpressionIndex = new int[3];
					for (int l = 0; l<2; l++){
						tripletExpressionIndex[l]=expressionGenes.indexOf(triplet[l]);
					}
					// calculate a temporary center.
					double[] tmpcenter=new double[expressionData[0].length];
					for(int m=0; m < expressionData[0].length; m++){
						tmpcenter[m]= (1.0/3.0)*(expressionData[tripletExpressionIndex[0]][m]+
								expressionData[tripletExpressionIndex[1]][m]+
								expressionData[tripletExpressionIndex[2]][m]);
						
					}
					//for(int x=0; x<tmpcenter.length;x++){System.out.println(tmpcenter[x]);}
					for(int n =0; n < moduleGenesArray.length; n++){
						int geneIndex = expressionGenes.indexOf(moduleGenesArray[n]);
						int tmp = 0;
						if (calculateDistance(expressionData[geneIndex], tmpcenter) <= radius){
							tmp++;
						}
						if(tmp>=maxGenes)
							center = tmpcenter;
					}
				
					
					
				}
			}
		}
		
		for(int i=0; i<center.length; i++){
			System.out.println(center[i]);
		}
		
		return center;
	}
	
	
	private static double calculateDistance(double[] geneExpression, double[] center){
		double distance=0;
		for(int i = 0; i<geneExpression.length;i++){
			double tmp = Math.pow((geneExpression[i]-center[i]),2);
			distance = distance + tmp;
		}
		return Math.sqrt(distance);
	}
	
	
	//hard coding inputs for now
	public static void readInputs() {
		bindingGenes = new ArrayList<String>();
		bindingTFs = new ArrayList<String>();
		bindingData = new double[][] {
				{.002, .003, .002},
				{.003, .001, .004},
				{.004, .004, .04},
				{.4, .005, .04}};
		expressionGenes = new ArrayList<String>();
		expressionExperiment = new ArrayList<String>();
		expressionData = new double[][] {
				{.002, .003, .002, .002},
				{.003, .001, .004, .003},
				{.004, .004, .002, .002},
				{.006, .9, .005, .04}};
		bindingGenes.add("G_1");
		bindingGenes.add("G_2");
		bindingGenes.add("G_3");
		bindingGenes.add("G_4");
		bindingTFs.add("TF_A");
		bindingTFs.add("TF_B");
		bindingTFs.add("TF_C");
		expressionGenes.add("G_1");
		expressionGenes.add("G_2");
		expressionGenes.add("G_3");
		expressionGenes.add("G_4");
		expressionExperiment.add("Ex_1");
		expressionExperiment.add("Ex_2");
		expressionExperiment.add("Ex_3");
		expressionExperiment.add("Ex_4");
	}

}
