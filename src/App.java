import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.IntStream;

public class App {
    public static void main(String[] args) throws Exception {
        
        Scanner input = new Scanner(System.in);

        int N = input.nextInt();
        int M = input.nextInt();

        // array de ingredientes ({1, 2, 3, ..., N})
        int[] NElements = IntStream.rangeClosed(1, N).toArray();

        // array de pares de ingredientes que nao combinam
        ArrayList<ArrayList<Integer>> MPairs = new ArrayList<ArrayList<Integer>>();

        // ler a lista de pares de ingredientes
        for (int i = 0; i < M; i++) {
            MPairs.add(new ArrayList<>(Arrays.asList(input.nextInt(), input.nextInt())));
        }

        // obter conjuntos de todos os sanduiches que NAO podem ser feitos
        Set<Set<Integer>> subsetsContainingMPairs = getSubsetsContainingAnotherSubset(NElements, MPairs);

        // quantidade total de ingredientes possiveis descontando o conjunto vazio
        int numberOfSubsets = (1 << NElements.length) - 1;

        // obtemos os sanduiches permitidos excluindo os que nao podem ser feitos
        int subsetsExcludingMPairs = numberOfSubsets - subsetsContainingMPairs.size();

        System.out.println(subsetsExcludingMPairs);

        input.close();
    }


    public static Set<Set<Integer>> getSubsetsContainingAnotherSubset(int[] set, ArrayList<ArrayList<Integer>> pairs) {
        Set<Set<Integer>> subsetsContainingAnotherSubset = new HashSet<>();   // sanduiches que nao podem ser feitos
        int numberOfSubsets = (1 << set.length);             // quantidade de subconjuntos = 2^(tamanho do conjunto)
        
        // utilizar a sequencia de numeros binarios para iterar sobre todos os subsets possiveis (se N=3: 000, 001, 010, 011, ...)
        for (int i = 0; i < numberOfSubsets; i++) {
            // formar um subset
            Set<Integer> subset = new HashSet<>();
            for (int j = 0; j < set.length; j++) {
                // adicionar o j-esimo elemento do set ao subset se apos deslocar j vezes para a direita o numero for impar
                if ((1 & (i >> j)) == 1) {
                    subset.add(set[j]);
                }
            }
            // adicionar o subset aos subsets de sanduiches que nao podem ser feitos
            // apenas se um dos pares esta contido no subset
            for (ArrayList<Integer> pair : pairs) {
                if (subset.containsAll(pair)) {
                    subsetsContainingAnotherSubset.add(subset);
                }
            }
        }
        return subsetsContainingAnotherSubset;
    }
}
