# SpringBootApp
Application on File Metadata upload with Springboot

Pre-Requisites-

Set the below propertied in application.properties file, to specify the default path for file upload in system
file.upload.path=/Users/pink/uploads



1. Upload one or multiple files -
  http://localhost:8081/file-upload/api/uploadFiles
  
2. http://localhost:8081/file-upload/api/getAllFilesMetaData?fileName=AIT

3. http://localhost:8081/file-upload/api/getAllFilesMetaData

4. http://localhost:8081/file-upload/api/getAllFilesMetaData?fileId=fileId

5. http://localhost:8081/file-upload/api/getAllFilesMetaData?pageNo=0&pageSize=5&sortKey=crtTs&sortOrder=ASC&fileName=signed


http://localhost:8081/file-upload/swagger-ui.html(need more improvements to make it workable)
