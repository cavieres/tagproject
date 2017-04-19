package tagProject.address.model;


import java.util.Arrays;
import org.terrier.indexing.Collection;
import org.terrier.indexing.SimpleFileCollection;
import org.terrier.matching.ResultSet;
import org.terrier.querying.Manager;
import org.terrier.querying.SearchRequest;
import org.terrier.structures.Index;
import org.terrier.structures.indexing.Indexer;
import org.terrier.structures.indexing.classical.BasicIndexer;
import org.terrier.utility.ApplicationSetup;
import org.terrier.matching.models.TF_IDF;

/**
 * Hello world!
 *
 */
public class IR 
{
	   public static void main(String[] args) throws Exception {

	    	// Directorio Indexado
	    	String aDirectoryToIndex = "C:\\Users\\luis\\Documents\\Java\\Books\\Spring";

	        // Map recuperacion archivo
	        ApplicationSetup.setProperty("indexer.meta.forward.keys", "filename");
	        ApplicationSetup.setProperty("indexer.meta.forward.keylens", "200");

	        Indexer indexer = new BasicIndexer("E:\\", "data");
	        Collection coll = new SimpleFileCollection(Arrays.asList(aDirectoryToIndex), true);
	        indexer.index(new Collection[]{coll});

	        Index index = Index.createIndex("E:\\", "data");

	    // Obtener propiedades archivos
	    ApplicationSetup.setProperty("querying.postfilters.order", "org.terrier.querying.SimpleDecorate");
	        ApplicationSetup.setProperty("querying.postfilters.controls", "decorate:org.terrier.querying.SimpleDecorate");

	    // Manager de consultas
	        Manager queryingManager = new Manager(index);

	    // Consulta
	        SearchRequest srq = queryingManager.newSearchRequestFromQuery("Madhusudhan Konda");

	    // Modelo de busqueda
	        srq.addMatchingModel("Matching",tagProject.address.model.ranking.MyRankingModel.class.getName());

	     // Obtener propiedades archivos
	        srq.setControl("decorate", "on");

	    // Ejecutar consulta
	        queryingManager.runSearchRequest(srq);

	    // Resultados
	        ResultSet results = srq.getResultSet();

	 
	        System.out.println("---------------------------------------------------------");
	        System.out.println(results.getExactResultSize()+" documents were scored");
	        System.out.println("The top "+results.getResultSize()+" of those documents were returned");
	        System.out.println("Document Ranking");
	        for (int i =0; i< results.getResultSize(); i++) {
	            int docid = results.getDocids()[i];
	            double score = results.getScores()[i];
	            System.out.println("   Rank "+i+": "+docid+" "+results.getMetaItem("filename", i)+" "+score);
	        }
	  }
}