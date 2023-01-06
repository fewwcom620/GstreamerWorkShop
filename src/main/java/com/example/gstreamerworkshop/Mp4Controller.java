package com.example.gstreamerworkshop;

import com.sun.org.apache.xerces.internal.parsers.XMLParser;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.mp4.MP4Parser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.ContentHandler;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class Mp4Controller {

    private String perByte = "1000";
    private int scale = 2;

    @GetMapping("/metadata")
    public List<Map<String, Object>> metadata() {
        File file = new File("/Users/fewwcom/Downloads/test");

        MP4Parser mp4Parser = new MP4Parser();
        List<Map<String, Object>> metaDatas = Arrays.stream(Objects.requireNonNull(file.listFiles()))
                .filter(File::isFile)
                .filter(fileInThis -> fileInThis.getName().contains(".MP4") ||
                        fileInThis.getName().contains(".mp4"))
                .map(fileInThis -> {
                    Mp4Metadata metadata = new Mp4Metadata();

                    metadata.setFileName(fileInThis.getName());

                    BodyContentHandler handler = new BodyContentHandler();
                    Metadata metadataTika = new Metadata();
                    ParseContext pContext = new ParseContext();
                    try {
                        FileInputStream inputStream = new FileInputStream(fileInThis);

                        mp4Parser.parse(inputStream, handler, metadataTika ,pContext);
                    } catch (FileNotFoundException e) {

                    } catch (TikaException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (SAXException e) {
                        throw new RuntimeException(e);
                    }

                    String results = String.join(",", metadataTika.names());
                    metadata.setDuration(metadataTika.get("xmpDM:duration"));
                    metadata.setNames(results);
                    metadata.setAudioRate(metadataTika.get("xmpDM:audioSampleRate"));
                    metadata.setCreatedBy(metadataTika.get("dcterms:created"));
                    long bytes = fileInThis.length();
                    String length = String.valueOf(bytes);
                    metadata.setLength(length);
                    BigDecimal bytesBD = new BigDecimal(length);
                    metadata.setKb(bytesBD
                            .divide(new BigDecimal(perByte))
                            .setScale(scale, BigDecimal.ROUND_HALF_UP)
                                    .toString());
                    metadata.setMb(bytesBD
                            .divide(new BigDecimal(perByte))
                            .divide(new BigDecimal(perByte))
                            .setScale(scale, BigDecimal.ROUND_HALF_UP)
                                    .toString());

                    metadata.setGb(bytesBD
                            .divide(new BigDecimal(perByte))
                            .divide(new BigDecimal(perByte))
                            .divide(new BigDecimal(perByte))
                            .setScale(scale, BigDecimal.ROUND_HALF_UP)
                            .toString());

                    return metadata.toMap();
                })
                .collect(Collectors.toList());

        return metaDatas;

    }
}
