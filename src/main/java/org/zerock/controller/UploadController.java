package org.zerock.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.AttachFileDTO;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j
public class UploadController {

   @GetMapping("/uploadForm")
   public void uploadForm() {

      log.info("upload form");
   }

   // @PostMapping("/uploadFormAction")
   // public void uploadFormPost(MultipartFile[] uploadFile, Model model) {
   //
   // for (MultipartFile multipartFile : uploadFile) {
   //
   // log.info("-------------------------------------");
   // log.info("Upload File Name: " +multipartFile.getOriginalFilename());
   // log.info("Upload File Size: " +multipartFile.getSize());
   //
   // }
   // }

   @PostMapping("/uploadFormAction")
   // ���߾��ε带���� MultipartFile ����
   public void uploadFormPost(MultipartFile[] uploadFile, Model model) {

      String uploadFolder = "C:\\upload";      // ���� ���ε� ��ġ

      for (MultipartFile multipartFile : uploadFile) {

         log.info("-------------------------------------");
         log.info("Upload File Name: " + multipartFile.getOriginalFilename());
         log.info("Upload File Size: " + multipartFile.getSize());

         File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());   // ���ε� ��ġ�� ���ϸ��� ���� ��ü�� ����

         try {
            multipartFile.transferTo(saveFile);   // saveFile���� �Ű��������Ͽ� ������ ����
         } catch (Exception e) {
            log.error(e.getMessage());
         } // end catch
      } // end for

   }
   
   // @PreAuthorize�� �ش� �޼��尡 ȣ��Ǳ� ������ �˻��Ѵ�. (������ �ش� �޼��带 ȣ���� ������ �ִ����� Ȯ��)
   // isAnonymouse()       = �α��� ���� ȣ��
   // isAuthenticated()   = �α��� ���θ� Ȯ��(�α����� ����ڸ� ȣ���� �����ϴ�.)
   // hasRole(member)      = ������ member�� ����ڸ� ȣ�Ⱑ��
   // hasRole(admin)      = ������ admin�� ����ڸ� ȣ�Ⱑ��
   @PreAuthorize("isAuthenticated()")
   //GET����� ����Ͽ� ����
   @GetMapping("/uploadAjax")
   public void uploadAjax() {

      log.info("upload ajax");
   }

   // @PostMapping("/uploadAjaxAction")
   // public void uploadAjaxPost(MultipartFile[] uploadFile) {
   //
   // log.info("update ajax post.........");
   //
   // String uploadFolder = "C:\\upload";
   //
   // for (MultipartFile multipartFile : uploadFile) {
   //
   // log.info("-------------------------------------");
   // log.info("Upload File Name: " + multipartFile.getOriginalFilename());
   // log.info("Upload File Size: " + multipartFile.getSize());
   //
   // String uploadFileName = multipartFile.getOriginalFilename();
   //
   // // IE has file path
   // uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") +
   // 1);
   // log.info("only file name: " + uploadFileName);
   //
   // File saveFile = new File(uploadFolder, uploadFileName);
   //
   // try {
   //
   // multipartFile.transferTo(saveFile);
   // } catch (Exception e) {
   // log.error(e.getMessage());
   // } // end catch
   //
   // } // end for
   //
   // }

   private String getFolder() {

      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   // SimpleDateFormat ��ü ����

      Date date = new Date();   // Date ��ü ����

      String str = sdf.format(date);   // Date��ü�� SimpleDateFormat �������� ����
      
      // Replace() = �ٲٰ���� ���ڷ� ���ڿ��� ġȯ
      // File.separator = ���α׷��� ���� ���� OS�� �ش��ϴ� �����ڸ� �����մϴ�.
      return str.replace("-", File.separator);
   }

   // @PostMapping("/uploadAjaxAction")
   // public void uploadAjaxPost(MultipartFile[] uploadFile) {
   //
   // String uploadFolder = "C:\\upload";
   //
   // // make folder --------
   // File uploadPath = new File(uploadFolder, getFolder());
   // log.info("upload path: " + uploadPath);
   //
   // if (uploadPath.exists() == false) {
   // uploadPath.mkdirs();
   // }
   // // make yyyy/MM/dd folder
   //
   // for (MultipartFile multipartFile : uploadFile) {
   //
   // log.info("-------------------------------------");
   // log.info("Upload File Name: " + multipartFile.getOriginalFilename());
   // log.info("Upload File Size: " + multipartFile.getSize());
   //
   // String uploadFileName = multipartFile.getOriginalFilename();
   //
   // // IE has file path
   // uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") +
   // 1);
   // log.info("only file name: " + uploadFileName);
   //
   // // File saveFile = new File(uploadFolder, uploadFileName);
   // File saveFile = new File(uploadPath, uploadFileName);
   //
   // try {
   //
   // multipartFile.transferTo(saveFile);
   // } catch (Exception e) {
   // log.error(e.getMessage());
   // } // end catch
   //
   // } // end for
   //
   // }

   // @PostMapping("/uploadAjaxAction")
   // public void uploadAjaxPost(MultipartFile[] uploadFile) {
   //
   // String uploadFolder = "C:\\upload";
   //
   // // make folder --------
   // File uploadPath = new File(uploadFolder, getFolder());
   // log.info("upload path: " + uploadPath);
   //
   // if (uploadPath.exists() == false) {
   // uploadPath.mkdirs();
   // }
   // // make yyyy/MM/dd folder
   //
   // for (MultipartFile multipartFile : uploadFile) {
   //
   // log.info("-------------------------------------");
   // log.info("Upload File Name: " + multipartFile.getOriginalFilename());
   // log.info("Upload File Size: " + multipartFile.getSize());
   //
   // String uploadFileName = multipartFile.getOriginalFilename();
   //
   // // IE has file path
   // uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") +
   // 1);
   // log.info("only file name: " + uploadFileName);
   //
   // UUID uuid = UUID.randomUUID();
   //
   // uploadFileName = uuid.toString() + "_" + uploadFileName;
   //
   // File saveFile = new File(uploadPath, uploadFileName);
   //
   // try {
   //
   // multipartFile.transferTo(saveFile);
   // } catch (Exception e) {
   // log.error(e.getMessage());
   // } // end catch
   //
   // } // end for
   //
   // }

   private boolean checkImageType(File file) {

      try {
         // probeContentType = ���� ������ ������ �ƴ϶� ������ Ȯ���ڸ� �̿��Ͽ� ����Ÿ���� �Ǵ�
         //                 Ȯ���ڰ� ���� ������ null�� ��ȯ
         //                 ���� ������ �������� �ʾƵ� Ȯ���ڷ� ����Ÿ���� ��ȯ
         //                 ���� Ÿ�� = path
         // file.toPath()   = file ��ü�� Path��ü�� ��ȯ (probeContentType�� ����Ÿ�� = Path)
         String contentType = Files.probeContentType(file.toPath());

         // startsWith() = � ���ڿ��� Ư�� ���ڷ� �����ϴ��� Ȯ���Ͽ� ����� true Ȥ�� false�� ��ȯ
         return contentType.startsWith("image");

      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      return false;
   }

   // @PostMapping("/uploadAjaxAction")
   // public void uploadAjaxPost(MultipartFile[] uploadFile) {
   //
   // String uploadFolder = "C:\\upload";
   //
   // // make folder --------
   // File uploadPath = new File(uploadFolder, getFolder());
   // log.info("upload path: " + uploadPath);
   //
   // if (uploadPath.exists() == false) {
   // uploadPath.mkdirs();
   // }
   // // make yyyy/MM/dd folder
   //
   // for (MultipartFile multipartFile : uploadFile) {
   //
   // log.info("-------------------------------------");
   // log.info("Upload File Name: " + multipartFile.getOriginalFilename());
   // log.info("Upload File Size: " + multipartFile.getSize());
   //
   // String uploadFileName = multipartFile.getOriginalFilename();
   //
   // // IE has file path
   // uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") +
   // 1);
   // log.info("only file name: " + uploadFileName);
   //
   // UUID uuid = UUID.randomUUID();
   //
   // uploadFileName = uuid.toString() + "_" + uploadFileName;
   //
   // try {
   // File saveFile = new File(uploadPath, uploadFileName);
   // multipartFile.transferTo(saveFile);
   // // check image type file
   // if (checkImageType(saveFile)) {
   //
   // FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" +
   // uploadFileName));
   //
   // Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100,
   // 100);
   //
   // thumbnail.close();
   // }
   //
   // } catch (Exception e) {
   // e.printStackTrace();
   // } //end catch
   // } // end for
   //
   // }

   @PostMapping(value = "/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   @ResponseBody
   //HttpEntity     : HTTP ��û(Request) �Ǵ� ����(Response)�� �ش��ϴ� HttpHeader�� HttpBody�� �����ϴ� Ŭ����
   //ResponseEntity : HttpEntity�� ��ӹ����� ��� �����Ϳ� HTTP �����ڵ带 ���� �����ϴ� Ŭ����
   //               �����ڵ�� ���� HTTP �����ڵ带 �����ϰ� ���� �����Ϳ� �Բ� ������ �� �ִ�.
   public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile) {

      List<AttachFileDTO> list = new ArrayList<>();   // List ��ü ����
      String uploadFolder = "C:\\upload";            // ���� ������ ������ �ּ� ����

      String uploadFolderPath = getFolder();         // getFolder()�޼����� ����� uploadFolderPath�� ����
      // make folder --------
      File uploadPath = new File(uploadFolder, uploadFolderPath);   // File ��ü ����

      if (uploadPath.exists() == false) {      // uploadFolder ��ο� ���丮�� ��/���� Ȯ��
         uploadPath.mkdirs();            // uploadFolder ��ο� ���丮�� ������ ���丮�� ����
      }
      // make yyyy/MM/dd folder

      for (MultipartFile multipartFile : uploadFile) {

         AttachFileDTO attachDTO = new AttachFileDTO();

         String uploadFileName = multipartFile.getOriginalFilename();   // ���� ������ �̸��� uploadFileName�� ����

         // uploadFileName�� ����� ������ ���ڿ��� "\\"+1�ΰ��� �߶� uploadFileName�� �ٽ� ����
         uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
         log.info("only file name: " + uploadFileName);
         // uploadFileName�� DTO�� ����
         attachDTO.setFileName(uploadFileName);

         // UUID : ���� ���� �ĺ��ڸ� �ǹ��ϸ� �ߺ��� ���� �ʴ� ������ ���� �����ϰ��� �Ҷ� �ַ� ����� �Ǵ� ���� �ĺ���
         // UUID.randomUUID() : UUID�� ����Ͽ� ���� �ĺ��ڸ� �����ϴ� �޼���
         UUID uuid = UUID.randomUUID();

         // �������� ������ UUID�� "_"�� ���ϸ��� �߰��� ���� uploadFileName�� ����
         uploadFileName = uuid.toString() + "_" + uploadFileName;

         try {
            File saveFile = new File(uploadPath, uploadFileName);   // ���� ���, ���ϸ��� �Ű������� ���� ��ü ����
            multipartFile.transferTo(saveFile);      // ���� ����

            attachDTO.setUuid(uuid.toString());         // UUID���� DTO�� ����
            attachDTO.setUploadPath(uploadFolderPath);   // ������ ������ ��θ� DTO�� ����

            // check image type file
            if (checkImageType(saveFile)) {      // checkImageType() �޼��带 ����Ͽ� �ش� ������ image���� Ȯ��

               attachDTO.setImage(true);      // �ش� ������ image��� �ش� image�� DTO�� ����

               // �־��� File ��ü�� ����Ű�� ������ ���� ���� ��ü�� ����
               // ������ ������ ������ ���� �� ������ ����� ���ο� ������ ����
               // �������ϸ� "s_"�� ���ؼ� ���ϸ� ���� (�Ϲ������� �������ϸ� �տ� s_�� ����Ѵٰ� ��)
               FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));   // 

               // Thumbnailator.createThumbnail() : thumbnail�� ����� �޼���
               // ������ thumbnail �̹������ �̹��� ũ�⸦ �Ű������� thumbnail ����
               Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);

               thumbnail.close();
            }

            // add to List
            list.add(attachDTO);   // ����Ʈ�� DTO�� ����

         } catch (Exception e) {
            e.printStackTrace();
         }

      } // end for
      return new ResponseEntity<>(list, HttpStatus.OK);
   }

   // ���� ����
   @GetMapping("/display")
   @ResponseBody
   // ResponseEntity<byte[]> : ���ڵ��� ���� �̸��� byte[]�� ����
   public ResponseEntity<byte[]> getFile(String fileName) {

      log.info("fileName: " + fileName);
      
      // ���� ��ο� ���ϸ��� �Ű������� ���� ��ü ����
      File file = new File("c:\\upload\\" + fileName);

      log.info("file: " + file);

      ResponseEntity<byte[]> result = null;

      try {
         // HTTP ��û�Ҷ� ������ ������(Body)�� �������ִ� ���(Header)�� ������ �Ѵ�
         // Spring Framework���� �������ִ� HttpHeaders Ŭ������ ����Ͽ� Header ������ �� �ִ�.
         // Header������ add()�޼��带 ����Ͽ� Header�� �� ������ �߰�
         HttpHeaders header = new HttpHeaders();

         header.add("Content-Type", Files.probeContentType(file.toPath()));
         
         // FileCopyUtils class�� ���ϰ� stream ���翡 ����� �� �ִ� �޼��带 �����ϴ� class
         // copyToByteArray() �޼���� �Ķ���ͷ� �ο��ϴ� File ��ü�̸�, ��� ������ �����Ͽ� Byte �迭�� ��ȯ���ִ�class
         result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      return result;
   }

   // @GetMapping(value = "/download", produces =
   // MediaType.APPLICATION_OCTET_STREAM_VALUE)
   // @ResponseBody
   // public ResponseEntity<Resource> downloadFile(String fileName) {
   //
   // log.info("download file: " + fileName);
   //
   // Resource resource = new FileSystemResource("c:\\upload\\" + fileName);
   //
   // log.info("resource: " + resource);
   //
   // return null;
   // }

   // @GetMapping(value = "/download", produces =
   // MediaType.APPLICATION_OCTET_STREAM_VALUE)
   // @ResponseBody
   // public ResponseEntity<Resource> downloadFile(String fileName) {
   //
   // log.info("download file: " + fileName);
   //
   // Resource resource = new FileSystemResource("c:\\upload\\" + fileName);
   //
   // log.info("resource: " + resource);
   //
   // String resourceName = resource.getFilename();
   //
   // HttpHeaders headers = new HttpHeaders();
   // try {
   // headers.add("Content-Disposition",
   // "attachment; filename=" + new String(resourceName.getBytes("UTF-8"),
   // "ISO-8859-1"));
   // } catch (UnsupportedEncodingException e) {
   // e.printStackTrace();
   // }
   // return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
   // }

   // @GetMapping(value="/download" ,
   // produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
   // @ResponseBody
   // public ResponseEntity<Resource>
   // downloadFile(@RequestHeader("User-Agent")String userAgent, String fileName){
   //
   // Resource resource = new FileSystemResource("c:\\upload\\" + fileName);
   //
   // if(resource.exists() == false) {
   // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
   // }
   //
   // String resourceName = resource.getFilename();
   //
   // HttpHeaders headers = new HttpHeaders();
   // try {
   //
   // boolean checkIE = (userAgent.indexOf("MSIE") > -1 ||
   // userAgent.indexOf("Trident") > -1);
   //
   // String downloadName = null;
   //
   // if (checkIE) {
   // downloadName = URLEncoder.encode(resourceName, "UTF8").replaceAll("\\+", "
   // ");
   // } else {
   // downloadName = new String(resourceName.getBytes("UTF-8"), "ISO-8859-1");
   // }
   //
   // headers.add("Content-Disposition", "attachment; filename=" + downloadName);
   //
   // } catch (UnsupportedEncodingException e) {
   // e.printStackTrace();
   // }
   //
   // return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
   // }

   // @GetMapping(value="/download" ,
   // produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
   // @ResponseBody
   // public ResponseEntity<Resource>
   // downloadFile(@RequestHeader("User-Agent")String userAgent, String fileName){
   //
   // Resource resource = new FileSystemResource("c:\\upload\\" + fileName);
   //
   // if(resource.exists() == false) {
   // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
   // }
   //
   // String resourceName = resource.getFilename();
   //
   // //remove UUID
   // String resourceOriginalName =
   // resourceName.substring(resourceName.indexOf("_")+1);
   //
   // HttpHeaders headers = new HttpHeaders();
   // try {
   //
   // boolean checkIE = (userAgent.indexOf("MSIE") > -1 ||
   // userAgent.indexOf("Trident") > -1);
   //
   // String downloadName = null;
   //
   // if(checkIE) {
   // downloadName = URLEncoder.encode(resourceOriginalName,
   // "UTF8").replaceAll("\\+", " ");
   // }else {
   // downloadName = new
   // String(resourceOriginalName.getBytes("UTF-8"),"ISO-8859-1");
   // }
   //
   // headers.add("Content-Disposition", "attachment; filename="+downloadName);
   //
   // } catch (UnsupportedEncodingException e) {
   // e.printStackTrace();
   // }
   //
   // return new ResponseEntity<Resource>(resource, headers,HttpStatus.OK);
   // }

   @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
   @ResponseBody
   // ResponseEntity�� view�� �������� �ʴ� ���·� ��û�� ó���ϰ� ���� ��� ������ �� HTTP �����ڵ带 �����Ͽ� ������ �� �ִ�. 
   public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent, String fileName) {

      // Resource : ������ ��θ� ã��
      // 1) UrlResource         : Prefix�� ���������� ������ְ� �ش� ���ҽ��� ��ġ�� �˷��ִ� URL����� ���ؼ� ���ҽ��� ��ġ�� �˷��ִ� ���
      //                     # Resource resource = new UrlResource("file:{������}");
      // 2) ClassPathResource      : Class Loader�� ���ؼ� ClassPath���� ���ҽ��� ã�� ���
      //                     # ClassPathResource resource = new ClassPathResource("dao.xml");
      // 3) FileSystemResource   : �����ζ� ����Ͽ� ���Ͻý��ۿ��� ���ҽ��� ã�� ���
      //                     # Resource resource = new FileSystemResource({������});
      Resource resource = new FileSystemResource("c:\\upload\\" + fileName);

      if (resource.exists() == false) {                  // �ش��ξ� �������� �ִ��� Ȯ��
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);   // ������ ������ ���������� ����
      }

      String resourceName = resource.getFilename();   // �ٿ�ε��� �����̸��� resourceName�� ����

      // ���� �ٿ�ε�� indexOf("_")�� ����Ͽ� ������ ���ϸ��� resourceOriginalName�� ����
      String resourceOriginalName = resourceName.substring(resourceName.indexOf("_") + 1);

      // 
      HttpHeaders headers = new HttpHeaders();
      try {
         // userAgent�� ����Ͽ� IE���� Ȯ��
         boolean checkIE = (userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("Trident") > -1);

         String downloadName = null;

         if (checkIE) {
            // URLEncoder�� URL���� �ѱ��� encoding ���ش�.
            // ������ URL�� +�� ������ +��ȣ ���� URL�� encoding���� �ʴ´�. (URLEncoder�� +��ȣ�� �������� ��ȯ�Ѵ�.)
            // replaceAll()�� ����Ͽ� +��ȣ�� \\+��ȣ�� ��ȯ
            downloadName = URLEncoder.encode(resourceOriginalName, "UTF8").replaceAll("\\+", " ");
         } else {
            // getBytes() �޼���� String(���ڿ�)�� default charset���� ���ڵ��Ͽ� byte �迭�� ��ȯ
            downloadName = new String(resourceOriginalName.getBytes("UTF-8"), "ISO-8859-1");
         }

         headers.add("Content-Disposition", "attachment; filename=" + downloadName);

      } catch (UnsupportedEncodingException e) {
         e.printStackTrace();
      }

      return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
   }
   
   // ���� ����
   // @PreAuthorize :  �ش� �޼��尡 ȣ��Ǳ� ������ �˻�(������ �ش� �޼��带 ȣ���� ������ �ִ����� Ȯ��)
   // isAuthenticated() : �α��� ��/�� Ȯ��
   @PreAuthorize("isAuthenticated()")
   @PostMapping("/deleteFile")
   @ResponseBody
   public ResponseEntity<String> deleteFile(String fileName, String type) {

      log.info("deleteFile: " + fileName);

      File file;

      try {
         // URLEncoder�η� ���ڵ��� ����� �ݴ�� ���ڵ��Ѵ�.
         file = new File("c:\\upload\\" + URLDecoder.decode(fileName, "UTF-8"));

         file.delete();   // ���ϻ���

         if (type.equals("image")) {
            // ������� �����ϱ����� s_�� ���Ե� ���ϸ��� largeFileName�� ����
            String largeFileName = file.getAbsolutePath().replace("s_", "");

            log.info("largeFileName: " + largeFileName);

            file = new File(largeFileName);

            file.delete();   // ����� ���� ����
         }

      } catch (UnsupportedEncodingException e) {
         e.printStackTrace();
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }

      return new ResponseEntity<String>("deleted", HttpStatus.OK);

   }
   

}