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
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

/**
 * lucene测试
 *
 * @author nabilzhang
 * @date 18/04/2017
 */
public class SimpleLuceneDemo {
    public static void main(String[] args) throws IOException, ParseException, InvalidTokenOffsetsException, URISyntaxException {
        Analyzer analyzer = new StandardAnalyzer();

        // Store theindex in memory:
        // 索引存到内存中的目录
//        Directory directory = new RAMDirectory();
        // To store anindex on disk, use this instead:
        Directory directory = FSDirectory.open(Paths.get(new URI("file:///tmp/testindex")));
        // 配置索引
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter iwriter = new IndexWriter(directory, config);
        // 这里，将5篇文档filedname信息和content信息存入索引
        Document doc[] = new Document[6];
        for (int i = 0; i < 6; i++) {
            doc[i] = new Document();
        }
        String[] text = {"中华人民共和国中央人民政府", "中国是个伟大的国家", "我出生在美丽的中国，我爱中国，中国",
                "中华美丽的中国爱你", "美国跟中国式的国家", "卧槽，你是中国的"};

        doc[0].add(new Field("fieldname", text[0], TextField.TYPE_STORED));
        //doc[0].add(new Field("content", text[5], TextField.TYPE_STORED));
        doc[1].add(new Field("fieldname", text[1], TextField.TYPE_STORED));
        doc[2].add(new Field("fieldname", text[2], TextField.TYPE_STORED));
        doc[3].add(new Field("fieldname", text[3], TextField.TYPE_STORED));
        doc[4].add(new Field("fieldname", text[4], TextField.TYPE_STORED));
        doc[5].add(new Field("fieldname", text[5], TextField.TYPE_STORED));
        iwriter.addDocument(doc[0]);
        iwriter.addDocument(doc[1]);
        iwriter.addDocument(doc[2]);
        iwriter.addDocument(doc[3]);
        iwriter.addDocument(doc[4]);
        iwriter.addDocument(doc[5]);
        iwriter.close();

        // Now searchthe index:
        // 索引构建完毕，准备搜索。
        // 设定搜索目录
        DirectoryReader ireader = DirectoryReader.open(directory);
        IndexSearcher isearcher = new IndexSearcher(ireader);
        // Parse asimple query that searches for "text":
        // QueryParserparser = new QueryParser(Version.LUCENE_CURRENT,
        // "fieldname",analyzer);
        // 使用同样的方式对多field进行搜索
        String[] multiFields = {"fieldname", "content"};
        MultiFieldQueryParser parser = new MultiFieldQueryParser(multiFields, analyzer);
        // 设定具体的搜索词
        Query query = parser.parse("卧槽，中国");
        TopDocs docs = isearcher.search(query, 10);//查找
        ScoreDoc[] hits = docs.scoreDocs;

        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<span style='color:green'>", "</span>");
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));
        //高亮htmlFormatter对象
        //设置高亮附近的字数
        highlighter.setTextFragmenter(new SimpleFragmenter(100));


        // assertEquals(1, hits.length);
        System.out.println("Searched " + hits.length + " documents.");
        // Iteratethrough the results:
        for (int i = 0; i < hits.length; i++) {
            Document hitDoc = isearcher.doc(hits[i].doc);
            String[] scoreExplain = null;
            // scoreExplain可以显示文档的得分详情，这里用split截取总分
            scoreExplain = isearcher.explain(query, hits[i].doc).toString()
                    .split(" ", 2);
            String scores = scoreExplain[0];
            // assertEquals("Thisis the text to be indexed.",
            // hitDoc.get("fieldname"));
            System.out.println("score:" + scores);
            String value = hitDoc.get("fieldname");
            ;
            TokenStream tokenStream = analyzer.tokenStream(value, new StringReader(value));
            String str1 = highlighter.getBestFragment(tokenStream, value);

            System.out.println(str1);
        }
        ireader.close();
        directory.close();
    }
}
