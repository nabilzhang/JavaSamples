package me.nabil.samples.search.lucence.mobile;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.ngram.NGramTokenizerFactory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.util.ClasspathResourceLoader;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.LongRange;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MobileLuceneSearchDemo
 *
 * @author nabil
 * @date 2020/12/8
 */
public class MobileLuceneSearchDemo {

    private static Directory DIRECTORY;

    static {
        try {
            DIRECTORY = FSDirectory.open(Paths.get(new URI("file:///tmp/testindex")));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        initIndex();

        search();
    }

    private static void search() throws IOException {
        DirectoryReader directoryReader = DirectoryReader.open(DIRECTORY);
        IndexSearcher indexSearcher = new IndexSearcher(directoryReader);

        BooleanQuery.Builder builder = new BooleanQuery.Builder()
                .setDisableCoord(true)
                .add(new BooleanClause(new PhraseQuery.Builder().add(new Term("mobile", "1")).build(), BooleanClause.Occur.MUST))
                .add(new BooleanClause(IntPoint.newExactQuery("city_id", 1), BooleanClause.Occur.MUST))
                .add(new BooleanClause(IntPoint.newExactQuery("province_id", 1), BooleanClause.Occur.MUST))
                .add(new BooleanClause(LongPoint.newRangeQuery("timestamp", 0, System.currentTimeMillis()), BooleanClause.Occur.MUST));

        Query query = builder.build();
        TopDocs docs = indexSearcher.search(query, 10);
        ScoreDoc[] hits = docs.scoreDocs;
        for (ScoreDoc doc : hits) {
            System.out.println(indexSearcher.doc(doc.doc).getFields());
        }
    }

    private static void initIndex() throws IOException {
        Map<String, Analyzer> fieldAl = new HashMap<>();
        Map<String, String> ngramParameterMap = new HashMap<>();
        ngramParameterMap.put("minGramSize", "1");
        ngramParameterMap.put("maxGramSize", "11");
        fieldAl.put("mobile",
                CustomAnalyzer.builder(new ClasspathResourceLoader())
                        .withTokenizer(NGramTokenizerFactory.class, ngramParameterMap)
                        .build());
        Analyzer analyzer = new PerFieldAnalyzerWrapper(new StandardAnalyzer(), fieldAl);

        // 配置索引
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(DIRECTORY, config);
        // 这里，写索引
        for (MobileItem mobile : getMobiles()) {
            Document doc = new Document();
            doc.add(new Field("mobile", mobile.getMobile(), TextField.TYPE_STORED));
            doc.add(new IntPoint("city_id", mobile.getCityId()));
            doc.add(new StoredField("city_id", mobile.getCityId()));
            doc.add(new IntPoint("province_id", mobile.getProvinceId()));
            doc.add(new StoredField("province_id", mobile.getProvinceId()));
            doc.add(new LongPoint("timestamp", mobile.getTimestamp()));
            doc.add(new StoredField("timestamp", mobile.getTimestamp()));

            doc.add(new LongRange("timestamp_range", new long[]{0L}, new long[]{mobile.getTimestamp()}));
            indexWriter.addDocument(doc);
        }
        indexWriter.close();
    }

    private static List<MobileItem> getMobiles() {
        List<MobileItem> result = new ArrayList<>();
        result.add(MobileItem.MobileItemBuilder.aMobileItem()
                .withMobile("13918989999")
                .withCityId(1)
                .withProvinceId(1)
                .withTimestamp(System.currentTimeMillis() - 1000 * 60)
                .build()
        );
        return result;
    }
}
