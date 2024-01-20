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
   // 다중업로드를위한 MultipartFile 설정
   public void uploadFormPost(MultipartFile[] uploadFile, Model model) {

      String uploadFolder = "C:\\upload";      // 파일 업로드 위치

      for (MultipartFile multipartFile : uploadFile) {

         log.info("-------------------------------------");
         log.info("Upload File Name: " + multipartFile.getOriginalFilename());
         log.info("Upload File Size: " + multipartFile.getSize());

         File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());   // 업로드 위치와 파일명을 파일 객체에 대입

         try {
            multipartFile.transferTo(saveFile);   // saveFile값을 매개변수로하여 파일을 저장
         } catch (Exception e) {
            log.error(e.getMessage());
         } // end catch
      } // end for

   }
   
   // @PreAuthorize는 해당 메서드가 호출되기 이전에 검사한다. (실제로 해당 메서드를 호출할 권한이 있는지를 확인)
   // isAnonymouse()       = 로그인 없이 호출
   // isAuthenticated()   = 로그인 여부를 확인(로그인한 사용자만 호출이 가능하다.)
   // hasRole(member)      = 권한이 member인 사용자만 호출가능
   // hasRole(admin)      = 권한이 admin인 사용자만 호출가능
   @PreAuthorize("isAuthenticated()")
   //GET방식을 사용하여 전송
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

      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   // SimpleDateFormat 객체 생성

      Date date = new Date();   // Date 객체 생성

      String str = sdf.format(date);   // Date객체를 SimpleDateFormat 형식으로 대입
      
      // Replace() = 바꾸고싶은 문자로 문자열을 치환
      // File.separator = 프로그램이 실행 중인 OS에 해당하는 구분자를 리턴합니다.
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
         // probeContentType = 실제 파일의 내용이 아니라 파일의 확장자를 이용하여 마임타입을 판단
         //                 확장자가 없는 파일은 null을 반환
         //                 실제 파일이 존재하지 않아도 확장자로 마입타입을 반환
         //                 리턴 타입 = path
         // file.toPath()   = file 객체를 Path객체로 변환 (probeContentType의 리턴타입 = Path)
         String contentType = Files.probeContentType(file.toPath());

         // startsWith() = 어떤 문자열이 특정 문자로 시작하는지 확인하여 결과를 true 혹은 false로 반환
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
   //HttpEntity     : HTTP 요청(Request) 또는 응답(Response)에 해당하는 HttpHeader와 HttpBody를 포함하는 클래스
   //ResponseEntity : HttpEntity를 상속받으며 결과 데이터와 HTTP 상태코드를 직접 제어하는 클래스
   //               에러코드와 같은 HTTP 상태코드를 전송하고 싶은 데이터와 함께 전송할 수 있다.
   public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile) {

      List<AttachFileDTO> list = new ArrayList<>();   // List 객체 생성
      String uploadFolder = "C:\\upload";            // 실제 파일을 저장할 주소 설정

      String uploadFolderPath = getFolder();         // getFolder()메서드의 결과를 uploadFolderPath에 대입
      // make folder --------
      File uploadPath = new File(uploadFolder, uploadFolderPath);   // File 객체 생성

      if (uploadPath.exists() == false) {      // uploadFolder 경로에 디렉토리의 유/무를 확인
         uploadPath.mkdirs();            // uploadFolder 경로에 디렉토리가 없으면 디렉토리를 생성
      }
      // make yyyy/MM/dd folder

      for (MultipartFile multipartFile : uploadFile) {

         AttachFileDTO attachDTO = new AttachFileDTO();

         String uploadFileName = multipartFile.getOriginalFilename();   // 원본 파일의 이름을 uploadFileName에 대입

         // uploadFileName의 경로중 마지막 문자열이 "\\"+1인것을 잘라서 uploadFileName에 다시 대입
         uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
         log.info("only file name: " + uploadFileName);
         // uploadFileName을 DTO에 저장
         attachDTO.setFileName(uploadFileName);

         // UUID : 범용 고유 식별자를 의미하며 중복이 되지 않는 유일한 값을 구성하고자 할때 주로 사용이 되는 고유 식별자
         // UUID.randomUUID() : UUID를 사용하여 고유 식별자를 생성하는 메서드
         UUID uuid = UUID.randomUUID();

         // 랜덤으로 생성된 UUID에 "_"와 파일명을 추가한 값을 uploadFileName에 대입
         uploadFileName = uuid.toString() + "_" + uploadFileName;

         try {
            File saveFile = new File(uploadPath, uploadFileName);   // 파일 경로, 파일명을 매개변수로 파일 객체 생성
            multipartFile.transferTo(saveFile);      // 파일 저장

            attachDTO.setUuid(uuid.toString());         // UUID값을 DTO에 저장
            attachDTO.setUploadPath(uploadFolderPath);   // 저장할 파일의 경로를 DTO에 저장

            // check image type file
            if (checkImageType(saveFile)) {      // checkImageType() 메서드를 사용하여 해당 파일이 image인지 확인

               attachDTO.setImage(true);      // 해당 파일이 image라면 해당 image를 DTO에 저장

               // 주어진 File 객체가 가리키는 파일을 쓰기 위한 객체를 생성
               // 기존의 파일이 존재할 때는 그 내용을 지우고 새로운 파일을 생성
               // 원본파일명에 "s_"를 더해서 파일명 생성 (일반적으로 원본파일명 앞에 s_를 사용한다고 함)
               FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));   // 

               // Thumbnailator.createThumbnail() : thumbnail을 만드는 메서드
               // 생성된 thumbnail 이미지명과 이미지 크기를 매개변수로 thumbnail 생성
               Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);

               thumbnail.close();
            }

            // add to List
            list.add(attachDTO);   // 리스트를 DTO에 저장

         } catch (Exception e) {
            e.printStackTrace();
         }

      } // end for
      return new ResponseEntity<>(list, HttpStatus.OK);
   }

   // 파일 보기
   @GetMapping("/display")
   @ResponseBody
   // ResponseEntity<byte[]> : 인코딩된 파일 이름을 byte[]로 받음
   public ResponseEntity<byte[]> getFile(String fileName) {

      log.info("fileName: " + fileName);
      
      // 파일 경로와 파일명을 매개변수로 파일 객체 생성
      File file = new File("c:\\upload\\" + fileName);

      log.info("file: " + file);

      ResponseEntity<byte[]> result = null;

      try {
         // HTTP 요청할때 보내는 데이터(Body)를 설명해주는 헤더(Header)도 보내야 한다
         // Spring Framework에서 제공해주는 HttpHeaders 클래스를 사용하여 Header 생성할 수 있다.
         // Header생성시 add()메서드를 사용하여 Header에 들어갈 내용을 추가
         HttpHeaders header = new HttpHeaders();

         header.add("Content-Type", Files.probeContentType(file.toPath()));
         
         // FileCopyUtils class는 파일과 stream 복사에 사용할 수 있는 메서드를 제공하는 class
         // copyToByteArray() 메서드는 파라미터로 부여하는 File 객체이며, 대상 파일을 복사하여 Byte 배열로 반환해주는class
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
   // ResponseEntity는 view를 제공하지 않는 형태로 요청을 처리하고 직접 결과 데이터 및 HTTP 상태코드를 설정하여 응답할 수 있다. 
   public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent, String fileName) {

      // Resource : 파일의 경로를 찾기
      // 1) UrlResource         : Prefix로 프로토콜을 명시해주고 해당 리소스의 위치를 알려주는 URL방식을 통해서 리소스의 위치를 알려주는 방식
      //                     # Resource resource = new UrlResource("file:{절대경로}");
      // 2) ClassPathResource      : Class Loader를 통해서 ClassPath에서 리소스를 찾는 방식
      //                     # ClassPathResource resource = new ClassPathResource("dao.xml");
      // 3) FileSystemResource   : 절대경로라를 사용하여 파일시스템에서 리소스를 찾는 방식
      //                     # Resource resource = new FileSystemResource({절대경로});
      Resource resource = new FileSystemResource("c:\\upload\\" + fileName);

      if (resource.exists() == false) {                  // 해당경로애 파일이이 있는지 확인
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);   // 파일이 없으면 상태정보를 리턴
      }

      String resourceName = resource.getFilename();   // 다운로드할 파일이름을 resourceName에 대입

      // 파일 다운로드시 indexOf("_")를 사용하여 원본의 파일명을 resourceOriginalName에 대입
      String resourceOriginalName = resourceName.substring(resourceName.indexOf("_") + 1);

      // 
      HttpHeaders headers = new HttpHeaders();
      try {
         // userAgent를 사용하여 IE버전 확인
         boolean checkIE = (userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("Trident") > -1);

         String downloadName = null;

         if (checkIE) {
            // URLEncoder은 URL상의 한글을 encoding 해준다.
            // 하지만 URL상에 +가 있으면 +기호 뒤의 URL은 encoding하지 않는다. (URLEncoder는 +기호를 공백으로 변환한다.)
            // replaceAll()를 사용하여 +기호를 \\+기호로 변환
            downloadName = URLEncoder.encode(resourceOriginalName, "UTF8").replaceAll("\\+", " ");
         } else {
            // getBytes() 메서드는 String(문자열)을 default charset으로 인코딩하여 byte 배열로 반환
            downloadName = new String(resourceOriginalName.getBytes("UTF-8"), "ISO-8859-1");
         }

         headers.add("Content-Disposition", "attachment; filename=" + downloadName);

      } catch (UnsupportedEncodingException e) {
         e.printStackTrace();
      }

      return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
   }
   
   // 파일 삭제
   // @PreAuthorize :  해당 메서드가 호출되기 이전에 검사(실제로 해당 메서드를 호출할 권한이 있는지를 확인)
   // isAuthenticated() : 로그인 유/무 확인
   @PreAuthorize("isAuthenticated()")
   @PostMapping("/deleteFile")
   @ResponseBody
   public ResponseEntity<String> deleteFile(String fileName, String type) {

      log.info("deleteFile: " + fileName);

      File file;

      try {
         // URLEncoder로로 인코딩된 결과를 반대로 디코딩한다.
         file = new File("c:\\upload\\" + URLDecoder.decode(fileName, "UTF-8"));

         file.delete();   // 파일삭제

         if (type.equals("image")) {
            // 썸네일을 삭제하기위해 s_가 포함된 파일명을 largeFileName에 대입
            String largeFileName = file.getAbsolutePath().replace("s_", "");

            log.info("largeFileName: " + largeFileName);

            file = new File(largeFileName);

            file.delete();   // 썸네일 파일 삭제
         }

      } catch (UnsupportedEncodingException e) {
         e.printStackTrace();
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }

      return new ResponseEntity<String>("deleted", HttpStatus.OK);

   }
   

}