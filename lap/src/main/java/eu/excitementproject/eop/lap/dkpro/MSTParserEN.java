package eu.excitementproject.eop.lap.dkpro;

import static org.uimafit.factory.AnalysisEngineFactory.createPrimitiveDescription;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.mstparser.MSTParser;
import de.tudarmstadt.ukp.dkpro.core.tokit.BreakIteratorSegmenter;
import de.tudarmstadt.ukp.dkpro.core.treetagger.TreeTaggerPosLemmaTT4J;

import eu.excitementproject.eop.lap.LAPException;
import eu.excitementproject.eop.lap.lappoc.LAP_ImplBaseAE;

/**
 * This LAPAccess implementation provides LAPAcess interface for English by using 
 * Sentence Splitter + Tree tagger + MSTParser. 
 * 
 * <P>
 * It will use default English Tree Tagger model, but you can select Parser model by the "variant" string in the constructor. 
 * (variant, is a model ID, that is used by DKPro parser AE) 
 * 
 * @author Gil
 */
public class MSTParserEN extends LAP_ImplBaseAE {

	/**
	 * Without any argument, this will initiate the instance with English default 
	 * model for MST Parser, with the capability of annotating the "three views" (Default, TextView & HypothesisView). 
	 * If you need only need to annotate a specific view (e.g. Default only, not for T & H View, etc) 
	 * call the full constructor with two arguments. (e.g. if you give one view only, its initialization is actually faster). 
	 * 
	 * @throws LAPException
	 */
	public MSTParserEN() throws LAPException 
	{
		this("default"); // default model 		
	}
	
	/**
	 * <P> 
	 * This constructor will initiate this MSTParser based LAP instance with the given modelVarient. 
	 * (Thus: de.tudarmstadt.ukp.dkpro.core.mstparser-model-parser-[LANGID]-[modelVarient] ) 
	 * 
	 * <P> 
	 * This will initiate the instance with the capability of annotating the "three views" (Default, TextView & HypothesisView). 
	 * If you need only need to annotate a specific view (e.g. Default only, not for T & H View, etc) 
	 * call the full constructor with two arguments. (e.g. if you give one view only, its initialization is faster).
	 * 
	 * @param modelVarient String of the model varient. If the given varient is not accessible in the class path, the underlying AE will raise an exception. 
	 * @throws LAPException
	 */
	public MSTParserEN(String modelVarient) throws LAPException 
	{
		super(); 
		languageIdentifier = "EN"; // set languageIdentifer
		this.modelVariant = modelVarient; // this will determine model-"variant" for model loading 
	}
	
	/**
	 * <P> 
	 * Full constructor, that accepts model Varient and view names. If you use this LAP for EOP EDAs (e.g. where you need TextView & Hypothesis View), you can simply call the constructor without view names (since their default is Default, TView and HView). 
	 * ModelVarient will load the desinated model-varient. 	(Thus: de.tudarmstadt.ukp.dkpro.core.mstparser-model-parser-[LANGID]-[modelVarient] ) 
	 * 
	 * @param modelVarient String of the model varient. If the given varient is not accessible in the class path, the underlying AE will raise an exception.
	 * @param views String array of "view names". Once initialized, the instance can only annotate this "previously given" Views only. (See LAP_ImplBaseAE for explanation). 
	 * @throws LAPException
	 */
	public MSTParserEN(String modelVarient, String[] views) throws LAPException 
	{
		super(views);
		languageIdentifier = "EN"; 
		this.modelVariant = modelVarient; 
	}
	
	@Override
	public AnalysisEngineDescription[] listAEDescriptors() throws LAPException {

		// This example uses DKPro BreakIterSegmenter, TreeTagger, and MSTParser 
		// simply return them in an array, with order. (sentence segmentation first, then tagging, then parsing) 
		AnalysisEngineDescription[] descArr = new AnalysisEngineDescription[3];
		try 
		{
			descArr[0] = createPrimitiveDescription(BreakIteratorSegmenter.class);
			descArr[1] = createPrimitiveDescription(TreeTaggerPosLemmaTT4J.class); 
			descArr[2] = createPrimitiveDescription(MSTParser.class,
					MSTParser.PARAM_VARIANT, modelVariant); 
		}
		catch (ResourceInitializationException e)
		{
			throw new LAPException("Unable to create AE descriptions", e); 
		}
		
		return descArr; 
				
	}

	private String modelVariant; 
	
}
