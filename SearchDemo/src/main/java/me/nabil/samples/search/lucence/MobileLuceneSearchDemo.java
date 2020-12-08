package me.nabil.samples.search.lucence;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO:类描述
 *
 * @author yuanshan
 * @date 2020/12/8
 */
public class MobileLuceneSearchDemo {

    private static Directory directory;

    static {
        try {
            directory = FSDirectory.open(Paths.get(new URI("file:///tmp/testindex")));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, ParseException {
        initIndex();

        search();
    }

    private static void search() throws IOException, ParseException {
        Analyzer analyzer = new StandardAnalyzer();
        DirectoryReader ireader = DirectoryReader.open(directory);
        IndexSearcher isearcher = new IndexSearcher(ireader);
        String[] multiFields = {"mobile", "content"};
        MultiFieldQueryParser parser = new MultiFieldQueryParser(multiFields, analyzer);
        // 设定具体的搜索词
        Query query = parser.parse("13");
        TopDocs docs = isearcher.search(query, 10);
        ScoreDoc[] hits = docs.scoreDocs;
        for (ScoreDoc doc : hits) {
            System.out.println(doc.toString());
        }
    }

    private static void initIndex() throws IOException {
        Analyzer analyzer = new StandardAnalyzer();
        // 配置索引
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter iwriter = new IndexWriter(directory, config);
        // 这里，写索引
        for (String mobile : getMobiles()) {
            Document doc = new Document();
            doc.add(new Field("mobile", mobile, TextField.TYPE_STORED));
            iwriter.addDocument(doc);
        }
        iwriter.close();
    }

    private static List<String> getMobiles() {
        List<String> result = new ArrayList<>();
        result.add("13917943433");
        return result;
    }
}
