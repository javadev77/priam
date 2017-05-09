//package fr.sacem.priam.web.rest;
//
//import fr.sacem.priam.common.exception.TechnicalException;
//import fr.sacem.priam.common.zip.UnzipFile;
//import org.apache.commons.compress.archivers.ArchiveException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.ui.bind.annotation.RequestMapping;
//import org.springframework.ui.bind.annotation.RequestMethod;
//import org.springframework.ui.bind.annotation.RestController;
//
//import java.io.File;
//import java.io.IOException;
//
///**
// * Created by benmerzoukah on 26/04/2017.
// */
//@RestController
//@RequestMapping("/app")
//public class SftpResource {
//
//
//  @RequestMapping(value = "/rest/sftp", method = RequestMethod.GET,  produces = MediaType.TEXT_PLAIN_VALUE)
//  public ResponseEntity<String> listFiles() {
//
//    StringBuilder sb = new StringBuilder();
//
//    File file = new File("/export/priam/ftp-inbound");
//    File toDir = new File("/export/priam/unzip" );
//    if(!toDir.exists()) {
//      toDir.mkdir();
//    }
//    System.out.println("Dir : " + file.getAbsolutePath());
//    if(file.exists() && file.isDirectory()) {
//        for(File received : file.listFiles()) {
//          System.out.println("File received : " + received.getAbsolutePath());
//          if(received.isFile()) {
//            sb.append("File :" + received.getName()).append("\n");
//          }
//
//          try {
//            new UnzipFile(received).uncompress(toDir);
//          } catch (IOException e) {
//            e.printStackTrace();
//          } catch (ArchiveException e) {
//            e.printStackTrace();
//          } catch (TechnicalException e) {
//            e.printStackTrace();
//          }
//        }
//    }
//
//    return new ResponseEntity<>(sb.toString(), HttpStatus.OK);
//  }
//}
