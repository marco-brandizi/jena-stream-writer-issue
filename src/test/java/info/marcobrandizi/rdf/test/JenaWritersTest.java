package info.marcobrandizi.rdf.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.apache.jena.riot.system.StreamOps;
import org.apache.jena.riot.system.StreamRDF;
import org.apache.jena.riot.system.StreamRDFWriter;
import org.apache.jena.riot.system.StreamRDFWriterFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * TODO: comment me!
 *
 * @author brandizi
 * <dl><dt>Date:</dt><dd>16 Nov 2018</dd></dl>
 *
 */
public class JenaWritersTest
{
	private static Model model = ModelFactory.createDefaultModel ();
	
	@BeforeClass
	public static void initInput () {
		model.read ( "target/test-classes/foaf-example.rdf" );
	}
	
	/**
	 * Passes
	 *  
	 */
	@Test
	public void testDataManager () throws FileNotFoundException, IOException
	{
		try ( OutputStream out = new FileOutputStream ( "target/test-data-mgr.rdf" ) )
		{
			RDFDataMgr.write ( out, model, Lang.RDFXML );
		}
	}

	
	/**
	 * Passes
	 */
	@Test
	public void testStreamWriterTurtle () throws FileNotFoundException, IOException
	{
		try ( OutputStream out = new FileOutputStream ( "target/test-stream-writer.ttl" ) )
		{
			StreamRDF writer = StreamRDFWriter.getWriterStream ( out, RDFFormat.TURTLE_BLOCKS );
			StreamOps.graphToStream ( model.getGraph (), writer );
		}
	}
	
	/**
	 * Fails, "No serialization for language Lang:RDF/XML"
	 */
	@Test
	public void testStreamWriterRDFLang () throws FileNotFoundException, IOException
	{
		try ( OutputStream out = new FileOutputStream ( "target/test-stream-writer-rdf-lang.rdf" ) )
		{
			StreamRDF writer = StreamRDFWriter.getWriterStream ( out, Lang.RDFXML );
			StreamOps.graphToStream ( model.getGraph (), writer );
		}
	}

	/**
	 * Fails, "Failed to find a writer factory for RDF/XML/pretty"
	 */
	@Test
	public void testStreamWriterRDFFormat () throws FileNotFoundException, IOException
	{
		try ( OutputStream out = new FileOutputStream ( "target/test-stream-writer-rdf-fmt.rdf" ) )
		{
			StreamRDF writer = StreamRDFWriter.getWriterStream ( out, RDFFormat.RDFXML );
			StreamOps.graphToStream ( model.getGraph (), writer );
		}
	}
}
