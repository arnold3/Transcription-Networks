import java.util.ArrayList;
import java.util.HashSet;


public class Main {
	
	public static ArrayList<String> bindingGenes;
	public static ArrayList<String> bindingTFs;
	public static double[][] bindingData;
	public static ArrayList<String> expressionGenes;
	public static ArrayList<String> expressionExperiment;
	public static double[][] expressionData;
	
	public static final double p1 = .005;
	public static final double p2 = .05;
	
	public static void main(String[] args){
		readInputs();
		runGRAM();
	}
	
	private static void runGRAM() {
		HashSet<HashSet<String>> exploredTfSets = new HashSet<HashSet<String>>(); 
		for (String gene_i : bindingGenes) {
			HashSet<String> tf_T = getTFSet(gene_i, p1);
			ArrayList<HashSet<String>> tf_subsetsofT = getAllSubsets(tf_T.toArray(new String[0]));
			for (HashSet<String> tf_F : tf_subsetsofT) {
				for (String s: tf_F) {
					System.out.print(s + " ");
				}
				System.out.println("\n");
			}
		}
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
